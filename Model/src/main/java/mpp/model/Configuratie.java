package mpp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "configuratie")
public class Configuratie extends Entitate<Integer>{
    @ManyToOne
    @JoinColumn(name = "pozitieId1")
    private Pozitie pozitie1;
    @ManyToOne
    @JoinColumn(name = "pozitieId2")
    private Pozitie pozitie2;
    @ManyToOne
    @JoinColumn(name = "pozitieId3")
    private Pozitie pozitie3;
    @ManyToOne
    @JoinColumn(name = "pozitieId4")
    private Pozitie pozitie4;
    @ManyToOne
    @JoinColumn(name = "pozitieId5")
    private Pozitie pozitie5;


    public Configuratie(){}

    public Configuratie(Pozitie pozitie1, Pozitie pozitie2,Pozitie pozitie3, Pozitie pozitie4, Pozitie pozitie5) {
        this.pozitie1 = pozitie1;
        this.pozitie2 = pozitie2;
        this.pozitie3 = pozitie3;
        this.pozitie4 = pozitie4;
        this.pozitie5 = pozitie5;
    }

    public Pozitie getPozitie1() {
        return pozitie1;
    }
    public void setPozitie1(Pozitie pozitie1) {
        this.pozitie1 = pozitie1;
    }
    public Pozitie getPozitie2() {
        return pozitie2;
    }
    public void setPozitie2(Pozitie pozitie2) {
        this.pozitie2 = pozitie2;
    }
    public Pozitie getPozitie3() {
        return pozitie3;
    }
    public void setPozitie3(Pozitie pozitie3) {
        this.pozitie3 = pozitie3;
    }
    public Pozitie getPozitie4() {
        return pozitie4;
    }
    public void setPozitie4(Pozitie pozitie4) {
        this.pozitie4 = pozitie4;
    }
    public Pozitie getPozitie5() {
        return pozitie5;
    }
    public void setPozitie5(Pozitie pozitie5) {
        this.pozitie5 = pozitie5;
    }
}
