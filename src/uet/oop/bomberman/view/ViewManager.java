package uet.oop.bomberman.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import uet.oop.bomberman.BombermanGame;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class ViewManager {
    private  static final  int HEIGHT = 480;
    private static final int WIDTH = 928;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    public ViewManager(AnchorPane mainPane, Scene mainScene, Stage mainStage) {
        this.mainPane = mainPane;
        this.mainScene = mainScene;
        this.mainStage = mainStage;
    }

    public ViewManager() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        createButtons();
    }

    public Stage getMainStage(){
        return  mainStage;
    }

    private  void createButtons(){
        BombermanButton button = new BombermanButton("click");
        mainPane.getChildren().add(button);
        button.setLayoutX(200);
        button.setLayoutY(200);
    }

    private void createBackground(){
        Image backgroundImage = new Image() {
            @Override
            public int getWidth(ImageObserver observer) {
                return 0;
            }

            @Override
            public int getHeight(ImageObserver observer) {
                return 0;
            }

            @Override
            public ImageProducer getSource() {
                return null;
            }

            @Override
            public Graphics getGraphics() {
                return null;
            }

            @Override
            public Object getProperty(String name, ImageObserver observer) {
                return null;
            }
        };

    }

}


