

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle{

    public Piece piece;

    public Tile(boolean light, int x, int y){ // light is used for the 2 types of tiles(light and dark). x and y refer to tile coordinates, not pixels
        setWidth(GameScreen.TILE_SIZE);
        setHeight(GameScreen.TILE_SIZE);

        relocate(x* GameScreen.TILE_SIZE, y * GameScreen.TILE_SIZE);

        if(light){
            setFill(Color.valueOf("#feb"));
        }
        else{
            setFill(Color.valueOf("#582"));
        }
    }

    public boolean hasPiece(){ //returns true if that tile has a piece

        return piece != null;
    }

    public Piece getPiece() {

        return piece;
    }

    public void setPiece(Piece piece) {

        this.piece = piece;
    }

}
