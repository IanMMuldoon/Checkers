
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameScreen extends Application {
    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    private static Stage gameStage;

    public static String Winner;


    private static Group tileGroup = new Group(); //separate Group for tiles and pieces so pieces are on top of tiles
    private static Group pieceGroup = new Group(); //Is there a problem if these are static?



    public Parent createContent()
    {
        LoginController.game = new Game(LoginController.getPlayer1ID(), LoginController.getPlayer2ID());
        this.DrawTiles();
        this.DrawPieces();

        return changeSideScene("SideMenu.fxml");
    }

    public void DrawTiles() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y); //If the sum or the coordinates is even then the tile is light

                LoginController.game.board[x][y] = tile;

                tileGroup.getChildren().add(tile);
                for (int i = 0; i < LoginController.game._pieces.length; i++) {
                    tile.setPiece(LoginController.game._pieces[i]);
                }
            }
        }
    }

    public void DrawPieces() {
        for (int i = 0; i < LoginController.game._pieces.length; i++) {
            if (LoginController.game._pieces[i] != null) {
                LoginController.game._pieces[i].CreateCircle();

                //Make copy of iterator to pass to event
                Piece piece = LoginController.game._pieces[i];

                LoginController.game._pieces[i].setOnMousePressed(e ->
                {
                    piece._isDragging = (piece._playerID == LoginController.game._getCurrentPlayerID());
                    if (piece._isDragging) {

                        piece.mouseX = e.getSceneX();
                        piece.mouseY = e.getSceneY();
                    }
                });

                LoginController.game._pieces[i].setOnMouseDragged(e ->
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

                    if (!convert._isValid || !LoginController.game._movePiece(piece, convert._row, convert._position)) {
                        piece.move(x0, y0); //Resets piece back to original position
                    }
                    else
                    {
                        this._removeMissingPieces();
                    }
                    LoginController.game._kingPiece(piece);
                    if(piece._isKing){
                        piece.CreateCircle();
                    }

                    if (LoginController.game.isGameOver())
                    {
                        GameOverController gameovercontroller = new GameOverController();
                        HistoryRecord[] records = HistoryFile.GetRecords();

                        if(LoginController.game._getWinnerUserID() == LoginController.game._getPlayer1UserID())
                        {
                            //Player 1 victory
                            HistoryFile.RecordWin(LoginController.game._getPlayer1UserID());
                            HistoryFile.RecordLoss(LoginController.game._getPlayer2UserID());
                            Winner = LoginController.getPlayerOneName();
                        }
                        else if(LoginController.game._getWinnerUserID() == LoginController.game._getPlayer2UserID())
                        {
                            //Player 2 victory
                            HistoryFile.RecordWin(LoginController.game._getPlayer2UserID());
                            HistoryFile.RecordLoss(LoginController.game._getPlayer1UserID());
                            Winner = LoginController.getPlayerTwoName();
                            //
                        }
                        try {
                            changeScene("GameOver.fxml");
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });

                try
                {
                    pieceGroup.getChildren().add(LoginController.game._pieces[i]);
                }
                catch (Exception ex)
                {

                }
            }
        }
    }

    public void Forfeit (ActionEvent actionEvent) throws IOException {
        gameStage.setScene(new Scene(changeSideScene("ForfeitPrompt.fxml")));
    }

    public void YesForfeit (ActionEvent actionEvent) throws IOException {
        HistoryFile.RecordLoss(LoginController.game._getCurrentPlayerID());

        if(LoginController.game._getCurrentPlayerID() == LoginController.getPlayer1ID()) {
            //Player 2 wins
            HistoryFile.RecordWin(LoginController.getPlayer2ID());
            Winner = LoginController.getPlayerTwoName();
        }
        else if(LoginController.game._getCurrentPlayerID() == LoginController.getPlayer2ID()) {
            //Player 1 wins
            HistoryFile.RecordWin(LoginController.getPlayer1ID());
            Winner = LoginController.getPlayerOneName();
        }

        changeScene("GameOver.fxml");
    }

    public void NoForfeit (ActionEvent actionEvent) {
        gameStage.setScene(new Scene(changeSideScene("SideMenu.fxml")));
    }

    public void DrawAsk (ActionEvent actionEvent) {
        gameStage.setScene(new Scene(changeSideScene("DrawGamePrompt.fxml")));
    }
    
    public void ReturnToMenu (ActionEvent actionEvent) throws IOException {
        gameStage.setScene(new Scene(changeSideScene("ReturnMainMenuPrompt.fxml")));
    }

    public void YesReturnToMenu (ActionEvent actionEvent) throws IOException {
        LoginController.game._reset();
        changeScene("MainMenu.fxml");
    }

    public void NoReturnToMenu (ActionEvent actionEvent) throws IOException {
        gameStage.setScene(new Scene(changeSideScene("SideMenu.fxml")));    }

    public void YesDraw (ActionEvent actionEvent) throws IOException {
        LoginController.game._reset();
        Winner = "Nobody";
        changeScene("GameOver.fxml");
    }

    public void NoDraw (ActionEvent actionEvent) {
        gameStage.setScene(new Scene(changeSideScene("SideMenu.fxml")));
    }

    private void _removeMissingPieces()
    {
        List<Piece> pieces = new ArrayList<>(Arrays.asList(LoginController.game._pieces));

        for (int i = 0; i < this.pieceGroup.getChildren().stream().count(); i++)
        {
            try
            {
                Node node  = this.pieceGroup.getChildren().get(i);

                //If the child object is a Piece and it is not in the Game Piece collection, remove it
                if (node instanceof Piece && !Arrays.stream(LoginController.game._pieces).anyMatch(p -> p == (Piece) node))
                {
                    this.pieceGroup.getChildren().remove(node);
                }
            }
            catch (Exception ex)
            {

            }
        }
    }

    public void _clearPieces()
    {
        this.pieceGroup.getChildren().clear();
    }

    @Override
    public void start (Stage primaryStage) throws Exception {
        gameStage = primaryStage;
//        gameStage.initStyle(StageStyle.UNDECORATED);
        gameStage.setScene(new Scene(createContent()));
        changeScene("MainMenu.fxml");
        gameStage.show();
    }
    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(
                getClass().getResource(fxml));

        Scene scene = new Scene(pane);
        gameStage.setScene(scene);
    }
    public void changeGameScene(){
        Scene scene = new Scene(createContent());
        gameStage.setTitle("Checkers");
        gameStage.setScene(scene);
    }

    public Pane changeSideScene(String fxml){
        BorderPane root = new BorderPane();
        Pane board = new Pane();
        Pane sidePane = null;
        try {
            sidePane = FXMLLoader.load(getClass().getResource(fxml));
        } catch (IOException e) {
            e.printStackTrace();
        }
        board.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        board.getChildren().addAll(tileGroup, pieceGroup);

        root.setCenter(board);
        root.setLeft(sidePane);

        return root;
    }

    private int toBoard(double pixel){
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    public static void main (String[]args){
        launch(args);

    }
}
