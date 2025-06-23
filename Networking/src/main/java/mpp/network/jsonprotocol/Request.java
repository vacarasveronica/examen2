package mpp.network.jsonprotocol;

// TODO 1: IMPORT MODELS FOR REQUEST CLASS

public class Request {

    private RequestType type;
    private String stringR;
    private Long aLong;
    private String alias;
    //private Joc joc;

    // TODO 2: ADD MODELS FOR REQUEST CLASS

    public Request(){}

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public String getStringR() {
        return stringR;
    }

    public void setStringR(String stringR) {
        this.stringR = stringR;
    }

    public Long getLong() {
        return aLong;
    }

    public void setLong(Long aLong) {
        this.aLong = aLong;
    }


//    public Joc getJoc(){
//        return joc;
//    }
//    public void setJoc(Joc joc){
//        this.joc = joc;
//    }
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
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