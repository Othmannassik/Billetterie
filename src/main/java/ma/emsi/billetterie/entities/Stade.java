package ma.emsi.billetterie.entities;

import java.io.Serializable;
import java.util.Objects;

public class Stade implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String lieu;
    private int maxPlace;
    public Stade(int id, String name, String lieu, int maxPlace) {
        this.id = id;
        this.name = name;
        this.lieu = lieu;
        this.maxPlace = maxPlace;
    }

    public Stade() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public int getMaxPlace() {
        return maxPlace;
    }

    public void setMaxPlace(int maxPlace) {
        this.maxPlace = maxPlace;
    }

    @Override
    public int hashCode() {
        return (this.id + this.maxPlace) * 22;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Stade obj1 = (Stade) obj;
        return Objects.equals(obj1.maxPlace, this.maxPlace);
    }

    @Override
    public String toString() {
        return "Stade [id=" + id + ", name=" + name + ", lieu=" + lieu + ", maxPlace=" + maxPlace +"]";
    }
}
