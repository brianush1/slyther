package net.gegy1000.slyther.client.db;

import java.util.List;

import org.hibernate.Session;

public class DatabaseImpl implements Database {

	public void	init() {
		
	}

	@Override
	public void addGame(GameStatistic game) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(game);
        session.getTransaction().commit();
	}

	@Override
	public List<GameStatistic> getGames() {
		// TODO Auto-generated method stub
		return null;
	}

}
