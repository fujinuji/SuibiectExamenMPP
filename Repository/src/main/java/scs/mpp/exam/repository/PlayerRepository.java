package scs.mpp.exam.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import scs.mpp.exam.entites.Player;

import java.util.List;

public class PlayerRepository {
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

    public PlayerRepository() {
        initialiseSessionFactory();
        session = sessionFactory.openSession();
    }

    public Player checkPlayer(String userName, String password) throws Exception {
        String hql = "FROM Player E WHERE E.name = :user_name AND E.password=:password";
        Query query = session.createQuery(hql);
        query.setParameter("user_name", userName);
        query.setParameter("password", password);
        List result = query.list();

        if (result.isEmpty()) {
            throw new Exception("User not found");
        }

        return (Player) result.get(0);
    }
}
