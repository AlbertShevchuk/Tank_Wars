import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controller {
    private TankObject tank0;
    private TankObject tank1;

    Controller(TankObject tank0, TankObject tank1) {
        this.tank0 = tank0;
        this.tank1 = tank1;
    }

    // handles controls, splits up keys for each tank
    public void EventHandlerPress(KeyEvent event) {
        KeyCode code = event.getCode();
        if( code.equals(KeyCode.UP) //condition for tank 1
                || code.equals(KeyCode.DOWN)
                || code.equals(KeyCode.LEFT)
                || code.equals(KeyCode.RIGHT)
                || code.equals(KeyCode.SLASH)
                || code .equals(KeyCode.PERIOD)
                ) {
            tank0.EventHandlerPress(event);
        }
        else if( code.equals(KeyCode.A) //condition for tank 2
                || code.equals(KeyCode.W)
                || code.equals(KeyCode.S)
                || code.equals(KeyCode.D)
                || code.equals(KeyCode.F)
                || code.equals(KeyCode.E)
                ) {
            tank1.EventHandlerPress(event);
        }

    }
    // handles controls, splits up keys for each tank
    public void EventHandlerRelease(KeyEvent event) {
        KeyCode code = event.getCode();
        if( code.equals(KeyCode.UP) //condition for tank 1
                || code.equals(KeyCode.DOWN)
                || code.equals(KeyCode.LEFT)
                || code.equals(KeyCode.RIGHT)
                || code .equals(KeyCode.PERIOD)
                ) {
            tank0.EventHandlerRelease(event);
        }
        else if( code.equals(KeyCode.A) //condition for tank 2
                || code.equals(KeyCode.W)
                || code.equals(KeyCode.S)
                || code.equals(KeyCode.D)
                || code.equals(KeyCode.E)
                ) {
            tank1.EventHandlerRelease(event);
        }
    }
}
