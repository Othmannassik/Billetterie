package ma.emsi.billetterie.entities;

import java.io.Serializable;
import java.util.Objects;

public class Billet implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private double prix;
    private Match match;

    public Billet(int id, double prix, Match match) {
        this.id = id;
        this.prix = prix;
        this.match = match;
    }

    public Billet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    @Override
    public int hashCode() {
        return (int)(this.id + this.prix) * 22;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Billet obj1 = (Billet) obj;
        return Objects.equals(obj1.match, this.match) && Objects.equals(obj1.prix, this.prix) ;
    }

    @Override
    public String toString() {
        return "Billet [id=" + id + ", prix=" + prix + ", Match=" + match +"]";
    }
}
