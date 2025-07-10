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


// represents the port (island) where ships can dock.
// this class handles the logic for checking if the port is available and releasing the port after a ship leaves.
 
public class Port {
    public int x; // x-coordinate of the port
    public int y; // y-coordinate of the port
    public boolean isOccupied = false; // flag indicating if the port is occupied

    public Port(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public synchronized boolean tryDock() {
        // synchronized method to attempt docking at the port
        if (!isOccupied) {
            isOccupied = true; // mark the port as occupied
            return true;
        }
        return false;
    }

    public synchronized void leaveDock() {
        // synchronized method to release the port after the ship leaves
        isOccupied = false;
    }
}
