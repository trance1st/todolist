import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginPage {

    public void build(final Stage stage) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.BASELINE_CENTER);
        gridPane.setPadding(new Insets(30, 0, 0, 0 ));
        Label labelEmail = new Label("Email");
        TextField textFieldEmail = new TextField();

        Label labelPassword = new Label("Password");
        TextField textFieldPassword = new TextField();
        Button buttonLogin = new Button("Login");
        Label labelRes = new Label();
        buttonLogin.setOnMouseClicked( e -> {
            try {
                Statement st = Storage.connection.createStatement();
                String query = String.format(
                        "select * from user where email='%s' and password='%s'",
                        textFieldEmail.getText(),
                        textFieldPassword.getText()
                );
                ResultSet rsLogin = st.executeQuery(query);
                if(rsLogin.next()) {
                    int userId = rsLogin.getInt("id");
                    Storage.userId = userId;
                    TodoManagerPage todoManagerPage = new TodoManagerPage();
                    todoManagerPage.build(stage);
                } else {
                    labelRes.setText("Credentiale incorecte");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        Button buttonRegister = new Button("Register");
        buttonRegister.setOnMouseClicked( e -> {
            RegisterPage registerPage = new RegisterPage();
            registerPage.build(stage);
        });
        gridPane.add(labelEmail, 0, 0);
        gridPane.add(textFieldEmail, 1, 0);
        gridPane.add(labelPassword, 0, 1);
        gridPane.add(textFieldPassword, 1,1);
        gridPane.add(buttonLogin, 0, 2);
        gridPane.add(buttonRegister, 1, 2);
        gridPane.add(labelRes, 2, 2);
        Scene scene = new Scene(gridPane, 600, 600);
        stage.setScene(scene);
        stage.show();
    }
}
