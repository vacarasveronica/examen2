package mpp.persistance.repository;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Configuration o să caute automat hibernate.properties în resources
            return new Configuration()
                    //.addAnnotatedClass(mpp.model.Configuratie.class)
                   // .addAnnotatedClass(mpp.model.Pozitie.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}






