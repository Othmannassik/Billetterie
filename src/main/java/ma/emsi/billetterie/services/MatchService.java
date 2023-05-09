package ma.emsi.billetterie.services;

import ma.emsi.billetterie.dao.IMatch;
import ma.emsi.billetterie.dao.IMatchImpl;
import ma.emsi.billetterie.entities.Match;

import java.util.List;

public class MatchService {
    private IMatch matchDao = new IMatchImpl();
    public List<Match> findAll() {
        return matchDao.findAll();
    }
    public Match find(int id){
        return matchDao.find(id);
    }
    public void save(Match match) {
        matchDao.insert(match);
    }
    public void update(Match match) {
        matchDao.update(match);
    }
    public void remove(Match match) {
        matchDao.delete(match.getId());
    }
    public void printMatchesIntoTxtFile(String file){
        matchDao.printMatchesTxtFile(file);
    }
}
