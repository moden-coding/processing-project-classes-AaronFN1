import java.util.ArrayList;

import processing.core.*;

public class App extends PApplet {
    public static void main(String[] args) {
        PApplet.main("App");
    }

    Ship ship;
    Laser laser;
    Asteroid asteroid;
    // ArrayList<Laser> lasers = new ArrayList<>();
    ArrayList<Asteroid> asteroids = new ArrayList<>();
    ArrayList<Laserbeam> laserbeams = new ArrayList<>();
    boolean upPressed;
    boolean downPressed;
    boolean leftPressed;
    boolean rightPressed;
    boolean rotating;
    boolean shooting;
    boolean play = true;
    boolean justShot = false;

    public void setup() {
        background(0);
        ship = new Ship(600, 400, .2, 35, 35, 75, this);
        for (int i = 0; i < 5; i++) {
            float startX = randomX();
            float startY = randomY();
            float speed = random(1, 3);
            float size = random(40, 70);
            float angle = random(TWO_PI);
            Asteroid asteroid = new Asteroid(startX, startY, speed, size, angle, this);
            asteroids.add(asteroid);
        }
    }

    public void settings() {
        size(1200, 800);
    }

    public void draw() {
        background(0);
        for (int a = 0; a < asteroids.size(); a++) {
            Asteroid r = asteroids.get(a);
            r.display();
            if (play) {
                r.movement();
            }
        }

        for (int i = 0; i < laserbeams.size(); i++) {
            Laserbeam l = laserbeams.get(i);
            // if (play) {
            l.shoot();
            // }
            l.display();
            for (int a = 0; a < asteroids.size(); a++) {
                Asteroid r = asteroids.get(a);
                float laserbeamDistance = l.laserAsteroidDistance(r.getX(), r.getY());
                if (r.colllide(laserbeamDistance)) {
                    if (r.getSize() >= r.getViableSize()) {
                        laserbeams.remove(l);
                    }
                    if (r.getSize() > r.getSmallestSize()) {
                        asteroids.remove(r);
                        for (int n = 0; n < 2; n++)
                            asteroids.add(
                                    new Asteroid(r.getX(), r.getY(), r.getSpeed(), r.getSize() / 2, random(TWO_PI),
                                            this));
                    }
                }
            }
        }
        ship.display();
        // if (play) {
        ship.movement();
        if (upPressed && rotating == false) {
            ship.moveUp();
        }
        if (downPressed) {
            ship.brake();
        }
        if (leftPressed) {
            ship.rotateLeft();
        }
        if (rightPressed) {
            ship.rotateRight();
        }
        if (shooting) {
            if (!justShot) {
                Laserbeam laserbeam = new Laserbeam(ship, laser, asteroids, this);
                laserbeams.add(laserbeam);
                justShot = true;
            }
        }
    }

    public void mousePressed() {
        play = !play;
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
        if (key == 'r') {
            for (int a = 0; a < asteroids.size(); a++) {
                Asteroid r = asteroids.get(a);
                asteroids.remove(r);
            }
            for (int i = 0; i < laserbeams.size(); i++) {
                Laserbeam l = laserbeams.get(i);
                laserbeams.remove(l);
            }
            for (int i = 0; i < 5; i++) {
                float startX = randomX();
                float startY = randomY();
                float speed = random(1, 3);
                float size = random(40, 70);
                float angle = random(TWO_PI);
                Asteroid asteroid = new Asteroid(startX, startY, speed, size, angle, this);
                asteroids.add(asteroid);
            }

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
            justShot = false;
        }
    }

    public float randomX() {
        return random(1200);
    }

    public float randomY() {
        return random(800);
    }
}
