package mpp.rest;

import mpp.model.Configuratie;

public class JocDTO {
    public Configuratie configuratie;
    public String pozPropuse;
    public Integer nrPuncte;
    public Integer secunde;

    public JocDTO(Configuratie configuratie, String pozPropuse, Integer nrPuncte, Integer secunde) {
        this.configuratie = configuratie;
        this.pozPropuse = pozPropuse;
        this.nrPuncte = nrPuncte;
        this.secunde = secunde;
    }
}
