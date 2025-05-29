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
    private float viableSize;
    private float smallestSize;

    public Asteroid(float startX, float startY, float speed, float size, float angle, PApplet c) {
        canvas = c;
        x = startX;
        y = startY;
        this.speed = speed;
        this.angle = angle;
        xDirection = PApplet.cos(angle) * speed;
        yDirection = PApplet.sin(angle) * speed;
        this.size = size;
        viableSize = 30;
        smallestSize = 5;
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
        if (size >= viableSize){
            canvas.noStroke();
            canvas.fill(150);
        }
        else{
            canvas.strokeWeight(3);
            canvas.stroke(50);
            canvas.noFill();
        }
        canvas.circle(x, y, size);
    }

    public boolean colllide(float distance) {
        if (distance <= size/2) {
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

    public float getViableSize() {
        return viableSize;
    }

    public float getSmallestSize() {
        return smallestSize;
    }

    public float getSpeed() {
        return speed;
    }
}