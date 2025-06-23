package mpp.network.jsonprotocol;

// TODO 1: IMPORT MODELS FOR REQUEST CLASS

import mpp.model.Configuratie;
import mpp.model.Joc;

public class Request {

    private RequestType type;
    private String stringR;
    private Long aLong;
    private String alias;
    private Joc joc;
    private Configuratie config;

    // TODO 2: ADD MODELS FOR REQUEST CLASS

    public Request(){}

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public Joc getJoc(){
        return joc;
    }
    public void setJoc(Joc joc){
        this.joc = joc;
    }
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public Configuratie getConfig(){
        return config;
    }
    public void setConfig(Configuratie config){
        this.config = config;
    }

    /* TODO 3: CREATE toString() FOR REQUEST CLASS
    @Override
    public String toString() {
        return "Request{" +
                "type=" + type +
                ", reservationManager=" + reservationManager +
                ", trip=" + trip +
                ", reservation=" + reservation +
                ", seat=" + seat +
                '}';
    }
     */
}