package mpp.client.gui;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
// TODO 1: IMPORT USED MODELS
import mpp.model.*;
import mpp.services.AppException;
import mpp.services.IObserver;
import mpp.services.IServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable, IObserver {
    @FXML
    private Label feedbackLabel;
    @FXML
    private GridPane gridPane;
    @FXML private TableView<MainDTO> tabelClasament;
    @FXML private TableColumn<MainDTO, String> colAlias;
    @FXML private TableColumn<MainDTO, Integer> colNrPuncte;
    @FXML private TableColumn<MainDTO, Integer> colSecunde;

    private ObservableList<MainDTO> listaEntitati = FXCollections.observableArrayList();

    private IServices server;

    //date pentru joc
    private User loggedUser;
    private Button[][] butoane = new Button[4][4];
    private LocalDateTime timpInceput;
    private LocalDateTime timpSfarsit;
    private Integer nrPuncte = 0;
    private String pozPropuse = "";
    private Joc joc;
    private Configuratie configuratieJoc;
    List<Pozitie> pozitii = new ArrayList<>();
    Configuratie confFinala;


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
    }


    public void setLoggedUser(User loggedUser) throws AppException, IOException, InterruptedException {
        this.loggedUser = loggedUser;
        initModel();
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        initGrid();
        colAlias.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAlias().toString())
        );

        colNrPuncte.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getNrPuncte()).asObject()
        );
        colSecunde.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getSecunde()).asObject()
        );

        tabelClasament.setItems(listaEntitati);

    }
    private void initGrid() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Button btn = new Button();
                btn.setPrefSize(80, 80);
                int finalI = i, finalJ = j;
                btn.setOnAction(e -> {
                    try {
                        handlePozitieClick(finalI, finalJ);
                    } catch (AppException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                butoane[finalI][finalJ] = btn;
                gridPane.add(btn, j, i);
            }
        }
    }

    public void handlePozitieClick(int row, int col) throws AppException, IOException, InterruptedException {
        pozPropuse += "("+ String.valueOf(row) + "," + String.valueOf(col) + ") ";
        if(row == confFinala.getPozitie1().getCoordonataX() && col == confFinala.getPozitie1().getCoordonataY()
            || row == confFinala.getPozitie2().getCoordonataX() && col ==confFinala.getPozitie2().getCoordonataY()
                || row == confFinala.getPozitie3().getCoordonataX() && col == confFinala.getPozitie3().getCoordonataY()
                || row == confFinala.getPozitie4().getCoordonataX() && col == confFinala.getPozitie4().getCoordonataY()
                || row == confFinala.getPozitie5().getCoordonataX() && col == confFinala.getPozitie5().getCoordonataY()
        ){
            endGame("Ai pierdut:(");
        }
        else{
            nrPuncte += row + 1;
            for(int i =0;i<4;i++){
                Button btn = butoane[row][i];
                btn.setDisable(true);
            }
            if(row == 3){
                endGame("Felicitari, ai castigat!");
            }
        }
    }

    private void endGame(String message) throws AppException, IOException, InterruptedException {
        timpSfarsit = LocalDateTime.now();
        Integer secunde = Math.toIntExact(Duration.between(timpInceput, timpSfarsit).getSeconds());
        joc.setSecunde(secunde);
        joc.setPozPropuse(pozPropuse);
        joc.setNrPuncte(nrPuncte);
        server.saveJoc(joc);

        feedbackLabel.setText("Ai obtinut scorul de: " + joc.getNrPuncte().toString());
        for(int i = 0; i<4; i++)
            for(int j = 0; j<4; j++){
                if (i == confFinala.getPozitie1().getCoordonataX() && j == confFinala.getPozitie1().getCoordonataY()
                        || i == confFinala.getPozitie2().getCoordonataX() && j == confFinala.getPozitie2().getCoordonataY()
                        || i == confFinala.getPozitie3().getCoordonataX() && j == confFinala.getPozitie3().getCoordonataY()
                        || i == confFinala.getPozitie4().getCoordonataX() && j == confFinala.getPozitie4().getCoordonataY()
                        || i == confFinala.getPozitie5().getCoordonataX() && j == confFinala.getPozitie5().getCoordonataY()
                ) {
                    Button btn = butoane[i][j];
                    btn.setText("Groapa");
                }
            }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over!");
        alert.setHeaderText(message);
        List<Joc> jocuri = new ArrayList<>();
        for(Joc j : server.findAllJoc())
            jocuri.add(j);

        jocuri.sort((j1, j2) -> {
            if (!j1.getNrPuncte().equals(j2.getNrPuncte()))
                return Integer.compare(j2.getNrPuncte(), j1.getNrPuncte());
            return Integer.compare(j2.getSecunde(), j1.getSecunde());
        });

        // Căutăm poziția jocului curent în listă pe baza punctelor și secundelor
        int pozitie = 1;
        for (Joc j : jocuri) {
            if (j.getNrPuncte().equals(joc.getNrPuncte()) &&
                    j.getSecunde().equals(joc.getSecunde())) {
                break;
            }
            pozitie++;
        }
        String content = "Your Position in Leaderboard: " + pozitie;

        alert.setContentText(content);
        alert.showAndWait();
    }



    private void initModel() throws AppException, IOException, InterruptedException {
        try{
            Iterable<Pozitie> poz = server.findAllPozitii();
            for(Pozitie p : poz){
                pozitii.add(p);
            }

            List<Pozitie> pozitiiSelectate = new ArrayList<>();

            for(int i = 0; i <4; i++){
                List<Pozitie> listaPtGenerareGropi = new ArrayList<>();
                for(Pozitie p : pozitii){
                    if(p.getCoordonataX().equals(i)){
                        listaPtGenerareGropi.add(p);
                    }
                }
                Collections.shuffle(listaPtGenerareGropi);
                Pozitie pozAleasa = listaPtGenerareGropi.get(0);
                pozitiiSelectate.add(pozAleasa);
                if(i == 3){
                    pozitiiSelectate.add(listaPtGenerareGropi.get(1));
                }
            }

            configuratieJoc = new Configuratie();
            configuratieJoc.setPozitie1(pozitiiSelectate.get(0));
            configuratieJoc.setPozitie2(pozitiiSelectate.get(1));
            configuratieJoc.setPozitie3(pozitiiSelectate.get(2));
            configuratieJoc.setPozitie4(pozitiiSelectate.get(3));
            configuratieJoc.setPozitie5(pozitiiSelectate.get(4));

            confFinala = server.saveConfiguratie(configuratieJoc);

        } catch (AppException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        timpInceput = LocalDateTime.now();
        joc = new Joc(loggedUser,confFinala,0,0,"");
        loadTableData();
    }

    private void loadTableData() throws AppException, IOException, InterruptedException {
        Iterable<Joc> j = server.findAllJoc();
        List<Joc> jocuri = new ArrayList<>();
        for(Joc joc : j) {
            jocuri.add(joc);
        }
        List<MainDTO> lisa = new ArrayList<>();
        MainDTO newJoc;
        for (Joc joc : jocuri) {

            newJoc = new MainDTO(joc.getUser().getAlias(),joc.getNrPuncte(),joc.getSecunde());
            lisa.add(newJoc);
        }
        //sortare descrescatoare
        Collections.sort(lisa, (j1, j2) -> {
            int cmp = Integer.compare(j2.getNrPuncte(), j1.getNrPuncte());
            if (cmp == 0)
                return Integer.compare(j2.getSecunde(), j1.getSecunde());
            return cmp;
        });

        listaEntitati.setAll(lisa);
    }

    /* TODO 2: IMPLEMENT THE OBSERVER METHODS */
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




    void logout() {}
}
