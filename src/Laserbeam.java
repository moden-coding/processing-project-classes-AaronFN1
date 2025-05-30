import java.util.*;

import processing.core.PApplet;

public class Laserbeam {
    private ArrayList<Laser> lasers;
    private ArrayList<Asteroid> asteroids;
    PApplet canvas;

    public Laserbeam(Ship ship, Laser laser, ArrayList<Asteroid> a, PApplet c) {
        canvas = c;
        lasers = new ArrayList<>();
        asteroids = a;
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

    public float laserAsteroidDistance(float asteroidX, float asteroidY) {
        float minDistance = 1000;
        for (int l = 0; l < lasers.size(); l++) {
            Laser laser = lasers.get(l);
            float x = laser.getX();
            float y = laser.getY();
            float distance = canvas.dist(x, y, asteroidX, asteroidY);
            if (distance < minDistance) {
                minDistance = distance;
            }
        }
        return minDistance;
    }

    public boolean isOffScreen(){
        Laser l = lasers.get(lasers.size()-1);
            if (l.getX()<0||l.getX()>1200){
                return true;
            }
            else if (l.getY()<0||l.getY()>800){
                return true;
            }
            else{
                return false;
            }
    }
}