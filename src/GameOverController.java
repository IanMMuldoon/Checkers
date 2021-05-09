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
import javafx.stage.Stage;



import java.net.URL;
import java.util.ResourceBundle;



public class GameOverController {
    private Game game = new Game(1,2);
    private GameScreen gamescreen = new GameScreen();
    private GameOverMain gameovermain = new GameOverMain();
    public Button rematchButton;
    public Button menuButton;
    public Button quitButton;


    public void handleReMatchButton(){
        gamescreen._clearPieces();
        game._reset();
        gamescreen.DrawPieces();
    }
    public void handleMenuButton(){

    }
    public void handleQuitButton(){

        System.exit(0);
    }

    }

