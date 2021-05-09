import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MatchHistoryController implements Initializable {
    public Button back_button;
    ObservableList list = FXCollections.observableArrayList();

    @FXML
    private ListView<String> historyList;

    public void initialize(URL url, ResourceBundle rb){
        loadData();
    }
    private void loadData(){
        list.removeAll(list);
        HistoryRecord[] records = HistoryFile.GetRecords();
        for(int i = 0; i < records.length; i++){
            list.add(records[i].toString());
        }
        historyList.getItems().addAll(list);
    }

    public void main_menu_swap(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Stage stage = (Stage) back_button.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }
}
