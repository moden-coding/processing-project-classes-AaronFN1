import java.util.*;

import processing.core.PApplet;

public class Laserbeam {
    float x;
    float y;
    ArrayList<Laser> lasers;
    PApplet canvas;

    public Laserbeam(Ship ship, Laser laser, PApplet c) {
        canvas = c;
        lasers = new ArrayList<>();

        for (int l = 0; l < 10; l++) {
            laser = new Laser(ship.getX() + 4 * l * canvas.sin(ship.getRotation()),
                    ship.getY() - 4 * l * canvas.cos(ship.getRotation()), ship.getRotation(), ship.getSpeed(), canvas);
            lasers.add(laser);
        }
    }

    public void display() {
        for (int l = 0; l < lasers.size(); l++) {
            Laser laser = lasers.get(l);
            canvas.stroke(255, 0, 0);
            canvas.strokeWeight(4);
            canvas.point(laser.getX(), laser.getY());
        }
    }

    public void shoot() {
        for (int l = 0; l < lasers.size(); l++) {
            Laser laser = lasers.get(l);
            laser.shoot();
        }
    }

    public ArrayList<Laser> getLasers() {
        return lasers;
    }

    public float getX() {
        Laser laser = lasers.get(0);
        return laser.getX();
    }

    public float getY() {
        Laser laser = lasers.get(0);
        return laser.getY();
    }
}
