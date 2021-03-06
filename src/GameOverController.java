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
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class GameOverController {
    @FXML
    public Text WinnerName;

    private GameScreen gamescreen = new GameScreen();
    public Button rematchButton;
    public Button menuButton;
    public Button quitButton;


    public void handleReMatchButton(){
        gamescreen.changeGameScene();
    }
    public void handleMenuButton(){
        try {
            gamescreen.changeScene("MainMenu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void changeText(String text){
        WinnerName.setText(text);
    }
    public void handleQuitButton(){

        System.exit(0);
    }

    }

