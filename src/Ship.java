import processing.core.PApplet;

public class Ship {
    private int x;
    private int y;
    private double xVelocity;
    private double yVelocity;
    private double velocity;
    private double xAcceleration;
    private double yAcceleration;
    private double acceleration;
    private int xDirection;
    private int yDirection;
    private double maxVelocity;
    private int xSize;
    private int ySize;
    
    public Ship(int shipX, int shipY, double shipVelocity, double shipAcceleration, double shipMaxVelocity, int shipXSize, int shipYSize, PApplet c){
        canvas = c;
        x = shipX;
        y = shipY;
        velocity = shipVelocity;
        acceleration = shipAcceleration;
        maxVelocity = shipMaxVelocity;
        xSize = shipXSize;
        ySize = shipYSize;
    }
    
    public void display(){
        canvas.rect
    }
}
