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

    public static String PlayerOneName;
    public static String PlayerTwoName;

    public static int PlayerOneID;
    public static int PlayerTwoID;

    GameScreen gamescreen = new GameScreen();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("Login Images/Checkers log in image.jpg");
        Image CheckerImage = new Image(brandingFile.toURI().toString());
        CheckersImage.setImage(CheckerImage);
    }

    public void backButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    public void playButtonAction(ActionEvent event) {
        if (PlayerOne.getText().isBlank() && PlayerTwo.getText().isBlank()) {
            loginMessageLabel.setText("Please enter name for Player One & Two");
        }
        else if (PlayerOne.getText().isBlank()){
            loginMessageLabel.setText("Please enter name for Player One");
        }
        else if (PlayerTwo.getText().isBlank()){
            loginMessageLabel.setText("Please enter name for Player Two");
        }
        else{
          HistoryFile.SaveName(PlayerOne.getText());
          HistoryFile.SaveName(PlayerTwo.getText());
          PlayerOneName = PlayerOne.getText();
          PlayerTwoName = PlayerTwo.getText();
          gamescreen.changeGameScene();

        }

        

    }

    public static int getPlayer1ID(){
        HistoryRecord[] records = HistoryFile.GetRecords();
        for(int i = 0; i < records.length; i++){
            if(records[i].Name.equals(PlayerOneName)){
                return records[i].ID;
            }
        }
        return PlayerOneID;
    }
    public static int getPlayer2ID(){
        HistoryRecord[] records = HistoryFile.GetRecords();
        for(int i = 0; i < records.length; i++){
            if(records[i].Name.equals(PlayerTwoName)){
                return records[i].ID;
            }
        }
        return PlayerOneID;
    }
    public static String getPlayerOneName(){
        return PlayerOneName;
    }
    public static String getPlayerTwoName(){
        return PlayerTwoName;
    }

    }