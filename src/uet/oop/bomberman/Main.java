package uet.oop.bomberman;

import javafx.application.Application;
import javafx.stage.Stage;
import uet.oop.bomberman.view.ViewManager;

public class Main  {
    public static void main(String[] args) {
        // add label
        System.out.println(
                "     ___    Nghia_BT_            Khoi_DM             __ _ _  \n" +
                        "    | _ ) ___ _ __ | |__  ___ _ _ _ __  __ _ _ _    / /| | | \n" +
                        "    | _ \\/ _ \\ '  \\| '_ \\/ -_) '_| '  \\/ _` | ' \\  / _ \\_  _|\n" +
                        "    |___/\\___/_|_|_|_.__/\\___|_| |_|_|_\\__,_|_||_| \\___/ |_| \n");
        BombermanGame.main(args);
//        ScrollAndClipBackground.main(args);
        System.exit(0);
    }
//    public static void main(String [] args){
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//            try{
//                ViewManager manager = new ViewManager();
//                primaryStage = manager.getMainStage();
//                primaryStage.show();
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }
//    }
}
