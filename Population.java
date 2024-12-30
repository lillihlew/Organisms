/*Assignment 5 (Population): Lilli Lewis 
 * 11/9/23
 * I confirm that the above list of sources is complete AND that I have 
 *  not talked to anyone else about the solution to this problem.*/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Population {
	//private fields
	private ArrayList<Organism> petriDish = new ArrayList<Organism>();
	/*Used an ArrayList because I don't know how many Organisms will be in it, but after it's constructed,
	 *it's size doesn't change, don't worry!*/
	private int ticks = 0;
	
	//constructor
	public Population(Map<String, Integer> counts) throws IllegalArgumentException{
		//extract number of each type of organism from map, then remove the associated key value pair
		int numCooperators = extractionHelper(counts, "Cooperator");
		int numDefectors = extractionHelper(counts, "Defector");
		int numPartialCooperators = extractionHelper(counts, "PartialCooperator");
		//throw exception if there is anything left in map
		if(counts.size()>0)
			throw new IllegalArgumentException("Error: only Organisms allowed in program are Cooperator, Defector, and Partial Cooperator");
		//loop through and add correct number of Cooperators to petriDish
		for(int i = 0; i < numCooperators; i++) {
			Cooperator newCoop = new Cooperator();
			petriDish.add(newCoop);
		}//for Cooperator
		//loop through and add correct number of Defectors to petriDish
		for(int i = 0; i < numDefectors; i++) {
			Defector newDef = new Defector();
			petriDish.add(newDef);
		}//for Defector
		//loop through and add correct number of PartialCooperators to petriDish
		for(int i = 0; i < numPartialCooperators; i++) {
			PartialCooperator newPcoop = new PartialCooperator();
			petriDish.add(newPcoop);
		}//for PartialCooperator
	}//constructor
	
	
	/**
	 * Extracts and returns the value associated with the key, removing the pair from the map
	 * @param counts, the counts Map input in the constructor
	 * @param key, the String of which type or organism 
	 * @return value, the number of counts of the organism
	 * @throws IllegalArgumentException
	 */
	private Integer extractionHelper(Map<String, Integer> counts, String key) throws IllegalArgumentException{
		Integer value = counts.get(key);
		if(value>=0) {//if there's not a negative number of Organisms
			counts.remove(key);
			return value;
		}//if
		else//tell user(me, since it's a private method) which key's value is negative
			throw new IllegalArgumentException("Error: cannot have negative "+key+" counts");	
	}//extractionHelper
	
	/**
	 * Petri dish access method
	 * @return the petri dish, an ArrayList<Organism>
	 */
	public ArrayList<Organism> getPetriDish(){
		return this.petriDish;
	}//getPetriDish
	
	/**
	 * updates organisms via their update method, checks if organisms cooperate
	 * and if so share energy with eight other organisms then lose an energy point,
	 * then finally check if organism can reproduce, and if so, reproduce and replace
	 * another organism in the dish. If there are less than eight other organisms,
	 * it will share energy with the amount of other organisms that there are.
	 */
	public void update() {
		int numOrgs = this.petriDish.size();
		for(int i = 0; i<numOrgs; i++) {
			Organism org = this.petriDish.get(i);
			org.update();//update Organisms via their update method
			Random r = new Random();
			if (org.cooperates()) {
				/*make a HashSet of 8 indexes to receive energy and iterate through 
				 * HashSet and increment energy of Organisms at the indexes in the ArrayList
				 */
				HashSet<Integer> moreEnergyIndexes = getEightIndexes(org);
				Iterator<Integer> itr = moreEnergyIndexes.iterator();
				while(itr.hasNext()) {
					this.petriDish.get(itr.next()).incrementEnergy();
				}//while
			}//if org cooperates
			if(org.getEnergy()>=10) {//if organism has enough energy
				Organism baby = org.reproduce(); //make new baby organism
				int replacementIndex = r.nextInt(numOrgs);//randomly generate an index holding an organism to be replaced
				this.petriDish.set(replacementIndex, baby);//replace said organism with baby
			}//if org can reproduce
		}//for, looping through petri dish
		ticks++;//increment ticks
	}//update
	
	
	/**
	 * Calcualtes how many organisms of one type there are in the petri dish
	 * @param org, an organism
	 * @return int, the number or organisms of the same type as org there are in the petri dish
	 */
	public int howMany(Organism org) {
		int num = 0;
		for(int i = 0; i<this.petriDish.size(); i++) {//loop through petri dish 
			if (org.getType().equals(this.petriDish.get(i).getType()))
				num++;
		}//for
		return num;
	}//howMany
	
	
	/**
	 * Returns a HashSet containing eight random indexes of petri dish at which 
	 * there are organisms of the same type as org
	 * @param org an organism
	 * @return HashSet<Integer>
	 */
	public HashSet<Integer> getEightIndexes(Organism org){
		//make a HashSet of 8 indexes to receive energy
		int receivers = 8;//the number of organisms to receive energy
		if(receivers>howMany(org))//if there are less than eight organisms of the same type
			receivers = howMany(org)-1;//there will be the number or organisms that there are -1 (because 1 is the giver) receivers
		HashSet<Integer> moreEnergyIndexes = new HashSet<Integer>();//using a set to avoid duplicates
		Random r = new Random();//make random object
		while(moreEnergyIndexes.size()<receivers) {//fill set with random indexes in the ArrayList
			int index = r.nextInt(this.petriDish.size());
			String petriDishRandomlyGeneratedType = this.petriDish.get(index).getType();
			String orgType = org.getType();
			if(orgType.equals(petriDishRandomlyGeneratedType)) {//if organisms are the same type
				moreEnergyIndexes.add(index);//add index to set
			}//if
		}//while
		return moreEnergyIndexes;
	}//getEightIndexes
	
	/**
	 * Calculates the mean cooperation probability for the petri dish
	 * @return the mean cooperation probability for the petri dish, a double
	 */
	public double calculateCooperationMean() {
		double sum = 0.0;
		//loop through dish and add each cooperation probability to sum
		for(int i = 0; i < petriDish.size(); i++) {
			sum+=petriDish.get(i).getCooperationProbability();
		}//for
		return sum / petriDish.size();
	}//calculateCooperationMean
	
	/**
	 * Loops through petri dish and gets population count for each type of organism
	 * @return a map containing the population types and counts for each organism
	 */
	public Map<String, Integer> getPopulationCounts(){
		int numCooperators = 0;
		int numDefectors = 0;
		int numPartCoop = 0;
		for(int i = 0; i < petriDish.size(); i++) {
			String type = petriDish.get(i).getType();
			switch(type) {
				case "Cooperator": {
					numCooperators++;
					break;
				}//Cooperator case
				case "Defector": {
					numDefectors++;
					break;
				}//Defector case
				case "PartialCooperator": {
					numPartCoop++;
					break;
				}//Partial Cooperator case
				/*no default because the only Strings in the PetriDish 
				 * are the three cases, as I assured in the constructor)*/
			}//switch 
		}//for
		Map<String, Integer> returner = new HashMap<String, Integer>();
		returner.put("Cooperator", numCooperators);
		returner.put("Defector", numDefectors);
		returner.put("PartialCooperator", numPartCoop);
		return returner;
	}//getPopulationCounts
	
	/**
	 * Ticks accessor method
	 * @return ticks, an int
	 */
	public int getTicks() {
		return this.ticks;
	}//getTicks
	
}//Population
