package mpp.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
// TODO 1: IMPORT USED MODELS
import mpp.model.User;
import mpp.services.IObserver;
import mpp.services.IServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, IObserver {
    @FXML
    private Text managerNameText;
    @FXML
    private TextField destinationField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField hourField;
    @FXML
    private Button searchButton;
    @FXML
    private TextField clientNameField;

    @FXML
    private VBox firstVBox;
    @FXML
    private VBox secondVBox;
    @FXML
    private VBox thirdVBox;
    @FXML
    private VBox fourthVBox;


    private IServices server;
    private User loggedUser;
    private static final Logger logger= LogManager.getLogger();

    public MainController() {
        System.out.println("MainController constructor");
    }

    public MainController(IServices server) {
        this.server = server;
        System.out.println("MainController constructor with server parameters");
    }

    public void setServer(IServices server) {
        this.server = server;
        //initModel();
    }


    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
        initModel();
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {

    }



    private void initModel() {

    }

    /* TODO 2: IMPLEMENT THE OBSERVER METHODS
    public void updateTable() throws AppException, IOException, InterruptedException {
        loadTableData();
        System.out.println("Tabelul a fost actualizat.");
    }

    @Override
    public void gameAdded(Joc j) throws InterruptedException {
        Platform.runLater(() -> {
            try {
                updateTable(); // Actualizează tabelul când un participant este adăugat
            } catch (AppException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
     */

    @FXML
    private void handleExit() {
        Stage stage = (Stage) searchButton.getScene().getWindow();
        stage.close();
    }
    public void handleLogout(ActionEvent actionEvent) {
        logout();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    void logout() {
//        try {
//            server.logout(loggedUser, this);
//            System.out.println("Logout successful (MainController).");
//        } catch (AppException e) {
//            System.out.println("Logout failed (MainController). " + e);
//        }
    }
}
