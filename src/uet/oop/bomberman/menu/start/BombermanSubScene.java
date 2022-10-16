package uet.oop.bomberman.menu.start;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class BombermanSubScene extends SubScene {
    private final static String FONT_PATH = "buttons/kenvector_future.ttf";
    private final static String BACKGROUND_IMAGE = "buttons/panel.png";

    private boolean isHidden;

    private int tranHiddenX = -676;
    private int tranShowX = 0;

    public BombermanSubScene(int layoutX, int layoutY) {
        super(new AnchorPane(), 300, 260);
        prefWidth(600);
        prefHeight(400);
        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 300, 260, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));

        setLayoutX(layoutX);
        setLayoutY(layoutY);
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
}