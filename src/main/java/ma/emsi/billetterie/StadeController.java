package ma.emsi.billetterie;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ma.emsi.billetterie.entities.Stade;
import ma.emsi.billetterie.services.StadeService;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class StadeController implements Initializable {
    private StadeService stadeService = new StadeService();
    @FXML
    private TextField TLieu;

    @FXML
    private TextField TMaxPlace;

    @FXML
    private TextField TNom;

    @FXML
    private TableView<Stade> Table;

    @FXML
    private TableColumn<Stade, Integer> colId;

    @FXML
    private TableColumn<Stade, String> colLieu;

    @FXML
    private TableColumn<Stade, Integer> colMaxPlace;

    @FXML
    private TableColumn<Stade, String> colNom;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnExportExcel;

    @FXML
    private Button btnImportExcel;

    @FXML
    void clearField(ActionEvent event) {
        clear();
    }

    @FXML
    void deleteStade(ActionEvent event) {
        stadeService.remove(stadeService.find(id));
        showStades();
        clear();
    }

    @FXML
    void saveStade(ActionEvent event) {
        Stade stade = new Stade();
        stade.setName(TNom.getText());
        stade.setLieu(TLieu.getText());
        stade.setMaxPlace(Integer.parseInt(TMaxPlace.getText()));
        stadeService.save(stade);
        showStades();
        clear();
    }

    @FXML
    void updateStade(ActionEvent event) {
        Stade stade = stadeService.find(id);
        stade.setName(TNom.getText());
        stade.setLieu(TLieu.getText());
        stade.setMaxPlace(Integer.parseInt(TMaxPlace.getText()));
        stadeService.update(stade);
        showStades();
        clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showStades();
    }

    public ObservableList<Stade> getStades(){
        ObservableList<Stade> list = FXCollections.observableArrayList();;
        for (Stade stade : stadeService.findAll())
            list.add(stade);
        return list;
    }

    public void showStades(){
        Table.setItems(getStades());
        colId.setCellValueFactory(new PropertyValueFactory<Stade, Integer>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<Stade, String>("name"));
        colLieu.setCellValueFactory(new PropertyValueFactory<Stade, String>("lieu"));
        colMaxPlace.setCellValueFactory(new PropertyValueFactory<Stade, Integer>("maxPlace"));
    }

    int id=0;
    @FXML
    void getData(MouseEvent event) {
        Stade stade = Table.getSelectionModel().getSelectedItem();
        id = stade.getId();
        TNom.setText(stade.getName());
        TLieu.setText(stade.getLieu());
        TMaxPlace.setText(stade.getMaxPlace()+"");
        btnSave.setDisable(true);
    }

    void clear(){
        TNom.setText(null);
        TLieu.setText(null);
        TMaxPlace.setText(null);
        btnSave.setDisable(false);
    }

    @FXML
    void ExportToExcel(ActionEvent event) {
        stadeService.exportToExcelFile("src/main/resources/ma/emsi/billetterie/StadeList.xlsx");
    }

    FileChooser fileChooser = new FileChooser();
    @FXML
    void ImportFromExcel(ActionEvent event) {
        File file = fileChooser.showOpenDialog(new Stage());
        stadeService.importFromExcelFile(file.getAbsolutePath());
        showStades();
    }
}
