package ma.emsi.billetterie;

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
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ma.emsi.billetterie.entities.Billet;
import ma.emsi.billetterie.entities.Competition;
import ma.emsi.billetterie.entities.Match;
import ma.emsi.billetterie.entities.Stade;
import ma.emsi.billetterie.services.BilletService;
import ma.emsi.billetterie.services.MatchService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class BilletController implements Initializable {
    BilletService billetService = new BilletService();
    MatchService matchService = new MatchService();
    @FXML
    private ChoiceBox<Integer> TMatch;

    @FXML
    private TextField TPrix;

    @FXML
    private TableView<Billet> Table;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnExportExcel;

    @FXML
    private Button btnImportExcel;

    @FXML
    private Button btnMatches;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnStades;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Billet, Integer> colId;

    @FXML
    private TableColumn<Billet, Match> colMatch;

    @FXML
    private TableColumn<Billet, Double> colPrix;

    @FXML
    void ExportToExcel(ActionEvent event) {
        billetService.exportToExcelFile("src/main/resources/ma/emsi/billetterie/BilletsList.xlsx");
    }

    FileChooser fileChooser = new FileChooser();
    @FXML
    void ImportFromExcel(ActionEvent event) {
        File file = fileChooser.showOpenDialog(new Stage());
        billetService.importFromExcelFile(file.getAbsolutePath());
        showBillets();
    }

    @FXML
    void clearField(ActionEvent event) {
        clear();
    }

    @FXML
    void deleteBillet(ActionEvent event) {
        billetService.remove(billetService.find(id));
        showBillets();
        clear();
    }

    int id = 0;
    @FXML
    void getData(MouseEvent event) {
        Billet billet = Table.getSelectionModel().getSelectedItem();
        id = billet.getId();
        TPrix.setText(billet.getPrix()+"");
        TMatch.setValue(billet.getMatch().getId());
        btnSave.setDisable(true);
    }

    @FXML
    void saveBillet(ActionEvent event) {
        Billet billet = new Billet();
        billet.setPrix(Double.parseDouble(TPrix.getText()));
        billet.setMatch(matchService.find(TMatch.getValue()));
        billetService.save(billet);
        showBillets();
        clear();
    }

    private void clear() {
        TPrix.setText(null);
        TMatch.setValue(null);
        btnSave.setDisable(false);
    }

    public ObservableList<Billet> getBillets(){
        ObservableList<Billet> list = FXCollections.observableArrayList();;
        for (Billet billet : billetService.findAll())
            list.add(billet);
        return list;
    }
    private void showBillets() {
        Table.setItems(getBillets());
        colId.setCellValueFactory(new PropertyValueFactory<Billet, Integer>("id"));
        colPrix.setCellValueFactory(new PropertyValueFactory<Billet, Double>("prix"));
        colMatch.setCellValueFactory(new PropertyValueFactory<Billet, Match>("match"));
    }

    @FXML
    void toMatches(ActionEvent event) {
        btnMatches.getScene().getWindow().hide();
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("Match.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Liste des Matches");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void toStades(ActionEvent event) {
        btnStades.getScene().getWindow().hide();
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("Stade.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Liste des Stades");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateBillet(ActionEvent event) {
        Billet billet = billetService.find(id);
        billet.setPrix(Double.parseDouble(TPrix.getText()));
        billet.setMatch(matchService.find(TMatch.getValue()));
        billetService.update(billet);
        showBillets();
        clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showBillets();
        List<Integer> matches = matchService.findAll().stream()
                .map(s -> s.getId()).toList();
        TMatch.getItems().addAll(matches);
    }
}
