
import DBConection;
import Emprunt;

import java.sql.*;

public class EmpruntDao {
    Connection connection = DBConection.getConnection();

    public void enregistrerEmprunt(Emprunt emprunt){
        try {
            PreparedStatement checkStatement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM emprunt where id_emprunt = ?");
            checkStatement.setLong(1, emprunt.getIdEmprunt());
            ResultSet rs = checkStatement.executeQuery();
            rs.next();
            boolean empruntExiste = rs.getInt(1) > 0;

            if (empruntExiste){
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE emprunt SET status = ? WHERE id_emprunt = ?");
                statement.setString(1, emprunt.getStatus().name() );
                statement.setLong(2, emprunt.getIdEmprunt());

                int rowUpdated = statement.executeUpdate();
                if (rowUpdated > 0){
                    System.out.println("The status updated successfully");

                }else {
                    System.out.println("Error while updating status");
                }
            }else {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO emprunt(id_emprunt, membre_id, livre_id, date_emprunt, date_retour_prev, date_retour_eff, status) VALUES(?, ?, ?, ?, ?, ?, ?)");
                statement.setLong(1, emprunt.getIdEmprunt());
                statement.setLong(2, emprunt.getMembreId());
                statement.setLong(3, emprunt.getLivreId());
                statement.setDate(4, Date.valueOf(emprunt.getDateEmprunt()));
                statement.setDate(5, Date.valueOf(emprunt.getDateRetourPrev()));
                if (emprunt.getDateRetourEff() != null) {
                    statement.setDate(6, Date.valueOf(emprunt.getDateRetourEff()));
                } else {
                    statement.setNull(6, java.sql.Types.DATE);
                }

                statement.setString(7, emprunt.getStatus().name());

                int rowAffected = statement.executeUpdate();
                if (rowAffected > 0 ){
                    System.out.println("The loan is successfully recorded");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
