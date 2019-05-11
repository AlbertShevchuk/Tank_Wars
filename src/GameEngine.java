import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.ArrayList;
import java.io.File;

public class GameEngine {
    protected StatusBar tank0StatusBar;
    protected StatusBar tank1StatusBar;
    protected CollisionDetection collision;
    protected MapBuilder map;
    protected MediaPlayer mediaPlayer;
    protected MediaPlayer mediaPlayer1;
    protected fireMechanic tank0fire;
    protected fireMechanic tank1fire;
    protected projectileObject tank0Bullet;
    protected projectileObject tank1Bullet;
    protected TankObject tank0;
    protected TankObject tank1;
    protected ArrayList<explosionObject> explosion;                                                                                                                             //Objects that are going to be used
    protected ArrayList<explosionObject> explosion1;
    protected ArrayList<TimerObject> countDownTimer;
    protected ArrayList<TimerObject> countDownTimer1;
    protected Controller control;
    protected Media explosionSound;
    protected Media explosionSound1;
    protected Image tankCannon, tank0IMG, tank0IMGF, tank1IMG, tank1IMGF, tankBulletIMG, tankBulletIMG1;
    protected Group root;
    protected int countDown = 0;
    protected int animationTimerLoop = 0;
    protected boolean firstLoop0 = false;                                                                                                                                       //vars for player switching mechanizm
    protected boolean firstLoop1 = false;

    private AimAnimation tank0Aim, tank1Aim;

    //initiate game world
    GameEngine(Group mMoot, Scene theScene, GraphicsContext gc) {                                                                                                               //get display parameters from main
        root = mMoot;
        //cannon image
        tankCannon = new Image("resources/tanks_turret1.png");

        //make tank 1
        tank0IMG = new Image("resources/newTanks_tankDesert1.png");
        tank0IMGF = new Image("resources/newTanks_tankDesert1F.png");
        tank0 = new TankObject(tank0IMG, tank0IMGF, tankCannon, 1000, 300, "UP", "DOWN", "LEFT", "RIGHT", "PERIOD", "SLASH");
        //addObserver(tank0);

        //make tank 2
        tank1IMG = new Image("resources/newTanks_tankGreen1.png");
        tank1IMGF = new Image("resources/newTanks_tankGreen1F.png");
        tank1 = new TankObject(tank1IMG, tank1IMGF, tankCannon, 50, 300, "W", "S", "A", "D", "E", "F");
        //addObserver(tank1);

        //create status bar
        tank0StatusBar = new StatusBar(tank0, root);
        tank1StatusBar = new StatusBar(tank1, root);

        //add node to node graph
        root.getChildren().add(tank0.getCannon());
        root.getChildren().add(tank1.getCannon());
        root.getChildren().add(tank0.getNode());
        root.getChildren().add(tank1.getNode());
        root.getChildren().add(tank0.getFlame());
        root.getChildren().add(tank1.getFlame());

        // set up controller and handler
        control = new Controller(tank0, tank1);
        theScene.setOnKeyPressed(control::EventHandlerPress);
        theScene.setOnKeyReleased(control::EventHandlerRelease);

        //create map and collision
        map = new MapBuilder(root, gc);
        collision = new CollisionDetection(map.getMapList(), map.getPowerList(), tank0, tank1);
        map.display();

        explosionSound = new Media(new File("src/resources/kenney_tankspack/Sounds/0017_explo_grenade_02_PremiumBeat.wav").toURI().toString());               //make media objects for explosion sounds
        explosionSound1 = new Media(new File("src/resources/kenney_tankspack/Sounds/0017_explo_mine_09_PremiumBeat.wav").toURI().toString());
        mediaPlayer = new MediaPlayer(explosionSound);
        mediaPlayer1 = new MediaPlayer(explosionSound1);

        tankBulletIMG = new Image("resources/kenney_tankspack/PNG/Default/tank_bullet3.png");                                                                      // images for the bullets
        tankBulletIMG1 = new Image("resources/kenney_tankspack/PNG/Default/tank_bullet4.png");

        tank0fire = new fireMechanic();                                                                                                                                 //each tank had its own fire mechanic class that is mostly link to the functions of the bullet
        tank1fire = new fireMechanic();

        tank0Bullet = new projectileObject(tankBulletIMG, tank0.getX(), tank0.getY(), tank0.getDegree(), 13);                                                   // init each tank has one individual bullet object
        tank1Bullet = new projectileObject(tankBulletIMG1, tank1.getX(), tank1.getY(), tank1.getDegree(), 13);

        explosion = new ArrayList<>();                                                                                                                                   //arrayList of explosion and countdown timer images
        explosion1 = new ArrayList<>();
        countDownTimer = new ArrayList<>();
        countDownTimer1 = new ArrayList<>();

        initExplosionObject(explosion, explosion1, countDownTimer, countDownTimer1);                                                                                   // fills up arrayList for both explosion, and both counteDownTimers

        tank0Aim = new AimAnimation(tank0, root);                                                                                                                       //animation timer object is where the countdown timer is going to be displayed
        tank1Aim = new AimAnimation(tank1, root);

        tank0fire.initTimer(countDownTimer, 1180, 0);
        tank1fire.initTimer(countDownTimer1, 0, 0);                                                                                                     //set position of both timers, this only needs to be done once since they will always  be in the same locaton

        tank0fire.endTurn = false;
        tank0fire.stopCounter = false;                                                                                                                                //flags set for tank1 to have the first turn
        tank0fire.nextTurn = true;
    }

