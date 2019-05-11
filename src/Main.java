import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("Battle Tank");
        //create the display
        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);

        // coordinate of map
        int width = 1280;
        int height = 640;
        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image backGround = new Image("resources/backGround2.png");

        //draw background
        gc.drawImage(backGround, 0, 0);
        int FLOOR = 465;    //floor coordinate
        primaryStage.show();
        GameEngine engine = new GameEngine(root, theScene, gc);
        //start the engine
        engine.gameLoop();
    }

    public static void main(String[] args)
    {
        launch(args);}
}