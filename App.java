/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.ginschel.xminimalgame;
import com.ginschel.xminimalgame.game.Gameclass;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JButton;
/**
 *
 * @author notebook
 */
public class App implements ActionListener{

    public static void main(String[] args) {
        Gameclass g = new Gameclass();
        Gameclass.Game game = g.new Game(10,10);
	System.out.println(game);
        JFrame window = new JFrame();
        JPanel panel = new JPanel();
        panel.setSize(600,600);
        int y = 0;
        for(int i = 0; i < 100; i++) {
            JButton test;
            window.add(test =new JButton("cord"+Integer.toString(i)));
            test.setBounds(10+25*i%10, 5+y*25, 5, 5);
            if(i%10==0 && i!=0) ++y;
            //((JButton)panel.getComponent(i)).setBounds(10+5*i, 5, 5, 4);
            //((JButton)panel.getComponent(i)).setBorderPainted(false);
            //((JButton)panel.getComponent(i)).setContentAreaFilled(false);
        }
        window.add(panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Narbrands Rückkehr");
        window.setSize(600,600);
        window.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        
    }
    }
}
