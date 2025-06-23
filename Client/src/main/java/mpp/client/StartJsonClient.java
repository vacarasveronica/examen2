package mpp.client;

import mpp.client.gui.LoginController;
import mpp.client.gui.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mpp.network.jsonprotocol.ServicesJsonProxy;
import mpp.services.IServices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class StartJsonClient extends Application {
    private static final Logger logger = LogManager.getLogger(StartJsonClient.class);
    private Stage primaryStage;

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";


    public void start(Stage primaryStage) throws Exception {
        logger.info("In start");
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartJsonClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IServices server = new ServicesJsonProxy(serverIP, serverPort);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogIn.fxml"));
        Parent root1 = loader.load();

        // Obține controller-ul și setează serverul
        LoginController loginController = loader.getController();
        loginController.setServer(server);

        // Configurează fereastra
        primaryStage.setScene(new Scene(root1, 300, 200));
        primaryStage.setTitle("Login");
        primaryStage.show();
    }
}
