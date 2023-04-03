package ma.fstt.trackingl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ma.fstt.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {


    @FXML
    private Button livreur;
    @FXML
    private Button commande;
    @FXML
    private Button produit;
    @FXML
    private Button ligne;
    @FXML
    private Button cancel;
    @FXML
    private Label label;
    @FXML
    private Label labelLV;
    @FXML
    private Label labelCM;
    @FXML
    private Label labelPR;
    @FXML
    private Label labelLC;


    @FXML
    public void handleButtonAction(ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;

        if (event.getSource() == livreur) {
            stage = (Stage) label.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        } else if (event.getSource() == commande) {
            stage = (Stage) label.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Commande-Test.fxml")));
        } else if (event.getSource() == produit) {
            stage = (Stage) label.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Produit-Test.fxml")));
        } else if (event.getSource() == ligne) {
            stage = (Stage) label.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LigneCommande-Test.fxml")));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void ButtonClickCancel() throws IOException {
        // to hide dashboard
        //cancel.getScene().getWindow().hide();
          System.exit(0);

    }

    public void initInterface() throws SQLException {
//        labelLV.setText("Livreur : "+new LivreurDAO().getCount());
        labelLV.setText(""+new LivreurDAO().getCount());
        labelCM.setText(""+new CommandeDAO().getCount());
        labelPR.setText(""+new ProduitDAO().getCount());
        labelLC.setText(""+new LigneCommandeDAO().getCount());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            initInterface();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
