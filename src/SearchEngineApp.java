import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

public class SearchEngineApp extends Application {
    ProjectTesterImp model;

    public SearchEngineApp() {
        model = new ProjectTesterImp();
    }


    public void start(Stage primaryStage) {
        Pane  aPane = new Pane();

        // Create the view
        SearchEngineView view = new SearchEngineView();
        aPane.getChildren().add(view);

        //handlers
        //when pressing hoogle button
        view.getButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String searchQuery = view.getTextField().getText();
                boolean boost = view.getRadioButton().isSelected();
                List<SearchResult> result = model.search(searchQuery, boost, 10);
                view.update(result);
            }
        });

        primaryStage.setTitle("Hoogle");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(aPane));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
