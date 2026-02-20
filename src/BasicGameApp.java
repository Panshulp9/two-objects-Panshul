//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section
//step 1: implement key listener
//Step 1: Implement mouse listener
public class BasicGameApp implements Runnable, KeyListener, MouseListener {

   //Variable Definition Section
   //Declare the variables used in the program 
   //You can set their initial values too
   
   //Sets the width and height of the program window
	final int WIDTH = 1000;
	final int HEIGHT = 700;

   //Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
   public JPanel panel;
   
	public BufferStrategy bufferStrategy;
	public Image astroPic;
    public Image asteroidPic;
    public Image backgroundPic;

   //Declare the objects used in the program
   //These are things that are made up of more than one variable type
	private Astronaut astro;
    public Astronaut astro2;
    public Asteroid asteroid1;
    public Asteroid asteroid2;
    public Rectangle startHitbox;
    public boolean startGame;



   // Main method definition
   // This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method  
	}


   // Constructor Method
   // This has the same name as the class
   // This section is the setup portion of the program
   // Initialize your variables and construct your program objects here.
	public BasicGameApp() {
        int randx = (int)(Math.random()*1000)+1;
        int randy = (int)(Math.random()*700)+1;
      setUpGraphics();
       
      //variable and objects
      //create (construct) the objects needed for the game and load up 
		backgroundPic = Toolkit.getDefaultToolkit().getImage("Space.jpeg");
        astroPic = Toolkit.getDefaultToolkit().getImage("astronaut.png"); //load the picture
        asteroidPic = Toolkit.getDefaultToolkit().getImage("ASTEROID ASTRO.jpeg");
		astro = new Astronaut(randx,randy);
        astro2 = new Astronaut (randx,randy);
        astro2.dx = -3;
        astro2.dy=5;
        astro2.height = 99;
        astro2.width = 77;
        asteroid1 = new Asteroid (427,640);
        asteroid2 = new Asteroid(477,507);
        asteroid1.dx= -asteroid1.dx;
        startHitbox = new Rectangle(100,100,100,100);
        startGame = false;


	}// BasicGameApp()

   
//*******************************************************************************
//User Method Section
//
// put your code to do things here.

   // main thread
   // this is the code that plays the game after you set things up
	public void run() {

      //for the moment we will loop things forever.
		while (true) {

         moveThings();  //move all the game objects
         render();  // paint the graphics
         pause(20); // sleep for 10 ms
		}
	}


	public void moveThings()
	{
      //calls the move( ) code in the objects
		if (startGame == true) {
            astro.move();
            astro2.move();
            asteroid1.move();
            asteroid2.move();
            crashing();
        }
	}
    public void crashing(){
        //if astronauts crash into each other
        if(astro.hitbox.intersects(astro2.hitbox)){
           //n System.out.println("crash");
            astro.dx=-astro.dx;
            astro2.dx=-astro2.dx;
            astro.dy=-astro.dy;
            astro2.dy=-astro.dy;
            astro2.isAlive = false;
        }
        if(asteroid2.hitbox.intersects(asteroid1.hitbox) && asteroid2.isCrashing == false){
            System.out.println("KABOOM!");
            asteroid2.height = asteroid2.height+10;
            asteroid2.isCrashing = true;
        }
        if (!asteroid1.hitbox.intersects(asteroid2.hitbox)){
            asteroid2.isCrashing = false;
        }

    }
	
   //Pauses or sleeps the computer for the amount specified in milliseconds
   public void pause(int time ){
   		//sleep
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {

			}
   }

   //Graphics setup method
   private void setUpGraphics() {
      frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.
   
      panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
      panel.setLayout(null);   //set the layout
   
      // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
      // and trap input events (Mouse and Keyboard events)
      canvas = new Canvas();

      //step 2: add KeyListener to canvas
       canvas.addKeyListener(this);
       //step 2: add mouse motion listener
       canvas.addMouseListener(this);

      canvas.setBounds(0, 0, WIDTH, HEIGHT);
      canvas.setIgnoreRepaint(true);
   
      panel.add(canvas);  // adds the canvas to the panel.
   
      // frame operations
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
      frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
      frame.setResizable(false);   //makes it so the frame cannot be resized
      frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!
      
      // sets up things so the screen displays images nicely.
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      canvas.requestFocus();
      System.out.println("DONE graphic setup");
   
   }


	//paints things on the screen using bufferStrategy
	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

		g.clearRect(0, 0, WIDTH, HEIGHT);
        g.drawRect(100, 100, 100, 100);
        g.setColor(Color.MAGENTA);
        g.fillRect(100, 100, 100, 100);
        g.setColor(Color.blue);
        g.drawString("START",133,150);


        if (startGame == true) {
            g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);
            //draw the image of the astronaut
            g.drawImage(astroPic, astro.xpos, astro.ypos, astro.width, astro.height, null);
            if (astro2.isAlive == true) {
                g.drawImage(astroPic, astro2.xpos, astro2.ypos, astro2.width, astro2.height, null);
            }
            if (asteroid1.isAlive == true) {
                g.drawImage(asteroidPic, asteroid1.xpos, asteroid1.ypos, asteroid1.width, asteroid1.height, null);
            }
            g.drawImage(asteroidPic, asteroid2.xpos, asteroid2.ypos, asteroid2.width, asteroid2.height, null);
            g.drawRect(astro.hitbox.x, astro.hitbox.y, astro.hitbox.width, astro.hitbox.height);
            g.drawRect(asteroid2.hitbox.x, asteroid2.hitbox.y, asteroid2.hitbox.width, asteroid2.hitbox.height);
        }
        g.dispose();


		bufferStrategy.show();
	}
    //step 3: add methods

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if (e.getKeyCode() == 38){
            System.out.println("going up");
            astro.ypos = astro.ypos-10;
            //astro.dy = -Math.abs(astro.dy);
            astro.isUp = true;
        }
        if (e.getKeyCode() == 37){
            System.out.println("going left");
            astro.xpos = astro.xpos-5;
        }
        if (e.getKeyCode() == 39){
            System.out.println("going right");
            astro.xpos = astro.xpos+5;
        }
        if(e.getKeyCode() == 40){
            System.out.println("going down");
            astro.ypos = astro.ypos+ 10;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("I stopped touching "+ e.getKeyCode());
        if(e.getKeyCode() == 38){
            System.out.println("not going up");
            astro.isUp = false;
        }
        if(e.getKeyCode() == 40){
            System.out.println("not going down");
            astro.isdown = false;
        }
    }

    //step 3: implement method
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e.getPoint());
        Rectangle pointHitbox = new Rectangle(e.getX(),e.getY(),1,1);
        if(startHitbox.intersects(pointHitbox)){
            System.out.println("Start game");
            startGame = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse entered the screen");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse exited the screen");
    }



}