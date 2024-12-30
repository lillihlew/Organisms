/*Assignment 5 (PartialCooperator): Lilli Lewis 
 * 11/9/23
 * I confirm that the above list of sources is complete AND that I have 
 *  not talked to anyone else about the solution to this problem.*/
import java.util.Random;

public class PartialCooperator extends Organism{
	//private fields
	private String type;
	private double cooperationProbability;
	
	//constructor
	public PartialCooperator() {
		this.type = "PartialCooperator";
		this.cooperationProbability = 0.5;
	}

	/**
	 * Always returns "PartialCooperator"
	 */
	@Override
	public String getType() {
		return this.type;
	}//getType

	/**
	 * Always returns a new PartialCooperator
	 */
	@Override
	public Organism reproduce() {
		this.resetEnergy();
		Organism newbie = new PartialCooperator();
		return newbie;
	}//reproduce

	/**
	 * Always returns 0.5
	 */
	@Override
	public double getCooperationProbability() {
		return this.cooperationProbability;
	}//getCooperationProbability

	/**
	 * Randomly generates and returns a boolean that is true 50% of the time
	 */
	@Override
	public boolean cooperates() {
		Random r = new Random();
		boolean rand = r.nextBoolean();
		return rand;
	}//cooperates

}//PartialCooperator
