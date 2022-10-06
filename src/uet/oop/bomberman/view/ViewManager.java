package uet.oop.bomberman.view;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ViewManager {
    private  static final  int HEIGHT = 768; //480
    private static final int WIDTH = 1024; //928
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    private final static int MENU_BUTTON_START_X = 100;
    private final static int MENU_BUTTON_START_Y = 150;

    private BombermanSubScene creditsSubScene;
    private BombermanSubScene helpSubScene;
    private BombermanSubScene scoreSubScene;

    private BombermanSubScene sceneToHide;
    List<BombermanButton> menuButtons;


//    public ViewManager(AnchorPane mainPane, Scene mainScene, Stage mainStage) {
//        this.mainPane = mainPane;
//        this.mainScene = mainScene;
//        this.mainStage = mainStage;
//    }
    public ViewManager() {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createSubScene();
        createButtons();
        createBackground();
        createLogo();

    }

    private void showSubScene(BombermanSubScene subScene){
        if(sceneToHide != null){
            sceneToHide.moveSubScene();
        }
        subScene.moveSubScene();
        sceneToHide = subScene;
    }

    private void createSubScene(){
        creditsSubScene = new BombermanSubScene();
        mainPane.getChildren().add(creditsSubScene);

        helpSubScene = new BombermanSubScene();
        mainPane.getChildren().add(helpSubScene);

        scoreSubScene = new BombermanSubScene();
        mainPane.getChildren().add(scoreSubScene);
    }

    public Stage getMainStage(){
        return  mainStage;
    }

    private void addMenuButton(BombermanButton button){
        button.setLayoutX(MENU_BUTTON_START_X);
        button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size() * 100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private  void createButtons(){
       createStartButton();
       createScoreButton();
       createHelpButton();
       createCreditsButton();
       createExitButton();
    }

    private void createStartButton(){
        BombermanButton startButton = new BombermanButton("PLAY");
        addMenuButton(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // them ho vao day nhe
            }
        });
    }

    private void createScoreButton(){
        BombermanButton scoreButton = new BombermanButton("SCORES");
        addMenuButton(scoreButton);

        scoreButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(scoreSubScene);
            }
        });
    }

    private void createHelpButton(){
        BombermanButton helpButton = new BombermanButton("HELP");
        addMenuButton(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(helpSubScene);
            }
        });
    }

    private void createCreditsButton(){
        BombermanButton creditsButton = new BombermanButton("CREDITS");
        addMenuButton(creditsButton);

        creditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(creditsSubScene);
            }
        });
    }

    private void createExitButton(){
        BombermanButton exitButton = new BombermanButton("EXIT");
        addMenuButton(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });
    }

    private void createBackground() {
        Image backgroundImage = new Image("buttons/background.png.png", 256, 256, false, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }

    private void createLogo(){
        ImageView logo = new ImageView("buttons/logo.png");
        logo.setLayoutX(400);
        logo.setLayoutY(50);

        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(new DropShadow());
            }
        });

        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(null);
            }
        });

        mainPane.getChildren().add(logo);
    }
}



