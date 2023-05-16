package ma.emsi.billetterie.dao;

import ma.emsi.billetterie.entities.Competition;
import ma.emsi.billetterie.entities.Match;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IMatchImpl implements IMatch {

    private Connection conn= DB.getConnection();
    private IStadeImpl stade = new IStadeImpl();
    @Override
    public void insert(Match match) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO footmatch (date,nbPlace,equipe1,equipe2,competition,idStade) " +
                    "VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setDate(1, (java.sql.Date) new Date(match.getDate().getTime()));
            ps.setInt(2, match.getNbPlace());
            ps.setString(3, match.getEquipe1());
            ps.setString(4, match.getEquipe2());
            ps.setString(5, match.getCompetition().toString());
            ps.setInt(6,match.getStade().getId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);

                    match.setId(id);
                }

                DB.closeResultSet(rs);
            } else {
                System.out.println("Aucune ligne renvoyée");
            }
        } catch (SQLException e) {
            System.err.println(e);;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(Match match) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE footmatch SET date = ? , nbPlace = ? , equipe1 = ? , " +
                    "equipe2 = ? , competition = ? , idStade = ? WHERE id = ?");

            ps.setDate(1, (java.sql.Date) new Date(match.getDate().getTime()));
            ps.setInt(2, match.getNbPlace());
            ps.setString(3, match.getEquipe1());
            ps.setString(4, match.getEquipe2());
            ps.setString(5, match.getCompetition().toString());
            ps.setInt(6,match.getStade().getId());

            ps.setInt(7, match.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de mise à jour d'un match");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void delete(int id) {

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM footmatch WHERE id = ?");

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de suppression d'un match");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Match find(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM footmatch WHERE id = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                Match match = new Match();

                match.setId(rs.getInt("id"));
                match.setDate(rs.getDate("date"));
                match.setNbPlace(rs.getInt("nbPlace"));
                match.setEquipe1(rs.getString("equipe1"));
                match.setEquipe2(rs.getString("equipe2"));
                match.setCompetition(Competition.valueOf(rs.getString("competition")));
                match.setStade(stade.find(rs.getInt("idStade")));

                return match;
            }

            return null;
        } catch (SQLException e) {
            System.err.println("problème de requête pour trouver un match");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public List<Match> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM footmatch");
            rs = ps.executeQuery();

            List<Match> listMatchs = new ArrayList<>();

            while (rs.next()) {
                Match match = new Match();

                match.setId(rs.getInt("id"));
                match.setDate(rs.getDate("date"));
                match.setNbPlace(rs.getInt("nbPlace"));
                match.setEquipe1(rs.getString("equipe1"));
                match.setEquipe2(rs.getString("equipe2"));
                match.setCompetition(Competition.valueOf(rs.getString("competition")));
                match.setStade(stade.find(rs.getInt("idStade")));

                listMatchs.add(match);
            }

            return listMatchs;
        } catch (SQLException e) {
            System.err.println("problème de requête pour sélectionner l ensemble des matchs");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

}
