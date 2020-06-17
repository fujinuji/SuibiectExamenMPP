package scs.mpp.exam.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import scs.mpp.exam.entites.Round;

import java.util.List;

public class RoundRepository  {
    private static SessionFactory sessionFactory;
    private static Session session;

    private static void initialiseSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    private static void closeSessionFactory() {
        if (sessionFactory != null)
            sessionFactory.close();
    }

    public RoundRepository() {
        initialiseSessionFactory();
        session = sessionFactory.openSession();
    }

    public void saveRound(Round game) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(game);
        session.getTransaction().commit();
        session.close();
    }

    public List<Round> getByPlayerAndGame(String player, Integer gameId) throws Exception {
        String hql = "FROM Round E WHERE E.gameId = :gagemId AND E.playerName=:player";
        Query query = session.createQuery(hql);
        query.setParameter("gagemId",gameId);
        query.setParameter("player",player);
        List result = query.list();

        if (result.isEmpty()) {
            throw new Exception("Round not found");
        }

        return (List<Round>) result.get(0);
    }
}
