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
    private int lives;
    private int shipColor;
    
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
        lives = 5;
        shipColor = canvas.color(255);
    }

    public void display() {
        canvas.pushMatrix();
        canvas.translate(x, y);
        canvas.rotate(rotationAngle);

        canvas.fill(shipColor);
        canvas.stroke(255);
        canvas.strokeWeight(2);

        canvas.rectMode(PApplet.CENTER);
        canvas.rect(0, 0, xSize, ySize);

        canvas.noStroke();
        canvas.fill(255, 0, 0);
        float tipY = -ySize / 2 - (ySize / 10f) / 2;
        canvas.rect(0, tipY, xSize / 3.5f, ySize / 10f);

        canvas.popMatrix();
    }

    public void reset() {
        x = 600;
        y = 400;
        xVelocity = 0;
        yVelocity = 0;
        rotationAngle = 0;
        friction = 0.995;
        lives = 5;
    }

    public void moveUp() {
        float angle = rotationAngle - PApplet.HALF_PI;
        xVelocity += Math.cos(angle) * velocity;
        yVelocity += Math.sin(angle) * velocity;
        limitSpeed();
    }

    public void moveDown() {
        float angle = rotationAngle - PApplet.HALF_PI;
        xVelocity -= Math.cos(angle) * velocity;
        yVelocity -= Math.sin(angle) * velocity;
        limitSpeed();
    }

    public void brake() {
        xVelocity *= 0.90;
        yVelocity *= 0.90;

        if (canvas.abs(xVelocity) < 0.05)
            xVelocity = 0;
        if (canvas.abs(yVelocity) < 0.05)
            yVelocity = 0;
    }

    public void rotateLeft() {
        rotationAngle -= 0.06f;
    }

    public void rotateRight() {
        rotationAngle += 0.06f;
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

    public void damageFlash(boolean outsideAsteroid){
        if (outsideAsteroid == false){
            shipColor = canvas.color(255, 0, 0);
        }
        else{
            shipColor = canvas.color(255);
        }
    }
    public void damage(float life) {
        // if (!insideAsteroid) {
        // insideAsteroid = true;
        lives -= life;
        // }

    }

    // public void beingDamaged(boolean damage) {
    // insideAsteroid = damage;
    // }

    public int getLives() {
        return lives;
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

    public float Distance(float asteroidX, float asteroidY) {
        // Step 1: Translate asteroid relative to ship
        float dx = asteroidX - x;
        float dy = asteroidY - y;

        // Step 2: Rotate asteroid point into ship's local coordinate system
        float unrotatedX = dx * PApplet.cos(-rotationAngle) - dy * PApplet.sin(-rotationAngle);
        float unrotatedY = dx * PApplet.sin(-rotationAngle) + dy * PApplet.cos(-rotationAngle);

        // Step 3: Clamp to the ship's rectangle edges
        float halfW = xSize / 2f;
        float halfH = ySize / 2f;
        float clampedX = PApplet.constrain(unrotatedX, -halfW, halfW);
        float clampedY = PApplet.constrain(unrotatedY, -halfH, halfH);

        // Step 4: Convert clamped point back to global coordinates
        float rotatedX = clampedX * PApplet.cos(rotationAngle) - clampedY * PApplet.sin(rotationAngle);
        float rotatedY = clampedX * PApplet.sin(rotationAngle) + clampedY * PApplet.cos(rotationAngle);
        float closestX = x + rotatedX;
        float closestY = y + rotatedY;

        // Step 5: Return distance from asteroid to closest point on ship
        return canvas.dist(asteroidX, asteroidY, closestX, closestY);
    }
}