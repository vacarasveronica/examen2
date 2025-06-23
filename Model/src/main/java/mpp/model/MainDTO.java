package mpp.model;

public class MainDTO {
    private String alias;
    private Integer nrPuncte;
    private Integer secunde;

    public MainDTO(String alias, Integer nrPuncte, Integer secunde) {
        this.alias = alias;
        this.nrPuncte = nrPuncte;
        this.secunde = secunde;
    }
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
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
}
