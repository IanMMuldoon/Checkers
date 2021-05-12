import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class GameOverController implements Initializable{
    @FXML
    public Text WinnerName;

    public Button rematchButton;
    public Button menuButton;
    public Button quitButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        WinnerName.setText(GameScreen.Winner + " Wins!");
    }

    public void handleReMatchButton(){
        GameScreen gamescreen = new GameScreen();
        gamescreen._clearPieces();
        gamescreen.changeGameScene();
    }
    public void handleMenuButton(){
        GameScreen gamescreen = new GameScreen();
        try {
            gamescreen.changeScene("MainMenu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleQuitButton(){

        System.exit(0);
    }

    }

