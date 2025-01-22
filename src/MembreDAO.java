import DBConection;
import Livre;
import Membre;

import java.sql.*;
import java.time.LocalDate;

public class MembreDao {

    Connection connection = DBConection.getConnection();

    public void saveMember(Membre membre){
        try {
            PreparedStatement checkedStatement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM membre WHERE id = ?");
            checkedStatement.setLong(1, membre.getId());
            ResultSet rs = checkedStatement.executeQuery();
            rs.next();
            boolean membreExists = rs.getInt(1) > 0;
            PreparedStatement statement;

            if (membreExists){
                statement = connection.prepareStatement(
                        "UPDATE membre SET nom = ? WHERE id = ?");

                statement.setString(1, membre.getNom());
                statement.setLong(2, membre.getId());

                int rowUpdated = statement.executeUpdate();
                if (rowUpdated > 0){
                    System.out.println("Name updated successfully");
                }else {
                    System.out.println("Error while updating");
                }
            }else {
                statement = connection.prepareStatement(
                        "INSERT INTO membre(id, nom, prenom, email, date_adhesion) VALUES(?, ?, ?, ?, ?)");
                statement.setLong(1, membre.getId());
                statement.setString(2,membre.getNom());
                statement.setString(3, membre.getPrenom());
                statement.setString(4, membre.getEmail());
                statement.setDate(5, Date.valueOf(membre.getDateAdhesion()));

            }
            int rowAffected = statement.executeUpdate();
            if (rowAffected > 0){
                System.out.println(membre.getNom()+" is added successfully");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Membre rechercherMembreParNom(String nom){
        Membre membre = null;

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM membre WHERE nom = ?");
            statement.setString(1, nom);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                Long id = rs.getLong("id");
                String nomMembre = rs.getString("nom");
                String prenomMembre = rs.getString("prenom");
                String emailMembre = rs.getString("email");
                LocalDate dateAdhesion = rs.getDate("date_adhesion").toLocalDate();


                membre = new Membre(id, nomMembre, prenomMembre, emailMembre, dateAdhesion);

            }else {
                System.out.println("No member found with name : "+ nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return membre;
    }

    public void supprimerMembre(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM membre WHERE id = ?");
            statement.setLong(1, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Member with id " + id + " deleted successfully.");
            } else {
                System.out.println("No member found with id " + id + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
