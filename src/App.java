import processing.core.*;

public class App extends PApplet {
    public static void main(String[] args) {
        PApplet.main("App");
    }

    int shipX;
    int shipY;
    double shipXVelocity;
    double shipYVelocity;
    double shipVelocity;
    double shipXAcceleration;
    double shipYAcceleration;
    double shipAcceleration;
    int xDirection;
    int yDirection;
    double maxVelocity;

    public void setup() {

        background(0);
        shipX = 600;
        shipY = 400;
        shipXVelocity = 0;
        shipYVelocity = 0;
        shipVelocity = 5;
        shipXAcceleration = 0;
        shipYAcceleration = 0;
        shipAcceleration = 0.2;
        yDirection = 0;
        xDirection = 0;
        maxVelocity = 33;

    }

    public void settings() {
        size(1200, 800);
    }

    public void draw() {
        background(0);
        rect(shipX, shipY, 70, 70);
        if (abs(xDirection) > 1){
        shipXVelocity += shipXAcceleration;
        }
        if (abs(yDirection) > 1){
        shipYVelocity += shipYAcceleration;
        }
        shipX += shipXVelocity;
        shipY += shipYVelocity;
        //#region acceleration velocity and direction resetting
        if (yDirection < 0 && shipYVelocity > 0) {
            shipYAcceleration = 0;
            shipYVelocity = 0;
        }
        if (yDirection > 0 && shipYVelocity < 0) {
            shipYAcceleration = 0;
            shipYVelocity = 0;
        }
        if (xDirection < 0 && shipXVelocity > 0) {
            shipXAcceleration = 0;
            shipXVelocity = 0;
        }
        if (xDirection > 0 && shipXVelocity < 0) {
            shipXAcceleration = 0;
            shipXVelocity = 0;
        }
        if (shipYVelocity == 0) {
            yDirection = 0;
            shipYAcceleration = 0;
        }
        if (shipXVelocity == 0) {
            xDirection = 0;
            shipXAcceleration = 0;
        }
        //#endregion
        // #region ship edge reset
        if (shipY > 800 + 35) {
            shipY = 0 - 35;
        }
        if (shipY < 0 - 70) {
            shipY = 800 + 35;
        }
        if (shipX > 1200 + 35) {
            shipX = 0 - 35;
        }
        if (shipX < 0 - 70) {
            shipX = 1200 + 35;
        }
        // #endregion
    }

    public void mousePressed() {
        System.out.println("X velocity = " + shipXVelocity);
        System.out.println("Y velocity = " + shipYVelocity);
    }

    public void keyPressed() {
        if (key == 'w' || keyCode == UP) {
            if (shipYVelocity > -maxVelocity) {
                shipYVelocity -= shipVelocity;
                yDirection--;
            }
            if (yDirection < 0) {
                shipYAcceleration = shipAcceleration;
            }
        }
        if (key == 's' || keyCode == DOWN) {
            if (shipYVelocity < maxVelocity) {
                shipYVelocity += shipVelocity;
                yDirection++;
            }
            if (yDirection > 0) {
                shipYAcceleration = -shipAcceleration;
            }
        }
        if (key == 'a' || keyCode == LEFT) {
            if (shipXVelocity > -maxVelocity) {
                shipXVelocity -= shipVelocity;
                xDirection--;
            }
            if (xDirection < 0) {
                shipXAcceleration = shipAcceleration;
            }
        }
        if (key == 'd' || keyCode == RIGHT) {
            if (shipXVelocity < maxVelocity) {
                shipXVelocity += shipVelocity;
                xDirection++;
            }
            if (xDirection > 0) {
                shipXAcceleration = -shipAcceleration;
            }
        }
    }
}
