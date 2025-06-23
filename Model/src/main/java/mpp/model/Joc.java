package mpp.model;

public class Joc extends Entitate<Integer>{
    private User user;
    private Configuratie configuratie;
    private Integer nrPuncte;
    private Integer secunde;
    private String pozPropuse;

    public Joc(User user, Configuratie configuratie, Integer scor, Integer secunde, String perechiPozPropuse) {
        this.user = user;
        this.configuratie = configuratie;
        this.nrPuncte = scor;
        this.secunde = secunde;
        this.pozPropuse = perechiPozPropuse;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Configuratie getConfiguratie() {
        return configuratie;
    }
    public void setConfiguratie(Configuratie configuratie) {
        this.configuratie = configuratie;
    }
    public Integer getNrPuncte() {
        return nrPuncte;
    }
    public void setNrPuncte(Integer nrPuncte) {
        this.nrPuncte = nrPuncte;
    }
    public Integer getSecunde() {
        return secunde;
    }
    public void setSecunde(Integer secunde) {
        this.secunde = secunde;
    }
    public String getPozPropuse() {
        return pozPropuse;
    }
    public void setPozPropuse(String perechiPozPropuse) {
        this.pozPropuse = perechiPozPropuse;
    }
}
