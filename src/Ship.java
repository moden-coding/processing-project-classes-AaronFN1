import processing.core.PApplet;

public class Ship {
    private PApplet canvas;
    private int x;
    private int y;
    private double xVelocity;
    private double yVelocity;
    private double velocity;
    private double xAcceleration;
    private double yAcceleration;
    private double acceleration;
    private int xDirection;
    private int yDirection;
    private double maxVelocity;
    private int xSize;
    private int ySize;

    public Ship(int shipX, int shipY, double shipVelocity, double shipAcceleration, double shipMaxVelocity,
            int shipXSize, int shipYSize, PApplet c) {
        canvas = c;
        x = shipX;
        y = shipY;
        velocity = shipVelocity;
        acceleration = shipAcceleration;
        maxVelocity = shipMaxVelocity;
        xSize = shipXSize;
        ySize = shipYSize;
        xVelocity = 0;
        yVelocity = 0;
        xDirection = 0;
        yDirection = 0;
        xAcceleration = 0;
        yAcceleration = 0;
    }

    public void display() {
        canvas.fill(255);
        canvas.rect(x, y, xSize, ySize);
    }

    //#region moving
    public void moveUp() {
        if (yVelocity > -maxVelocity) {
            yVelocity -= velocity;
            yDirection--;
        }
        if (yDirection < 0) {
            yAcceleration = acceleration;
        }
    }

    public void moveDown() {
        if (yVelocity < maxVelocity) {
            yVelocity += velocity;
            yDirection++;
        }
        if (yDirection > 0) {
            yAcceleration = -acceleration;
        }
    }

    public void moveLeft() {
        if (xVelocity > -maxVelocity) {
            xVelocity -= velocity;
            xDirection--;
        }
        if (xDirection < 0) {
            xAcceleration = acceleration;
        }
    }

    public void moveRight() {
        if (xVelocity < maxVelocity) {
            xVelocity += velocity;
            xDirection++;
        }
        if (xDirection > 0) {
            xAcceleration = -acceleration;
        }
    }

    public void movement() {
        if (xDirection > 1 || xDirection <-1) {
            xVelocity += xAcceleration;
        }
        if (yDirection > 1 || yDirection <-1) {
            yVelocity += yAcceleration;
        }
        x += xVelocity;
        y += yVelocity;
        // #region acceleration velocity and direction resetting
        if (yDirection < 0 && yVelocity > 0) {
            yAcceleration = 0;
            yVelocity = 0;
        }
        if (yDirection > 0 && yVelocity < 0) {
            yAcceleration = 0;
            yVelocity = 0;
        }
        if (xDirection < 0 && xVelocity > 0) {
            xAcceleration = 0;
            xVelocity = 0;
        }
        if (xDirection > 0 && xVelocity < 0) {
            xAcceleration = 0;
            xVelocity = 0;
        }
        if (yVelocity == 0) {
            yDirection = 0;
            yAcceleration = 0;
        }
        if (xVelocity == 0) {
            xDirection = 0;
            xAcceleration = 0;
        }
        // #endregion
        // #region ship edge reset
        if (y > 800 + ySize / 2) {
            y = 0 - ySize / 2;
        }
        if (y < 0 - ySize) {
            y = 800 + ySize / 2;
        }
        if (x > 1200 + xSize / 2) {
            x = 0 - xSize / 2;
        }
        if (x < 0 - xSize) {
            x = 1200 + xSize / 2;
        }
        // #endregion
    }
    //#endregion
}
