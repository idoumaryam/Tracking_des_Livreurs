package ma.fstt.model;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class LigneCommandeDAO extends BaseDAO<LigneCommande> {
    public LigneCommandeDAO() throws SQLException {

        super();
    }

    @Override
    public void save(LigneCommande object) throws SQLException {

        String request = "insert into ligne_Commande (id_commande , id_produit , quantite) values (?, ? , ?)";

        // mapping objet table

        this.preparedStatement = this.connection.prepareStatement( request );

        // mapping
        this.preparedStatement.setLong( 1, object.getId_commande() );

        this.preparedStatement.setLong( 2, object.getId_produit() );

        this.preparedStatement.setLong( 3, object.getQuantite() );

        this.preparedStatement.execute();
    }

    @Override
    public void update( LigneCommande ligne ) throws SQLException {


        try {
            // Création d'une requête SQL pour mettre à jour les données dans la base de données
            String request = "UPDATE ligne_commande SET id_commande = ?, id_produit = ?, quantite = ? WHERE id = ?";

            this.preparedStatement = this.connection.prepareStatement(request);

            // mapping
            this.preparedStatement.setLong( 1, ligne.getId_commande() );
            this.preparedStatement.setLong( 2, ligne.getId_produit() );
            this.preparedStatement.setLong( 3, ligne.getQuantite() );
            this.preparedStatement.setLong(4, ligne.getId());


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
    public void delete( LigneCommande object ) throws SQLException {

        String request = "delete from ligne_commande where id = ?";

        this.preparedStatement = this.connection.prepareStatement(request);

        // mapping
        this.preparedStatement.setLong(1, object.getId());

        this.preparedStatement.executeUpdate();
    }

    @Override
    public List<LigneCommande> getAll() throws SQLException {

        List<LigneCommande> mylist = new ArrayList<LigneCommande>();

        String request = "select * from ligne_Commande ";

        this.statement = this.connection.createStatement();

        this.resultSet = this.statement.executeQuery( request );

// parcours de la table
        while ( this.resultSet.next() ) {

// mapping table objet
            mylist.add( new LigneCommande( this.resultSet.getLong( 1 ),this.resultSet.getLong(2),
                    this.resultSet.getLong( 3 ),
                    this.resultSet.getLong(4)) );
        }
        return mylist;
    }

    @Override
    public LigneCommande getOne( Long id ) throws SQLException {
        return null;
    }

    public int getCount() throws SQLException {
        String request = "select count(*) from ligne_Commande";
        this.statement = this.connection.createStatement();
        this.resultSet = this.statement.executeQuery(request);
        resultSet.next();
        return resultSet.getInt(1);

    }
}
