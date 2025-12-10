public class Asteroid {
    public String name;                //holds the name of the hero
    public int xpos;                //the x position
    public int ypos;                //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.


    // METHOD DEFINITION SECTION

    // Constructor Definition
    // A constructor builds the object when called and sets variable values.


    //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.
    public Asteroid(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        dx = 3;
        dy = 10;
        width = 70;
        height = 80;
        isAlive = true;

    } // constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {
        if(ypos>700){//wrap when hitting the bottom wall
            ypos = 0;
        }
        if(ypos<0){//wrap when hitting the top
            ypos=700;
        }
        if(xpos>1000){
            xpos = 0;
        }
        if(xpos<0){
            xpos = 1000;
        }
        //todo: make the asteroid wrap when it hits the right and left walls

        xpos = xpos + dx;
        ypos = ypos + dy;

    }
}
