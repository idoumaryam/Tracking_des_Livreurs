package ma.fstt.trackingl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ma.fstt.model.Livreur;
import ma.fstt.model.LivreurDAO;

import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private TextField idL;
    @FXML
    private TextField nom;
    @FXML
    private TextField tele;
    @FXML
    private Button btnreturn;
    @FXML
    private TableView<Livreur> mytable;
    @FXML
    private TableColumn<Livreur, Long> col_id;
    @FXML
    private TableColumn<Livreur, String> col_nom;
    @FXML
    private TableColumn<Livreur, String> col_tele;

    @FXML
    protected void onSaveButtonClick() {
        int idLivreur = Integer.parseInt( idL.getText() );
        // accees a la bdd
        try {
            LivreurDAO livreurDAO = new LivreurDAO();
            Livreur liv = new Livreur( (int)idLivreur, nom.getText(), tele.getText() );
            livreurDAO.save( liv );
            UpdateTable();
            empty();

        }
        catch ( SQLException e ) {
            throw new RuntimeException( e );
        }
    }

    @FXML
    private void DeleteButtonAction( ActionEvent event ) {
        // Récupérer l'élément sélectionné dans le TableView
        Livreur livreur = mytable.getSelectionModel().getSelectedItem();
        if ( livreur != null ) {
            // Supprimer l'élément de la base de données
            try {
                LivreurDAO livreurDAO = new LivreurDAO();
                livreurDAO.delete( livreur );
            }
            catch ( SQLException e ) {
                throw new RuntimeException( e );
            }
            // Supprimer l'élément de la liste observable liée au TableView
            mytable.getItems().remove( livreur );
        }
    }

    @FXML
    protected void onRowClick() {
        Livreur liv = mytable.getSelectionModel().getSelectedItem();
        // accees a la bdd
        idL.setText( "" + liv.getId_livreur() );
        idL.setEditable( false );
        nom.setText( liv.getNom() );
        tele.setText( liv.getTelephone() );

    }

    @FXML
    private void UpdateButtonAction( ActionEvent event ) {
        // Récupérer l'élément sélectionné dans le TableView
        Livreur livreur = mytable.getSelectionModel().getSelectedItem();

        if ( livreur != null ) {
            try {
                // Récupérer les nouvelles valeurs depuis les champs de saisie
                int idlivreur = Integer.parseInt( idL.getText() );
                String NomLivreur = nom.getText();
                String teleLivreur = tele.getText();

                // Créer un nouvel objet Livreur avec les nouvelles valeurs
                Livreur liv = new Livreur( (int)idlivreur, NomLivreur, teleLivreur );

                // Mettre à jour le livreur dans la base de données en utilisant la méthode update de la classe LivreurDAO
                LivreurDAO livreurDAO = new LivreurDAO();
                livreurDAO.update( liv );

                // Mettre à jour le TableView avec les données mises à jour
                UpdateTable();
                // Vider les champs de saisie
                empty();
            }
            catch ( SQLException e ) {
                throw new RuntimeException( e );
            }
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

    public void UpdateTable() {
        col_id.setCellValueFactory( new PropertyValueFactory<Livreur, Long>( "id_livreur" ) );
        col_nom.setCellValueFactory( new PropertyValueFactory<Livreur, String>( "nom" ) );
        col_tele.setCellValueFactory( new PropertyValueFactory<Livreur, String>( "telephone" ) );
        mytable.setItems( getDataLivreurs() );
    }

    public static ObservableList<Livreur> getDataLivreurs() {

        LivreurDAO livreurDAO = null;
        ObservableList<Livreur> listC = FXCollections.observableArrayList();
        try {
            livreurDAO = new LivreurDAO();
            for ( Livreur ettemp : livreurDAO.getAll() )
                listC.add( ettemp );
        }
        catch ( SQLException e ) {
            throw new RuntimeException( e );
        }
        return listC;
    }

    public void empty() {

        idL.setText( "" );
        idL.setEditable( true );
        nom.setText( "" );
        tele.setText( "" );

    }

    @Override
    public void initialize( URL location, ResourceBundle resources ) {
        UpdateTable();

    }
}