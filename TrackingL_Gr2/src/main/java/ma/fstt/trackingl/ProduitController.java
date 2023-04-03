package ma.fstt.trackingl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ma.fstt.model.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProduitController implements Initializable {
    @FXML
    private TextField idP;
    @FXML
    private TextField nom ;
    @FXML
    private TextField categ ;
    @FXML
    private TextField prix ;
    @FXML
    private Button btnreturn;
    @FXML
    private TableView<Produit> mytable ;
    @FXML
    private TableColumn<Produit ,Long> col_id ;
    @FXML
    private TableColumn <Produit ,String> col_nom ;
    @FXML
    private TableColumn <Produit ,String> col_cat ;
    @FXML
    private TableColumn<Produit ,Double> col_prix ;




    @FXML
    protected void onSaveButtonClick() {
        int idProduit = Integer.parseInt(idP.getText());
        int prixProduit = Integer.parseInt(prix.getText());

        // accees a la bdd
        try {
            ProduitDAO produitDAO = new ProduitDAO();
            Produit Pro = new Produit( idProduit, nom.getText() , categ.getText(), prixProduit);
            produitDAO.save(Pro);
            UpdateTable();
            empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void DeleteButtonAction(ActionEvent event) {
        // Récupérer l'élément sélectionné dans le TableView
        Produit produit = mytable.getSelectionModel().getSelectedItem();
        if (produit != null) {
            // Supprimer l'élément de la base de données
            try {
                ProduitDAO produitDAO = new ProduitDAO();
                produitDAO.delete(produit);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            // Supprimer l'élément de la liste observable liée au TableView
            mytable.getItems().remove(produit);
        }
    }
    @FXML
    public void handleButtonAction( ActionEvent event ) throws Exception {
        Stage stage = null;
        Parent root = null;
        if ( event.getSource() == btnreturn ) {
            stage = (Stage)btnreturn.getScene().getWindow();
            root = FXMLLoader.load( Objects.requireNonNull( getClass().getResource( "Dashboard.fxml" ) ) );
        }
        Scene scene = new Scene( root );
        stage.setScene( scene );
        stage.show();
    }
    @FXML
    protected void onRowClick() {
        Produit pro = mytable.getSelectionModel().getSelectedItem() ;
        // accees a la bdd
        idP.setText( ""+pro.getId_produit() );
        idP.setEditable(false);
        nom.setText(pro.getNom());
        categ.setText(pro.getCategorie());
        prix.setText(""+pro.getPrix());
    }
    @FXML
    private void UpdateButtonAction(ActionEvent event){

        // Récupérer l'élément sélectionné dans le TableView
        Produit produit = mytable.getSelectionModel().getSelectedItem();


        if (produit != null) {
            try {
                // Récupérer les nouvelles valeurs depuis les champs de saisie
                int idproduit = Integer.parseInt(idP.getText());
                String NomProduit = nom.getText();
                String CateProduit = categ.getText();
                int prixProduit = Integer.parseInt(prix.getText());

                // Créer un nouvel objet Commande avec les nouvelles valeurs
                Produit pro = new Produit(idproduit, NomProduit, CateProduit, prixProduit);

                // Mettre à jour le produit dans la base de données en utilisant la méthode update de la classe ProduitDAO
                ProduitDAO produitDAO = new ProduitDAO();
                produitDAO.update(pro);

                // Mettre à jour le TableView avec les données mises à jour
                UpdateTable();

                // Vider les champs de saisie
                empty();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void UpdateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<Produit,Long>("id_produit"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Produit,String>("nom"));
        col_cat.setCellValueFactory(new PropertyValueFactory<Produit,String>("categorie"));
        col_prix.setCellValueFactory(new PropertyValueFactory<Produit,Double>("prix"));
        mytable.setItems(this.getDataProduits());
    }

    public static ObservableList<Produit> getDataProduits(){

        ProduitDAO produitDAO = null;
        ObservableList<Produit> listP = FXCollections.observableArrayList();
        try {
            produitDAO = new ProduitDAO();
            for (Produit Ptemp : produitDAO.getAll())
                listP.add(Ptemp);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listP ;
    }




    public void empty(){
        idP.setText("");
        idP.setEditable(true);
        nom.setText(""); ;
        categ.setText(""); ;
        prix.setText(""); ;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpdateTable();


    }

}
