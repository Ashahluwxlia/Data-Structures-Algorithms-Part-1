/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Question_3;

/**
 *
 * @author xhu
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ThreadLocalRandom;


 // panel class representing the GUI panel where the simulation is displayed.
 //handles the ship movements, detects crashes, and updates the island image based on the ship's state.
 
public class Panel extends JPanel implements KeyListener {

    public int number_ship = 20; // total number of ships in the simulation
    public boolean program_starts = false; // flag to indicate if the simulation has started
    public Ship[] ships = new Ship[number_ship]; // array to hold the ships
    public Port port; // the port (island) where ships will dock
    public boolean isIslandOccupied = false; // flag to indicate if the island is currently occupied
    public List<String> crashMessages = new ArrayList<>(); // list to store crash messages
    public AtomicInteger dockedShipsCount = new AtomicInteger(0); // counter for docked ships

    public Image ship_image; // image of the ship
    public Image island_image; // image of the island when no ship is docked
    public Image boat_island_image; // image of the island when a ship is docked

    public Panel() {
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        port = new Port(900, 500); // initialize the port at specific coordinates

        for (int i = 0; i < number_ship; i++) {
            ships[i] = new Ship(20, i * 50, port, this); // create ships and set their initial positions
        }

        ship_image = new ImageIcon("boat.png").getImage(); // load the ship image
        island_image = new ImageIcon("land.png").getImage(); // load the island image
        boat_island_image = new ImageIcon("boat_land.png").getImage(); // load the island image with a docked ship

        // button dimensions
        int buttonWidth = 300; // increased width to fit the text
        int buttonHeight = 30;

        // calculate the center position for the buttons
        int panelWidth = 1000; // assuming a panel width of 1000
        int unsyncButtonX = (panelWidth / 2) - (buttonWidth + 10); // center for the first button
        int syncButtonX = (panelWidth / 2) + 10; // center for the second button (10 pixels space between)

        // button to start unsynchronized mode
        JButton unsyncButton = new JButton("Unsynchronized Mode or Press 'W' key");
        unsyncButton.setBounds(unsyncButtonX, 10, buttonWidth, buttonHeight);
        unsyncButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ship.synchronizedMode = false; // set mode to unsynchronized
                if (!program_starts) {
                    program_starts = true;
                    startShipsInGroups(); // start ships in groups for unsynchronized mode
                }
            }
        });
        this.add(unsyncButton);

        // button to start synchronized mode
        JButton syncButton = new JButton("Synchronized Mode or Press 'Space' key");
        syncButton.setBounds(syncButtonX, 10, buttonWidth, buttonHeight);
        syncButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ship.synchronizedMode = true; // set mode to synchronized
                if (!program_starts) {
                    program_starts = true;
                    for (Ship ship : ships) {
                        ship.start(); // start all ships in synchronized mode
                    }
                }
            }
        });
        this.add(syncButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Monospaced", Font.BOLD, 20));

        // draw all the ships on the panel
        for (int i = 0; i < ships.length; i++) {
            g.drawImage(ship_image, ships[i].x, ships[i].y, this);
        }

        // update the island image based on whether it is occupied or all ships have docked
        if (isIslandOccupied || dockedShipsCount.get() >= number_ship) {
            g.drawImage(boat_island_image, port.x, port.y, this);
        } else {
            g.drawImage(island_image, port.x, port.y, this);
        }

        // draw any crash messages on the panel
        g.setColor(Color.RED);
        for (int i = 0; i < crashMessages.size(); i++) {
            g.drawString(crashMessages.get(i), 10, 50 + (i * 30));
        }

        repaint(); // repaint the panel to keep the GUI updated
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        System.out.println("\"" + ke.getKeyChar() + "\" is typed."); // log the typed key
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_SPACE && !program_starts) {
            // start synchronized mode if the space bar is pressed
            program_starts = true;
            Ship.synchronizedMode = true;
            for (Ship ship : ships) {
                ship.start(); // start all ships in synchronized mode
            }
        } else if (ke.getKeyCode() == KeyEvent.VK_W && !program_starts) {
            // start unsynchronized mode if the 'W' key is pressed
            program_starts = true;
            Ship.synchronizedMode = false;
            startShipsInGroups(); // start ships in groups for unsynchronized mode
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    public void startShipsInGroups() {
        // start ships in small groups to simulate unsynchronized mode
        List<Ship> shipList = new ArrayList<>();
        Collections.addAll(shipList, ships);
        Collections.shuffle(shipList);

        // start ships in random groups with delays between them
        for (int i = 0; i < shipList.size(); i += ThreadLocalRandom.current().nextInt(2, 4)) {
            for (int j = i; j < i + 3 && j < shipList.size(); j++) {
                shipList.get(j).start(); // start each ship thread
            }
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1500)); // random delay between group starts
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateIslandImage(boolean isOccupied) {
        // update the flag for island occupation and increment the docked ship count
        isIslandOccupied = isOccupied;
        if (!isOccupied) {
            dockedShipsCount.incrementAndGet();
        }
    }

    public void registerCrash(Ship ship) {
        // register a crash and add a message to be displayed on the GUI
        crashMessages.add("Crash detected at position: " + ship.x + ", " + ship.y);
        ship.interrupt(); // interrupt the ship thread that caused the crash
    }
}
