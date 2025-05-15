import processing.core.*;

public class App extends PApplet {
    public static void main(String[] args) {
        PApplet.main("App");
    }

    Ship ship;

    public void setup() {
        background(0);
        ship = new Ship(600, 400, 5, .2, 40, 35, 70, this);
    }

    public void settings() {
        size(1200, 800);
    }

    public void draw() {
        background(0);
        ship.display();
        ship.movement();
    }

    public void mousePressed() {
        // System.out.println("X velocity = " + shipXVelocity);
        // System.out.println("Y velocity = " + shipYVelocity);
    }

    public void keyPressed() {
        if (key == 'w' || keyCode == UP) {
            ship.moveUp();
        }
        if (key == 's' || keyCode == DOWN) {
            ship.moveDown();
            ship.rotate(45);
        }
        if (key == 'a' || keyCode == LEFT) {
            ship.moveLeft();
        }
        if (key == 'd' || keyCode == RIGHT) {
            ship.moveRight();
        }
    }
}