        // loops through game, and the holy grail of our game
        public void gameLoop () {
            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {

                    tank0StatusBar.display();
                    tank1StatusBar.display();                                                                                                                         //these variable are essential to be always checking for both tanks
                    gravityCheck(tank0, tank1, map.getMapList(), map.getPowerList());
                    tank0.checkHealth();
                    tank1.checkHealth();
                    tank0.moveTank();
                    tank1.moveTank();
                    collision.collisionCheck(root);
                    tank0.update(tank0fire.stopCounter);
                    tank1.update(tank1fire.stopCounter);
                                                                                                                        //PLAYER ONE
                if((!tank0fire.endTurn)&&(tank0fire.nextTurn)) {                                                                                                       //starting from here is what handles the different turns for the tanks, needs to be refractered
                    if (!tank0fire.stopCounter) {                                                                                                                      //this is the counter that keeps track of player1s game timer
                        if((animationTimerLoop == 0) && (firstLoop0 == false))
                        {
                            tank0fire.updateTimer(root, countDownTimer);
                            countDown++;
                            firstLoop0 = true;                                                                                                                          //always updated to keep track of the number firs number always displayed b4 the controlls are released
                            animationTimerLoop++;
                        }
                        if (animationTimerLoop == 60) {                                                                                                                 //every time the animation timer is looped 60 times the next number is displayed
                            tank0fire.updateTimer(root, countDownTimer);                                                                                                //i belive the animation timer runs at 60 frames per second witch will make it look like the number are doing down by seconds
                            animationTimerLoop = 0;
                            countDown++;

                        } else
                            animationTimerLoop++;
                    }

                    tank1Aim.zeroOut(0);
                    tankHandler(tank0fire, tank0, tank1, tank0Bullet);                                                                                                  //at this point player one has controlls and player two's trijectory is not displayed
                    tank0Aim.display(map.getMapList());
                }
                if((tank0fire.endTurn)&&(tank0fire.nextTurn))                                                                                                           //when the turn ends and next turn flags are set
                {
                    root.getChildren().remove(countDownTimer.get(countDown-1).getNode());
                    countDownTimer.get(countDown-1).moveObject();
                    tank0fire.stopCounter = true;                                                                                                                       //the last contdown image gets removed
                    tank1fire.stopCounter = false;
                    tank0fire.nextTurn = false;
                    tank1fire.endTurn = false;                                                                                                                          //all the nessesary flags to stop payer ones turn are enabled
                    tank1fire.nextTurn = true;                                                                                                                          // all of the nessisary flags to start player twos turn are enabled
                    countDown = 0;
                    animationTimerLoop = 0;
                    firstLoop0 = false;
                    tank0fire.time = 0;
                }
                                                                                                                        //PLAYER TWO
                    if((!tank1fire.endTurn)&&(tank1fire.nextTurn)) {                                                                                                    //these variable are essential to be always checking for both tanks
                        if (!tank1fire.stopCounter) {                                                                                                                   //this is the counter that keeps track of player2s game timer
                            if((animationTimerLoop == 0) && (firstLoop1 == false))
                            {
                                tank1fire.updateTimer(root, countDownTimer1);
                                countDown++;
                                firstLoop1 = true;                                                                                                                      //always updated to keep track of the number firs number always displayed b4 the controlls are released
                                animationTimerLoop++;
                            }
                            if (animationTimerLoop == 60) {                                                                                                             //every time the animation timer is looped 60 times the next number is displayed
                                tank1fire.updateTimer(root, countDownTimer1);                                                                                           //i belive the animation timer runs at 60 frames per second witch will make it look like the number are doing down by seconds
                                animationTimerLoop = 0;
                                countDown++;

                            } else
                                animationTimerLoop++;
                        }

                        tank0Aim.zeroOut(0);                                                                                                                        //at this point player two has controlls and player one's trijectory is not displayed
                        tankHandler(tank1fire, tank1, tank0, tank1Bullet);
                        tank1Aim.display(map.getMapList());
                    }
                    if((tank1fire.endTurn)&&(tank1fire.nextTurn))                                                                                                          //when the turn ends and next turn flags are set
                    {
                        root.getChildren().remove(countDownTimer1.get(countDown-1).getNode());
                        countDownTimer1.get(countDown-1).moveObject();                                                                                                     //the last contdown image gets removed
                        tank0fire.stopCounter = false;                                                                                                                     //all the nessesary flags to stop payer twoss turn are enabled
                        tank1fire.stopCounter = true;                                                                                                                      // all of the nessisary flags to start player twos turn are enabled
                        tank1fire.nextTurn = false;
                        tank0fire.endTurn = false;
                        tank0fire.nextTurn = true;
                        countDown = 0;
                        animationTimerLoop = 0;
                        tank1fire.time = 0;
                        firstLoop1 = false;
                    }
                }
            };
            timer.start();
        }

        public void tankHandler(fireMechanic tankFire, TankObject tank, TankObject otherTank,  projectileObject tankBullet)                                             //tank handeler handles all of the rules of each tank
        {
            switch (tank.getPowerUp())
            {
                case 3: tank.setSpeed(3);
                    tank.setPowerUp(0);
                    break;
                case 1: tank.reduceHeath(50);
                    tank.setPowerUp(0);
                    break;                                                                                                                                              //switch for power ups
                case 2: if(tank.getHealth()+50 > 100)
                    tank.setHealth(100);
                else
                    tank.reduceHeath(-50);
                    tank.setPowerUp(0);
                    break;
            }

            if (tank.getFire() && !tankFire.lock)                                                                                                                       //If tank has had fire button pressed and its bullet has not exploded
            {
                tankFire.stopCounter = true;                                                                                                                            //created a bullet and turn fire button state off
                tankFire.addBullet(tankBullet, tank.getX(), tank.getY(), tank.getDegree());
                tank.fireOff();
                mediaPlayer.stop();
                mediaPlayer1.stop();                                                                                                                                     //sounds are stoped
                tank.setSpeed(1);
            }

            if (tankFire.lock && !tankFire.getHasExploaded())                                                                                                           //else if the bullet has been fired
            {
                if (tankFire.boundaryDetection(tankBullet, root))                                                                                                       //and if check and see if the bullet flew off the map
                {
                    tankBullet.cTimeReset();
                    tankFire.lock = false;
                    tankFire.hasExploded = false;                                                                                                                       //set flags
                    tankFire.time = 0;
                    tankFire.endTurn = true;
                } else                                                                                                                                                  //if not then keep updating position and change movement
                {
                    tankBullet.moveObject();
                    tankBullet.update(root);
                }

                if (tankFire.tankCollisionDetection(otherTank.getNode(), tankBullet.getNode(), root))                                                                   //if players bullet has collided with other players tank
                {
                    tankBullet.cTimeReset();                                                                                                                            //reset the timer used for rotating the bullet

                    if (tank.getPowerUp() == 4) {                                                                                                                       //power up
                        mediaPlayer1.play();                                                                                                                            //play loaded explosion sound
                        tankFire.initExplosion(explosion1, otherTank.getX() - 120, otherTank.getY() - 120);                                         //init explosion
                        root.getChildren().remove(tankBullet.selectedImage);
                        otherTank.reduceHeath(tankBullet.getDamage());
                        otherTank.reduceHeath(tankBullet.getDamage());
                        otherTank.reduceHeath(tankBullet.getDamage());                                                                                                  //reduce health 3 times (-39HP)

                    } else {                                                                                                                                            //normal explosion
                        mediaPlayer.play();                                                                                                                             //play loaded explosion sound
                        tankFire.initExplosion(explosion, otherTank.getX() - 52, otherTank.getY() - 25);
                        root.getChildren().remove(tankBullet.selectedImage);                                                                                            //Start the explosion animation
                        otherTank.reduceHeath(tankBullet.getDamage());                                                                                                  //reduce health by scaler
                    }
                }

                if (collision.WallsCollisionDetection(tankBullet.getNode(), root))                                                                                      //if player bullet has hit any of the walls
                {
                    tankBullet.cTimeReset();                                                                                                                            //reset rotation timer for the bullet
                                                                                                                                                                         //power up
                    if (tank.getPowerUp() == 4) {
                        mediaPlayer1.play();                                                                                                                            //play loaded explosion sound
                        tankFire.initExplosion(explosion1, tankBullet.getX() - 120, tankBullet.getY() - 120);                                       //init and remove bullet from screen
                        root.getChildren().remove(tankBullet.selectedImage);

                    } else {
                        mediaPlayer.play();                                                                                                                             //same as above but for a normal exolosion
                        tankFire.initExplosion(explosion, tankBullet.getX() - 52, tankBullet.getY() - 25);
                        root.getChildren().remove(tankBullet.selectedImage);
                    }
                }
            }

            if (tankFire.getHasExploaded())                                                                                                                             //if the bullet's explosion animation has started
            {
                if (tank.getPowerUp() == 4) {                                                                                                                           //and if the power up is set
                    if (tankFire.updateExplosion(root, explosion1))                                                                                                     //update images for the explosion and when finished
                    {tank.setPowerUp(0);}                                                                                                                               //set the power up state back to zero
                }
                else {
                    if (tankFire.updateExplosion(root, explosion))
                    {tank.setPowerUp(0);}                                                                                                                               //other wise display image for a normal explosion
                }
            }
        }

        public void initExplosionObject(ArrayList < explosionObject > explosion, ArrayList < explosionObject > explosion1, ArrayList < TimerObject > timer, ArrayList < TimerObject > timer1)       //function that initilizes explosions and display timers
        {
            Image IMG;
            String name;

            for (int i = 1; i <= 64; i++) {
                if (i < 10) {
                    name = "resources/kenney_tankspack/PNG/Explosion/EXPLOSION000" + i + ".png";
                    IMG = new Image(name);
                    explosion.add(new explosionObject(IMG, 0, 0));
                } else {                                                                                                                                                               //load the images into the arrayList
                    name = "resources/kenney_tankspack/PNG/Explosion/EXPLOSION00" + i + ".png";
                    IMG = new Image(name);
                    explosion.add(new explosionObject(IMG, 0, 0));
                }
            }

            for (int i = 1; i <= 300; i++) {
                if (i < 10) {
                    name = "resources/kenney_tankspack/PNG/Explosion2/explosion000" + i + ".png";
                    IMG = new Image(name);
                    explosion1.add(new explosionObject(IMG, 0, 0));
                } else if (i < 100) {
                    name = "resources/kenney_tankspack/PNG/Explosion2/explosion00" + i + ".png";                                                                                    //load the images into the arrayList
                    IMG = new Image(name);
                    explosion1.add(new explosionObject(IMG, 0, 0));
                } else {
                    name = "resources/kenney_tankspack/PNG/Explosion2/explosion0" + i + ".png";
                    IMG = new Image(name);
                    explosion1.add(new explosionObject(IMG, 0, 0));
                }
            }

            for (int i = 7; i >= 0; i--) {
                name = "resources/Timer/number" + i + ".png";
                IMG = new Image(name);
                timer.add(new TimerObject(IMG, 0, 0));                                                                                                                      //load images backwards into the arraylist
                name = "resources/Timer/number" + i + ".png";
                IMG = new Image(name);
                timer1.add(new TimerObject(IMG, 0, 0));
            }
        }

        // creates artificial gravity
        public void gravityCheck (TankObject tank0, TankObject
        tank1, ArrayList < MapObject > maplist, ArrayList < PowerUpObject > powerUpList){
            Boolean gravity0 = true;
            Boolean gravity1 = true;
            tank0.getRectangle().setY(tank0.getY() + 1);
            tank1.getRectangle().setY(tank1.getY() + 1);

            //adds future position to check
            for (PowerUpObject aPowerUpList : powerUpList) {
                aPowerUpList.getRectangle().setY(aPowerUpList.getY() + 1);
                aPowerUpList.setY(aPowerUpList.getY() + 1);
            }
            //loops through MapObject arrayList to check gravity
            for (MapObject aList : maplist) {
                if (tank0.getRectangle().getBoundsInParent().intersects(aList.getRectangle().getBoundsInParent())) {
                    gravity0 = false;
                    tank0.getRectangle().setY(tank0.getY() - 1);
                }
                if (tank1.getRectangle().getBoundsInParent().intersects(aList.getRectangle().getBoundsInParent())) {
                    gravity1 = false;
                    tank1.getRectangle().setY(tank1.getY() - 1);
                }
                //check each power up object with mapObject
                for (PowerUpObject aPowerUpList : powerUpList) {
                    // if collide, cancel out changes
                    if (aPowerUpList.getRectangle().getBoundsInParent().intersects(aList.getRectangle().getBoundsInParent())) {
                        aPowerUpList.getRectangle().setY(aPowerUpList.getY() - 1);
                        aPowerUpList.setY(aPowerUpList.getY() - 1);
                    }
                }
            }
            // display new power up position
            for (PowerUpObject aPowerUpList : powerUpList) {
                aPowerUpList.moveObject();
            }
            //tank on tank gravity bug handler
            if (!gravity0 && tank0.getRectangle().getBoundsInParent().intersects(tank1.getRectangle().getBoundsInParent())) {
                gravity1 = false;
                tank1.getRectangle().setY(tank1.getY() - 1);
            }
            if (!gravity1 && tank0.getRectangle().getBoundsInParent().intersects(tank1.getRectangle().getBoundsInParent())) {
                gravity0 = false;
                tank0.getRectangle().setY(tank1.getY() - 1);
            }
            if (gravity0)
                tank0.setY(tank0.getY() + 1);
            if (gravity1)
                tank1.setY(tank1.getY() + 1);
        }
    }

