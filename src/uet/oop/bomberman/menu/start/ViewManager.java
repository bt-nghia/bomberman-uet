package uet.oop.bomberman.menu.start;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class ViewManager {
    private static final int WIDTH = BombermanGame.CAMERA_WIDTH * Sprite.SCALED_SIZE; //928
    private static final int HEIGHT = BombermanGame.CAMERA_HEIGHT * Sprite.SCALED_SIZE; //480
    private final static int MENU_BUTTON_START_X = 100;
    private final static int MENU_BUTTON_START_Y = 150;
    private static AnchorPane mainPane;
    List<BombermanButton> menuButtons;
    private final Scene mainScene;
    private final Stage mainStage;
    private BombermanSubScene creditsSubScene;
    private BombermanSubScene helpSubScene;
    private BombermanSubScene scoreSubScene;
    private Group groupRoot;
    private BombermanSubScene sceneToHide;


    public ViewManager(AnchorPane mainPane, Scene mainScene, Stage mainStage) {
        ViewManager.mainPane = mainPane;
        this.mainScene = mainScene;
        this.mainStage = mainStage;
    }

    public ViewManager(Scene mainScene, Stage stage, Group root) {
        this.mainScene = mainScene;
        this.mainStage = stage;
        this.groupRoot = root;
        mainPane = new AnchorPane();
        menuButtons = new ArrayList<>();
        createBackground();
        stage.setScene(mainScene);
        createSubScene();
        createButtons();
        createLogo();
        root.getChildren().add(mainPane);
    }

    public ViewManager() {
        createBackground();
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createSubScene();
        createButtons();
        createLogo();
    }

    private static void createBackground() {
        Image backgroundImage = new Image("buttons/background.png", 256, 256, false, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
//        ImageView imageView = new ImageView("textures/body.png");
//        imageView.setLayoutX(0);
//        imageView.setLayoutY(0);
//        imageView.setFitHeight(600);
//        imageView.setFitWidth(400);

        mainPane.setBackground(new Background(background));
//        mainPane.getChildren().add(imageView);
    }

    private void showSubScene(BombermanSubScene subScene) {
        if (sceneToHide != null) {
            sceneToHide.moveSubScene();
        }
        subScene.moveSubScene();
        sceneToHide = subScene;
    }

    private void createSubScene() {
        creditsSubScene = new BombermanSubScene(1014, 150);
        mainPane.getChildren().add(creditsSubScene);

        helpSubScene = new BombermanSubScene(1014, 150);
        mainPane.getChildren().add(helpSubScene);

        scoreSubScene = new BombermanSubScene(1014 - 676, 150);
        scoreSubScene.setTranHiddenX(0);
        scoreSubScene.setTranShowX(676);
        mainPane.getChildren().add(scoreSubScene);
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void addMenuButton(BombermanButton button) {
        button.setLayoutX(MENU_BUTTON_START_X - 40);
        button.setLayoutY((MENU_BUTTON_START_Y - 95) + menuButtons.size() * 100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private void createButtons() {
        createStartButton();
        createScoreButton();
        createHelpButton();
        createExitButton();
        createCreditsButton();
    }

    private void createStartButton() {
        BombermanButton startButton = new BombermanButton("PLAY");
        addMenuButton(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BombermanGame.gameStart = 1;
            }
        });
    }

    private void createScoreButton() {
        BombermanButton scoreButton = new BombermanButton("SCORES");
        addMenuButton(scoreButton);

        scoreButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(scoreSubScene);
            }
        });
    }

    private void createHelpButton() {
        BombermanButton helpButton = new BombermanButton("HELP");
        addMenuButton(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(helpSubScene);
            }
        });
    }

    private void createCreditsButton() {
        BombermanButton creditsButton = new BombermanButton("CREDITS");
        addMenuButton(creditsButton);

        creditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(creditsSubScene);
            }
        });
    }

    private void createExitButton() {
        BombermanButton exitButton = new BombermanButton("EXIT");
        addMenuButton(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });
    }

    private void createLogo() {
        ImageView logo = new ImageView("buttons/logo3.png");
        logo.setLayoutX(340);
        logo.setLayoutY(40);

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

    public void startGame() {
        mainPane.setVisible(false);
    }
}