import java.util.ArrayList;

import processing.core.*;

public class App extends PApplet {
    public static void main(String[] args) {
        PApplet.main("App");
    }

    Ship ship;
    ArrayList<Laser> lasers = new ArrayList<>();
    boolean upPressed;
    boolean downPressed;
    boolean leftPressed;
    boolean rightPressed;
    boolean rotating;
    boolean shooting;

    public void setup() {
        background(0);
        ship = new Ship(600, 400, .2, 35, 35, 75, this);
    }

    public void settings() {
        size(1200, 800);
    }

    public void draw() {
        background(0);
        for (int i = 0; i < lasers.size(); i++) {
            Laser l = lasers.get(i);
            l.update();
            l.display();
            if (l.isOffScreen()) {
                lasers.remove(i);
            }

        }
        ship.display();
        ship.movement();
        if (upPressed && rotating == false){
            ship.moveUp();
        }
        if (downPressed && rotating == false){
            ship.moveDown();
        }
        if (leftPressed){
            ship.rotateLeft();
        }
        if (rightPressed){
            ship.rotateRight();
        }
        if (shooting){
            lasers.add(new Laser(ship.getX(), ship.getY(), ship.getRotation(), ship.getSpeed(), this));
        }
    }

    public void mousePressed() {
        // System.out.println("X velocity = " + shipXVelocity);
        // System.out.println("Y velocity = " + shipYVelocity);
    }

    public void keyPressed() {
        if (key == 'w' || keyCode == UP) {
            upPressed = true;
        }
        if (key == 's' || keyCode == DOWN) {
            downPressed = true;
        }
        if (key == 'a' || keyCode == LEFT) {
            leftPressed = true;
            rotating = true;
        }
        if (key == 'd' || keyCode == RIGHT) {
            rightPressed = true;
            rotating = true;
        }
        if (key == ' ') {
            shooting = true;
        }
    }

    public void keyReleased() {
        if (key == 'w' || keyCode == UP) {
            upPressed = false;
        }
        if (key == 's' || keyCode == DOWN) {
            downPressed = false;
        }
        if (key == 'a' || keyCode == LEFT) {
            leftPressed = false;
            rotating = false;
        }
        if (key == 'd' || keyCode == RIGHT) {
            rightPressed = false;
            rotating = false;
        }
        if (key == ' ') {
            shooting = false;
        }
    }
}
