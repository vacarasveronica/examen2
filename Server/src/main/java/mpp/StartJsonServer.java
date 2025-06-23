package mpp;

import mpp.persistance.ConfiguratieRepoInterface;
import mpp.persistance.JocRepoInterface;
import mpp.persistance.PozitieRepoInterface;
import mpp.persistance.UserRepoInterface;
import mpp.persistance.repository.ConfiguratieDbRepo;
import mpp.persistance.repository.JocDbRepo;
import mpp.persistance.repository.PozitieDbRepo;
import mpp.persistance.repository.UserDbRepo;
import mpp.server.ServicesImpl;
import mpp.network.utils.AbstractServer;
import mpp.network.utils.ServerException;
import mpp.network.utils.JsonConcurrentServer;
// TODO 1: IMPORT USED REPOSITORIES
import mpp.services.IServices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class StartJsonServer {

    private static final Logger logger = LogManager.getLogger(StartJsonServer.class);

    private static int defaultPort = 55555;
    public static void main(String[] args) {

        logger.info("Server starting ...");

        Properties serverProps = new Properties();
        try {
            serverProps.load(StartJsonServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties "+e);
            return;
        }
        UserRepoInterface userRepo = new UserDbRepo(serverProps);
        ConfiguratieRepoInterface confRepo = new ConfiguratieDbRepo();
        JocRepoInterface jocRepo = new JocDbRepo(serverProps);
        PozitieRepoInterface pozRepo = new PozitieDbRepo();
        IServices serverImpl = new ServicesImpl(userRepo,confRepo,jocRepo,pozRepo);
        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(serverProps.getProperty("server.port"));
        } catch (NumberFormatException nef){
            System.err.println("Wrong Port Number " + nef.getMessage());
            System.err.println("Using default port "+ defaultPort);
        }
        System.out.println("Starting server on port: " + serverPort);
        AbstractServer server = new JsonConcurrentServer(serverPort, serverImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        } finally {
            try {
                server.stop();
            } catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }
    }
}
