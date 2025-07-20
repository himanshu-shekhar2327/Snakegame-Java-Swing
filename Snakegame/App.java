package Snakegame;
// import Snakegamer.java.*;
import javax.swing.*;
 
public class App{
    public static void main(String[] args) {
        int boardwidth=600;
        int boardHeight=600;
        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardwidth,boardHeight);
        frame.setLocationRelativeTo(null);//this will place the window in the center
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //creating the instance of two varible

        SnakeGame snakeGame =new SnakeGame(boardHeight, boardwidth);
        frame.add(snakeGame);
        frame.pack();//it will placae the jpanel in four dimension
        snakeGame.requestFocus();//snake game is going to be listening the key pressed


    }
}