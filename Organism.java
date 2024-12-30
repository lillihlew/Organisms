/*Assignment 5 (Organism): Lilli Lewis 
 * 11/9/23
 * I confirm that the above list of sources is complete AND that I have 
 *  not talked to anyone else about the solution to this problem.*/
public abstract class Organism {
	//private fields
	private int energy;
	
	//constructor
	public Organism() {
		this.energy = 0;
	}
	
	/**
	 * Updates the given organism by incrementing energy
	 */
	public void update() {
		incrementEnergy();
	}//update
	
	/**
	 * energy accessor method
	 * @return energy, an int
	 */
	public int getEnergy() {
		return this.energy;
	}//getEnergy

	/**
	 * increases organism's energy by 1
	 */
	public void incrementEnergy() {
		this.energy++;
	}//incrementEnergy
	
	/**
	 * decreases organism's energy by 1 unless energy is already 0, in which case it does nothing
	 */
	public void decrementEnergy() {
		if(getEnergy()!=0)
			this.energy--;
	}//decrementEnergy
	
	/**
	 * sets organism's energy to 0
	 */
	public void resetEnergy() {
		this.energy = 0;
	}//resetEnergy
	
	/**
	 * gets organism's type
	 * @return organism's type as a String
	 */
	public abstract String getType();
	
	/**
	 * Called by update when the organism can reproduce. Creates an offspring organism and returns it.
	 * @return Organism, the offspring
	 */
	public abstract Organism reproduce();
	
	/**
	 * Computes and returns cooperation probability of this organism.
	 * @return double, probability of cooperation 
	 */
	public abstract double getCooperationProbability();
	
	/**
	 * checks if organism cooperates
	 * @return boolean, true if organism cooperates
	 */
	public abstract boolean cooperates();
	
}//Organism
