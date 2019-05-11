import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;


public class TankObject extends GameObject {
    private boolean up,down,left,right;
    private boolean fire, alive, jump;
    private String UP, DOWN, LEFT, RIGHT, JUMP, FIRE;
    private int degree, health, lives, speed, powerUp;
    private Image img2;
    private Node cannon;
    private boolean offsetCannon = false;
    private int prevX, prevY, spawnX, spawnY;
    private Node flame;

    TankObject(Image img, Image img2, Image cannon,int x, int y, String UP, String DOWN, String LEFT, String RIGHT, String JUMP, String FIRE) {
        super(img,x,y);
        this.img2 = img2;
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;
        this.fire = false;
        this.alive = true;
        this.jump = false;
        this.UP = UP;
        this.DOWN = DOWN;
        this.LEFT = LEFT;
        this.RIGHT = RIGHT;
        this.JUMP = JUMP;
        this.FIRE = FIRE;
        this.degree = 345;
        this.health = 100;
        this.lives = 3;
        this.cannon = new ImageView(cannon);
        this.prevX = x;
        this.prevY = y;
        this.spawnX = x;
        this.spawnY = y;
        this.speed = 1;
        this.powerUp = 0;
        this.flame = new ImageView("resources/fire.png");
    }

    //moves take based on coordinates
    public void moveTank() {
        node.relocate(x, y);
        //rotate canon
        cannon.setRotate(degree);

        // cannon depending on which way the tank is facing
        if(offsetCannon) {
            cannon.relocate(x+5, y+5);
        } else {
            cannon.relocate(x, y+5);
        }
        if(jump)
            flame.relocate(x+10, y+50);
        else
            flame.relocate(-50, -50);
    }

    public void fireOff() {
        fire = false;
    }

    public boolean getFire() {
        return fire;
    }

    public int getDegree() {
        return this.degree;
    }

    public Node getCannon() {
        return this.cannon;
    }

    public int getPowerUp(){return this.powerUp;}

    public void setPowerUp(int selection){this.powerUp = selection; }

    public int getHealth() {return health; }

    public void setHealth(int value) {this.health = value; }

    public int getLives() { return lives; }

    public void reduceHeath(int damage) { health -= damage; }

    public void loseLife() { lives -= 1; }

    public void setSpeed(int iSpeed) { speed = iSpeed; }

    public Node getFlame() {
        return flame;
    }

    public void checkHealth() {
        if(health <= 0) {
            x = spawnX;
            y = spawnY;
            moveTank();
            loseLife();
            health = 100;
        }
    }

    // on collide, reset coordinates
    public void collided() {
        x = prevX;
        y = prevY;
    }

    // handles movement
    public void EventHandlerPress(KeyEvent event) {
        String code = event.getCode().toString();
        if(code.equals(UP)) up = true;
        if(code.equals(DOWN)) down = true;
        if(code.equals(LEFT)) left = true;
        if(code.equals(RIGHT)) right = true;
        if(code.equals(JUMP)) jump = true;
        if(code.equals(FIRE)) fire = true;
    }
    //handles movement
    public void EventHandlerRelease(KeyEvent event) {
        String code = event.getCode().toString();
        if(code.equals(UP)) up = false;
        if(code.equals(DOWN)) down = false;
        if(code.equals(LEFT)) left = false;
        if(code.equals(RIGHT)) right = false;
        if(code.equals(JUMP)) jump = false;
    }

    //controller update
    public void update(boolean freezeMovement) {
        //save prev coordinates for collision
        prevX = x;
        prevY = y;

        if(up) {
            if(degree > 270)
                if(degree-1 > 270)
                    degree -= 1;
            if(degree+1 < 270)
                degree += 1;
        }

        if(down) {
            if(degree > 270) {
                if(degree+1 < 360)
                    degree += 1;
            }
            else if(degree-1 > 180)
                degree -= 1;
        }

        if(!freezeMovement) {
            if (right) {
                if (x + node.getBoundsInLocal().getWidth() + speed < 1280) {
                    x += speed;
                    Img.setImage(img);  //flip tank
                    offsetCannon = false;
                    if (degree < 270)
                        degree += 2 * (270 - degree);
                }
            }

            if (left) {
                if (x - speed > 0) {
                    x -= speed;
                    Img.setImage(img2); //flip tank
                    offsetCannon = true;
                    if (degree > 270)
                        degree -= 2 * (degree - 270);
                }
            }
            //jump
            if (jump) {
                if (y - 2 > 0) {
                    y -= 2;
                }
            }
        }
        //set rectangle for collision
        rectangle.setX(x);
        rectangle.setY(y);
    }
}
