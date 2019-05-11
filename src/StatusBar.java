import javafx.scene.Group;
import javafx.scene.image.Image;

public class StatusBar {
    private StatusBarObject health1, health2, health3, health4;
    private StatusBarObject life;
    private TankObject tank;    //reference to tank

    StatusBar(TankObject tank, Group root) {
        this.tank = tank;
        int x = tank.getX();
        int y = tank.getY();
        this.life = new StatusBarObject(life3, x -32, y);
        health1 = new StatusBarObject(heartFull, x, y);
        health2 = new StatusBarObject(heartFull, x +32, y);
        health3 = new StatusBarObject(heartFull, x +32*2, y);
        health4 = new StatusBarObject(heartFull, x +32*3, y);
        root.getChildren().add(life.getNode());
        root.getChildren().addAll(health1.getNode(), health2.getNode(), health3.getNode(), health4.getNode());
    }

    // calls update function and displays status bar
    public void display() {
        updateLives();
        updateHealth();
        updateStatusBarLocation();
        //display
        life.moveObject();
        health1.moveObject();
        health2.moveObject();
        health3.moveObject();
        health4.moveObject();
    }

    //update lives depending on lives of tank
    private void updateLives() {
        if(tank.getLives() == 0) {
            life.changeImg(life0);
        } else if(tank.getLives() == 1) {
            life.changeImg(life1);
        } else if(tank.getLives() == 2) {
            life.changeImg(life2);
        } else if(tank.getLives() == 3) {
            life.changeImg(life3);
        }
    }

    //update the health bar depending on HP of tank
    //must check all 4 health blocks for each stage of health
    private void updateHealth() {
        int health = tank.getHealth();
        if(health > 87) {
            health4.changeImg(heartFull);
            health3.changeImg(heartFull);
            health2.changeImg(heartFull);
            health1.changeImg(heartFull);
        } else if(health > 75) {
            health4.changeImg(heartHalf);
            health3.changeImg(heartFull);
            health2.changeImg(heartFull);
            health1.changeImg(heartFull);
        } else if(health > 62) {
            health4.changeImg(heartEmpty);
            health3.changeImg(heartFull);
            health2.changeImg(heartFull);
            health1.changeImg(heartFull);
        } else if(health > 50) {
            health4.changeImg(heartEmpty);
            health3.changeImg(heartHalf);
            health2.changeImg(heartFull);
            health1.changeImg(heartFull);
        } else if(health > 37) {
            health4.changeImg(heartEmpty);
            health3.changeImg(heartEmpty);
            health2.changeImg(heartFull);
            health1.changeImg(heartFull);
        } else if(health > 25) {
            health4.changeImg(heartEmpty);
            health3.changeImg(heartEmpty);
            health2.changeImg(heartHalf);
            health1.changeImg(heartFull);
        } else if(health >= 12) {
            health4.changeImg(heartEmpty);
            health3.changeImg(heartEmpty);
            health2.changeImg(heartEmpty);
            health1.changeImg(heartFull);
        } else if(health >= 0) {
            health4.changeImg(heartEmpty);
            health3.changeImg(heartEmpty);
            health2.changeImg(heartEmpty);
            health1.changeImg(heartHalf);
        } else {
            health4.changeImg(heartEmpty);
            health3.changeImg(heartEmpty);
            health2.changeImg(heartEmpty);
            health1.changeImg(heartEmpty);
        }
    }

    //update location of the status bar relative to the tank
    private void updateStatusBarLocation() {
        int tempX = tank.getX();
        int tempY = tank.getY();

        life.setX(tempX-32);
        life.setY(tempY-35);
        health1.setX(tempX);
        health1.setY(tempY-35);
        health2.setX(tempX+32);
        health2.setY(tempY-35);
        health3.setX(tempX+32*2);
        health3.setY(tempY-35);
        health4.setX(tempX+32*3);
        health4.setY(tempY-35);
    }

    private Image life0 = new Image("resources/StatusBar/hud0.png");
    private Image life1 = new Image("resources/StatusBar/hud1.png");
    private Image life2 = new Image("resources/StatusBar/hud2.png");
    private Image life3 = new Image("resources/StatusBar/hud3.png");

    private Image heartEmpty = new Image("resources/StatusBar/hudHeart_empty.png");
    private Image heartHalf = new Image("resources/StatusBar/hudHeart_half.png");
    private Image heartFull = new Image("resources/StatusBar/hudHeart_full.png");
}
