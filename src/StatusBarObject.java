import javafx.scene.image.Image;

public class StatusBarObject extends GameObject {

    StatusBarObject(Image img, int x, int y) {
        super(img, x, y);
    }

    //change image of status bar
    public void changeImg(Image img) {
        Img.setImage(img);
    }
}
