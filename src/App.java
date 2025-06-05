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

    //declaring variables
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
    boolean cheeseModeTurnedOn = false;
    ArrayList<Integer> highscore = new ArrayList<>();
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
        //setup
        if (scene == 1) {
            cheeseMode = false;
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
        //actual game
        if (scene == 2) {
            //moving the asteroids and damage checking
            for (int a = 0; a < asteroids.size(); a++) {
                Asteroid r = asteroids.get(a);
                r.display();
                if (play) {
                    r.movement();
                }

                // if(r.insideShip(ship)){
                //     outsideAsteroid = false;
                // }

                if (r.getSize() >= r.getViableSize()) {
                    if (ship.Distance(r.getX(), r.getY()) <= r.getSize() / 2) {
                        outsideAsteroid = false;
                    }
                }
            }
            //damage
            if (outsideAsteroid == false && previousOutsideAsteroid == true) {
                // if (cheeseMode == false) {
                ship.damage(1);
                // }
            }
            previousOutsideAsteroid = outsideAsteroid;

            //text on screen
            textAlign(LEFT);
            textSize(30);
            fill(255);
            text("Lives: " + ship.getLives(), 10, 30);
            textAlign(RIGHT);
            text("Score: " + score, 1190, 30);
            
            //lasers to display and check if they hit an asteroid
            for (int i = 0; i < laserbeams.size(); i++) {
                Laserbeam l = laserbeams.get(i);
                if (play) {
                    l.shoot();
                }
                l.display();
                if (l.isOffScreen()) {
                    laserbeams.remove(l);
                }
                //checking if it hit an asteroid
                for (int a = 0; a < asteroids.size(); a++) {
                    Asteroid r = asteroids.get(a);
                    float laserbeamDistance = l.laserAsteroidDistance(r.getX(), r.getY());
                    if (r.colllide(laserbeamDistance)) {
                        if (r.getSize() >= r.getViableSize()) {
                            if (cheeseMode == false) {
                                laserbeams.remove(l);
                            }
                            score++;
                            //making new asteroids after destroying old ones
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
            //losing
            if (ship.getLives() <= 0) {
                scene = 3;
            }
            ship.damageFlash(outsideAsteroid);
            ship.display();
            //moving the ship
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
            //scores and such
            textAlign(CENTER);
            fill(255);
            textSize(50);
            text("Score: " + score, 600, 300);
            getHighscores();
            if (!cheeseModeTurnedOn) {
                newHighscore();
                cheeseModeTurnedOn = false;
            }
            saveHighscores();
            text(dieMessage, 600, 250);
            textSize(40);
            text("Highscore: " + highscore.get(0), 600, 350);
            text("Second:  " + highscore.get(1), 600, 390);
            text("Third: " + highscore.get(2), 600, 430);
            text("Fourth: " + highscore.get(3), 600, 470);
            text("Fifth: " + highscore.get(4), 600, 510);
            textSize(30);
            text("Click to retry.", 600, 560);
        }
    }

    boolean getHighscoresProcessed = false;

    //getting and setting highscores
    public void getHighscores() {
        if (!getHighscoresProcessed) {
            try (Scanner scanner = new Scanner(Paths.get("Highscores.txt"))) {

                while (scanner.hasNextLine()) {
                    String savedHighscore = scanner.nextLine();
                    highscore.add(Integer.valueOf(savedHighscore));
                }
                getHighscoresProcessed = true;

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    boolean newHighscoreProcessed = false;
    boolean newHighscore = false;

    public void newHighscore() {
        if (!newHighscoreProcessed) {
            for (int i = 0; i < highscore.size(); i++) {
                if (score > highscore.get(i)) {
                    if (!newHighscore) {
                        highscore.add(i, score);
                        newHighscore = true;
                        if (i > 0) {
                            dieMessage = "Top 5!";
                        }
                        if (i == 0) {
                            dieMessage = "New Highscore!!";
                        }
                    }
                }

            }
            newHighscoreProcessed = true;
        }
    }

    boolean saveHighscoresProcessed = false;

    public void saveHighscores() {
        if (!saveHighscoresProcessed) {
            try (PrintWriter writer = new PrintWriter("Highscores.txt")) {
                for (int i = 0; i < 5; i++) {
                    writer.write(String.valueOf(highscore.get(i)) + "\n");
                }
                saveHighscoresProcessed = true;
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
        }
    }
    
    //pause key
    public void mousePressed() {
        play = !play;
        if (scene == 3) {
            scene = 1;
        }
    }

    //controls
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
                cheeseMode = false;
                play = true;
            }
        }
        if (key == 'c') {
            cheeseMode = !cheeseMode;
            cheeseModeTurnedOn = true;
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

    //creating an asteroid
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