import processing.core.*;

public class App extends PApplet{
    public static void main(String[] args)  {
        PApplet.main("App");
    }

    int shipX;
    int shipY;
    double shipXVelocity;
    double shipYVelocity;
    double shipVelocity;
    double shipXAcceleration;
    double shipYAcceleration;
    double shipAcceleration;
    int xDirection;
    int yDirection;

    public void setup(){
        
        background(0);
        shipX = 600;
        shipY = 400;
        shipXVelocity = 0;
        shipYVelocity = 0;
        shipVelocity = 10;
        shipXAcceleration = 0;
        shipYAcceleration = 0;
        shipAcceleration = 0.5;
        yDirection = 0;
        xDirection = 0;
        
    }

    public void settings(){
        size(1200, 800);
    }

    public void draw(){
        background(0);
       rect(shipX, shipY, 70, 70);
       shipXVelocity+=shipXAcceleration;
       shipYVelocity+=shipYAcceleration;
       shipX+=shipXVelocity;
       shipY+=shipYVelocity;
       if (yDirection<0 && shipYVelocity > 0){
        shipYAcceleration = 0;
        shipYVelocity = 0;
       }
       if (yDirection>0 && shipYVelocity < 0){
        shipYAcceleration = 0;
        shipYVelocity = 0;
       }
       if (xDirection<0 && shipXVelocity > 0){
        shipXAcceleration = 0;
        shipXVelocity = 0;
       }
       if (yDirection<0 && shipXVelocity > 0){
        shipXAcceleration = 0;
        shipXVelocity = 0;
       }
    //    if (shipYVelocity == 0){
    //     yDirection=0;
    //    }
    //    if (shipXVelocity == 0 && shipXAcceleration == 0){
    //     xDirection = 0;
    //    }
       //#region ship edge reset
       if (shipY>800+70){
        shipY = 0 - 70;
       }
       if (shipY<0-70){
        shipY = 800+ 70;
       }
       if (shipX>1200+70){
        shipX = 0 - 70;
       }
       if (shipX<0-70){
        shipX = 1200+70;
       }
       //#endregion
    }

    public void keyPressed(){
        if (key == 'w'){
            shipYVelocity-=shipVelocity;
            shipYAcceleration = shipAcceleration;
            yDirection--;
        }
        if (key == 's'){
            shipYVelocity+=shipVelocity;
            shipYAcceleration = -shipAcceleration;
            yDirection++;
        }
        if (key == 'a'){
            shipXVelocity-=shipVelocity;
            shipXAcceleration = shipAcceleration;
            xDirection--;
        }
        if (key == 'd'){
            shipXVelocity+=shipVelocity;
            shipXAcceleration = -shipAcceleration;
            xDirection++;
        }
    }
}
