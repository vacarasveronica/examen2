package mpp.client.gui;

import javafx.fxml.FXMLLoader;
import mpp.model.*;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import mpp.services.AppException;
import mpp.services.IServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField aliasField;

    @FXML
    private Label errorMessageLabel;

    private FadeTransition fadeOut;
    private MainController mainController;
    private User loggedUser;
    private IServices server;
    private Stage stage;
    private Parent mainParent;

    public void setServer(IServices server) {
        this.server = server;
    }
    public void setParent(Parent parent){
        mainParent = parent;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private static final Logger logger= LogManager.getLogger();

    @FXML
    private void handleLogin(ActionEvent actionEvent) {
        logger.traceEntry("handleLogin");
        String alias = aliasField.getText().trim();
        if (alias.isEmpty() ) {
            showErrorAlert("Completati toate campurile!","error-label");
            return;
        }

        if (authenticate(alias)) {
            logger.traceExit("login successful");
            openMainWindow();
        } else {
            logger.traceExit("login failed");
            showErrorAlert("Utilizator sau parola incorecta!","error-label");
        }
    }

    private boolean authenticate(String username) {
        // Rulează autentificarea pe un thread de fundal
        logger.traceEntry("authenticate");
        try {
            Iterable<User> users = server.findAllUser();
            for (User u : users) {
                if (u.getAlias().equals(username)) {
                    logger.info("a gasit ceva");
                    loggedUser = u;
                    return true;
                }
            }
        } catch (IOException | InterruptedException | AppException e) {
            e.printStackTrace();
        }
        logger.traceExit("authenticate failed");
        return false;
    }

    private void openMainWindow() {

        Platform.runLater(() -> {
            try {
                System.out.println("Deschid MainWindow...");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
                // Verifică dacă resursa este validă
                if (loader.getLocation() == null) {
                    System.out.println("Fișierul Main.fxml nu a fost găsit!");
                    return;
                }

                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Joc");
                stage.setScene(new Scene(root));


                MainController mainController = loader.getController();
                mainController.setServer(server);
                mainController.setLoggedUser(loggedUser);
                server.login(loggedUser.getAlias(),mainController);
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        mainController.logout();
                        System.exit(0);
                    }
                });

                stage.show();
                System.out.println("Fereastra principală deschisă cu succes.");
                Stage loginStage = (Stage) aliasField.getScene().getWindow();
                loginStage.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (AppException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void showErrorAlert(String message, String styleClass) {
        errorMessageLabel.setText(message);
        errorMessageLabel.getStyleClass().setAll(styleClass);
        errorMessageLabel.setVisible(true);

        fadeOut = new FadeTransition(Duration.seconds(2), errorMessageLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }
}
