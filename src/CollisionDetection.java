import javafx.scene.Node;
import javafx.scene.Group;
import java.util.ArrayList;

public class CollisionDetection {
    private ArrayList<MapObject> Maplist;   // list of map objects
    private ArrayList<PowerUpObject> powerUpList;   // list of power up objects
    private TankObject tank0;
    private TankObject tank1;

    CollisionDetection(ArrayList<MapObject> Maplist, ArrayList<PowerUpObject> powerUpList, TankObject tank0, TankObject tank1) {
        this.Maplist = Maplist;
        this.powerUpList = powerUpList;
        this.tank0 = tank0;
        this.tank1 = tank1;
    }

    //tank collisionCheck
    public void collisionCheck(Group root) {
        //if two tanks collide
        if (tank0.getRectangle().getBoundsInParent().intersects(tank1.getRectangle().getBoundsInParent())) {
            tank0.collided();
            tank1.collided();
        }
        //if tank collides with map
        for (MapObject aList : Maplist) {
            //if tank0 collides with objects
            if (aList.getRectangle().getBoundsInParent().intersects(tank0.getRectangle().getBoundsInParent())) {
                tank0.collided();
            }
            //if tamk1 collides with objects
            if (aList.getRectangle().getBoundsInParent().intersects(tank1.getRectangle().getBoundsInParent())) {
                tank1.collided();
            }
        }
        // checks for collision with power up objects
        for (int i = 0; i < powerUpList.size(); i++) {
            if (powerUpList.get(i).getRectangle().getBoundsInParent().intersects(tank0.getRectangle().getBoundsInParent())) {
                root.getChildren().remove(powerUpList.get(i).getNode());
                tank0.setPowerUp(powerUpList.get(i).powerUpVersion);
                powerUpList.remove(i);
                continue;
            }
            if (powerUpList.get(i).getRectangle().getBoundsInParent().intersects(tank1.getRectangle().getBoundsInParent())) {
                root.getChildren().remove(powerUpList.get(i).getNode());
                tank1.setPowerUp(powerUpList.get(i).powerUpVersion);
                powerUpList.remove(i);

            }
        }
    }

    //collision of bullets on walls
    public boolean WallsCollisionDetection(Node projectile, Group root) {
        for (int i = 0; i < Maplist.size(); i++) {             //traverses mapList
            if (Maplist.get(i).getRectangle().getBoundsInParent().intersects(projectile.getBoundsInParent())) {    // if collides
                root.getChildren().remove(projectile);  //removes projectile Object

                if (Maplist.get(i).getDestructible()) { // if mapObject is destructible, removes from list delete
                    root.getChildren().remove(Maplist.get(i).getNode());
                    Maplist.remove(i);
                }
                return true;
            }
        }
        return false;
    }
}