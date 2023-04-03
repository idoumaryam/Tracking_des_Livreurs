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
import ma.fstt.model.CommandeDAO;
import ma.fstt.model.ProduitDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class LigneController implements Initializable {
    private static long id;
    @FXML
    private ComboBox<Commande> cmboxCM;
    @FXML
    private ComboBox<Produit> cmboxPR;
    @FXML
    private TextField quant;
    @FXML
    private Button btnreturn;
    @FXML
    private TableView<LigneCommande> mytable;
    @FXML
    private TableColumn<LigneCommande, Long> col_id;
    @FXML
    private TableColumn<LigneCommande, Long> col_com;
    @FXML
    private TableColumn<LigneCommande, Long> col_pro;
    @FXML
    private TableColumn<LigneCommande, Long> col_quant;


    @FXML
    protected void onSaveButtonClick() {
        long idCommande = cmboxCM.getSelectionModel().getSelectedItem().getId_commande();
        long idProduit = cmboxPR.getSelectionModel().getSelectedItem().getId_produit();
        int quantite = Integer.parseInt( quant.getText() );

        // accees a la bdd
        try {
            LigneCommandeDAO ligneDAO = new LigneCommandeDAO();
            LigneCommande liv = new LigneCommande( 0L,idCommande, idProduit, (long) quantite );
            ligneDAO.save( liv );
            UpdateTable();
            empty();
        }
        catch ( SQLException e ) {
            throw new RuntimeException( e );
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
    private void DeleteButtonAction(ActionEvent event) {
        // Récupérer l'élément sélectionné dans le TableView
        LigneCommande ligne = mytable.getSelectionModel().getSelectedItem();
        if (ligne != null) {
            // Supprimer l'élément de la base de données
            try {
                LigneCommandeDAO ligneDAO = new LigneCommandeDAO();
                ligneDAO.delete(ligne);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            // Supprimer l'élément de la liste observable liée au TableView
            mytable.getItems().remove(ligne);
        }
    }


    @FXML
    protected void onRowClick() throws SQLException {
        LigneCommande ligne = mytable.getSelectionModel().getSelectedItem() ;
        id= ligne.getId();
        cmboxCM.setValue(new CommandeDAO().getOne((long) ligne.getId_commande()));
        cmboxPR.setValue(new ProduitDAO().getOne((long) ligne.getId_produit()));
        quant.setText(""+ligne.getQuantite());

    }
    @FXML
    private void UpdateButtonAction(ActionEvent event){
        // Récupérer l'élément sélectionné dans le TableView
        LigneCommande ligne = mytable.getSelectionModel().getSelectedItem();
        if (ligne != null) {
            try {
                // Récupérer les nouvelles valeurs depuis les champs de saisie
                long idcommande = cmboxCM.getSelectionModel().getSelectedItem().getId_commande();
                long idproduit = cmboxPR.getSelectionModel().getSelectedItem().getId_produit();
                int quantProduit = Integer.parseInt(quant.getText());

                // Créer un nouvel objet Commande avec les nouvelles valeurs
                LigneCommande lig = new LigneCommande(id, idcommande, idproduit,quantProduit );

                // Mettre à jour la ligne Commande dans la base de données en utilisant la méthode update de la classe LigneCommandeDAO
                LigneCommandeDAO ligneDAO = new LigneCommandeDAO();
                ligneDAO.update(lig);

                // Mettre à jour le TableView avec les données mises à jour
                UpdateTable();

                // Vider les champs de saisie
                empty();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void UpdateTable() {
        col_id.setCellValueFactory(new PropertyValueFactory<LigneCommande,Long>("id"));
        col_com.setCellValueFactory( new PropertyValueFactory<LigneCommande, Long>( "id_commande" ) );
        col_pro.setCellValueFactory( new PropertyValueFactory<LigneCommande, Long>( "id_produit" ) );
        col_quant.setCellValueFactory( new PropertyValueFactory<LigneCommande, Long>( "quantite" ) );
        mytable.setItems( getDataLignes() );
    }

    public static ObservableList<LigneCommande> getDataLignes() {

        LigneCommandeDAO ligneDAO = null;
        ObservableList<LigneCommande> listL = FXCollections.observableArrayList();
        try {
            ligneDAO = new LigneCommandeDAO();
            for ( LigneCommande ltemp : ligneDAO.getAll() )
                listL.add( ltemp );
        }
        catch ( SQLException e ) {
            throw new RuntimeException( e );
        }
        return listL;
    }

    public void UpdateComboBox() throws SQLException {
        ObservableList<Commande> listCM = FXCollections.observableArrayList();
        listCM.addAll(new CommandeDAO().getAll());
        cmboxCM.setItems(listCM);
        //update LV combobox
        ObservableList<Produit> listPR = FXCollections.observableArrayList();
        listPR.addAll(new ProduitDAO().getAll());
        cmboxPR.setItems(listPR);

    }

    public void empty(){

        cmboxCM.setValue(null);
        cmboxPR.setValue(null);
        quant.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources ) {
        UpdateTable();

        try {
            UpdateComboBox();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
