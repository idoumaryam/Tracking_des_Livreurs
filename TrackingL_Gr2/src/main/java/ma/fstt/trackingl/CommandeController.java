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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ma.fstt.model.Commande;
import ma.fstt.model.CommandeDAO;
import ma.fstt.model.Livreur;

import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ComboBox;
import ma.fstt.model.LivreurDAO;

public class CommandeController implements Initializable {

    @FXML
    private TextField idC;
    @FXML
    private TextField date;
    @FXML
    private Button btnreturn;
    @FXML
    public TextField distance;
//    public TextField idL;
    @FXML
    private TableView<Commande> mytable;
    @FXML
    private TableColumn<Commande, Long> col_id;
    @FXML
    private TableColumn<Commande, String> col_date;
    @FXML
    private TableColumn<Commande, String> col_stat;
    @FXML
    private TableColumn<Commande, Double> col_distance;
    @FXML
    private TableColumn<Commande, Long> col_idL;
    @FXML
    private ComboBox<String> mycombo;
    @FXML
    private ComboBox<Livreur> cmboxLV;


    @FXML
    protected void onSaveButtonClick() {

        // accees a la bdd

        try {
            CommandeDAO commandeDAO = new CommandeDAO();

            int idCommande = Integer.parseInt(idC.getText());
            int disCommande = Integer.parseInt(distance.getText());
            int idLivreur = cmboxLV.getSelectionModel().getSelectedItem().getId_livreur();

            Commande com = new Commande(idCommande, date.getText(), mycombo.getValue(), (double) disCommande, idLivreur);
            commandeDAO.save(com);
            UpdateTable();
            empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleButtonAction(ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;
        if (event.getSource() == btnreturn) {
            stage = (Stage) btnreturn.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Dashboard.fxml")));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void DeleteButtonAction(ActionEvent event) {

        // Récupérer l'élément sélectionné dans le TableView
        Commande commande = mytable.getSelectionModel().getSelectedItem();
        if (commande != null) {
            // Supprimer l'élément de la base de données
            try {
                CommandeDAO commandeDAO = new CommandeDAO();
                commandeDAO.delete(commande);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            // Supprimer l'élément de la liste observable liée au TableView
            mytable.getItems().remove(commande);
        }


    }

    @FXML
    protected void onRowClick() throws SQLException {
        Commande cmd = mytable.getSelectionModel().getSelectedItem();
        // accees a la bdd
        idC.setText("" + cmd.getId_commande());
        idC.setEditable(false);
        date.setText(cmd.getDate_commande());
        mycombo.setValue(cmd.getStatut());
        distance.setText("" + cmd.getDistance());
        cmboxLV.setValue(new LivreurDAO().getOne((long) cmd.getId_livreur()));

    }

    @FXML
    private void UpdateButtonAction(ActionEvent event) {
        // Récupérer l'élément sélectionné dans le TableView
        Commande commande = mytable.getSelectionModel().getSelectedItem();


        if (commande != null) {
            try {
                // Récupérer les nouvelles valeurs depuis les champs de saisie
                int idCommande = Integer.parseInt(idC.getText());
                String dateCommande = date.getText();
                String statCommande = mycombo.getValue();
                double distCommande = Double.parseDouble(distance.getText());
                int idLivreur = cmboxLV.getSelectionModel().getSelectedItem().getId_livreur();

                // Créer un nouvel objet Commande avec les nouvelles valeurs
                Commande com = new Commande(idCommande, dateCommande, statCommande, distCommande, idLivreur);

                // Mettre à jour la commande dans la base de données en utilisant la méthode update de la classe CommandeDAO
                CommandeDAO commandeDAO = new CommandeDAO();
                commandeDAO.update(com);

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
        col_id.setCellValueFactory(new PropertyValueFactory<Commande, Long>("id_commande"));
        col_date.setCellValueFactory(new PropertyValueFactory<Commande, String>("date_commande"));
        col_stat.setCellValueFactory(new PropertyValueFactory<Commande, String>("statut"));
        col_distance.setCellValueFactory(new PropertyValueFactory<Commande, Double>("distance"));
        col_idL.setCellValueFactory(new PropertyValueFactory<Commande, Long>("id_livreur"));
        mytable.setItems(getDataCommades());
    }

    public static ObservableList<Commande> getDataCommades() {

        CommandeDAO commandeDAO = null;
        ObservableList<Commande> listfx = FXCollections.observableArrayList();
        try {
            commandeDAO = new CommandeDAO();
            listfx.addAll(commandeDAO.getAll());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listfx;
    }

    public void empty() {

        idC.clear();
        idC.setEditable(true);
        date.clear();
        mycombo.getSelectionModel().clearSelection();
        distance.clear();
        cmboxLV.setValue(null);

    }

    public void UpdateComboBox() throws SQLException {
        ObservableList<String> listC = FXCollections.observableArrayList();
        listC.addAll("livrée", "En attente de livraison");
        mycombo.setItems(listC);
        //update LV combobox
        ObservableList<Livreur> listLV = FXCollections.observableArrayList();
        listLV.addAll(new LivreurDAO().getAll());
        cmboxLV.setItems(listLV);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpdateTable();
        try {
            UpdateComboBox();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



}
