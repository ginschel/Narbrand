/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ginschel.narbrand;


/**
 *
 * @author notebook
 */
import java.util.ArrayList;
import java.util.Random;
import java.util.Objects;
public class GameClass {
    class World {
	int[][] world;
	Capturable[][] capturableObjects;
	World(int x, int y) {
		world = new int[x][y];
		capturableObjects = new Capturable[x][y];
		generation();
	}
	World generation() {
		Random rand = new Random();
		for (int x = 0; x < world.length; x++) {
			for(int y = 0; y<world[0].length;y++) {
				int terrainType = rand.nextInt(10)+1;
				if(terrainType < 3) {
					int capturableType= rand.nextInt(4)+1;
					world[x][y] = capturableType;
					switch(capturableType) {
						case 1:
							capturableObjects[x][y] = new Mine();
							break;
						case 2:
							capturableObjects[x][y] = new Forest();
							break;
						case 3:
							capturableObjects[x][y] = new Lake();
							break;
						case 4:
							capturableObjects[x][y] = new Village();
							break;
						default:
                                                       
					}
					continue;
				}
				world[x][y] = 0;
                                capturableObjects[x][y] = new Grass();
			}
		}
		return this;
	}
	String represent(int[][] world) {
		String out = "\n";
		for(int y = 0; y< world[0].length; y++) {
			for(int x = 0;x<world.length;x++) {
				out+=Integer.toString(world[x][y])+ " ";
			}
		out+="\n";
		}
		return out;
	}
	@Override
	public String toString() {
		return represent(world);
	}


}
abstract class Human {
	int hp = 20;
	int damage = 5;
	int speed = 4;
}
class Soldier extends Human {
	int hp = 80;
	int damage = 20;
	int speed = 2;

}
class Worker extends Human {

}
abstract class Moveable {
	public Player owner;
	int x,y;
	int groupSpeed;
	abstract int getWorkerNumber();
        abstract void move(int x, int y);

}
class Soldiers extends Moveable{
	ArrayList<Soldier> soldiers;
	Soldiers(Soldier soldier, Player owner) {
                soldiers = new ArrayList<Soldier>();
		soldiers.add(soldier);
                this.owner = owner;
		if(!Objects.nonNull(owner)){
			if(!Objects.nonNull(owner.soldiergroups)) owner.soldiergroups = new ArrayList<Soldiers>();
		}
		owner.soldiergroups.add(this);
	}
        void move(int x, int y) {
		if(groupSpeed <= Math.abs(this.x-x) && groupSpeed <= Math.abs(this.y-y)) {
			this.x = x; this.y=y;
                        if(Objects.nonNull(owner.game.world.capturableObjects[x][y])) {
                            owner.game.world.capturableObjects[x][y].garrison = this; 
                        }
                        else {
                            System.out.println("Your workergroups are merged together");
                            mergeGroup(this,owner.game.world.capturableObjects[x][y].garrison );
                        }
                }
		else System.out.println("You cant make this move. your group is too slow!");
	}
	void addSoldier(Soldier soldier) {
		soldiers.add(soldier);
	}
        void mergeGroup(Soldiers from, Soldiers to) {
            for(Soldier soldier : from.soldiers) to.addSoldier(soldier);
            owner.soldiergroups.remove(to);
        }
        void splitGroup(int number, int x, int y) {
            if (soldiers.size() > 1 && number < soldiers.size() && Objects.nonNull(owner.game.world.capturableObjects[x][y].garrison)) {
                Soldiers newSoldiers;
                newSoldiers = new Soldiers(soldiers.remove(0), owner);
                for (int i=0; i< number-1;i++) newSoldiers.addSoldier(soldiers.remove(i));
                newSoldiers.move(x, y);
            }
        }
        void attackGroup(Soldiers defender) {
            for(Soldier soldier : soldiers) {
                defender.soldiers.get(0).hp -=soldier.damage;
                if(defender.soldiers.get(0).hp <1) defender.soldiers.remove(0);
            }
        }
	//deprecated
	int getWorkerNumber(){return 0;}
}
class Workers extends Moveable{
	ArrayList<Worker> workers;
	int[][] cords;
	Workers(Worker worker) {
		workers.add(worker);
		if(!Objects.nonNull(owner)){
			if(!Objects.nonNull(owner.workergroups)) owner.workergroups = new ArrayList<Workers>();
		}
		owner.workergroups.add(this);

	}
        void move(int x, int y) {
		if(groupSpeed <= Math.abs(this.x-x) && groupSpeed <= Math.abs(this.y-y)) {
			this.x = x; this.y=y;
                        if(Objects.nonNull(owner.game.world.capturableObjects[x][y])) {
                            owner.game.world.capturableObjects[x][y].population = this; 
                        }
                        else {
                            System.out.println("Your workergroups are merged together");
                            mergeGroup(this,owner.game.world.capturableObjects[x][y].population );
                        }
                }
		else System.out.println("You cant make this move. your group is too slow!");
	}
        void mergeGroup(Workers from, Workers to) {
            for(Worker worker : from.workers) to.addWorker(worker);
            owner.workergroups.remove(to);
        }
	void addWorker(Worker worker) {
		workers.add(worker);
	}
	int getWorkerNumber() {
		return workers.size();
	}
}
class Faction {
        Game game;
	int gold, wood, food;
	ArrayList<Workers> workergroups;
	ArrayList<Soldiers> soldiergroups;
}
class Neutral extends Faction {

}
class Player extends Faction {
	String name;
	public boolean ready;
	Capturable[] capturedObjects;
	Player(String name) {
		this.name = name;
                workergroups = new ArrayList<Workers>();
                soldiergroups = new ArrayList<Soldiers>();
	}
	void setTurn() {
		ready=true;
	}
}
public abstract class Capturable {
    int type = 0;
    public Player owner;
    public Workers population;
    public Soldiers garrison;
    void determineOwner() {
        if(population!=null) owner = population.owner;
        if(garrison!=null) owner = garrison.owner;
    }
    abstract void giveRessources();
}
public class Grass extends Capturable {
    void giveRessources(){};
}
public class Barracks extends Capturable {
	void produceSoldier() {
            determineOwner();
		if(!Objects.nonNull(garrison)) {
			garrison = new Soldiers(new Soldier(), owner);
			return;
		}
		garrison.addSoldier(new Soldier());
	}
	void giveRessources() { }
}
public class Village extends Capturable {
    int type = 4;
	void giveRessources() {
	if(Objects.nonNull(population)) population.owner.food += 10*population.getWorkerNumber();
	}
	void produceWorker() {
		if(!Objects.nonNull(population)) {
			population = new Workers(new Worker());
			return;
		}
		population.addWorker(new Worker());
	}
}
public class Forest extends Capturable {
    int type = 2;
    void giveRessources() {
	if(Objects.nonNull(population)) population.owner.wood += 10*population.getWorkerNumber();
	}
}
public class Lake extends Capturable {
    int type = 3;  
    void giveRessources() {
        if(Objects.nonNull(population)) population.owner.food += 10*population.getWorkerNumber();
    }
}
public class Mine extends Capturable {
    int type = 1;
    void giveRessources() {
        if(Objects.nonNull(population)) population.owner.gold += 10*population.getWorkerNumber();
	}
}
public class Game {
	int rounds;
	Player[] players;
	World world;
	public Game(int x, int y) {
		world = new World(x,y);
	}
	Game nextTurn() {
		for(Player player : players) {
			if(player.ready == false) {
				System.out.println("The game cant precede until "+player.name+" has made his turn!");
				return this;
			}
		}
		++rounds;
		for(Capturable[] capturableObjects : world.capturableObjects) {
		for(Capturable capturableObject : capturableObjects) if(Objects.nonNull(capturableObject)) capturableObject.giveRessources();
		}
		return this;
	}
	@Override
	public String toString() {
		return world.toString();
	}
}


}
