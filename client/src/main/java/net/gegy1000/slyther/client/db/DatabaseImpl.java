package net.gegy1000.slyther.client.db;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<GameStatistic> getGames() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction t = session.beginTransaction();
		List<GameStatistic> gsl = null;
		try {
			@SuppressWarnings("unchecked")
			Query<GameStatistic> query = session.createQuery("FROM GameStatistic ORDER BY GAMEDATE DESC");
			query.setMaxResults(100);
			gsl = query.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			return(null);
		}
		t.commit();
		return(gsl);
	}

	/* (non-Javadoc)
	 * @see net.gegy1000.slyther.client.db.Database#getMostRecentGame()
	 */
	@Override
	public GameStatistic getMostRecentGame() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction t = session.beginTransaction();
		GameStatistic gs = null;
		try {
			@SuppressWarnings("unchecked")
			Query<GameStatistic> query = session.createQuery("FROM GameStatistic ORDER BY GAMEDATE DESC");
			query.setFirstResult(0);
			query.setMaxResults(1);
			gs = query.uniqueResult();
			
		} catch (Exception e) {
			e.printStackTrace();
			return(null);
		}
		t.commit();
		return(gs);
		
	}

	
}
