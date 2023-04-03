package ma.fstt.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO extends BaseDAO<Commande> {

    public CommandeDAO() throws SQLException {

        super();
    }

    @Override
    public void save( Commande object ) throws SQLException {

        String request = "insert into commande (id_commande , date_commande , statut, distance, id_livreur) values (?, ? , ?, ?, ? )";

        // mapping objet table

        this.preparedStatement = this.connection.prepareStatement( request );

        // mapping
        this.preparedStatement.setLong( 1, object.getId_commande() );

        this.preparedStatement.setString( 2, object.getDate_commande() );

        this.preparedStatement.setString( 3, object.getStatut() );

        this.preparedStatement.setDouble( 4, object.getDistance() );

        this.preparedStatement.setLong( 5, object.getId_livreur() );


        this.preparedStatement.execute();
    }

    @Override
    public void update(Commande commande) throws SQLException {
        try {
            // Création d'une requête SQL pour mettre à jour les données dans la base de données
            String request = "UPDATE commande SET date_commande = ?, statut = ?, distance = ?, id_livreur = ? WHERE id_commande = ?";

            this.preparedStatement = this.connection.prepareStatement(request);

            // mapping
            this.preparedStatement.setString(1, commande.getDate_commande());
            this.preparedStatement.setString(2, commande.getStatut());
            this.preparedStatement.setDouble(3, commande.getDistance());
            this.preparedStatement.setLong(4, commande.getId_livreur());
            this.preparedStatement.setLong(5, commande.getId_commande());

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
    public void delete( Commande object ) throws SQLException {

        String request = "delete from commande where id_commande = ?";

        this.preparedStatement = this.connection.prepareStatement(request);

        // mapping
        this.preparedStatement.setLong(1, object.getId_commande());

        this.preparedStatement.executeUpdate();
    }

    @Override
    public List<Commande> getAll() throws SQLException {

        List<Commande> mylist = new ArrayList<Commande>();

        String request = "select * from commande";

        this.statement = this.connection.createStatement();

        this.resultSet = this.statement.executeQuery( request );

        // parcours de la table
        while ( this.resultSet.next() ) {
            // mapping table objet
            mylist.add( new Commande( this.resultSet.getInt( "id_commande" ),
                    this.resultSet.getString( "date_commande" ),
                    this.resultSet.getString( "statut" ),
                    this.resultSet.getDouble( "distance" ),
                    this.resultSet.getInt( "id_livreur" ) ) );
        }


        return mylist;
    }

    //@Override
    //public Commande getOne( Long id ) throws SQLException {return null;}

    @Override
    public Commande getOne(Long id) throws SQLException {
        String request = "select * from commande where id_commande = ?";

        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1, id);
        this.resultSet = this.preparedStatement.executeQuery();
        while (resultSet.next()) {
            return new Commande(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getDouble(4),resultSet.getInt(5));


        }
        return null;
    }


    public int getCount() throws SQLException {
        String request = "select count(*) from commande";
        this.statement = this.connection.createStatement();
        this.resultSet = this.statement.executeQuery(request);
        resultSet.next();
        return resultSet.getInt(1);

    }
}
