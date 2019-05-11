import javafx.scene.Group;
import javafx.scene.Node;
import java.util.ArrayList;

class fireMechanic {                                                                                                            //fireMechanic is in charge of anything bullet related

    protected int time;
    protected boolean lock = false;
    protected boolean hasExploded;
    protected boolean stopCounter = true;                                                                                       //flags
    protected boolean endTurn = true;
    protected boolean nextTurn = false;

    void addBullet(projectileObject tankBullet, int tankX, int tankY, int degree)                                               //create a bullet
    {
            tankBullet.x = tankX;                                                                                               //set bullets coordinates to of the tanks
            tankBullet.y = tankY;
            tankBullet.projectileAngle = degree;                                                                                //bullet needs an initial angle and a always changing angle
            tankBullet.cannonAngle = degree;
            lock = true;                                                                                                        //once bullet is created then turn the lock on
            hasExploded = false;                //bullet has not exploded
            time = 0;                                                                                                           //time is used to irate threw arraylist of explosions for each frame to create a moving onject, and the Countdown timer
    }

    public void initExplosion(ArrayList<explosionObject> nuke, int projectileX, int projectileY)                                 //explosion is added by setting every single image in the arrayList to the location of where the colision has occured
    {
        for(int i = 0; i < nuke.size(); i++)
        {
            nuke.get(i).setX(projectileX);
            nuke.get(i).setY(projectileY);
        }
        hasExploded = true;                                                                                                     //set exploaded flag
    }

    public void initTimer(ArrayList<TimerObject> timer, int timerX, int timerY)                                                 //timer is added by setting every single image in the arrayList to the location of where the timer is placed
    {
        for(int i = 0; i < timer.size(); i++)
        {
            timer.get(i).setX(timerX);
            timer.get(i).setY(timerY);
        }
    }

    public boolean boundaryDetection(projectileObject projectile, Group root)                                                   //boundary checks if bullet flown of the top, left, and right border of the map
    {
        if((projectile.x >= 1278)||(projectile.x <= 2)||(projectile.y <= 2))                                                    //use the position of the coordinates to check limits
        {                                                                                                                       //this faction also prevents the bullet's update function to have "divided by zero" major math error
            projectile.rotationFlag = false;
            root.getChildren().remove(projectile.selectedImage);                                                                //if the detection is occurred remove the bullet from the screen and turn the rotation flag off
            return true;                                                                                                        //detection occurs return true
        }
        return false;                                                                                                           //otherwise return false
    }

    public boolean tankCollisionDetection(Node tank, Node projectile, Group root)                                              //Bullet and Tank's Collision Detection
    {
        if (projectile.getBoundsInParent().intersects(tank.getBoundsInParent()))                                                //using bounds and intersections to check, found this at //https://stackoverflow.com/questions/15013913/checking-collision-of-shapes-with-javafx
        {
            root.getChildren().remove(projectile);                                                                              //remove the bullet if colision occures and return true
            return true;
        }
        return false;                                                                                                           //otherwise return false
    }

    public boolean getHasExploaded()                                                                                            //getter for hasExploded bool
    {return hasExploded;}

    public boolean updateExplosion(Group root, ArrayList<explosionObject> explosion)                                            //takes an array of Images and display's them except the very last one.
    {
        if(time == 0)
        {
            explosion.get(time).moveObject();
            root.getChildren().add(explosion.get(time).getNode());                                                              //first moved then displayed
        }
        else
        {
            root.getChildren().remove(explosion.get(time-1).getNode());                                                         //in between last object is removed
            explosion.get(time).moveObject();                                                                                   //the current object is moved
            root.getChildren().add(explosion.get(time).getNode());

            if(time == explosion.size()-1)
            {
                root.getChildren().remove(explosion.get(time).getNode());                                                        //and if current is the last-1 image then
                this.hasExploded = false;
                this.lock = false;
                this.time = 0;                                                                                                   //set the flags
                this.endTurn = true;
                return true;
            }
        }
        this.time += 1;
        return false;
    }

    public void updateTimer(Group root, ArrayList<TimerObject> timer)                                                        //simmilar function as for explosions
    {
        if(time == 0)
        {

            root.getChildren().remove(timer.get(time).getNode());
            timer.get(time).moveObject();
            root.getChildren().add(timer.get(time).getNode());
            this.time += 1;                                                                                                  //last removed first moved then displayed
        }
        else {
            root.getChildren().remove(timer.get(time - 1).getNode());                                                         //in between last object is removed
            timer.get(time).moveObject();                                                                                    //the current object is moved
            root.getChildren().add(timer.get(time).getNode());
            this.time += 1;
        }
        if(time == timer.size())
        {
            this.time = 0;                                                                                                  //if you reach the end flags are set
            this.stopCounter = true;
        }
    }
}
