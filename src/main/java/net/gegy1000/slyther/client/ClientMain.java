package net.gegy1000.slyther.client;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.commons.io.IOUtils;

import net.gegy1000.slyther.Version;
import net.gegy1000.slyther.client.db.DatabaseImpl;
import net.gegy1000.slyther.client.db.GameStatistic;
import net.gegy1000.slyther.client.db.HibernateUtil;
import net.gegy1000.slyther.util.Log;
import net.gegy1000.slyther.util.OperatingSystem;
import net.gegy1000.slyther.util.SystemUtils;
import net.gegy1000.slyther.util.UIUtils;

public class ClientMain {
    private static final String NATIVES_DIR = "natives";

	public static void main(String[] args) throws Exception {
		parseParams(args);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		loadNatives();
		SlytherClient client = new SlytherClient();
		HibernateUtil.buildSessionFactory(SystemUtils.getGameFolder());
		client.database = new DatabaseImpl();
		try {
			client.gameStatistic = client.database.getMostRecentGame();
		} catch (ExceptionInInitializerError e) {
			JOptionPane.showMessageDialog(null, "Failed to initialize database.\nIs another copy of Slyther running?",
					"Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e1) {

		}
		if (client.gameStatistic == null)
			client.gameStatistic = new GameStatistic();
		client.run();
	}

    private static void loadNatives(){
        File folder = new File(SystemUtils.getGameFolder(), NATIVES_DIR);
        createNativesFolder(folder);
        OperatingSystem os = SystemUtils.getOS();
        if (os == OperatingSystem.WINDOWS) {
            if (!new File(folder.getPath() + "/jinput-dx8_64.dll").exists()) {
                extractFromClasspath("jinput-dx8_64.dll", folder);
                extractFromClasspath("jinput-dx8.dll", folder);
                extractFromClasspath("jinput-raw_64.dll", folder);
                extractFromClasspath("jinput-raw.dll", folder);
                extractFromClasspath("lwjgl.dll", folder);
                extractFromClasspath("lwjgl64.dll", folder);
                extractFromClasspath("OpenAL32.dll", folder);
                extractFromClasspath("OpenAL64.dll", folder);
            }
        } else if (os == OperatingSystem.SOLARIS) {
            if (!new File(folder.getPath() + "/liblwjgl.so").exists()) {
                extractFromClasspath("liblwjgl.so", folder);
                extractFromClasspath("liblwjgl64.so", folder);
                extractFromClasspath("libopenal.so", folder);
                extractFromClasspath("libopenal64.so", folder);
            }
        } else if (os == OperatingSystem.LINUX) {
            if (!new File(folder.getPath() + "/liblwjgl.so").exists()) {
                extractFromClasspath("liblwjgl.so", folder);
                extractFromClasspath("liblwjgl64.so", folder);
                extractFromClasspath("libopenal.so", folder);
                extractFromClasspath("libopenal64.so", folder);
            }
        } else if (os == OperatingSystem.MACOSX) {
            if (!new File(folder.getPath() + "/openal.dylib").exists()) {
                extractFromClasspath("liblwjgl.dylib", folder);
                extractFromClasspath("libjinput-osx.jnilib", folder);
                extractFromClasspath("openal.dylib", folder);
            }
        }
        System.setProperty("net.java.games.input.librarypath", folder.getAbsolutePath());
        System.setProperty("org.lwjgl.librarypath", folder.getAbsolutePath());
    }

    private static void createNativesFolder(File folder) {
        if (folder.exists() && !folder.isDirectory()) {
            Desktop desktop = Desktop.getDesktop();
            JButton browseFolder;
            JButton ok = new JButton(UIManager.getString("OptionPane.okButtonText"));
            Object[] options;
            if (desktop.isSupported(Action.BROWSE)) {
                browseFolder = new JButton("Browse Folder");
                options = new Object[] { browseFolder, ok };
            } else {
                browseFolder = null;
                options = new Object[] { ok };
            }
            JOptionPane msg = new JOptionPane(
                    "We must create this folder \"" + folder + "\" and there is a file called natives in its place.\n"+
                    "Rename or move at this time or it will be deleted. Resistance is futile.",
                    JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null,
                    options
            );
            if (browseFolder != null) {
                browseFolder.addActionListener(action -> {
                    try {
                        Desktop.getDesktop().browse(folder.getParentFile().toURI());
                    } catch (Exception e) {
                        UIUtils.displayException("Unable to browse folder", e);
                    }
                });
            }
            ok.addActionListener(action -> msg.setValue(0));
            JDialog dialog = msg.createDialog("Slyther");
            try (InputStream icon = ClientMain.class.getResourceAsStream("/textures/icon_32.png")) {
                dialog.setIconImage(ImageIO.read(icon)); 
            } catch (IOException e) {}
            dialog.setVisible(true);
            dialog.dispose();
            folder.delete();
        }
        folder.mkdirs();
    }

    private static void extractFromClasspath(String fileName, File folder) {
        FileOutputStream out = null;
        InputStream fin = null;
        try {
            out = new FileOutputStream(new File(folder, fileName));
            fin = ClientMain.class.getResourceAsStream("/" + fileName);
            IOUtils.copy(fin, out);
        } catch (IOException e) {
            UIUtils.displayException("Failed to extract file", e);
        } finally {
            if (fin != null) {
            	try {
					fin.close();
				} catch (IOException e) {}
            }
        	if (out != null) {
	            try {
					out.close();
				} catch (IOException e) {}
        	}
        }
    }
    
    private static void parseParams(String[] args) {
    	for (int i=0; i<args.length; i++) {
    		if (args[i].equals("-d")) {
   				Log.showDebug = true;
    		} else if (args[i].equals("-g")) {
    			if (i+1 < args.length) {
    				SystemUtils.setGameFolder(args[i+1]);
    				i++;
    			} else {
    				System.err.format("-g requires a directory option");
    			}
    		} else if (args[i].equals("-v")) {
    			System.out.println("Slyther version: " + Version.SlytherVersion);
    			System.exit(1);
    		}
    	}
    }
}
