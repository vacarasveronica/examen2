package mpp.persistance.repository;


import mpp.model.Configuratie;
import mpp.model.Joc;
import mpp.model.Pozitie;
import mpp.model.User;
import mpp.persistance.JocRepoInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JocDbRepo implements JocRepoInterface {

    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public JocDbRepo(Properties props) {
        logger.info("Initializing JocDbRepo with properties: {}", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Joc findOne(Integer id) {
        logger.traceEntry("Finding game with id: {}", id);
        Connection con = dbUtils.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement(
                "SELECT J.id, J.nrPuncte, J.secunde, J.pozPropuse, " +
                        "U.id as userId, U.alias, " +
                        "C.id as configuratieId, " +
                        "P1.id AS p1id, P1.coordonataX AS x1, P1.coordonataY AS y1, " +
                        "P2.id AS p2id, P2.coordonataX AS x2, P2.coordonataY AS y2, " +
                        "P3.id AS p3id, P3.coordonataX AS x3, P3.coordonataY AS y3, " +
                        "P4.id AS p4id, P4.coordonataX AS x4, P4.coordonataY AS y4, " +
                        "P5.id AS p5id, P5.coordonataX AS x5, P5.coordonataY AS y5 " +
                        "FROM joc J " +
                        "INNER JOIN users U ON J.userId = U.id " +
                        "INNER JOIN configuratie C ON J.configuratieId = C.id "+
                        "INNER JOIN pozitie P1 ON C.pozitieId1 = P1.id " +
                        "INNER JOIN pozitie P2 ON C.pozitieId2 = P2.id " +
                        "INNER JOIN pozitie P3 ON C.pozitieId3 = P3.id " +
                        "INNER JOIN pozitie P4 ON C.pozitieId4 = P4.id " +
                        "INNER JOIN pozitie P5 ON C.pozitieId5 = P5.id " +
                        "WHERE J.id = ?")) {

            preStmt.setInt(1, id);
            try (ResultSet rs = preStmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(rs.getString("alias"));
                    user.setId(rs.getInt("userId"));

                    Pozitie p1 = new Pozitie(rs.getInt("x1"), rs.getInt("y1"));
                    p1.setId(rs.getInt("p1id"));
                    Pozitie p2 = new Pozitie(rs.getInt("x2"), rs.getInt("y2"));
                    p2.setId(rs.getInt("p2id"));
                    Pozitie p3 = new Pozitie(rs.getInt("x3"), rs.getInt("y3"));
                    p3.setId(rs.getInt("p3id"));
                    Pozitie p4 = new Pozitie(rs.getInt("x4"), rs.getInt("y4"));
                    p4.setId(rs.getInt("p4id"));
                    Pozitie p5 = new Pozitie(rs.getInt("x5"), rs.getInt("y5"));
                    p5.setId(rs.getInt("p5id"));

                    Configuratie configuratie = new Configuratie(p1, p2, p3, p4, p5);
                    configuratie.setId(rs.getInt("configuratieId"));

                    Joc joc = new Joc(
                            user,
                            configuratie,
                            rs.getInt("nrPuncte"),
                            rs.getInt("secunde"),
                            rs.getString("pozPropuse")
                    );
                    joc.setId(rs.getInt("id"));

                    logger.traceExit(joc);
                    return joc;
                }
            }

        } catch (SQLException e) {
            logger.error("Error DB", e);
        }

        logger.traceExit("game not found");
        return null;
    }

    @Override
    public Iterable<Joc> findAll() {
        logger.traceEntry();
        List<Joc> jocuri = new ArrayList<>();
        Connection con = dbUtils.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement(
                "SELECT J.id, J.nrPuncte, J.secunde, J.pozPropuse, " +
                        "U.id as userId, U.alias, " +
                        "C.id as configuratieId, " +
                        "P1.id AS p1id, P1.coordonataX AS x1, P1.coordonataY AS y1, " +
                        "P2.id AS p2id, P2.coordonataX AS x2, P2.coordonataY AS y2, " +
                        "P3.id AS p3id, P3.coordonataX AS x3, P3.coordonataY AS y3, " +
                        "P4.id AS p4id, P4.coordonataX AS x4, P4.coordonataY AS y4, " +
                        "P5.id AS p5id, P5.coordonataX AS x5, P5.coordonataY AS y5 " +
                        "FROM joc J " +
                        "INNER JOIN users U ON J.userId = U.id " +
                        "INNER JOIN configuratie C ON J.configuratieId = C.id "+
                        "INNER JOIN pozitie P1 ON C.pozitieId1 = P1.id " +
                        "INNER JOIN pozitie P2 ON C.pozitieId2 = P2.id " +
                        "INNER JOIN pozitie P3 ON C.pozitieId3 = P3.id " +
                        "INNER JOIN pozitie P4 ON C.pozitieId4 = P4.id " +
                        "INNER JOIN pozitie P5 ON C.pozitieId5 = P5.id ")) {

            try (ResultSet rs = preStmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User(rs.getString("alias"));
                    user.setId(rs.getInt("userId"));

                    Pozitie p1 = new Pozitie(rs.getInt("x1"), rs.getInt("y1"));
                    p1.setId(rs.getInt("p1id"));
                    Pozitie p2 = new Pozitie(rs.getInt("x2"), rs.getInt("y2"));
                    p2.setId(rs.getInt("p2id"));
                    Pozitie p3 = new Pozitie(rs.getInt("x3"), rs.getInt("y3"));
                    p3.setId(rs.getInt("p3id"));
                    Pozitie p4 = new Pozitie(rs.getInt("x4"), rs.getInt("y4"));
                    p4.setId(rs.getInt("p4id"));
                    Pozitie p5 = new Pozitie(rs.getInt("x5"), rs.getInt("y5"));
                    p5.setId(rs.getInt("p5id"));

                    Configuratie configuratie = new Configuratie(p1, p2, p3, p4, p5);
                    configuratie.setId(rs.getInt("configuratieId"));

                    Joc joc = new Joc(
                            user,
                            configuratie,
                            rs.getInt("nrPuncte"),
                            rs.getInt("secunde"),
                            rs.getString("pozPropuse")
                    );
                    joc.setId(rs.getInt("id"));

                    jocuri.add(joc);
                }
            }

        } catch (SQLException e) {
            logger.error("Error DB", e);
        }

        logger.traceExit(jocuri);
        return jocuri;
    }

    @Override
    public Joc save(Joc entity) {
        logger.traceEntry("Saving game {}", entity);
        Connection con = dbUtils.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement(
                "INSERT INTO joc (userId, configuratieId, nrPuncte, secunde, pozPropuse) VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            preStmt.setInt(1, entity.getUser().getId());
            preStmt.setInt(2, entity.getConfiguratie().getId());
            preStmt.setInt(3, entity.getNrPuncte());
            preStmt.setInt(4, entity.getSecunde());
            preStmt.setString(5, entity.getPozPropuse());

            preStmt.executeUpdate();

            try (ResultSet rs = preStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    entity.setId(id);
                    logger.trace("Saved game with ID: {}", id);
                    return entity;
                }
            }

        } catch (SQLException e) {
            logger.error("Error saving joc", e);
        }

        logger.traceExit();
        return null;
    }

    @Override
    public Joc delete(Integer id) {
        logger.traceEntry("Deleting game with id: {}", id);
        Joc joc = findOne(id);
        if (joc == null) return null;

        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("DELETE FROM joc WHERE id = ?")) {
            preStmt.setInt(1, id);
            preStmt.executeUpdate();
            logger.traceExit(joc);
            return joc;
        } catch (SQLException e) {
            logger.error("Error deleting joc", e);
        }

        return null;
    }

    @Override
    public Joc update(Joc entity) {
        logger.traceEntry("Updating game {}", entity);
        Connection con = dbUtils.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement(
                "UPDATE joc SET userId = ?, configuratieId = ?, nrPuncte = ?, secunde = ?, pozPropuse = ? WHERE id = ?")) {

            preStmt.setInt(1, entity.getUser().getId());
            preStmt.setInt(2, entity.getConfiguratie().getId());
            preStmt.setInt(3, entity.getNrPuncte());
            preStmt.setInt(4, entity.getSecunde());
            preStmt.setString(5, entity.getPozPropuse());
            preStmt.setInt(6, entity.getId());

            preStmt.executeUpdate();
            logger.traceExit(entity);
            return entity;

        } catch (SQLException e) {
            logger.error("Error updating joc", e);
        }

        return null;
    }
}
