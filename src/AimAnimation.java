import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class AimAnimation {
    private TankObject tank;    //reference for each tank
    private Image point = new Image("resources/hudX.png");
    private ArrayList<Node> list = new ArrayList<>();
    private ArrayList<Rectangle>  object = new ArrayList<>();
    private double cTime;

    // create aim for tank
    AimAnimation(TankObject tank, Group root) {
        this.tank = tank;
        this.cTime = 0.0;
        init(root);
    }

    // initialize list to display
    private void init(Group root) {
        for(int i = 0; i < 125; i++) {  //125 is an arbitrary number, will change will checks for collision to stop the prediction
            Node temp = new ImageView(point);
            // place outside of view for first frame
            temp.relocate(-32, -32);
            list.add(temp);
            root.getChildren().add(temp);
            // create rectangles for collision
            Rectangle rec = new Rectangle(-32, -32, point.getWidth(), point.getHeight());
            object.add(rec);
        }
    }

    // display aim for tank, if collides, zero out rest
    public void display(ArrayList<MapObject> map) {
        int angle = tank.getDegree();
        int x = tank.getX();
        int y = tank.getY();
        outerloop:
        for(int i = 0; i < list.size(); i++) {  // loops through points to display
            if(angle < 270) {   // calculate position of objects
                y -= 9*Math.sin( Math.toRadians(angle-180-cTime));
                x -= 9*Math.cos( Math.toRadians(angle-180-cTime));
            }
            else {
                y -= 9*Math.sin(Math.toRadians(angle+180+cTime));
                x -= 9*Math.cos(Math.toRadians(angle+180+cTime));
            }
            if(cTime % 3 == 0) { // display every 3 nodes so won't be clustered
                list.get(i).relocate(x,y);
                object.get(i).relocate(x,y);
            }
            cTime += 1;
            //checks when collides to end display Aim
            for (MapObject aMap : map) {
                if (object.get(i).getBoundsInParent().intersects(aMap.getRectangle().getBoundsInParent()) || !boundCheck(x,y)) {
                    zeroOut(i);
                    break outerloop;
                }
            }
        }
        cTime = 0.0;    // reset timer for next prediction
    }

    //zeros out the rest of Aim
    protected void zeroOut(int temp) {
        for(int i = temp; i < list.size(); i++) {
            list.get(i).relocate(-32,-32);
            object.get(i).relocate(-32,-32);
        }
    }

    // check for bound of aim
    private boolean boundCheck(int x, int y) {
        return x > 0 && x < 1280 && y > 0 && y < 640;
    }

}