import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    public Button playButton;
    public Button matchHistoryButton;
    public Button quitButton;
    private GameScreen gamescreen = new GameScreen();



    public void handlePlayButton() {
        gamescreen.changeGameScene();
    }

    public void handleHistoryButton() {
    }

    public void handleQuitButton() {
    }
}
