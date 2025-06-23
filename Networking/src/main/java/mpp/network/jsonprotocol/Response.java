package mpp.network.jsonprotocol;


// TODO 1: IMPORT MODELS FOR RESPONSE CLASS

import mpp.model.*;

import java.util.List;

public class Response {

    private ResponseType type;
    private String errorMessage;
    private List<User> users;
    private List<Configuratie> configuratii;
    private List<Joc> jocuri;
    private List<Pozitie> pozitii;
    Joc joc;
    Configuratie configuratie;

    // TODO 2: ADD MODELS FOR RESPONSE CLASS

    public Response(){}

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUtilizatori(List<User> utilizatori) {
        this.users = utilizatori;
    }

    public List<Configuratie> getConfiguratii() {
        return configuratii;
    }
    public void setConfiguratii(List<Configuratie> configuratii) {
        this.configuratii = configuratii;
    }
    public void setConfiguratie(Configuratie configuratie) {
        this.configuratie = configuratie;
    }
    public Configuratie getConfig() {
        return configuratie;
    }

    public List<Joc> getJocuri() {
        return jocuri;
    }
    public void setJocuri(List<Joc> jocuri) {
        this.jocuri = jocuri;
    }
    public Joc getJoc() {
        return joc;
    }
    public void setJoc(Joc joc) {
        this.joc = joc;
    }
    public List<Pozitie> getPozitii() {
        return pozitii;
    }
    public void setPozitii(List<Pozitie> pozitii) {
        this.pozitii = pozitii;
    }


    /* TODO 3: CREATE toString() FOR RESPONSE CLASS
    @Override
    public String toString() {
        return "Response{" +
                "type=" + type +
                ", reservationManager=" + reservationManager +
                ", trip=" + trip +
                ", reservation=" + reservation +
                ", seat=" + seat +
                '}';
     */
}
