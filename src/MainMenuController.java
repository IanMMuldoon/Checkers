import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    public Button playButton;
    public Button matchHistoryButton;
    public Button quitButton;
    private GameScreen gamescreen = new GameScreen();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void quit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void match_history_swap(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MatchHistory.fxml"));
            Stage stage = (Stage) matchHistoryButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }

    }

    public void game_screen_swap(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) playButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }


    }
}
