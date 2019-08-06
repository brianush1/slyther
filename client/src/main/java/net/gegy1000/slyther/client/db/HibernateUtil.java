package net.gegy1000.slyther.client.db;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	public static SessionFactory buildSessionFactory(File gameFolder) {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			Configuration c = new Configuration().configure();
			if (gameFolder != null)
				c.setProperty("hibernate.connection.url", "jdbc:h2:" + gameFolder.getPath() + File.separator + "statistics");
			sessionFactory = c.buildSessionFactory();
			return(sessionFactory);
		}
		catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}