import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class GameModeDisplay extends Application {
    Label labelIntroSelection, label_unratedRated, label_untimeTimed, label_gameMode;
    ToggleGroup group_ratedUnrated, group_timedUnTimed, group_gameModes;
    RadioButton btn_unrated,btn_rated, btn_timed, btn_untimed, btn_kings, btn_flyingKings, btn_puzzle;

    @Override
    public void start(Stage primaryStage) {//throws Exception{
        primaryStage.setTitle("Checkers Game");
        GridPane grid = new GridPane();

        // create a label
        //labelIntroSelection = new Label("Select for the type of game mode: ");
        label_unratedRated = new Label("Unrated or Rated Match: ");
        label_untimeTimed = new Label("Untimed or Timed Match: ");
        label_gameMode = new Label("What type of game mode?: ");

        // create radiobuttons
        btn_unrated = new RadioButton("Unrated");
        btn_rated = new RadioButton("Rated");
        btn_timed = new RadioButton("Timed");
        btn_untimed = new RadioButton("Not Timed");
        btn_kings = new RadioButton("Kings");
        btn_flyingKings = new RadioButton("Flying Kings");
        btn_puzzle = new RadioButton("Puzzle (No rating for this game mode)");

        // Exit button , closes window
        Button exitBtn = new Button("Quit");
        exitBtn.setOnAction(e-> Platform.exit());//closes window
        HBox layout_exit = new HBox(10);
        layout_exit.setAlignment(Pos.BOTTOM_CENTER);
        layout_exit.getChildren().add(exitBtn);
        grid.add(layout_exit, 1, 7);

        //Next screen
        Button nextBtn = new Button("Next");
        nextBtn.setOnAction(e-> {


        }); //move to desingated screen
        HBox layout_next = new HBox(10);
        layout_next.setAlignment(Pos.BOTTOM_RIGHT);
        layout_next.getChildren().add(nextBtn);
        grid.add(layout_next, 2, 7);

        //Previous screen
        Button previousBtn = new Button("Previous");
        //nextBtn.setOnAction(e-> ...);//move to desingated screen
        HBox layout_previous = new HBox(10);
        layout_previous.setAlignment(Pos.BOTTOM_LEFT);
        layout_previous.getChildren().add(previousBtn);
        grid.add(layout_previous, 0, 7);

        // create a toggle group for radio buttons
        group_ratedUnrated = new ToggleGroup();
        group_timedUnTimed = new ToggleGroup();
        group_gameModes = new ToggleGroup();

        // add radiobuttons to toggle group
        btn_unrated.setToggleGroup(group_ratedUnrated);
        btn_rated.setToggleGroup(group_ratedUnrated);
        btn_timed.setToggleGroup(group_timedUnTimed);
        btn_untimed.setToggleGroup(group_timedUnTimed);
        btn_kings.setToggleGroup(group_gameModes);
        btn_flyingKings.setToggleGroup(group_gameModes);
        btn_puzzle.setToggleGroup(group_gameModes);

        //if buttons are selected,
        // click next to move to the designated screen with the buttons that were chosen
      /*  nextBtn.setOnAction(e->
        {
            //if ,else statements when a certain group of buttons are selected
        }
        );*/

        Text sceneTitle = new Text("Select what you want to play");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BLACK, 15));

        grid.add(sceneTitle, 1,0, 2,1);

        //adding rows with designated labels
        //grid.addRow(0, labelIntroSelection);
        //grid.addRow(0, sceneTitle);
        grid.addRow(1,label_unratedRated, btn_unrated, btn_rated);
        grid.addRow(2, label_untimeTimed, btn_timed, btn_untimed);
        grid.addRow(3, label_gameMode, btn_kings, btn_flyingKings, btn_puzzle);

        //row placements
        ColumnConstraints leftCol = new ColumnConstraints();
        leftCol.setHalignment(HPos.RIGHT);
        leftCol.setHgrow(Priority.SOMETIMES);
        ColumnConstraints rightCol = new ColumnConstraints();
        rightCol.setHalignment(HPos.LEFT);
        rightCol.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(leftCol,rightCol);
        grid.setHgap(10);//5
        grid.setVgap(20);//10
        grid.setPadding(new Insets(20));//20

        // create a scene
        //Scene sc = new Scene(r, 300, 300);
        Scene sc = new Scene(grid);

        // set the scene
        primaryStage.setScene(sc);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}