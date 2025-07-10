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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel implements KeyListener {

    public Snake snake; // the snake that moves around
    public int[] numberX = new int[10]; // stores the x-coordinates for the numbers
    public int[] numberY = new int[10]; // stores the y-coordinates for the numbers
    public int[] numbers = new int[10]; // stores the generated numbers from 1-10
    public int letterX, letterY; // coordinates for the letter
    public char currentLetter; // stores the current letter on the panel
    public Random rand = new Random(); // used to generate random positions
    public boolean letterGenerated = false; // flag to check if a letter has been placed
    public boolean numbersGenerated = false; // flag to check if numbers have been placed

    public Panel() {
        this.addKeyListener(this); // listen for key presses
        this.setFocusable(true); // make sure the panel can receive key events

        // create the snake with the initial panel size
        Snake.setPanelSize(getWidth(), getHeight());
        snake = new Snake(500, 500);

        // listen for when the panel gets resized so we can adjust the game elements
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Snake.setPanelSize(getWidth(), getHeight());
                if (!numbersGenerated) {
                    generateNumbers();
                    numbersGenerated = true;
                }
                if (!letterGenerated) {
                    generateNewLetter();
                    letterGenerated = true;
                }
            }
        });

        // set up a timer that runs the game loop every 100 milliseconds
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snake.move(); // move the snake in its current direction
                checkCollisions(); // see if the snake has hit anything
                repaint(); // redraw the panel to show any changes
            }
        });
        timer.start(); // start the game loop
    }

    
     // generates and places the numbers randomly on the panel. 
     // this method will be called initially and every time the snake hits a number.
     
    public void generateNumbers() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        if (panelWidth > 0 && panelHeight > 0) {
            for (int i = 0; i < 10; i++) {
                numbers[i] = 1 + rand.nextInt(10); // generates numbers between 1 and 10
                numberX[i] = rand.nextInt(panelWidth - 20); // generate random X coordinate
                numberY[i] = rand.nextInt(panelHeight - 20); // generate random Y coordinate
            }
        } else {
            throw new IllegalStateException("Panel dimensions are not set properly.");
        }
    }

    
     // generates a new letter at a random position on the panel.
     
    public void generateNewLetter() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        if (panelWidth > 0 && panelHeight > 0) {
            currentLetter = generateRandomLetter(); // generate a new random letter
            letterX = rand.nextInt(panelWidth - 20); // adjust for panel size
            letterY = rand.nextInt(panelHeight - 20); // adjust for panel size
        } else {
            throw new IllegalStateException("Panel dimensions are not set properly.");
        }
    }

    
     //checks if the snake has collided with a letter or a number.
     // if a number is hit, it removes the corresponding letter from the snake's body,
     // and all numbers are regenerated at new positions with new values.
     // if a letter is hit, the snake grows by one letter, and a new letter is generated.
     
    public void checkCollisions() {
        // check if the snake has collided with the letter
        if (Math.abs(snake.getBody().getFirst()[0] - letterX) < 20 &&
            Math.abs(snake.getBody().getFirst()[1] - letterY) < 20) {
            snake.eatLetter(currentLetter); // grow the snake by eating the letter
            generateNewLetter(); // generate a new letter in a new position
        }

        // check if the snake has collided with any numbers
        for (int i = 0; i < 10; i++) {
            if (Math.abs(snake.getBody().getFirst()[0] - numberX[i]) < 20 &&
                Math.abs(snake.getBody().getFirst()[1] - numberY[i]) < 20) {
                snake.hitNumber(numbers[i]); // use the actual number that was generated

                // regenerate all the numbers in new positions
                generateNumbers();
                break;
            }
        }
    }

    
     // generates a random letter between A and Z. this will be the letter the
     // snake can "eat."
     // @return a randomly generated character (A-Z).
     
    public char generateRandomLetter() {
        return (char) ('A' + rand.nextInt(26)); // random letter from A-Z
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // set the background color
        setBackground(Color.BLACK);

        // draw the snake
        snake.draw(g);

        // set the color for the letter and draw it
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("" + currentLetter, letterX, letterY); // draw the current letter

        // set the color for the numbers and draw them at their stored positions
        g.setColor(Color.CYAN);
        g.setFont(new Font("Arial", Font.BOLD, 18)); // adjusted font size to 18
        for (int i = 0; i < 10; i++) {
            g.drawString("" + numbers[i], numberX[i], numberY[i]);
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if (snake.getVelocityY() == 0) {
                    snake.setVelocity(0, -20); // move up
                }
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if (snake.getVelocityY() == 0) {
                    snake.setVelocity(0, 20); // move down
                }
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                if (snake.getVelocityX() == 0) {
                    snake.setVelocity(-20, 0); // move left
                }
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if (snake.getVelocityX() == 0) {
                    snake.setVelocity(20, 0); // move right
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {}
}
