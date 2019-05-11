import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class projectileObject extends GameObject{                                                                  //projectileObject is incharge of the bullet

    protected double projectileAngle, cannonAngle, cTime;                                                   //this is the angle thats always changing as the bullet is flying
    protected ImageView selectedImage;                                                                      //image view is helps woth the rotation
    protected boolean rotationFlag;                                                                         //flag that sets the initial rotation to the cannons angle// cannon angle that never changes
    protected int damage;

    projectileObject(Image img, int x, int y, int angle, int damage)                                        //projectile object has an image, x, y, the angle, and amount of damage it carries
    {
        super(img, x, y);                                                                                   //inharates from game object
        this.projectileAngle = (double) angle;                                                              //both cannonAngle and projectileAngle are set to the same angle that tankObject feeds
        this.cannonAngle = (double) angle;
        selectedImage = new ImageView();                                                                    //new image view is created in order to rotate image
        rotationFlag = false;                                                                               //initialy rotation flag is false
        this.damage = damage;
        cTime = 0.0;                                                                                        //timer that changes the rotation and position of the bullet per frame
    }

    public int getDamage() {
        return damage;
    }

    public void cTimeReset(){cTime = 0.0;}

    public void update(Group root)
    {
        root.getChildren().remove(selectedImage);                                                           //remove the previous loaded image
        selectedImage.setX(x);
        selectedImage.setY(y);                                                                              //set bullets position
        this.selectedImage.setImage(img);                                                                   //select the image

        if(cannonAngle < 270) {                                                                             //if tank is looking to the left
            y -= 9*Math.sin( Math.toRadians(cannonAngle-180-cTime));
            x -= 9*Math.cos( Math.toRadians(cannonAngle-180-cTime));                                        //update cordinates to the sine and cosine curve times a scaler for speed
            selectedImage.setRotate(projectileAngle);
            projectileAngle--;                                                                              //decrease rotation angle each frame
        }
        else {                                                                                              //else tank is looking right
            y -= 9*Math.sin(Math.toRadians(cannonAngle+180+cTime));
            x -= 9*Math.cos(Math.toRadians(cannonAngle+180+cTime));                                         //update cordinates to the sine and cosine curve times a scaler for speed
            selectedImage.setRotate(projectileAngle);
            projectileAngle++;                                                                              //increase
        }

        moveObject();
        root.getChildren().add(selectedImage);                                                              //change position and image to screen
        cTime += 1;
    }
}
