package ma.emsi.billetterie.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Match implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Date date;
    private int nbPlace;
    private String equipe1;
    private String equipe2;
    private Competition competition;
    private Stade stade;

    public Match(int id, Date date, int nbPlace, String equipe1, String equipe2, Competition competition, Stade stade) {
        this.id = id;
        this.date = date;
        this.nbPlace = nbPlace;
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;
        this.competition = competition;
        this.stade = stade;
    }

    public Match() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNbPlace() {
        return nbPlace;
    }

    public void setNbPlace(int nbPlace) {
        this.nbPlace = nbPlace;
    }

    public String getEquipe1() {
        return equipe1;
    }

    public void setEquipe1(String equipe1) {
        this.equipe1 = equipe1;
    }

    public String getEquipe2() {
        return equipe2;
    }

    public void setEquipe2(String equipe2) {
        this.equipe2 = equipe2;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Stade getStade() {
        return stade;
    }

    public void setStade(Stade stade) {
        this.stade = stade;
    }

    @Override
    public int hashCode() {
        return (this.id + this.nbPlace) * 22;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Match obj1 = (Match) obj;
        return Objects.equals(obj1.equipe1, this.equipe1) &&
                Objects.equals(obj1.equipe2, this.equipe2) && Objects.equals(obj1.date, this.date);
    }

    @Override
    public String toString() {
        return "Match [id=" + id + ", date=" + date + ", nbPlace=" + nbPlace + ", equipe1=" + equipe1 + ", equipe2=" + equipe2 + ", competition=" + competition + ", stade=" + stade +"]";
    }
}
