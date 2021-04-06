

import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Ellipse;

public class Piece extends StackPane{
    int playerID;
    int position;
    private PieceType type;

    public Piece(PieceType type, int x, int y){ //Takes the piece type and board coordinates
        this.type = type;

        relocate(x* Main.TILE_SIZE, y* Main.TILE_SIZE);

        Ellipse circle = new Ellipse(Main.TILE_SIZE * 0.3125, Main.TILE_SIZE * 0.26);
        if(type == PieceType.RED) {  //Depending on type, fill circle red or white
            circle.setFill(Color.RED);

        }
        else{
            circle.setFill(Color.WHITE);
        }
        circle.setTranslateX((Main.TILE_SIZE - Main.TILE_SIZE * 0.3125 * 2)/2);
        circle.setTranslateY((Main.TILE_SIZE - Main.TILE_SIZE * 0.26 * 2)/2);
        getChildren().addAll(circle);
    }

    public int getPlayerID() {

        return playerID;
    }
    public int getPosition(){

        return position;
    }

}