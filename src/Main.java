
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private Game game = new Game(1, 2);

    private Group tileGroup = new Group(); //separate Group for tiles and pieces so pieces are on top of tiles
    private Group pieceGroup = new Group();

    private Button takeback;
    private Button forfeit;
    private Button retMenu;

    private Parent createContent() throws IOException {
        BorderPane root = new BorderPane();
        Pane board = new Pane();
        Pane sidePane = FXMLLoader.load(getClass().getResource("/SideMenu.fxml"));
        board.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        board.getChildren().addAll(tileGroup, pieceGroup);

        this.setButtons(sidePane);

        root.setCenter(board);
        root.setLeft(sidePane);

        this.DrawTiles();
        this.DrawPieces();

        return root;
    }

    private void setButtons (Pane Parent) throws IOException {
        takeback = (Button) Parent.getChildren().get(0);
        forfeit = (Button) Parent.getChildren().get(1);
        retMenu = (Button) Parent.getChildren().get(2);
    }

    public void DrawTiles() {
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

    public void DrawPieces() {
        for (int i = 0; i < game._pieces.length; i++) {
            if (game._pieces[i] != null) {
                game._pieces[i].CreateCircle();

                //Make copy of iterator to pass to event
                Piece piece = game._pieces[i];

                takeback.setOnMouseClicked(mouseEvent -> {          //Need to have prompt, then logic for take back

                });

                forfeit.setOnMouseClicked(mouseEvent -> {           //Need to find which player's turn it is, then end game

                });

                retMenu.setOnMouseClicked(mouseEvent -> {           //End game and return to menu, when menu is made

                });


                game._pieces[i].setOnMousePressed(e ->
                {
                    piece._isDragging = (piece._playerID == game._getCurrentPlayerID());
                    if (piece._isDragging) {

                        piece.mouseX = e.getSceneX();
                        piece.mouseY = e.getSceneY();
                    }
                });

                game._pieces[i].setOnMouseDragged(e ->
                {
                    if (piece._isDragging)
                        piece.relocate(e.getSceneX() - piece.mouseX + piece.oldX, e.getSceneY() - piece.mouseY + piece.oldY);
                });

                piece.setOnMouseReleased(e ->
                {
                    int newX = toBoard(piece.getLayoutX());
                    int newY = toBoard(piece.getLayoutY());

                    int x0 = toBoard(piece.getOldX());
                    int y0 = toBoard(piece.getOldY());

                    Position convert = Position.getPieceRP(newX, newY, 0);

                    if (!convert._isValid || !game._movePiece(piece, convert._row, convert._position)) {
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

                    if (game.isGameOver())
                    {
                        System.out.println("Game Over!");

                        this._clearPieces();

                        game._reset();

                        this.DrawPieces();

                        HistoryRecord[] records = HistoryFile.GetRecords();

                        if(game._getWinnerUserID() == game._getPlayer1UserID())
                        {
                            //Player 1 victory
                        }
                        else if(game._getWinnerUserID() == game._getPlayer2UserID())
                        {
                            //Player 2 victory
                        }
                        else
                        {
                            //We fucked up
                        }
                    }

                });

                try
                {
                    pieceGroup.getChildren().add(game._pieces[i]);
                }
                catch (Exception ex)
                {

                }
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

    private void _clearPieces()
    {
        this.pieceGroup.getChildren().clear();
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
