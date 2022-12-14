package uet.oop.bomberman.menu.start;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BombermanSubScene extends SubScene {
    private final static String FONT_PATH = "buttons/kenvector_future.ttf";
    private final static String BACKGROUND_IMAGE = "buttons/panel.png";

    private boolean isHidden = true;

    private int tranHiddenX = -676;
    private int tranShowX = 0;

    public BombermanSubScene(ViewManager.menu option) {
        super(new AnchorPane(), 300, 260);
        prefWidth(600);
        prefHeight(400);
        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 300, 260, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();
        switch (option) {
            case HELP:
                root2.getChildren().add(createHelpContent());
                break;
            case SCORES:
                VBox box = createScoresContent();
                if(box != null) {
                    root2.getChildren().add(box);
                }
                break;
        }

        root2.setBackground(new Background(image));

//        setLayoutX(layoutX);
//        setLayoutY(layoutY);
        setLayoutX(1014);
        setLayoutY(180 - 30);
    }

    public int getTranHiddenX() {
        return tranHiddenX;
    }

    public void setTranHiddenX(int tranHiddenX) {
        this.tranHiddenX = tranHiddenX;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public int getTranShowX() {
        return tranShowX;
    }

    public void setTranShowX(int tranShowX) {
        this.tranShowX = tranShowX;
    }

    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);
        if (isHidden) {
            transition.setToX(tranHiddenX);
            isHidden = false;
        } else {
            transition.setToX(tranShowX);
            isHidden = true;
        }

        transition.play();
    }
    public Label createLabel(String text, int size, int x, int y) {
        Label label = new Label(text);
        label.setFont(new Font(FONT_PATH, size));
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setMaxWidth(500);
        label.setWrapText(true);
        label.setGraphicTextGap(30);
        return label;
    }

    public VBox createHelpContent() {
        Label label = createLabel("H?????ng d???n c??ch ch??i:", 25, 50, 50);
        Label label1 = createLabel("Di chuy???n sang tr??i", 15, 50, 50);
        ImageView leftIcon = new ImageView(new Image("buttons/key_left.png", 20, 20, false, true));
        label1.setGraphic(leftIcon);
        Label label2 = createLabel("Di chuy???n sang ph???i", 15, 50, 50);
        ImageView rightIcon = new ImageView(new Image("buttons/key_right.png", 20, 20, false, true));
        label2.setGraphic(rightIcon);
        Label label3 = createLabel("Di chuy???n l??n tr??n", 15, 50, 50);
        ImageView upIcon = new ImageView(new Image("buttons/key_up.png", 20, 20, false, true));
        label3.setGraphic(upIcon);
        Label label4 = createLabel("Di chuy???n xu???ng d?????i", 15, 50, 50);
        ImageView downIcon = new ImageView(new Image("buttons/key_down.png", 20, 20, false, true));
        label4.setGraphic(downIcon);
        Label label5 = createLabel("?????t bomb", 15, 50, 50);
        ImageView spaceIcon = new ImageView(new Image("buttons/key_space.png", 30, 30, false, true));
        label5.setGraphic(spaceIcon);
        VBox box = new VBox(6);
        box.getChildren().addAll(label, label1, label2, label3, label4, label5);
        box.setLayoutX(40);
        box.setLayoutY(40);
        return box;
    }

    public VBox createScoresContent()  {
        try{
            File file = new File("res/score/highscores.txt");
            Scanner sc = new Scanner(file);
            VBox box = new VBox(10);
            box.setLayoutX(50);
            box.setLayoutY(50);
            Label text = createLabel(/*"Top ng?????i ch??i ??i???m cao:"*/"HIGH SCORE: ", 20, 30 + 270, 50);
            box.getChildren().add(text);
            List<Integer> highScore = new ArrayList<>();
            while(sc.hasNextLine()) {
                highScore.add(Integer.parseInt(sc.nextLine()));
//                Label label = createLabel(" - " + sc.nextLine(), 15, 200, 50);
//                box.getChildren().add(label);
            }
            highScore.sort(Collections.reverseOrder());
            for(int i = 0; i < Math.min(4, highScore.size()); i++) {
                Label label = createLabel("Anonymous #" + (i + 1) + " - "  + highScore.get(i).toString(), 15, 200 + 270, 50);
                box.getChildren().add(label);
            }
            return box;
        } catch (Exception fileException) {
            System.out.println(fileException.getMessage());
        }
        return null;
    }
}