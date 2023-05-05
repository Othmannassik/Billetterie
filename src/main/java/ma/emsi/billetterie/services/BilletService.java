package ma.emsi.billetterie.services;

import ma.emsi.billetterie.dao.IBillet;
import ma.emsi.billetterie.dao.IBilletImpl;
import ma.emsi.billetterie.entities.Billet;

import java.util.List;

public class BilletService {
    private IBillet billetDao = new IBilletImpl();
    public List<Billet> findAll() {
        return billetDao.findAll();
    }
    public Billet find(int id){
        return billetDao.find(id);
    }
    public void save(Billet billet) {
        billetDao.insert(billet);
    }
    public void update(Billet billet) {
        billetDao.update(billet);
    }
    public void remove(Billet billet) {
        billetDao.delete(billet.getId());
    }
}
