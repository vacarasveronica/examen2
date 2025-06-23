package mpp.persistance.repository;



import mpp.model.User;
import mpp.persistance.UserRepoInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class UserDbRepo implements UserRepoInterface {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public UserDbRepo(Properties props) {
        logger.info("Initializing UtilizatorRepo with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public User findOne(Integer id) {
        logger.traceEntry("Finding user with id: {}", id);
        Connection con = dbUtils.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            preStmt.setInt(1, id);

            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    String alias = result.getString("alias");

                    User u = new User(alias);
                    u.setId(id);

                    logger.traceExit(u);
                    return u;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit("Participant not found");
        return null;
    }


    @Override
    public Iterable<User> findAll() {
        logger.traceEntry("Getting all users");
        Connection con = dbUtils.getConnection();
        List<User> utilizatrori = new ArrayList<>();

        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM users")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String alias = result.getString("alias");

                    User u = new User(alias);
                    u.setId(id);
                    utilizatrori.add(u);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit(utilizatrori);
        return utilizatrori;
    }

    @Override
    public User save(User entity) {
        logger.traceEntry("Saving user {}", entity);
        Connection con = dbUtils.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement(
                "INSERT INTO users (alias) VALUES (?)")) {
            preStmt.setString(1, entity.getAlias());

            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit();
        return null;
    }

    @Override
    public User delete(Integer id) {
        logger.traceEntry("Deleting user with id: {}", id);
        Connection con = dbUtils.getConnection();

        // Mai întâi, verificăm dacă participantul există
        User userToDelete = findOne(id);

        if (userToDelete == null) {
            logger.traceExit("user not found for deletion");
            return null;
        }

        try (PreparedStatement preStmt = con.prepareStatement("DELETE FROM users WHERE id = ?")) {
            preStmt.setInt(1, id);
            int result = preStmt.executeUpdate();

            if (result > 0) {
                logger.traceExit("Deleted user with id: {}", id);
                return userToDelete;
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        return null;
    }


    @Override
    public User update(User entity) {
        logger.traceEntry("Updating user with id {}", entity.getId());
        Connection con = dbUtils.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement(
                "UPDATE users SET alias=? WHERE id=?")) {
            preStmt.setString(1, entity.getAlias());
            preStmt.setInt(2, entity.getId());

            int result = preStmt.executeUpdate();
            logger.trace("Updated {} instances", result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        logger.traceExit();
        return null;
    }


}


