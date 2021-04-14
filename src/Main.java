

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private Game game = new Game(1,2);

    private Tile[][] board = new Tile[WIDTH][HEIGHT];

    private Group tileGroup = new Group(); //separate Group for tiles and pieces so pieces are on top of tiles
    private Group pieceGroup = new Group();

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);


        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y); //If the sum or the coordinates is even then the tile is light

                board[x][y] = tile; //filling our board up with tiles

                tileGroup.getChildren().add(tile);
                for (int i = 0; i < game._pieces.length; i++) {
                    tile.setPiece(game._pieces[i]);
                }
            }
        }
                for (int i = 0; i < game._pieces.length; i++) {
                    if (game._pieces[i] != null) {
                        pieceGroup.getChildren().add(game._pieces[i]);
                    }
                }



        return root;
    }
    @Override
    public void start (Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Checkers");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public Piece makePiece (PieceType type,int x, int y){
        Piece piece = new Piece(type, x, y);
       piece.setOnMouseReleased(e->{
            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());
       });
        return piece;
    }
    public static Piece makePieceConvert(PieceType type,int row, int position){
        int x = 0;
        int y = Math.abs(row - 8);
        if(row%2 == 1){
            switch (position){
                case 1:
                    x = 0;
                    break;
                case 2:
                    x = 2;
                    break;
                case 3:
                    x = 4;
                    break;
                case 4:
                    x = 6;
                    break;
            }
        }
        else{
           switch (position){
             case 1:
                x = 1;
                break;
             case 2:
                x = 3;
                break;
             case 3:
                x = 5;
                break;
             case 4:
                x = 7;
                break;
           }
        }
        Piece piece = new Piece(type, x, y);

        return piece;
    }
    private int toBoard(double pixel){
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    public static void main (String[]args){
        launch(args);

    }
}
