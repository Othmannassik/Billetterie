package ma.emsi.billetterie.dao;

import ma.emsi.billetterie.entities.Billet;

import java.util.List;

public interface IBillet {
    void insert(Billet billet);
    void update(Billet billet);
    void delete(int id);
    Billet find(int id);
    List<Billet> findAll();
}
