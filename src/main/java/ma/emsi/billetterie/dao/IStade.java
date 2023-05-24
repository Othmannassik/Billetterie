package ma.emsi.billetterie.dao;

import ma.emsi.billetterie.entities.Stade;

import java.util.List;

public interface IStade {
    void insert(Stade stade);
    void update(Stade stade);
    void delete(int id);
    Stade find(int id);
    List<Stade> findAll();

    Stade getStadeByNom(String nom);
}
