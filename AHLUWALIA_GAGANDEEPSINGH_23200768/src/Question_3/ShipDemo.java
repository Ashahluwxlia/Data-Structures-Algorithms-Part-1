/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Question_3;

import javax.swing.JFrame;

/**
 *
 * @author xhu
 */
public class ShipDemo {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ship-Island Port Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel panel = new Panel();

        frame.getContentPane().add(panel);
        frame.setSize(1000, 1050);
        frame.setVisible(true);
    }
}
