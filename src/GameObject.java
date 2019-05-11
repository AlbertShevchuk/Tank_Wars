import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public abstract class GameObject {
    protected int x, y, width, height;  //TODO use Yspeed later to replace constant
    protected Image img;
    protected Node node;
    protected ImageView Img;
    protected Rectangle rectangle;  //object for collision

    GameObject(Image img, int x, int y){
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = (int) img.getWidth();
        this.height = (int) img.getHeight();
        this.Img = new ImageView(img);
        this.node = Img;
        this.rectangle = new Rectangle(x, y, img.getWidth(), img.getHeight());
    }
    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public Image getImg() {
        return this.img;
    }

    public Node getNode(){
        return this.node;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setX(int a){
        this.x = a;
    }

    public void setY(int b){
        this.y = b;
    }

    public void moveObject() {
        node.relocate(x, y);
    }

}