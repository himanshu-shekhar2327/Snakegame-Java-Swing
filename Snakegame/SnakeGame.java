package Snakegame;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;//this will create new block and placed it into arraylist
import java.util.Random;//this will create a random box
import javax.swing.*;


public class SnakeGame extends JPanel implements ActionListener,KeyListener{
    private class Tile{ //the tile class is private becaue only snake class can only access the tile class
        int x;
        int y;
        Tile(int x,int y){
            this.x=x;
            this.y=y;
        }
    }
    //creating the variablet
    int boardHeight;
    int boardwidth;
    int tileSize=25;
    //creating the constructor
    Tile snakeHead;//creating a variable
    Tile food;//creating the food varible

    // for snake eating food and grow its body we need arraylist and store it 
    ArrayList<Tile> snakebody ;
     Random random;
    //game logic 
    Timer gameLoop;
    int VelocityX;
    int VelocityY;
    boolean gameover =false;


    
    
    
    SnakeGame(int boardHeight,int boardwidth){
        this.boardHeight=boardHeight;
        this.boardwidth=boardwidth;
        setPreferredSize(new Dimension(this.boardwidth,this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);//we can make the game panel to listen the key pressedd by this method
        setFocusable(true);



        snakeHead = new Tile(5, 5);//default staring place
        snakebody = new ArrayList<Tile>();
        food = new Tile(10,10);
        random = new Random();

        placefood();
        VelocityX = 0;
        VelocityY = 1;
        gameLoop = new Timer(100,this);//assigning  gameloop to new Timer 
        gameLoop.start();

    }
    //creating  a function
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    //for drawing
    public void draw(Graphics g){
        g.setColor(Color.red);
        g.fill3DRect(food.x*tileSize,food.y*tileSize ,tileSize , tileSize,true);
        //Snake Head
        g.setColor(Color.green);
        g.fill3DRect(snakeHead.x*tileSize,snakeHead.y*tileSize ,tileSize,tileSize,true);
        // snake body
        for (int i=0;i<snakebody.size();i++){
            Tile snakePart = snakebody.get(i);
            g.fillRect(snakePart.x*tileSize,snakePart.y*tileSize,tileSize,tileSize);
            g.fill3DRect(snakePart.x*tileSize,snakePart.y*tileSize,tileSize,tileSize,true);
        }
        //score());
        g.setFont(new Font("Aerial",Font.PLAIN,16));
        if(gameover){
            g.setColor(Color.red);
            g.drawString("Game Over"+String.valueOf(snakebody.size()) ,tileSize-16,tileSize);
        }
        else{
            
            g.drawString("Score:" +String.valueOf(snakebody.size()),tileSize-16,tileSize);
        }


    }

    public void placefood(){
        food.x=random.nextInt(boardwidth/tileSize);//600/25=24
        food.y=random.nextInt(boardHeight/tileSize);
    }
    //creating the function to detect the head  of snake and the food
    public boolean collision(Tile tile1 , Tile tile2){
        return tile1.x==tile2.x && tile1.y==tile2.y;
    }
    public void move(){
        //eating the food
        if(collision(snakeHead, food)){
            snakebody.add(new Tile(food.x , food.y));
            placefood();
        }
        //moving the snake body 
        for (int i =snakebody.size()-1;i>=0;i--){
            Tile snakePart = snakebody.get(i);
            if(i==0){
                snakePart.x=snakeHead.x;
                snakePart.y=snakeHead.y;
                
            }
            else{
                Tile prevSnakePart=snakebody.get(i-1);
                snakePart.x=prevSnakePart.x;
                snakePart.y=prevSnakePart.y;
                
            }
            
            
        }
        //this is going to be called every 100 milliseconds and we are udating the velocity in x and y direction
        snakeHead.x+=VelocityX;
        snakeHead.y+=VelocityY;
        //game over condition iteration throw all the body parts of snake
        
        for (int i=0 ; i<snakebody.size();i++){
            Tile snakePart = snakebody.get(i);
            //collide with the snake head'
            if(collision(snakeHead, snakePart)){
                gameover=true;
            }
        }
        if(snakeHead.x*tileSize<0 || snakeHead.x*tileSize > boardwidth || snakeHead.y*tileSize  < 0 || snakeHead.y*tileSize > boardHeight){
            gameover = true;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e){
        move();
        repaint();//100 milisecond we are goinng to call draw function
        if(gameover){
            gameLoop.stop();
        }

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP && VelocityY!=1){
            VelocityX=0;
            VelocityY=-1;
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN && VelocityY!=-1){
            VelocityX=0;
            VelocityY=1;

        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT && VelocityX !=1){
            VelocityY=0;
            VelocityX=-1;
        }
        else if (e.getKeyCode()==KeyEvent.VK_RIGHT && VelocityX != -1){
            VelocityX=1;
            VelocityY=0;
        }
    }
    //do not need this
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

    
}
