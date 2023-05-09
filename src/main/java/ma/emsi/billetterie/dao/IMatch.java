package ma.emsi.billetterie.dao;

import ma.emsi.billetterie.entities.Match;

import java.util.List;

public interface IMatch {
    void insert(Match match);
    void update(Match match);
    void delete(int id);
    Match find(int id);
    List<Match> findAll();
    void printMatchesTxtFile(String filePath);
}
