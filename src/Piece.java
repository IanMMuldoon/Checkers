

import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Ellipse;

public class Piece extends StackPane {
    public int _playerID;
    public int _row;
    public int _position;
    public boolean _isKing = false;

    private double mouseX, mouseY;
    private double oldX, oldY;

    private PieceType type;

    public Piece(){
        this._position = 0;
        this._row = 0;
        this._playerID = 0;
        this._isKing = false;
    }

    public Piece(PieceType type, int x, int y) { //Takes the piece type and board coordinates
        this.type = type;

        move(x , y );

        Ellipse circle = new Ellipse(Main.TILE_SIZE * 0.3125, Main.TILE_SIZE * 0.26);
        if (type == PieceType.RED) {  //Depending on type, fill circle red or white
            circle.setFill(Color.RED);

        } else {
            circle.setFill(Color.WHITE);
        }
        circle.setTranslateX((Main.TILE_SIZE - Main.TILE_SIZE * 0.3125 * 2) / 2);
        circle.setTranslateY((Main.TILE_SIZE - Main.TILE_SIZE * 0.26 * 2) / 2);
        getChildren().addAll(circle);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });


    setOnMouseDragged(e->{
        relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
    });
}

    public void move(int x, int y){
        oldX = x * Main.TILE_SIZE;
        oldY = y * Main.TILE_SIZE;
        relocate(oldX, oldY);
    }
    public int getPlayerID() {

        return _playerID;
    }
    public int getPosition(){

        return _position;
    }
    public double getOldX(){
        return oldX;
    }
    public double getOldY() {
        return oldY;
    }
}