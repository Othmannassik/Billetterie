package ma.emsi.billetterie.dao;

import ma.emsi.billetterie.entities.Billet;
import ma.emsi.billetterie.entities.Stade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IBilletImpl implements IBillet {

    private Connection conn= DB.getConnection();
    private IMatchImpl match = new IMatchImpl();
    @Override
    public void insert(Billet billet) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO billet (prix,idMatch) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setDouble(1, billet.getPrix());
            ps.setInt(2, billet.getMatch().getId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);

                    billet.setId(id);
                }

                DB.closeResultSet(rs);
            } else {
                System.out.println("Aucune ligne renvoyée");
            }
        } catch (SQLException e) {
            System.err.println("problème d'insertion d'un billet");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(Billet billet) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE billet SET prix = ? , idMatch = ? WHERE id = ?");

            ps.setDouble(1, billet.getPrix());
            ps.setDouble(2, billet.getMatch().getId());
            ps.setInt(3, billet.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de mise à jour d'un billet");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM billet WHERE id = ?");

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de suppression d'un billet");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Billet find(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM billet WHERE id = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                Billet billet = new Billet();

                billet.setId(rs.getInt("id"));
                billet.setPrix(rs.getDouble("prix"));
                billet.setMatch(match.find(rs.getInt("idMatch")));

                return billet;
            }

            return null;
        } catch (SQLException e) {
            System.err.println("problème de requête pour trouver un billet");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public List<Billet> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM billet");
            rs = ps.executeQuery();

            List<Billet> listBillets = new ArrayList<>();

            while (rs.next()) {
                Billet billet = new Billet();

                billet.setId(rs.getInt("id"));
                billet.setPrix(rs.getDouble("prix"));
                billet.setMatch(match.find(rs.getInt("idMatch")));

                listBillets.add(billet);
            }

            return listBillets;
        } catch (SQLException e) {
            System.err.println("problème de requête pour sélectionner l ensemble des billets");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }
}
