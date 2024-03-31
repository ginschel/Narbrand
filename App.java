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
import ginschel.narbrand.GameClass.*;
/**
 *
 * @author notebook
 */
public class App implements KeyListener{
    String path = "C:\\Users\\Alex\\Documents\\NetBeansProjects\\Narbrand\\src\\main\\resources\\icons\\";
    boolean selected = false; 
    BuildandRecruit BaR = new BuildandRecruit();
    GarrisonandWorkers GaW = new GarrisonandWorkers();
    JFrame window;
    Player playerblue;
    Player playerred;
    String lastButton;
    GameClass g;
    GameClass.Game game;
    JButton[] map;
    //Mapvars
    int y = 0; int mapboxwidth = 60; int height = 10;
    public App() {
        g = new GameClass();
        game = g.new Game(10,10);
	System.out.println(game);
        window = new JFrame();
        //Spieler
        playerblue=g.new Player("Carolus Rex",game);
        playerred= g.new Player("Kazimierz Wielki",game);
        game.world.capturableObjects[0][3].garrison = g.new Soldiers(g.new Soldier(),playerblue);
        game.world.capturableObjects[0][3].determineOwner();
        //Booleans
       

               //Spielerinformationen
        JLabel player = new JLabel("Player"); 
        player.setBounds(630,height-15, 50, 50);
        JLabel playername = playerblue.ready == false ? new JLabel(playerblue.name) : new JLabel(playerred.name);
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

       BaR.setBounds(620,height+400,160,343); window.add(BaR);
       //Building/Destroy Menu

       GaW.setBounds(620,height+100,160,300); window.add(GaW);
       
        //Map
        
       updateMap();

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
    void updateMap() {
        if(map!=null) {
            for(JButton button : map) window.remove(button);
            map = null;
        }
        map = new JButton[100];
        for(int i = 0; i < 100; i++) {
            JButton test; ImageIcon icon =new ImageIcon(path+"grass.png");
            if(i%10==0 && i!=0) y=(y+1)%10;
            int type = game.world.world[i%10][y];
            //System.out.println(game.world.world[0][0]);
            //System.out.println(Integer.toString(i) + ": "+Integer.toString(game.world.capturableObjects[i%10][y].type));
            
            Capturable capt =game.world.capturableObjects[i%10][y];
            if(capt.population != null) {
                if(capt.population.owner == playerblue) icon = getIcon(type,"blue");
                if(capt.population.owner == playerred) icon = getIcon(type,"red");
            }
            else if(capt.garrison != null) {
                if(capt.garrison.owner ==playerblue) icon = getIcon(type,"blue");
                if(capt.garrison.owner == playerred) icon = getIcon(type,"red");
            }
            else icon = getIcon(type,"");
            
            window.add(test =new JButton(icon));
             test.setBorderPainted(false);
            map[i] = test;
            test.setBounds(10+mapboxwidth*(i%10), height+5+y*mapboxwidth, mapboxwidth, mapboxwidth);
             test.setFocusable(false); 
            test.setToolTipText(Integer.toString(i%10)+" "+Integer.toString(y)); 
            test.addActionListener((e) -> {
                String[] twovars = test.getToolTipText().split(" "); 
                System.out.println(twovars[0]+";"+twovars[1]);
                if(lastButton!=test.getToolTipText() && selected == false) {
                    
                    if(game.world.capturableObjects[Integer.valueOf(twovars[0])][Integer.valueOf(twovars[1])].owner == playerblue) {
                        System.out.println(Integer.toString(type));
                        System.out.println(game.world.capturableObjects[Integer.valueOf(twovars[0])][Integer.valueOf(twovars[1])].owner.name);
                        BaR.setVisible(true); GaW.setVisible(true); 
                        selected = true; lastButton = test.getToolTipText();
                        setGaW(game.world.capturableObjects[Integer.valueOf(twovars[0])][Integer.valueOf(twovars[1])]);
                    }
                }
                else if(lastButton!=test.getToolTipText() && selected == true) {
                    String[] twooldvars = lastButton.split(" "); 
                    Capturable oldcap = game.world.capturableObjects[Integer.valueOf(twooldvars[0])][Integer.valueOf(twooldvars[1])];
                    if(oldcap.garrison!=null)
                       oldcap.garrison.move(Integer.valueOf(twovars[0]),Integer.valueOf(twovars[1]));    
                       //redrawMap
                       updateMap();
                }
            }
            );
        }
    }
    void setGaW(Capturable capt) {

        if(capt.garrison!=null) {
            GaW.infantrySize.setText(Integer.toString(capt.garrison.infantry));
            GaW.archerySize.setText(Integer.toString(capt.garrison.archery));
            GaW.cavalrySize.setText(Integer.toString(capt.garrison.cavalry));
        }
    }
    ImageIcon getIcon(int type, String owner) {
        if(owner == "blue") {
             switch(type) {
                case 1:
                    return new ImageIcon(path+"mineblue.png");
                case 2:
                    return new ImageIcon(path+"garrisonforrestblue.png");
                case 3:
                    return new ImageIcon(path+"lake.png");
                case 4:
                    return new ImageIcon(path+"villageblue.png");
                default:
                    return new ImageIcon(path+"grassblue.png");
            }
        }
        else if(owner == "red") {
             switch(type) {
                case 1:
                    return new ImageIcon(path+"minered.png");
                case 2:
                    return new ImageIcon(path+"garrisonforrestred.png");
                case 3:
                    return new ImageIcon(path+"lake.png");
                case 4:
                    return new ImageIcon(path+"villagered.png");
                default:
                    return new ImageIcon(path+"grassred.png");
            }
        }
        else {
             switch(type) {
                case 1:
                    return new ImageIcon(path+"mine.png");
                case 2:
                    return new ImageIcon(path+"forrest.png");
                case 3:
                    return new ImageIcon(path+"lake.png");
                case 4:
                    return new ImageIcon(path+"village.png");
                default:
                    return new ImageIcon(path+"grass.png");
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case 10:
                    System.out.println("Next Round");
                    updateMap();
                    break;
            case 27:
                    System.out.println("Deselect");
                    this.BaR.setVisible(false); this.GaW.setVisible(false); selected = false;
                    break;
            default:
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
    }
    

}
