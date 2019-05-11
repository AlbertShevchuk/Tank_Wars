import javafx.scene.image.Image;

public class DestructibleObject extends MapObject {

    DestructibleObject(Image img, int x, int y) {               //simple explosion class that has an Image, x , y components, and a  distructable var
        super(img, x, y);
        this.destructible = true;   //if object is destructible
    }
}
