import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;

public class MapBuilder {
    private String path = "src/resources/platformer-pack-redux-360-assets/PNG";
    private GraphicsContext gc;
    private ArrayList<MapObject> mapList = new ArrayList<>();
    private ArrayList<PowerUpObject> powerList = new ArrayList<>();
    private Group root;

    MapBuilder(Group root, GraphicsContext gc) {
        this.gc = gc;
        this.root = root;
        create(mapTest);
    }

    //reads in map array and creates arraylist of mapObjects
    public void create(int [][] arr) {
        //default background image
        gc.drawImage(bg0, 0, 0);
        gc.drawImage(bg0, 1024,0);

        // traverses 2D array to creat map
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr[0].length; j++) {
                if(arr[i][j] == 0) {
                    //blank space, for reference
                } else if(arr[i][j] == 1) {         // make IndestructibleObject
                    IndestructibleObject object = new IndestructibleObject(img0, j*64, i*64);
                    mapList.add(object);
                } else if(arr[i][j] == 2) {         // make DestructibleObject
                    DestructibleObject object = new DestructibleObject(img1, j*64, i*64);
                    mapList.add(object);
                } else if(arr[i][j] == 3) {         // make power up
                    PowerUpObject object = new PowerUpObject(img2, j*64+1, i*64, 1);
                    powerList.add(object);
                } else if(arr[i][j] == 4) {         // make power up
                    PowerUpObject object = new PowerUpObject(img3, j*64+1, i*64, 2);
                    powerList.add(object);
                } else if(arr[i][j] == 5) {         // make power up
                    PowerUpObject object = new PowerUpObject(img4, j*64+1, i*64, 3);
                    powerList.add(object);
                } else if(arr[i][j] == 6) {         // make power up
                    PowerUpObject object = new PowerUpObject(img5, j*64+1, i*64, 4);
                    powerList.add(object);
                }
            }
        }
    }

    public void display() {
        //display MapObjects
        for (MapObject aMapList : mapList) {
            if (aMapList.getDestructible()) {
                root.getChildren().add(aMapList.getNode());
                aMapList.moveObject();
            } else
                gc.drawImage(aMapList.getImg(), aMapList.getX(), aMapList.getY());
        }

        //display PowerUpObjects
        for (PowerUpObject aPowerList : powerList) {
            root.getChildren().add(aPowerList.getNode());
            aPowerList.moveObject();
        }
    }

    public ArrayList<MapObject> getMapList() {
        return mapList;
    }

    public ArrayList<PowerUpObject> getPowerList() {
        return powerList;
    }

    //mapObjects
    private Image bg0 = new Image("resources/MapBuilder/blue_desert.png");
    private Image img0 = new Image("resources/MapBuilder/dirt.png");
    private Image img1 = new Image("resources/MapBuilder/sand.png");
    private Image img2 = new Image("resources/MapBuilder/tanks_barrelRed.png");
    private Image img3 = new Image("resources/MapBuilder/tanks_crateArmor.png");
    private Image img4 = new Image("resources/MapBuilder/tanks_crateRepair.png");
    private Image img5 = new Image("resources/MapBuilder/tanks_crateWood.png");

    private int [][] mapTest = new int[][] {                            //map matrix
            {0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,1,1,0,1,1,0,0,6,0,0,0,0,0,0,0,0,0},
            {0,5,1,0,1,0,0,1,1,1,2,2,2,0,0,4,2,2,2,0},
            {0,2,2,0,0,0,0,2,0,0,0,0,0,0,0,2,1,1,1,1},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,3,2,2,0,0,0,0,0,0,0,0,0},
            {1,0,0,0,0,0,1,1,1,1,1,2,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
    };

    //not used, bigger map
    private int [][] map1 = new int[][] {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
}