import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.ResourceBundle;

import java.io.File;
import java.net.URL;

public class LoginController implements Initializable{

    @FXML
    private Button backButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView CheckersImage;
    @FXML
    private TextField PlayerOne;
    @FXML
    private TextField PlayerTwo;

    GameScreen gameScreen = new GameScreen();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("Login Images/Checkers log in image.jpg");
        Image CheckerImage = new Image(brandingFile.toURI().toString());
        CheckersImage.setImage(CheckerImage);
    }

    public void backButtonAction(ActionEvent event) {
        System.out.println("pressed");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    public void playButtonAction(ActionEvent event) throws IOException {
        if (PlayerOne.getText().isBlank() && PlayerTwo.getText().isBlank()) {
            loginMessageLabel.setText("Please enter name for Player One & Two");
        }
        else if (PlayerOne.getText().isBlank()){
            loginMessageLabel.setText("Please enter name for Player One");
        }
        else if (PlayerTwo.getText().isBlank()){
            loginMessageLabel.setText("Please enter name for Player Two");
        }else {
            gameScreen.changeGameScene();
        }





    }


    }