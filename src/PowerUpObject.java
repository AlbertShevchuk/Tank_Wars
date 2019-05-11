import javafx.scene.image.Image;

public class PowerUpObject extends GameObject {
    protected int powerUpVersion;
    PowerUpObject(Image img, int x, int y, int value) {
        super(img, x, y);
        powerUpVersion = value;
    }
}