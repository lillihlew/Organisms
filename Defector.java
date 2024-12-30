/*Assignment 5 (Defector): Lilli Lewis 
 * 11/9/23
 * I confirm that the above list of sources is complete AND that I have 
 *  not talked to anyone else about the solution to this problem.*/
public class Defector extends Organism {
	//private fields
	private String type;
	private double cooperationProbability;
	
	//constructor
	public Defector() {
		this.type = "Defector";
		this.cooperationProbability = 0.0;
	}

	/**
	 * Always returns "Defector"
	 */
	@Override
	public String getType() {
		return this.type;
	}//getType

	/**
	 * Always returns a new  Defector
	 */
	@Override
	public Organism reproduce() {
		this.resetEnergy();
		Organism newbie = new Defector();
		return newbie;
	}//reproduce

	/**
	 * Always returns 0.0
	 */
	@Override
	public double getCooperationProbability() {
		return this.cooperationProbability;
	}//getCooperationProbability

	/**
	 * Always returns false
	 */
	@Override
	public boolean cooperates() {
		return false;
	}//cooperates

}//Defector
