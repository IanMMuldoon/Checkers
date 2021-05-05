
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;

public class Main extends Application {
    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private Game game = new Game(1,2);

    private Group tileGroup = new Group(); //separate Group for tiles and pieces so pieces are on top of tiles
    private Group pieceGroup = new Group();


    private Parent createContent() throws Exception{
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);

        Button ProposeTakeback = new Button("Propose \nTakeback");
        ProposeTakeback.setLayoutX(830);
        ProposeTakeback.setLayoutY(200);
        ProposeTakeback.setMinWidth(150);
        ProposeTakeback.setMinHeight(100);
        ProposeTakeback.setStyle("-fx-font-size:20");
        ProposeTakeback.setOnAction(e -> {
            game._proposedTakeback();
            Piece piece = game.takeBack;
            /*

                The logic is done. Still have to move pieces.
             */
        });

        Button Forfeit = new Button("Forfeit");
        Forfeit.setLayoutX(830);
        Forfeit.setLayoutY(500);
        Forfeit.setMinWidth(150);
        Forfeit.setMinHeight(100);
        Forfeit.setStyle("-fx-font-size:20");
        Forfeit.setOnAction(e ->{
            game._callForfeit();
            /*
            * The logic is done. Just have to close window and update Match History or Player stats.
            * */
        });

        Button Draw = new Button("Draw");
        Draw.setLayoutX(830);
        Draw.setLayoutY(350);
        Draw.setMinWidth(150);
        Draw.setMinHeight(100);
        Draw.setStyle("-fx-font-size:20");
        Draw.setOnAction( e-> {
            game._callDraw();
            /*
            * Logic is done. Just have to close window and update data
            * */
        });

        root.getChildren(). addAll(ProposeTakeback, Forfeit, Draw);
        DrawTiles();
        DrawPieces();

        return root;
    }
    public void DrawTiles(){
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y); //If the sum or the coordinates is even then the tile is light

                game.board[x][y] = tile;

                tileGroup.getChildren().add(tile);
                for (int i = 0; i < game._pieces.length; i++) {
                    tile.setPiece(game._pieces[i]);
                }
            }
        }
    }
    public void DrawPieces()
    {

        for (int i = 0; i < game._pieces.length; i++)
        {
            if (game._pieces[i] != null) {
                game._pieces[i].CreateCircle();

                //Make copy of iterator to pass to event
                Piece piece = game._pieces[i];

                game._pieces[i].setOnMousePressed(e ->
                {
                    piece._isDragging = (piece._playerID == game._getCurrentPlayerID());
                    if (piece._isDragging)
                    {

                        piece.mouseX = e.getSceneX();
                        piece.mouseY = e.getSceneY();
                    }
                });

                game._pieces[i].setOnMouseDragged(e->
                {
                    if (piece._isDragging)
                        piece.relocate(e.getSceneX() - piece.mouseX + piece.oldX, e.getSceneY() - piece.mouseY + piece.oldY);
                });

                piece.setOnMouseReleased(e->
                {
                    int newX = toBoard(piece.getLayoutX());
                    int newY = toBoard(piece.getLayoutY());

                    int x0 = toBoard(piece.getOldX());
                    int y0 = toBoard(piece.getOldY());

                    Position convert = Position.getPieceRP(newX, newY, 0);

                    if (!convert._isValid || ! game._movePiece(piece, convert._row, convert._position))
                    {
                        piece.move(x0, y0);
                    }
                    else
                    {
                        this._removeMissingPieces();
                    }
                    game._kingPiece(piece);
                    if(piece._isKing){
                        piece.CreateCircle();
                    }

                });

                pieceGroup.getChildren().add(game._pieces[i]);
            }
        }
    }

    private void _removeMissingPieces()
    {
        List<Piece> pieces = new ArrayList<>(Arrays.asList(game._pieces));

        for (int i = 0; i < this.pieceGroup.getChildren().stream().count(); i++)
        {
            try
            {
                Node node  = this.pieceGroup.getChildren().get(i);

                //If the child object is a Piece and it is not in the Game Piece collection, remove it
                if (node instanceof Piece && !Arrays.stream(game._pieces).anyMatch(p -> p == (Piece) node))
                {
                    this.pieceGroup.getChildren().remove(node);
                }
            }
            catch (Exception ex)
            {

            }
        }
    }

    @Override
    public void start (Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Checkers");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private int toBoard(double pixel){
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    public static void main (String[]args){
        launch(args);

    }
}
