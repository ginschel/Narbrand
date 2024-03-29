/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ginschel.narbrand;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JScrollBar;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
/**
 *
 * @author notebook
 */
public class App implements KeyListener{
    public App() {
        GameClass g = new GameClass();
        GameClass.Game game = g.new Game(10,10);
	System.out.println(game);
        JFrame window = new JFrame();
        //Image-Icons
        String path = "C:\\Users\\Alex\\Documents\\NetBeansProjects\\Narbrand\\src\\main\\resources\\icons\\";
        ImageIcon grass = new ImageIcon(path+"grass.png");
        ImageIcon lake = new ImageIcon(path+"lake.png");
        ImageIcon village = new ImageIcon(path+"village.png");
        ImageIcon forrest = new ImageIcon(path+"forrest.png");
       ImageIcon mine = new ImageIcon(path+"mine.png");
        //Map
        int y = 0; int mapboxwidth = 60; int height = 10;
        for(int i = 0; i < 100; i++) {
            JButton test; ImageIcon icon;
            int temp = game.world.world[i%10][y];
            //System.out.println(game.world.world[0][0]);
            //System.out.println(Integer.toString(i) + ": "+Integer.toString(game.world.capturableObjects[i%10][y].type));
            switch(temp) { 
                case 1:
                    icon = mine;
                    break;
                case 2:
                    icon = forrest;
                    break;
                case 3:
                    icon = lake;
                    break;
                case 4:
                    icon = village;
                    break;
                default:
                    icon = grass;
            }
            window.add(test =new JButton(icon));
             test.setBorderPainted(false);
            if(i%10==0 && i!=0) ++y;
            test.setBounds(10+mapboxwidth*(i%10), height+5+y*mapboxwidth, mapboxwidth, mapboxwidth);
            
            test.addActionListener(e -> System.out.println(Integer.toString(temp)));
        }
        //Spielerinformationen
        JLabel player = new JLabel("Player"); 
        player.setBounds(630,height-15, 50, 50);
        JLabel playername = new JLabel("unknown");
        playername.setBounds(630+60,height-15, 100, 50);
        JSeparator sep1 = new JSeparator(SwingConstants.HORIZONTAL); sep1.setBounds(630, height+23, 150, 7);
        window.add(sep1);
        JLabel wood = new JLabel("Wood"); wood.setBounds(630,height+12,50,50);
        JLabel woodcount = new JLabel("0"); woodcount.setBounds(630+60,height+12,100,50);
        JLabel food = new JLabel("Food"); food.setBounds(630,height+30,50,50);
        JLabel foodcount = new JLabel("0"); foodcount.setBounds(630+60,height+30,100,50);
        JLabel gold = new JLabel("Gold"); gold.setBounds(630,height+48,50,50);
        JLabel goldcount = new JLabel("0"); goldcount.setBounds(630+60,height+48,100,50);
        window.add(player);
         window.add(playername);
         window.add(food); window.add(foodcount);
         window.add(wood); window.add(woodcount);
         window.add(gold); window.add(goldcount);
        //Capturable/Building-Information (Ressources can be destroyed) Walls can be built
       Playerinformation pl = new Playerinformation();
       pl.setBounds(10,620,615,133); pl.jTable1.setBounds(10,620,615,400); 
       
       //Recruitment Window
       BuildandRecruit BaR = new BuildandRecruit();
       BaR.setBounds(620,height+400,160,343); window.add(BaR);
       //Building/Destroy Menu
       GarrisonandWorkers GaW = new GarrisonandWorkers();
       GaW.setBounds(620,height+100,160,300); window.add(GaW);
       //Keyboard-Enter Shortcut for next Round
       
       //Deselect Shotcut esc
       
       
       //JScrollPane l = new JScrollPane(pl.jTable1); l.setVisible(true); pl.add(l);
        //fenster
        window.add(pl);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Narbrand");
        window.setSize(800,800);
       // window.setIconImage(forrest.getImage());
       window.setLayout(null);
       window.setFocusable(true);
       window.addKeyListener(this);
        window.setVisible(true);
    }
    public static void main(String[] args) {
            App app = new App();
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case 10:
                    System.out.println("Next Round");
                    break;
            case 27:
                    System.out.println("Deselect");
                    break;
            default:
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
    }
    

}
