/**
 * 
 */
package net.gegy1000.slyther.client.recording;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.io.filefilter.WildcardFileFilter;

import net.gegy1000.slyther.client.ClientConfig;
import net.gegy1000.slyther.network.MessageByteBuffer;
import net.gegy1000.slyther.util.Log;
import net.gegy1000.slyther.util.SystemUtils;

/** Replay Manager<br>
 * <ul>
 * <li>Determine new Replay filenames
 * <li>Delete old Replays
 * <li>Parse existing Replay files
 * </ul>
 * @author dick
 *
 */
public enum ReplayMan {
	INSTANCE;
	public List<Replay>	replayFiles;
	
	public File getNewReplayFile(ClientConfig config) {
		GregorianCalendar cal = new GregorianCalendar();
		String s = String.format("slyther.%1$tY%1$tm%1$td%1$tH%1$tM%1$ts.record", cal);
		maybeDeleteReplayFiles(config);
		File f = new File(SystemUtils.getReplayFolder(), s);
		try {
			f.createNewFile();
		} catch (IOException e) {
			Log.catching(e);
			e.printStackTrace();
			return(null);
		}
		return(f);
	}

	public void getReplayFiles() {
		List<Replay> list = new ArrayList<>();
		final char[] SLYT = new char[] { 'S', 'L', 'Y', 'T'};
		File dir = SystemUtils.getReplayFolder();
		FileFilter ff = new WildcardFileFilter("*.record");
		File[] flist = dir.listFiles(ff);
		byte[] sig = new byte[6];
		MessageByteBuffer bb;
		for (File f : flist) {
			RandomAccessFile raf;
			try {
				raf = new RandomAccessFile(f, "r");
			} catch (FileNotFoundException e) {
				Log.catching(e);
				continue;
			}
			try {
				long flen = raf.length();
				raf.seek(flen-6);
				int bread = raf.read(sig);
				if (bread != 6)
					throw new Exception("Failed to read signature");
				boolean gotSig = true;
				bb = new MessageByteBuffer(sig);
				for (char c : SLYT) {
					int cc = bb.readUInt8();
					if (c != cc) {
						Log.error("getReplayFiles: Mismatched sig expected {} got {}", c, cc);
						Log.error("file: {}", f.getPath());
						try {
							raf.close();
						} catch (Exception e) {
							Log.catching(e);
						}
						gotSig = false;
						break;
					}
				}
				if (gotSig) {
					int datalen = bb.readUInt16();
					raf.seek(flen-datalen-2);
					byte[] data = new byte[datalen];
					raf.read(data);
					bb = new MessageByteBuffer(data);
					Replay r = new Replay();
					r.setPathname(f.getAbsolutePath());
					r.setGamedate(new Date(bb.readInt64()));
					r.setDuration(bb.readInt32());
					r.setKills(bb.readInt32());
					r.setLength(bb.readInt32());
					r.setRank(bb.readInt32());
					list.add(r);
				}
			} catch (Exception e) {
				Log.error("getReplayFiles: error {}", e.getLocalizedMessage());
				Log.error("file: {}", f.getPath());
				Log.catching(e);
			} finally {
				try {
					raf.close();
				} catch (IOException e1) {}
				
			}
			
		}
		list.sort(null);
		replayFiles = list;
	}
	
	private void maybeDeleteReplayFiles(ClientConfig config) {
		getReplayFiles();

		for (String s : config.replaysToKeep) {
			ListIterator<Replay> iter = replayFiles.listIterator();
			while (iter.hasNext()) {
				Replay r = iter.next();
				if (r.getPathname().equals(s)) {
					iter.remove();
					break;
				}
			}
		}
		for (int i = config.numReplaysToKeep-1; i<replayFiles.size(); i++) {
			Replay r = replayFiles.get(i);
			File f = new File(r.getPathname());
			f.delete();
		}
	}
}
