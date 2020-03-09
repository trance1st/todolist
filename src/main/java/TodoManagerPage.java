import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class TodoManagerPage {

    public void build(Stage stage) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.BASELINE_CENTER);
        gridPane.setVgap(40);
        gridPane.setHgap(40);
        TextField todo = new TextField();
        ListView<String> todos = new ListView<>();

        try {
            Statement stGetTodos = Storage.connection.createStatement();
            String query = String.format("select * from todo where user_id=%s",
                    Storage.userId);
            ResultSet resultSet = stGetTodos.executeQuery(query);
            while (resultSet.next()) {
                String todoCurrent = resultSet.getString("description");
                todos.getItems().add(todoCurrent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Button add = new Button("Adauga");
        add.setOnMouseClicked( e -> {
            String todoText = todo.getText();

            try {
                Statement st = Storage.connection.createStatement();
                String query = String.format(
                        "insert into todo values(null, %s, '%s')",
                        Storage.userId,
                        todoText
                );
                st.executeUpdate(query);

                todos.getItems().add(todoText);
                todo.setText("");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        gridPane.add(todo, 0, 0);
        gridPane.add(add, 1, 0);

        Button delete = new Button("Stergere");
        delete.setOnMouseClicked( e -> {
            try {
                Statement st = Storage.connection.createStatement();
                String selected = todos.getSelectionModel().getSelectedItem();
                String query = String.format("delete from todo where description='%s'",
                        selected);
                st.executeUpdate(query);
                todos.getItems().removeAll(selected);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        gridPane.add(todos, 0, 1);
        gridPane.add(delete, 1, 1);

        Scene scene = new Scene(gridPane, 600, 600);
        stage.setScene(scene);
    }
}
