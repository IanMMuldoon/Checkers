import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;


import java.net.URL;
import java.util.ResourceBundle;

public class MatchHistoryController implements Initializable {
    ObservableList list = FXCollections.observableArrayList();

    @FXML
    private ListView<String> historyList;

    public void initialize(URL url, ResourceBundle rb){
        loadData();
    }
    private void loadData(){
        list.removeAll(list);
        String a = "User1 goes here";
        String b = "User2 goes here";
        String c = "User3 goes here";
        String d = "User4 goes here";
        list.addAll(a,b,c,d);
        historyList.getItems().addAll(list);
    }

}
