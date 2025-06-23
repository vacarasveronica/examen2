package mpp.persistance.repository;

import mpp.model.Pozitie;
import mpp.persistance.PozitieRepoInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PozitieDbRepo implements PozitieRepoInterface {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Pozitie findOne(Integer id) {
        logger.traceEntry("Finding pozitie with id: {}", id);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Pozitie pozitie = session.get(Pozitie.class, id);
            logger.traceExit(pozitie);
            return pozitie;
        } catch (Exception e) {
            logger.error("Error finding pozitie with id: " + id, e);
            return null;
        }
    }

    @Override
    public Iterable<Pozitie> findAll() {
        logger.traceEntry("Finding all pozitii");
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            List<Pozitie> pozitii = session.createQuery("from Pozitie", Pozitie.class).list();
            logger.traceExit(pozitii);
            return pozitii;
        } catch (Exception e) {
            logger.error("Error finding all pozitii", e);
            return List.of();
        }
    }

    @Override
    public Pozitie save(Pozitie entity) {
        logger.traceEntry("Saving pozitie {}", entity);
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
            logger.traceExit(entity);
            return entity;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            logger.error("Error saving pozitie", e);
            return null;
        }
    }

    @Override
    public Pozitie delete(Integer id) {
        logger.traceEntry("Deleting pozitie with id: {}", id);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Pozitie pozitie = session.get(Pozitie.class, id);
            if (pozitie != null) {
                Transaction tx = session.beginTransaction();
                session.remove(pozitie);
                tx.commit();
                logger.traceExit(pozitie);
                return pozitie;
            }
            logger.info("Pozitie with id {} not found", id);
            return null;
        } catch (Exception e) {
            logger.error("Error deleting pozitie with id: " + id, e);
            return null;
        }
    }

    @Override
    public Pozitie update(Pozitie entity) {
        logger.traceEntry("Updating pozitie {}", entity);
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
            logger.traceExit(entity);
            return entity;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            logger.error("Error updating pozitie", e);
            return null;
        }
    }
}
