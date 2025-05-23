import java.util.*;

import processing.core.PApplet;

public class Laserbeam {
    float x;
    float y;
    ArrayList<Laser> lasers;
    PApplet canvas;

    public Laserbeam(Ship ship, PApplet c) {
        canvas = c;
        for (int l = 0; l < 10; l++) {
            Laser laser = new Laser(ship.getX() + 4 * l * canvas.sin(ship.getRotation()),
                    ship.getY() - 4 * l * canvas.cos(ship.getRotation()), ship.getRotation(), ship.getSpeed(), canvas);
            lasers.add(laser);
        }
    }
}
