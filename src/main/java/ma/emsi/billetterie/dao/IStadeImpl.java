package ma.emsi.billetterie.dao;

import ma.emsi.billetterie.entities.Stade;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IStadeImpl implements IStade {
    private Connection conn= DB.getConnection();
    @Override
    public void insert(Stade stade) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO stade (name,lieu,maxPlace) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, stade.getName());
            ps.setString(2, stade.getLieu());
            ps.setInt(3,stade.getMaxPlace());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);

                    stade.setId(id);
                }

                DB.closeResultSet(rs);
            } else {
                System.out.println("Aucune ligne renvoyée");
            }
        } catch (SQLException e) {
            System.err.println("problème d'insertion d'un stade");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(Stade stade) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE stade SET name = ? , lieu = ? , maxPlace = ? WHERE id = ?");

            ps.setString(1, stade.getName());
            ps.setString(2, stade.getLieu());
            ps.setInt(3, stade.getMaxPlace());
            ps.setInt(4, stade.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM stade WHERE id = ?");

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de suppression d'un stade");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Stade find(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM stade WHERE id = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                Stade stade = new Stade();

                stade.setId(rs.getInt("id"));
                stade.setName(rs.getString("name"));
                stade.setLieu(rs.getString("lieu"));
                stade.setMaxPlace(rs.getInt("maxPlace"));

                return stade;
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
    public List<Stade> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM stade");
            rs = ps.executeQuery();

            List<Stade> listStades = new ArrayList<>();

            while (rs.next()) {
                Stade stade = new Stade();

                stade.setId(rs.getInt("id"));
                stade.setName(rs.getString("name"));
                stade.setLieu(rs.getString("lieu"));
                stade.setMaxPlace(rs.getInt("maxPlace"));

                listStades.add(stade);
            }

            return listStades;
        } catch (SQLException e) {
            System.err.println("problème de requête pour sélectionner l ensemble des stades");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }

    }

    @Override
    public void storeStadeDataFromTxtFile(String filePath) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filePath));
            Stade stade = null;
            String readLine = br.readLine();

            while(readLine != null){

                String [] std  = readLine.split("\\|");

                stade = new Stade();
                stade.setName(std[0].trim());
                stade.setLieu(std[1].trim());
                stade.setMaxPlace(Integer.parseInt(std[2].trim()));

                insert(stade);
                readLine = br.readLine();
            }
            System.out.println("Stade Data Stored Successfully in "+ filePath);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

