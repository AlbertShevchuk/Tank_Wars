import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class IndestructibleObject extends MapObject {

    IndestructibleObject(Image img, int x, int y) {
        super(img, x, y);
        this.destructible = false;
    }

}