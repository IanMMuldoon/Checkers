import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;


import java.net.URL;
import java.util.ResourceBundle;



public class GameOverController {
    private Game game = new Game(1,2);
    private GameScreen gamescreen = new GameScreen();
    public Button rematchButton;
    public Button menuButton;
    public Button quitButton;

    public void handleReMatchButton(){
        gamescreen._clearPieces();
        game._reset();
        gamescreen.DrawPieces();
    }
    public void handleMenuButton(){
        System.exit(0);
    }
    public void handleQuitButton(){
        System.exit(0);
    }

    }

