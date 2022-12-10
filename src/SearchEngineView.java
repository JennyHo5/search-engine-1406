import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.collections.FXCollections;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class SearchEngineView extends Pane {
    private ListView<String> resultList;
    private TextField searchField;
    private Button searchButton;
    private Label title;
    private RadioButton isBoost;

    public ListView<String> getResultList() { return resultList; }
    public TextField getTextField() { return searchField; }
    public Button getButton() { return searchButton; }
    public RadioButton getRadioButton() { return isBoost;}

    public SearchEngineView() {
        //create a Label
        title = new Label("Hoogle");
        title.relocate(220, 30);
        title.setFont(new Font("Rockwell", 60));

        //create a radio button
        isBoost = new RadioButton("use PageRank boost");
        isBoost.relocate(237, 161);
        isBoost.setPrefSize(166, 38);

        // Create the lists
        resultList = new ListView<String>();
        resultList.relocate(110, 199);
        resultList.setPrefSize(420,240);

        // Create the TextField
        searchField = new TextField();
        searchField.relocate(162, 131);
        searchField.setPrefSize(227,25);

        // Create the button
        searchButton = new Button("Hoogle!");
        searchButton.relocate(403, 131);
        searchButton.setPrefSize(73,25);

        // Add all the components to the Pane
        getChildren().addAll(title, isBoost, resultList, searchField, searchButton);

        setPrefSize(640, 480);
    }

    public void update(List<SearchResult> result) {
        List<String> resultToString = new ArrayList<>();
        for (SearchResult sr : result) {
            String title = sr.getTitle();
            double score = sr.getScore();
            String r = title + ", " + score;
            resultToString.add(r);
        }
        ObservableList<String> displayResult = FXCollections.observableArrayList(resultToString);
        resultList.setItems(displayResult);
    }

}