package mpp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pozitie")
public class Pozitie extends Entitate<Integer> {
    @Column(name = "coordonataX", nullable = false)
    private Integer coordonataX;
    @Column(name = "coordonataY", nullable = false)
    private Integer coordonataY;

    public Pozitie(Integer coordonataX, Integer coordonataY) {
        this.coordonataX = coordonataX;
        this.coordonataY = coordonataY;
    }

    public Pozitie() {

    }

    public Integer getCoordonataX() {
        return coordonataX;
    }
    public Integer getCoordonataY() {
        return coordonataY;
    }
}
