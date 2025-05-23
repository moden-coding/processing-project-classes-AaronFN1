import processing.core.PApplet;

public class Asteroid {
    private PApplet canvas;
    private float x;
    private float y;
    private float xDirection;
    private float yDirection;
    private float size;
    private float angle;
    private float speed;

    public Asteroid(float startX, float startY, float speed, float size, float angle, PApplet c) {
        canvas = c;
        x = startX;
        y = startY;
        this.speed = speed;
        this.angle = angle;
        xDirection = PApplet.cos(angle) * speed;
        yDirection = PApplet.sin(angle) * speed;
        this.size = size;

    }

    public void movement() {
        x += xDirection;
        y += yDirection;

        if (x > 1200 + size / 2) {
            x = 0 - size / 2;
        }
        if (x < 0 - size / 2) {
            x = 1200 + size / 2;
        }
        if (y > 800 + size / 2) {
            y = 0 - size / 2;
        }
        if (y < 0 - size / 2) {
            y = 800 + size / 2;
        }
    }

    public void display() {
        canvas.noFill();
        canvas.stroke(150);
        canvas.strokeWeight(2);
        canvas.circle(x, y, size);
    }

    public boolean colllide(float x, float y) {
        if ((canvas.dist(x, y, this.x, this.y)) <= size/2) {
            return true;
        } else {
            return false;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSize() {
        return size;
    }

    public float getSpeed() {
        return speed;
    }
}
