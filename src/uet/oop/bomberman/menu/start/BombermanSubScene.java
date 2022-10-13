package uet.oop.bomberman.menu.start;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BombermanSubScene extends SubScene {

    private final  static String FONT_PATH = "buttons/kenvector_future.ttf";
    private final static String BACKGROUND_IMAGE = "buttons/panel.png";

    private boolean isHidden = true;


    public BombermanSubScene(ViewManager.menu option) {
        super(new AnchorPane(), 300, 250);
        prefWidth(600);
        prefHeight(400);
        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE,300, 250, false, true),
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

        setLayoutX(1014);
        setLayoutY(180 - 30);
    }
    public void moveSubScene(){
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        if(isHidden){
            transition.setToX(-676);
            isHidden = false;
        }else{
            transition.setToX(0);
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
        Label label = createLabel("Hướng dẫn cách chơi:", 30, 50, 50);
        Label label1 = createLabel("Di chuyển sang trái", 20, 50, 50);
        ImageView leftIcon = new ImageView(new Image("buttons/key_left.png", 20, 20, false, true));
        label1.setGraphic(leftIcon);
        Label label2 = createLabel("Di chuyển sang phải", 20, 50, 50);
        ImageView rightIcon = new ImageView(new Image("buttons/key_right.png", 20, 20, false, true));
        label2.setGraphic(rightIcon);
        Label label3 = createLabel("Di chuyển lên trên", 20, 50, 50);
        ImageView upIcon = new ImageView(new Image("buttons/key_up.png", 20, 20, false, true));
        label3.setGraphic(upIcon);
        Label label4 = createLabel("Di chuyển xuống dưới", 20, 50, 50);
        ImageView downIcon = new ImageView(new Image("buttons/key_down.png", 20, 20, false, true));
        label4.setGraphic(downIcon);
        Label label5 = createLabel("Đặt bomb", 20, 50, 50);
        ImageView spaceIcon = new ImageView(new Image("buttons/key_space.png", 30, 30, false, true));
        label5.setGraphic(spaceIcon);
        VBox box = new VBox(10);
        box.getChildren().addAll(label, label1, label2, label3, label4, label5);
        box.setLayoutX(50);
        box.setLayoutY(50);
        return box;
    }

    public VBox createScoresContent()  {
        try{
            File file = new File("res/buttons/scores.txt");
            Scanner sc = new Scanner(file);
            VBox box = new VBox(10);
            box.setLayoutX(50);
            box.setLayoutY(50);
            Label text = createLabel("Top người chơi điểm cao nhất:", 30, 50, 50);
            box.getChildren().add(text);
            while(sc.hasNextLine()) {
                Label label = createLabel(" - " + sc.nextLine(), 20, 200, 50);
                box.getChildren().add(label);
            }
            return box;
        } catch (FileNotFoundException fileException) {
            System.out.println(fileException);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}