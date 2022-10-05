package uet.oop.bomberman.level;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Level extends Application {
    public int P1_Score = 0;
    public int P2_Score = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // vertical box
        VBox vertBox = new VBox();
        vertBox.setPrefSize(400, 400);
        vertBox.setAlignment(Pos.CENTER);

        // horizontal box for the players
        HBox PlayerName = new HBox();
        PlayerName.setAlignment(Pos.CENTER);
        PlayerName.setPadding(new Insets(25, 25, 25, 25));

        // horizontal box for the scores
        HBox Score = new HBox();
        Score.setAlignment(Pos.CENTER);

        // horizontal box to show a goal
        HBox Goal = new HBox();
        Goal.setAlignment(Pos.CENTER);

        // player1 name
        Label team1 = new Label("Player 1");
        team1.setTextFill(Color.RED);
        team1.setAlignment(Pos.TOP_LEFT);
        team1.setPadding(new Insets(25, 25, 25, 25));

        // player2 name
        Label team2 = new Label("Player 2");
        team2.setTextFill(Color.BLUE);
        team2.setAlignment(Pos.TOP_RIGHT);
        team2.setPadding(new Insets(25, 25, 25, 25));

        // player1 score
        Label text1 = new Label();
        text1.setAlignment(Pos.CENTER_LEFT);
        text1.setText(Integer.toString(P1_Score));
        text1.setPadding(new Insets(25, 30, 50, 25));

        // player2 score
        Label text2 = new Label();
        text2.setAlignment(Pos.CENTER_RIGHT);
        text2.setText(Integer.toString(P2_Score));
        text2.setPadding(new Insets(25, 30, 50, 25));

        // player1 goal
        Button btn1 = new Button("Player 1 score");
        btn1.setAlignment(Pos.BOTTOM_LEFT);
        btn1.setPadding(new Insets(10, 10, 10, 10));

        // player2 goal
        Button btn2 = new Button("Player 2 score");
        btn2.setPadding(new Insets(10, 10, 10, 10));
        btn2.setAlignment(Pos.BOTTOM_RIGHT);

        // event for player1 goal
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                // increase score
                P1_Score = P1_Score + 1;
                // display player1 score
                text1.setText(Integer.toString(P1_Score));
                if (P1_Score == 3) {
                    resetScore(text1, text2);
                }

            }

        });

        // event for player2 goal
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                // increase player2 score
                P2_Score = P2_Score + 1;
                // display score
                text2.setText(Integer.toString(P2_Score));

                if (P2_Score == 3) {
                    resetScore(text1, text2);

                }
            }


        });

        PlayerName.getChildren().addAll(team1, team2);
        Score.getChildren().addAll(text1, text2);
        Goal.getChildren().addAll(btn1, btn2);

        vertBox.getChildren().addAll(PlayerName, Score, Goal);
        primaryStage.setTitle("Counter");

        Scene scene = new Scene(vertBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void resetScore(Label text1, Label text2) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        P1_Score = 0;
        P2_Score = 0;
        text1.setText(Integer.toString(P1_Score));
        text2.setText(Integer.toString(P2_Score));


    }
}
