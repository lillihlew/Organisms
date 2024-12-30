/*Assignment 5 (Cooperator): Lilli Lewis 
 * 11/9/23
 * I confirm that the above list of sources is complete AND that I have 
 *  not talked to anyone else about the solution to this problem.*/
public class Cooperator extends Organism{
	//private fields
	private String type;
	private double cooperationProbability;
	
	//constructor
	public Cooperator() {
		this.type = "Cooperator";
		this.cooperationProbability = 1.0;
	}
	
	/**
	 * Always returns "Cooperator"
	 */
	@Override
	public String getType() {
		return this.type;
	}//getType

	/**
	 * Returns a new Cooperator
	 */
	@Override
	public Organism reproduce() {
		this.resetEnergy();
		Organism newbie = new Cooperator();
		return newbie;
	}//reproduce

	/**
	 * Always returns 1.0
	 */
	@Override
	public double getCooperationProbability() {
		return this.cooperationProbability;
	}//getCooperationProbability

	/**
	 * Always returns true
	 */
	@Override
	public boolean cooperates() {
		return true;
	}//cooperates

}//Cooperator
