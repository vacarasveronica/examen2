package mpp.persistance.repository;

import mpp.model.Configuratie;
import mpp.persistance.ConfiguratieRepoInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ConfiguratieDbRepo implements ConfiguratieRepoInterface {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Configuratie findOne(Integer id) {
        logger.traceEntry("Finding configuratie with id: {}", id);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Configuratie configuratie = session.get(Configuratie.class, id);
            logger.traceExit(configuratie);
            return configuratie;
        } catch (Exception e) {
            logger.error("Error finding configuratie with id: " + id, e);
            return null;
        }
    }

    @Override
    public Iterable<Configuratie> findAll() {
        logger.traceEntry("Finding all configuratii");
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            List<Configuratie> configuratii = session.createQuery("from Configuratie", Configuratie.class).list();
            logger.traceExit(configuratii);
            return configuratii;
        } catch (Exception e) {
            logger.error("Error finding all configuratii", e);
            return List.of();
        }
    }

    @Override
    public Configuratie save(Configuratie entity) {
        logger.traceEntry("Saving configuratie {}", entity);
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
            logger.traceExit(entity);
            return entity;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            logger.error("Error saving configuratie", e);
            return null;
        }
    }

    @Override
    public Configuratie delete(Integer id) {
        logger.traceEntry("Deleting configuratie with id: {}", id);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Configuratie c = session.get(Configuratie.class, id);
            if (c != null) {
                Transaction tx = session.beginTransaction();
                session.remove(c);
                tx.commit();
                logger.traceExit(c);
                return c;
            }
            logger.info("Configuratie with id {} not found", id);
            return null;
        } catch (Exception e) {
            logger.error("Error deleting configuratie with id: " + id, e);
            return null;
        }
    }

    @Override
    public Configuratie update(Configuratie entity) {
        logger.traceEntry("Updating configuratie {}", entity);
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
            logger.traceExit(entity);
            return entity;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            logger.error("Error updating configuratie", e);
            return null;
        }
    }
}
