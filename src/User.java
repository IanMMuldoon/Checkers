import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;



public class User  {
    Stage window = new Stage();
    GridPane MainLayout = new GridPane();



    public void display(boolean twoPlayer) throws Exception{

        MainLayout.setPadding(new Insets(10, 10, 10, 10));
        MainLayout.setVgap(10);
        MainLayout.setHgap(10);

        Label username = new Label("Username:");
        GridPane.setConstraints(username, 0, 0);


        TextField nameInput = new TextField();
        nameInput.setPromptText("username");
        GridPane.setConstraints(nameInput, 1, 0);

        Label password= new Label("Password:");
        GridPane.setConstraints(password, 0, 1);

        TextField passwordInput = new TextField();
        passwordInput.setPromptText("password");
        GridPane.setConstraints(passwordInput, 1, 1);

        Button login = new Button("Login");
        GridPane.setConstraints(login, 1, 2);

        Button back = new Button("Back");
        GridPane.setConstraints(back, 0,2);



        if(twoPlayer == true) {
            login.setOnAction(e ->{
                System.out.println("You arent where you need to be good luck ");
                window.close();
                User secondLogin = new User();
                try {
                    secondLogin.display(false);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }


            });
        } else {
            login.setOnAction(e ->{
                System.out.println("You are where you think you are");
                window.close();
                System.out.println("You should have closed the window");
                MainMenu mainMenu = new MainMenu();
                try {
                    mainMenu.start(window);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }


            });
        }


        back.setOnAction(e ->{
            window.close();
            MainScreen mainScreen= new MainScreen();
            try {
                mainScreen.start(window);

            } catch (Exception exception) {
                exception.printStackTrace();
            }


        });



        MainLayout.getChildren().addAll(username, nameInput, passwordInput, password, login, back);

        Scene scene = new Scene(MainLayout, 300, 300);

        Stage window = new Stage();
        window.setTitle("User Login");
        window.setScene(scene);
        window.show();

    }







}
