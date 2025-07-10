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
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Collections;

/**
 * the Snake class represents the snake in the game. it takes care of moving the snake,
 * making it grow when it eats letters, and handling interactions with numbers.
 */
public class Snake {

    public LinkedList<int[]> body; // list of segments that make up the snake's body
    public LinkedList<Character> eatenLetters; // list of letters the snake has eaten
    public int length; // current length of the snake
    public int x, y; // current head position of the snake
    public int vx, vy; // velocity in x and y direction (controls movement direction)
    public static final int SIZE = 20; // size of each snake segment (in pixels)
    public static int PANEL_WIDTH = 800; // default width of the game panel
    public static int PANEL_HEIGHT = 600; // default height of the game panel

    /**
     * this is the constructor for the Snake class. it sets up the snake at the
     * given start position.
     * @param startX the starting X position of the snake's head.
     * @param startY the starting Y position of the snake's head.
     */
    public Snake(int startX, int startY) {
        body = new LinkedList<>(); // initialize the list for the snake's body segments
        eatenLetters = new LinkedList<>(); // initialize the list of letters the snake eats
        x = startX;
        y = startY;
        vx = 20; // initial velocity (moving right)
        vy = 0;
        length = 1; // start with a length of 1
        body.add(new int[]{x, y}); // add the initial head position to the body
    }

    /**
     * sets the velocity (direction) of the snake.
     * @param vx the velocity in the X direction.
     * @param vy the velocity in the Y direction.
     */
    public void setVelocity(int vx, int vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public int getVelocityX() {
        return vx;
    }

    public int getVelocityY() {
        return vy;
    }

    
     // moves the snake based on its current velocity. if the snake reaches the
     // edge of the panel, it wraps around to the opposite side.
     
    public void move() {
        if (vx != 0 || vy != 0) {
            // update the head position based on the current velocity
            x += vx;
            y += vy;

            // wrap around screen edges (teleport to opposite side)
            if (x < 0) x = PANEL_WIDTH - SIZE;
            if (x >= PANEL_WIDTH) x = 0;
            if (y < 0) y = PANEL_HEIGHT - SIZE;
            if (y >= PANEL_HEIGHT) y = 0;

            // add the new head position to the body
            body.addFirst(new int[]{x, y});
            
            // if the body exceeds the length, remove the last segment
            if (body.size() > length) {
                body.removeLast();
            }
        }
    }

    
     //makes the snake grow by adding a new segment.
     
    public void grow() {
        length++;
        body.addFirst(new int[]{x, y}); // add a new segment at the head's position
    }

    
     // when the snake eats a letter, this method is called. it adds the letter
     //to the list of eaten letters and makes the snake grow.
     //@param letter the letter that the snake eats.
     
    public void eatLetter(char letter) {
        eatenLetters.add(letter); // add the letter to the eaten letters list
        Collections.sort(eatenLetters); // sort the letters alphabetically
        grow(); // increase the snake's length
    }

    
     // when the snake hits a number, this method is called. it makes the snake shrink,
     //and removes the corresponding letter segment.
     // @param number the number that determines which segment to remove.
     
    public void hitNumber(int number) {
        if (length > 1 && !eatenLetters.isEmpty()) { // make sure there's at least one segment to remove
            int indexToDrop = Math.min(number, eatenLetters.size() - 1);
            eatenLetters.remove(indexToDrop); // remove the letter at the specified index
            body.remove(indexToDrop + 1); // remove the corresponding body segment
            length--;
            if (length < 1) length = 1; // ensure length does not drop below 1
        }
    }

    
     //draws the snake on the game panel. the head is drawn in green, and the
     // body segments are drawn in blue with letters displayed on them.
     // @param g the Graphics object used for drawing.
     
    public void draw(Graphics g) {
        // set color for snake's head
        g.setColor(Color.GREEN);

        // draw the snake's body with letters on it
        for (int i = 0; i < body.size(); i++) {
            int[] segment = body.get(i);
            if (i == 0) {
                // draw the head of the snake
                g.fillOval(segment[0], segment[1], SIZE, SIZE);
            } else {
                // draw the body segments
                g.setColor(Color.BLUE);
                g.fillOval(segment[0], segment[1], SIZE, SIZE);

                // draw the letters on the body segments
                if (i - 1 < eatenLetters.size()) {
                    g.setColor(Color.WHITE);
                    g.drawString(String.valueOf(eatenLetters.get(i - 1)), segment[0] + SIZE / 4, segment[1] + SIZE * 3 / 4);
                }
            }
        }
    }

    
     // sets the size of the game panel. this is used to handle wrapping and
     // make sure the snake moves correctly within the panel bounds.
     //@param width the width of the panel.
     // @param height the height of the panel.
     
    public static void setPanelSize(int width, int height) {
        PANEL_WIDTH = width;
        PANEL_HEIGHT = height;
    }

    
     //returns the list of body segments of the snake.
     // @return a LinkedList of int arrays representing the snake's body segments.
     
    public LinkedList<int[]> getBody() {
        return body;
    }
}
