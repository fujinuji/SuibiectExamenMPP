package scs.mpp.exam.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import scs.mpp.exam.entites.Game;

import java.util.List;

public class GameRepository {
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

    public GameRepository() {
        initialiseSessionFactory();
        session = sessionFactory.openSession();
    }

    public void saveGame(Game game) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(game);
        session.getTransaction().commit();
        session.close();
    }

    public Game getById(String id) throws Exception {
        String hql = "FROM Game E WHERE E.id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id",id);
        List result = query.list();

        if (result.isEmpty()) {
            throw new Exception("Game not found");
        }

        return (Game) result.get(0);
    }
}
