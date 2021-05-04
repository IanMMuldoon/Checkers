import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;


public class MatchHistory{

    String _filePath = "history.txt"l
    
    public static void writeMH(int gameID, int player1ID, int player2ID, int winnerID){
        //create file
        File file = new File(this._filePath);
        if(!file.exists()){
            file.createNewFile();
        }

        // write to file
        FileWriter myWriter = new FilWriter(this.filePath);
        String print = String.valueOf(gameID) + "\t" + String.valueOf(player1ID) + "\t" + String.valueOf(player2ID) + "\t" + String.valueOf(winnderID) + "\n";

        myWriter.write(print);
        myWriter.close();
    }

}
