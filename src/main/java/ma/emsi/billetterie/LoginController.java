package ma.emsi.billetterie;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import ma.emsi.billetterie.entities.User;
import ma.emsi.billetterie.services.UserService;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    UserService userService = new UserService();
    @FXML
    private TextField TMdp;

    @FXML
    private TextField TUsername;

    @FXML
    private Button btnConn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnConn.setOnAction(actionEvent -> login());
    }

    public void login(){
        String username = TUsername.getText();
        String password = TMdp.getText();
        User user = userService.findByUsername(username);
        try {
            password = userService.encryptPassword(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (user == null || !user.getPassword().equals(password)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Login Error", ButtonType.CANCEL);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Login Success", ButtonType.OK);
            alert.show();
        }

    }
}
