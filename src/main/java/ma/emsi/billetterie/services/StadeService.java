package ma.emsi.billetterie.services;

import ma.emsi.billetterie.dao.IStade;
import ma.emsi.billetterie.dao.IStadeImpl;
import ma.emsi.billetterie.entities.Stade;

import java.util.List;

public class StadeService {
    private IStade stadeDao = new IStadeImpl();
    public List<Stade> findAll() {
        return stadeDao.findAll();
    }
    public Stade find(int id){
        return stadeDao.find(id);
    }
    public void save(Stade stade) {
        stadeDao.insert(stade);
    }
    public void update(Stade stade) {
        stadeDao.update(stade);
    }
    public void remove(Stade stade) {
        stadeDao.delete(stade.getId());
    }
}
