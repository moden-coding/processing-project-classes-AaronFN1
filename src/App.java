import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

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
    boolean tabOrEnterPressed;
    boolean leftPressed;
    boolean rightPressed;
    boolean rotating;
    boolean shooting;
    boolean play = true;
    boolean justShot = false;
    boolean outsideAsteroid;
    boolean previousOutsideAsteroid = false;
    boolean cheeseMode = false;
    int highscore;
    int score = 0;
    int scene = 1;
    String dieMessage = "You lose :(";

    public void setup() {
        background(0);
        ship = new Ship(600, 400, .18, 35, 35, 75, this);
    }

    public void settings() {
        size(1200, 800);
    }

    public void draw() {
        background(0);
        outsideAsteroid = true;
        if (scene == 1) {
            asteroids.removeAll(asteroids);
            laserbeams.removeAll(laserbeams);
            for (int i = 0; i < 5; i++) {
                asteroidMaker(0);
            }
            ship.reset();
            score = 0;
            scene = 2;
            play = true;
        }
        if (scene == 2) {
            for (int a = 0; a < asteroids.size(); a++) {
                Asteroid r = asteroids.get(a);
                r.display();
                if (play) {
                    r.movement();
                }
                if (r.getSize() >= r.getViableSize()) {
                    if (ship.Distance(r.getX(), r.getY()) <= r.getSize() / 2) {
                        outsideAsteroid = false;
                    }
                }
            }
            if (outsideAsteroid == false && previousOutsideAsteroid == true) {
                // if (cheeseMode == false) {
                ship.damage(1);
                // }
            }
            previousOutsideAsteroid = outsideAsteroid;

            textAlign(LEFT);
            textSize(30);
            fill(255);
            text("Lives: " + ship.getLives(), 10, 30);
            textAlign(RIGHT);
            text("Score: " + score, 1190, 30);

            for (int i = 0; i < laserbeams.size(); i++) {
                Laserbeam l = laserbeams.get(i);
                if (play) {
                    l.shoot();
                }
                l.display();
                for (int a = 0; a < asteroids.size(); a++) {
                    Asteroid r = asteroids.get(a);
                    float laserbeamDistance = l.laserAsteroidDistance(r.getX(), r.getY());
                    if (r.colllide(laserbeamDistance)) {
                        if (r.getSize() >= r.getViableSize()) {
                            if (cheeseMode == false) {
                                laserbeams.remove(l);
                            }
                            score++;
                            if (!cheeseMode) {
                                if (score % 7 == 0) {
                                    asteroidMaker(1);
                                }
                            }
                            if (cheeseMode) {
                                if (score % 7 == 0) {
                                    asteroidMaker(1);
                                }
                            }
                            if (score % 10 == 0) {
                                ship.damage(-1);
                            }
                        }
                        if (r.getSize() > r.getSmallestSize()) {
                            asteroids.remove(r);
                            for (int n = 0; n < 2; n++) {
                                float speed;
                                if (r.getSize() > r.getViableSize()) {
                                    speed = r.getSpeed() + (float) 0.7;
                                    asteroids.add(
                                            new Asteroid(r.getX(), r.getY(), speed, r.getSize() / 2, random(TWO_PI),
                                                    this));
                                }
                            }
                        }
                    }
                }
            }
            if (ship.getLives() <= 0) {
                scene = 3;
            }
            ship.damageFlash(outsideAsteroid);
            ship.display();
            if (play) {
                ship.movement();
                // if (upPressed && !rotating) {
                // ship.moveUp();
                if (upPressed) {
                    ship.moveUp();
                }
                if (downPressed) {
                    ship.moveDown();
                }
                if (tabOrEnterPressed) {
                    ship.brake();
                }
                if (leftPressed) {
                    ship.rotateLeft();
                }
                if (rightPressed) {
                    ship.rotateRight();
                }
                if (shooting) {
                    if (cheeseMode == false) {
                        if (!justShot) {
                            Laserbeam laserbeam = new Laserbeam(ship, laser, asteroids, this);
                            laserbeams.add(laserbeam);
                            justShot = true;
                        }
                    }
                    if (cheeseMode) {
                        Laserbeam laserbeam = new Laserbeam(ship, laser, asteroids, this);
                        laserbeams.add(laserbeam);
                        justShot = true;
                    }
                }
            }
        }
        if (scene == 3) {
            // highscore checking
            textAlign(CENTER);
            fill(255);
            textSize(40);
            text("Score: " + score, 600, 350);
            try (Scanner scanner = new Scanner(Paths.get("Highscores.txt"))) {

                while (scanner.hasNextLine()) {
                    String savedHighscore = scanner.nextLine();
                    highscore = Integer.valueOf(savedHighscore);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            if (score > highscore) {
                highscore = score;
                dieMessage = "New highscore!";
                try (PrintWriter writer = new PrintWriter("Highscores.txt")) {
                    writer.println(highscore); // Writes the integer to the file
                    writer.close(); // Closes the writer and saves the file
                } catch (IOException e) {
                    System.out.println("An error occurred while writing to the file.");
                    e.printStackTrace();
                }

            }
            text(dieMessage, 600, 300);
            text("Highscore: " + highscore, 600, 400);
            textSize(30);
            text("Click to retry.", 600, 450);
        }
    }

    public void mousePressed() {
        play = !play;
        if (scene == 3) {
            scene = 1;
        }
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
        if (keyCode == ENTER || keyCode == TAB) {
            tabOrEnterPressed = true;
        }
        if (key == ' ') {
            shooting = true;
        }
        if (key == 'r') {
            if (scene == 2) {
                asteroids.removeAll(asteroids);
                laserbeams.removeAll(laserbeams);
                for (int i = 0; i < 5; i++) {
                    asteroidMaker(0);
                }
                ship.reset();
                score = 0;
                play = true;
            }
        }
        if (key == 'c') {
            cheeseMode = !cheeseMode;
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
        if (keyCode == ENTER || keyCode == TAB) {
            tabOrEnterPressed = false;
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

    public void asteroidMaker(int position) {
        float startX = randomX();
        float startY = randomY();
        if (position == 1) {
            float side = Math.round(random(1));
            float xSide = 0;
            float ySide = 0;
            if (side == 0) {
                xSide = Math.round(random(1, 2));
            } else {
                ySide = Math.round(random(1, 2));
            }

            if (xSide == 1) {
                // startX = random(5);
                startX = 0;
            }
            if (xSide == 2) {
                // startX = random(1195, 1200);
                startX = 1200;
            }
            if (ySide == 1) {
                // startX = random(5);
                startY = 0;
            }
            if (ySide == 2) {
                // startY = random(795, 800);
                startY = 800;
            }
        }
        float speed = random(1, 4);
        float size = random(120, 180);
        float angle = random(TWO_PI);
        Asteroid asteroid = new Asteroid(startX, startY, speed, size, angle, this);
        asteroids.add(asteroid);
    }
}