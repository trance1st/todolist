import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class RegisterPage {

    public void build(Stage s) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);
        Label labelName = new Label("Nume");
        TextField textFieldName = new TextField();
        gridPane.add(labelName, 0, 0);
        gridPane.add(textFieldName, 1, 0);

        Label labelEmail = new Label("Email");
        TextField textFieldEmail = new TextField();
        gridPane.add(labelEmail, 0, 1);
        gridPane.add(textFieldEmail, 1, 1);

        Label labelPassword = new Label("Password");
        TextField textFieldPassword = new TextField();
        gridPane.add(labelPassword, 0, 2);
        gridPane.add(textFieldPassword, 1, 2);

        Button buttonSubmit = new Button("Register");
        buttonSubmit.setOnMouseClicked( e -> {
            try {
                Statement st = Storage.connection.createStatement();
                String query =
                        String.format("insert into user values(null, '%s', '%s', '%s')",
                                textFieldName.getText(),
                                textFieldEmail.getText(),
                                textFieldPassword.getText()
                        );
                st.executeUpdate(query);
                LoginPage loginPage = new LoginPage();
                loginPage.build(s);
            } catch (SQLIntegrityConstraintViolationException e2) {
                //II afisez un mesaj.
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        Button buttonBack = new Button("Back");
        buttonBack.setOnMouseClicked( e -> {
            LoginPage loginPage = new LoginPage();
            loginPage.build(s);
        });
        gridPane.add(buttonSubmit, 0, 3);
        gridPane.add(buttonBack, 1, 3);
        Scene scene = new Scene(gridPane, 600, 600);
        s.setScene(scene);


    }
}
