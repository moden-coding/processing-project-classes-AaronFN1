import processing.core.PApplet;

public class Laser {
    private PApplet canvas;
    private float x, y;
    private float velocity;
    private float angle;
    private float dx, dy;

    public Laser(float startX, float startY, float rotationAngle, float speed, PApplet c) {
        canvas = c;
        x = startX;
        y = startY;
        angle = rotationAngle - PApplet.HALF_PI;
        if (speed > 8){
        velocity = speed;
        }
        else {
            velocity = 8;
        }
        dx = (float) Math.cos(angle) * velocity;
        dy = (float) Math.sin(angle) * velocity;
    }

    public void shoot() {
        x += dx;
        y += dy;
    }

    public void display() {
        canvas.stroke(255, 0, 0);
        canvas.strokeWeight(4);
        canvas.point(x, y);
    }

    public boolean isOffScreen() {
        return x < 0 || x > 1200 || y < 0 || y > 800;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }
}
