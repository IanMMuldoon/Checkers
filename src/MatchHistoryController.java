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
        HistoryRecord[] records = HistoryFile.GetRecords();
        for(int i = 0; i < records.length; i++){
            list.add(records[i].toString());
        }
        historyList.getItems().addAll(list);
    }

}
