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


    public BombermanSubScene() {
        super(new AnchorPane(), 300, 250);
        prefWidth(600);
        prefHeight(400);
        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 300, 250, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));

        setLayoutX(1014);
        setLayoutY(180 - 30);
    }

    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        if (isHidden) {
            transition.setToX(-676);
            isHidden = false;
        } else {
            transition.setToX(0);
            isHidden = true;
        }

        transition.play();
    }
}