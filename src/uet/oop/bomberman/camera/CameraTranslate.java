package uet.oop.bomberman.camera;

import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class CameraTranslate {
    public static void CameraController(Stage stage, Rotate rotX, Rotate rotY) {
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
//            switch (event.getCode().toString()) {
//                case "LEFT":
//                    rotY.setAngle(rotY.getAngle() + 10);
//                    break;
//                case "RIGHT":
//                    rotY.setAngle(rotY.getAngle() - 10);
//                    break;
//                case "UP":
//                    rotX.setAngle(rotX.getAngle() + 10);
//                    break;
//                case "DOWN":
//                    rotX.setAngle(rotX.getAngle() - 10);
//
//            }
        });
    }
}
