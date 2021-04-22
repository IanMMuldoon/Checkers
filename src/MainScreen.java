
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.Text;



public class MainScreen extends Application {
    private Stage window;
    private static final int APP_W = 500;
    private static final int APP_H = 300;

    private final BorderPane MainLayout = new BorderPane();
    private final HBox Title = new HBox();
    private final VBox Choices = new VBox(10);
    private Text mainTitle = new Text();

    Button FirstPlayer = new Button("One Player");
    Button SecondPlayer = new Button("Second Player");
    Button Quit = new Button("Quit");





    public void start(Stage primaryStage) throws Exception {
        createTitle();
        createChoices();

        FirstPlayer.setOnAction(e ->{
            window.close();
            System.out.println("Closed first window");
            User playerOne = new User();
            try {
                playerOne.display(false);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        });

        SecondPlayer.setOnAction(e ->{
            window.close();
            User playerTwo = new User();
            try {
                playerTwo.display(true);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        });

        Quit.setOnAction(e -> {
            System.exit(0);
        });

        MainLayout.setTop(Title);
        MainLayout.setCenter(Choices);

        Scene scene = new Scene(MainLayout, APP_W, APP_H);

        window = primaryStage;
        window.setTitle("Checkers");
        window.setScene(scene);
        window.show();


    }





    private void createTitle()
    {
        mainTitle.setText("Checkers");
        mainTitle.setFill(Color.CYAN);
        mainTitle.setFont(Font.font("Arial", 40));
        mainTitle.setStrokeWidth(1);
        mainTitle.setStroke(Color.BLACK);

        Title.setAlignment(Pos.CENTER);
        Title.getChildren().addAll(mainTitle);
    }


    private void createChoices()
    {
        Choices.setAlignment(Pos.BASELINE_CENTER);
        Choices.getChildren().addAll(FirstPlayer, SecondPlayer, Quit);
    }

    public static void main(String[] args) {
        launch(args);
    }






}
