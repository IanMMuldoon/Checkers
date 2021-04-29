

import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Ellipse;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;


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

        Ellipse circle = new Ellipse(Main.TILE_SIZE * 0.3125, Main.TILE_SIZE * 0.26);
        if (type == PieceType.RED)   //Depending on type, fill circle red or white
            circle.setFill(Color.RED);
        else
            circle.setFill(Color.WHITE);

        circle.setTranslateX((Main.TILE_SIZE - Main.TILE_SIZE * 0.3125 * 2) / 2);
        circle.setTranslateY((Main.TILE_SIZE - Main.TILE_SIZE * 0.26 * 2) / 2);


        if (_isKing) {
            t.setTranslateX((Main.TILE_SIZE - Main.TILE_SIZE * 0.3125 * 2) / 2);
            t.setTranslateY((Main.TILE_SIZE - Main.TILE_SIZE * 0.26 * 2) / 2);
            this.getChildren().addAll(circle, t);
        }else{

            this.getChildren().addAll(circle);
        }
    }
    public void move(int x, int y)
    {
        oldX = x * Main.TILE_SIZE;
        oldY = y * Main.TILE_SIZE;
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