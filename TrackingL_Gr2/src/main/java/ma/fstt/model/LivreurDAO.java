package ma.fstt.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivreurDAO extends BaseDAO<Livreur> {
    public LivreurDAO() throws SQLException {

        super();
    }

    @Override
    public void save(Livreur object) throws SQLException {

        String request = "insert into livreur (id_livreur , nom , telephone) values (?, ? , ?)";

        // mapping objet table

        this.preparedStatement = this.connection.prepareStatement(request);

        // mapping
        this.preparedStatement.setLong(1, object.getId_livreur());

        this.preparedStatement.setString(2, object.getNom());

        this.preparedStatement.setString(3, object.getTelephone());

        this.preparedStatement.execute();
    }

    @Override
    public void update(Livreur object) throws SQLException {

        try {
            // Création d'une requête SQL pour mettre à jour les données dans la base de données
            String request = "UPDATE livreur SET nom = ?, telephone = ? WHERE id_livreur = ?";

            this.preparedStatement = this.connection.prepareStatement(request);

            // mapping
            this.preparedStatement.setString(1, object.getNom());
            this.preparedStatement.setString(2, object.getTelephone());
            this.preparedStatement.setLong(3, object.getId_livreur());

            // Exécution de la requête SQL pour mettre à jour les données dans la base de données
            int lignesModifiees = this.preparedStatement.executeUpdate();

            // Vérification que les données ont bien été modifiées
            if (lignesModifiees > 0) {
                System.out.println("Données modifiées avec succès !");
            } else {
                System.out.println("Aucune donnée n'a été modifiée.");
            }

            // Fermeture du statement
            this.preparedStatement.close();

        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification des données : " + e.getMessage());
        }

    }

    @Override
    public void delete(Livreur object) throws SQLException {
        String request = "delete from livreur where id_livreur = ?";

        this.preparedStatement = this.connection.prepareStatement(request);

        // mapping
        this.preparedStatement.setLong(1, object.getId_livreur());

        this.preparedStatement.executeUpdate();
    }

    @Override
    public List<Livreur> getAll() throws SQLException {

        List<Livreur> mylist = new ArrayList<Livreur>();

        String request = "select * from livreur ";

        this.statement = this.connection.createStatement();

        this.resultSet = this.statement.executeQuery(request);

// parcours de la table
        while (this.resultSet.next()) {

// mapping table objet
            mylist.add(new Livreur(this.resultSet.getInt(1),
                    this.resultSet.getString(2),
                    this.resultSet.getString(3)));
        }
        return mylist;
    }

    @Override
    public Livreur getOne(Long id) throws SQLException {
        String request = "select * from livreur where id_livreur = ?";

        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1, id);
        this.resultSet = this.preparedStatement.executeQuery();
        while (resultSet.next()) {
            return new Livreur(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
        }
        return null;
    }


    public int getCount() throws SQLException {
        String request = "select count(*) from livreur";
        this.statement = this.connection.createStatement();
        this.resultSet = this.statement.executeQuery(request);
        resultSet.next();
        return resultSet.getInt(1);

    }
}
