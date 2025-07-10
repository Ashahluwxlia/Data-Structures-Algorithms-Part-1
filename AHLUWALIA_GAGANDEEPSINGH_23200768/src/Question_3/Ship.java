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

import java.util.concurrent.ThreadLocalRandom;

//Answer the following questions in your Ship class (put question and your answer on the first line of your code).
//“What is your monitor object? Why?”

//Answer: The monitor object is the 'Port' object ('port'). The Port object serves as the monitor 
//because it is the shared resource that multiple ship threads need to access in a synchronized manner.
//By synchronizing on the Port object, we can ensure that only one ship thread can dock at the port 
//at any given time, preventing race conditions and ensuring safe operation in synchronized mode.

// ship class representing a ship thread in the simulation.
// each ship moves towards the island (port) and attempts to dock.
// the behavior changes based on whether the simulation is in synchronized or unsynchronized mode.

public class Ship extends Thread {
    public int x; // ship's current x-coordinate
    public int y; // ship's current y-coordinate
    public Port port; // the shared port (island) object where ships dock
    public static boolean synchronizedMode; // flag indicating whether the simulation is in synchronized mode
    public Panel panel; // reference to the Panel for updating the GUI
    public int STEP_SIZE = 10; // step size for ship movement
    public int ANIMATION_DELAY = 30; // delay between movement steps

    public Ship(int x, int y, Port port, Panel panel) {
        this.x = x;
        this.y = y;
        this.port = port;
        this.panel = panel;
    }

    @Override
    public void run() {
        while (true) {
            if (synchronizedMode) {
                synchronized (port) { // synchronize on the port object to ensure safe docking
                    if (port.tryDock()) { // check if the port is available
                        moveTowardsPort(); // move the ship towards the port
                        dockAndLeave(); // dock at the port and then leave
                        break;
                    }
                }
            } else {
                if (port.tryDock()) { // check if the port is available
                    moveTowardsPort(); // move the ship towards the port
                    if (port.isOccupied) {
                        panel.registerCrash(this); // register a crash if another ship is already at the port
                    }
                    dockAndLeave(); // dock at the port and then leave
                    break;
                }
                moveTowardsPort(); // continue moving towards the port even if docking is not successful
            }
        }
    }

    public void moveTowardsPort() {
        // move the ship step by step towards the port's coordinates
        while (x != port.x || y != port.y) {
            if (x < port.x) x += STEP_SIZE;
            if (y < port.y) y += STEP_SIZE;

            if (x > port.x) x -= STEP_SIZE;
            if (y > port.y) y -= STEP_SIZE;

            panel.repaint(); // repaint the panel to update ship's position
            try {
                Thread.sleep(ANIMATION_DELAY); // delay to animate the ship's movement
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void dockAndLeave() {
        // the ship docks at the port and stays for a certain time before leaving
        this.x = port.x;
        this.y = port.y;
        panel.updateIslandImage(true); // update the island image to show the ship is docked

        try {
            Thread.sleep(1000); // ship stays at the port for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(100, 1000)); // random delay before leaving
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        port.leaveDock(); // release the port for other ships
        panel.updateIslandImage(false); // update the island image to show the port is free

        if (!synchronizedMode) {
            // in unsynchronized mode, check if any other ship is at the same position and register a crash
            for (Ship otherShip : panel.ships) {
                if (otherShip != this && otherShip.x == this.x && otherShip.y == this.y) {
                    panel.registerCrash(this); // register a crash
                    this.interrupt(); // interrupt the current ship thread
                    otherShip.interrupt(); // interrupt the other ship thread
                    break;
                }
            }
        }
    }
}
