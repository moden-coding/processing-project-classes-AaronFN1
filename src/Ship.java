import processing.core.PApplet;

public class Ship {
    private PApplet canvas;
    private float x, y;
    private float xVelocity, yVelocity;
    private float velocity;
    private float maxVelocity;
    private int xSize, ySize;
    private float rotationAngle;
    private double friction;

    public Ship(int shipX, int shipY, double shipVelocity, double shipMaxVelocity,
            int shipXSize, int shipYSize, PApplet c) {
        canvas = c;
        x = shipX;
        y = shipY;
        velocity = (float) shipVelocity;
        maxVelocity = (float) shipMaxVelocity;
        xSize = shipXSize;
        ySize = shipYSize;
        xVelocity = 0;
        yVelocity = 0;
        rotationAngle = 0;
        friction = 0.995;
    }

    public void display() {
        canvas.pushMatrix();
        canvas.translate(x, y);
        canvas.rotate(rotationAngle);

        canvas.fill(255);
        canvas.stroke(255);
        canvas.strokeWeight(2);

        canvas.rectMode(PApplet.CENTER);
        canvas.rect(0, 0, xSize, ySize);

        canvas.popMatrix();
    }

    // public void reset(){
    //     x = 0;
    //     y = 0;
    //     velocity = (float) shipVelocity;
    //     maxVelocity = (float) shipMaxVelocity;
    //     xSize = shipXSize;
    //     ySize = shipYSize;
    //     xVelocity = 0;
    //     yVelocity = 0;
    //     rotationAngle = 0;
    //     friction = 0.995;
    // }

    public void moveUp() {
        float angle = rotationAngle - PApplet.HALF_PI;
        xVelocity += Math.cos(angle) * velocity;
        yVelocity += Math.sin(angle) * velocity;
        limitSpeed();
    }

    public void brake() {
        xVelocity *= 0.95;
        yVelocity *= 0.95;

        if (canvas.abs(xVelocity) < 0.05)
            xVelocity = 0;
        if (canvas.abs(yVelocity) < 0.05)
            yVelocity = 0;
    }

    public void rotateLeft() {
        rotationAngle -= 0.05f;
    }

    public void rotateRight() {
        rotationAngle += 0.05f;
    }

    public void movement() {
        x += xVelocity;
        y += yVelocity;

        xVelocity *= friction;
        yVelocity *= friction;

        // Screen wrapping
        if (x > 1200 + xSize / 2) {
            x = 0 - xSize / 2;
        }
        if (x < 0 - xSize / 2) {
            x = 1200 + xSize / 2;
        }
        if (y > 800 + ySize / 2) {
            y = 0 - ySize / 2;
        }
        if (y < 0 - ySize / 2) {
            y = 800 + ySize / 2;
        }
    }

    private void limitSpeed() {
        if (getSpeed() > maxVelocity) {
            float scale = maxVelocity / getSpeed();
            xVelocity *= scale;
            yVelocity *= scale;
        }
    }

    public float getSpeed() {
        return PApplet.sqrt(xVelocity * xVelocity + yVelocity * yVelocity);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRotation() {
        return rotationAngle;
    }
}
