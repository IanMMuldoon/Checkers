import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Ellipse;

import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Piece extends StackPane {
    public int _playerID;
    public int _row;
    public int _position;
    public boolean _isKing = false;
    public int x, y;

    public boolean _isDragging;

    public double mouseX, mouseY;
    public double oldX, oldY;

    public PieceType type;

    public Piece()
    {
        this._position = 0;
        this._row = 0;
        this._playerID = 0;
        this._isKing = false;
    }

    public void CreateCircle() {
        Text t = new Text();
        t.setText("K");
        t.setFont(Font.font("Verdana", 20));

        Position position = Position.getPieceXY(this);
        move(position._x, position._y);

        Ellipse circle = new Ellipse(GameScreen.TILE_SIZE * 0.3125, GameScreen.TILE_SIZE * 0.26);
        if (type == PieceType.RED)   //Depending on type, fill circle red or white
            circle.setFill(Color.RED);
        else
            circle.setFill(Color.WHITE);

        circle.setTranslateX((GameScreen.TILE_SIZE - GameScreen.TILE_SIZE * 0.3125 * 2) / 2);
        circle.setTranslateY((GameScreen.TILE_SIZE - GameScreen.TILE_SIZE * 0.26 * 2) / 2);


        if (_isKing) {
            t.setTranslateX((GameScreen.TILE_SIZE - GameScreen.TILE_SIZE * 0.3125 * 2) / 2);
            t.setTranslateY((GameScreen.TILE_SIZE - GameScreen.TILE_SIZE * 0.26 * 2) / 2);
            this.getChildren().addAll(circle, t);
        }else{

            this.getChildren().addAll(circle);
        }
    }
    public void move(int x, int y)
    {
        oldX = x * GameScreen.TILE_SIZE;
        oldY = y * GameScreen.TILE_SIZE;
        relocate(oldX, oldY);
    }
    public int getPlayerID()
    {
        return _playerID;
    }
    public int getPosition()
    {
        return _position;
    }
    public double getOldX(){
        return oldX;
    }
    public double getOldY() {
        return oldY;
    }
}