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
import ma.emsi.billetterie.entities.Competition;
import ma.emsi.billetterie.entities.Match;
import ma.emsi.billetterie.entities.Stade;
import ma.emsi.billetterie.services.MatchService;
import ma.emsi.billetterie.services.StadeService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class MatchController implements Initializable {

    private MatchService matchService = new MatchService();
    private StadeService stadeService = new StadeService();
    @FXML
    private ChoiceBox<String> TCompetition;

    @FXML
    private DatePicker TDate;

    @FXML
    private TextField TEquipe1;

    @FXML
    private TextField TEquipe2;

    @FXML
    private TextField TNbPlace;

    @FXML
    private ChoiceBox<String> TStade;

    @FXML
    private TableView<Match> Table;

    @FXML
    private Button btnBillets;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnExportExcel;

    @FXML
    private Button btnImportExcel;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnStades;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Match, String> colCompetition;

    @FXML
    private TableColumn<Match, Date> colDate;

    @FXML
    private TableColumn<Match, String> colEquipe1;

    @FXML
    private TableColumn<Match, String> colEquipe2;

    @FXML
    private TableColumn<Match, Integer> colId;

    @FXML
    private TableColumn<Match, Integer> colNbPlace;

    @FXML
    private TableColumn<Match, Stade> colStade;

    @FXML
    void ExportToExcel(ActionEvent event) {
        matchService.exportToExcelFile("src/main/resources/ma/emsi/billetterie/MatchesList.xlsx");
    }

    FileChooser fileChooser = new FileChooser();
    @FXML
    void ImportFromExcel(ActionEvent event) {
        File file = fileChooser.showOpenDialog(new Stage());
        matchService.importFromExcelFile(file.getAbsolutePath());
        showMatches();
    }

    @FXML
    void clearField(ActionEvent event) {
        clear();
    }

    private void clear() {
        TDate.setValue(null);
        TNbPlace.setText(null);
        TEquipe1.setText(null);
        TEquipe2.setText(null);
        TCompetition.setValue(null);
        TStade.setValue(null);
        btnSave.setDisable(false);
    }

    @FXML
    void deleteMatch(ActionEvent event) {
        matchService.remove(matchService.find(id));
        showMatches();
        clear();
    }

    int id=0;
    /*@FXML
    void getData(MouseEvent event) {

    }*/

    @FXML
    void saveMatch(ActionEvent event) {
        Match match = new Match();
        //default time zone
        ZoneId defaultZoneId = ZoneId.systemDefault();
        //creating the instance of LocalDate using the day, month, year info
        LocalDate localDate = TDate.getValue();
        //local date + atStartOfDay() + default time zone + toInstant() = Date
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        match.setDate(date);
        match.setNbPlace(Integer.parseInt(TNbPlace.getText()));
        match.setEquipe1(TEquipe1.getText());
        match.setEquipe2(TEquipe2.getText());
        match.setCompetition(Competition.valueOf(TCompetition.getValue()));
        match.setStade(stadeService.getStadeByNom(TStade.getValue()));
        matchService.save(match);
        showMatches();
        clear();
    }

    @FXML
    void toBillets(ActionEvent event) {
        btnBillets.getScene().getWindow().hide();
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
    void updateMatch(ActionEvent event) {
        Match match = matchService.find(id);
        //default time zone
        ZoneId defaultZoneId = ZoneId.systemDefault();
        //creating the instance of LocalDate using the day, month, year info
        LocalDate localDate = TDate.getValue();
        //local date + atStartOfDay() + default time zone + toInstant() = Date
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        match.setDate(date);
        match.setNbPlace(Integer.parseInt(TNbPlace.getText()));
        match.setEquipe1(TEquipe1.getText());
        match.setEquipe2(TEquipe2.getText());
        match.setCompetition(Competition.valueOf(TCompetition.getValue()));
        match.setStade(stadeService.getStadeByNom(TStade.getValue()));
        matchService.update(match);
        showMatches();
        clear();
    }

    public ObservableList<Match> getMatches(){
        ObservableList<Match> list = FXCollections.observableArrayList();;
        for (Match match : matchService.findAll())
            list.add(match);
        return list;
    }

    public void showMatches(){
        Table.setItems(getMatches());
        colId.setCellValueFactory(new PropertyValueFactory<Match, Integer>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<Match, Date>("date"));
        colNbPlace.setCellValueFactory(new PropertyValueFactory<Match, Integer>("nbPlace"));
        colEquipe1.setCellValueFactory(new PropertyValueFactory<Match, String>("equipe1"));
        colEquipe2.setCellValueFactory(new PropertyValueFactory<Match, String>("equipe2"));
        colCompetition.setCellValueFactory(new PropertyValueFactory<Match, String>("competition"));
        colStade.setCellValueFactory(new PropertyValueFactory<Match, Stade>("stade"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showMatches();
        List<String> stades = stadeService.findAll().stream()
                .map(s -> s.getName()).toList();
        List<Competition> list = Arrays.stream(Competition.values()).toList();
        List<String> competitions = list.stream().map(item -> item.name()).toList();
        TStade.getItems().addAll(stades);
        TCompetition.getItems().addAll(competitions);
    }

    @FXML
    public void getData(MouseEvent mouseEvent) {
        Match match = Table.getSelectionModel().getSelectedItem();
        id = match.getId();
        TDate.setValue(LocalDate.now());
        TNbPlace.setText(match.getNbPlace()+"");
        TEquipe1.setText(match.getEquipe1());
        TEquipe2.setText(match.getEquipe2());
        TCompetition.setValue(match.getCompetition().name());
        TStade.setValue(match.getStade().getName());
        btnSave.setDisable(true);
    }
}
