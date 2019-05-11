import javafx.scene.image.Image;

public abstract class MapObject extends GameObject {
    protected Boolean destructible;
    MapObject(Image img, int x, int y) {
        super(img, x, y);
    }

    public Boolean getDestructible() {
        return destructible;
    }
}
