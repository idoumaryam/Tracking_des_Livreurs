package ma.fstt.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO extends BaseDAO<Produit> {

    public ProduitDAO() throws SQLException {

        super();
    }

    @Override
    public void save( Produit object ) throws SQLException {

        String request = "insert into produit (id_produit , nom , categorie, prix) values (?, ? , ?, ?)";

        // mapping objet table
        this.preparedStatement = this.connection.prepareStatement( request );
        // mapping
        this.preparedStatement.setInt( 1, object.getId_produit() );
        this.preparedStatement.setString( 2, object.getNom() );
        this.preparedStatement.setString( 3, object.getCategorie() );
        this.preparedStatement.setInt( 4, object.getPrix() );
        this.preparedStatement.execute();
    }

    @Override
    public void update( Produit produit ) throws SQLException {

        try {
            // Création d'une requête SQL pour mettre à jour les données dans la base de données
            String request = "UPDATE produit SET nom = ?, categorie = ?, prix = ? WHERE id_produit = ?";

            this.preparedStatement = this.connection.prepareStatement(request);

            // mapping
            this.preparedStatement.setString(1, produit.getNom());
            this.preparedStatement.setString(2, produit.getCategorie());
            this.preparedStatement.setDouble(3, produit.getPrix());
            this.preparedStatement.setLong(4, produit.getId_produit());


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
    public void delete(Produit object) throws SQLException {
        String request = "delete from produit where id_produit = ?";

        this.preparedStatement = this.connection.prepareStatement(request);

        // mapping
        this.preparedStatement.setLong(1, object.getId_produit());

        this.preparedStatement.executeUpdate();
    }

    @Override
    public List<Produit> getAll() throws SQLException {

        List<Produit> mylist = new ArrayList<>();

        String request = "select * from produit ";

        this.statement = this.connection.createStatement();

        this.resultSet = this.statement.executeQuery( request );

// parcours de la table
        while ( this.resultSet.next() ) {

// mapping table objet
            mylist.add( new Produit( this.resultSet.getInt( 1 ),
                    this.resultSet.getString( 2 ),
                    this.resultSet.getString( 3 ),
                    this.resultSet.getInt( 4 ) ) );
        }
        return mylist;
    }

    //@Override
    //public Produit getOne( Long id ) throws SQLException {return null;}


    @Override
    public Produit getOne(Long id) throws SQLException {
        String request = "select * from produit where id_produit = ?";

        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1, id);
        this.resultSet = this.preparedStatement.executeQuery();
        while (resultSet.next()) {
            return new Produit(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                  resultSet.getInt(4));


        }
        return null;
    }



    public int getCount() throws SQLException {
        String request = "select count(*) from produit";
        this.statement = this.connection.createStatement();
        this.resultSet = this.statement.executeQuery(request);
        resultSet.next();
        return resultSet.getInt(1);

    }
}
