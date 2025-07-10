/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Question_2;

/**
 *
 * @author xhu
 */

import javax.swing.JFrame;

/**
 * main class to start the Snake Game. this class sets up the game window
 * and kicks off the game loop.
 */
public class SnakeGame {

    public static void main(String[] args) {
        // create a new JFrame (the game window)
        JFrame frame = new JFrame("Snake Game");
        
        // set the default close operation so the game exits when the window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // create an instance of the Panel (where the game happens)
        Panel panel = new Panel();
        
        // add the panel to the frame
        frame.getContentPane().add(panel);
        
        // set the size of the game window
        frame.setSize(1000, 1000);
        
        // make the window visible
        frame.setVisible(true);
    }
}
