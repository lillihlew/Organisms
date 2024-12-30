/*Assignment 5 (ALifeSim): Lilli Lewis 
 * 11/9/23
 * I confirm that the above list of sources is complete AND that I have 
 *  not talked to anyone else about the solution to this problem.*/
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class ALifeSim {
	
	public static void main(String[] args) throws InputMismatchException{
		int numArgs = 4;//4 is the correct number of command line arguments
		
		//check for 4 CLAs
		if(args.length!=numArgs) {
			System.out.println("Error: improper imput. Please call file in the following format:");
			System.out.println("java ALifeSim <#/iterations> <#/cooperators> <#/defectors> <#/partial cooperators>");
			System.exit(0);
		}//if there aren't enough arguments
		
		//check for integer CLAs
		try {//the lines in here that will throw an exception are those that include Integer.valueOf(args[i])
			//make map and add organism types and counts to it (negative arguments are handled in constructor)
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("Cooperator", Integer.valueOf(args[1]));
			map.put("Defector", Integer.valueOf(args[2]));
			map.put("PartialCooperator", Integer.valueOf(args[3]));
			
			//make new population using map of organism types and counts
			Population population = new Population(map);
			
			
			//update the population number of iterations specified by the user
			for(int i= 0; i < Integer.valueOf(args[0]); i++) {
				population.update();
				makeGraphicArray(population);
				Thread.sleep(10000);//taken from assignment page
			}//for
			
			//save getPopulationCounts into a map called populationCounts
			Map<String, Integer> populationCounts = population.getPopulationCounts();
			
			//use populationCounts to print outcomes of program
			System.out.println("After "+population.getTicks()+" ticks:\n"
					+ "Cooperators = "+populationCounts.get("Cooperator")+"\n"
					+ "Defectors   = "+populationCounts.get("Defector")+" \n"
					+ "Partial     = "+populationCounts.get("PartialCooperator")+"  \n"
					+ "\n"
					+ "Mean Cooperation Probability = "+population.calculateCooperationMean());
			
			
		}//try
		catch(NumberFormatException e) {//will only happen if args are not integers
			System.out.println("Error: only Integers are accepted as command line arguments.");	
		}//catch
		catch(InterruptedException e) {//took this from assignment instructions
			e.printStackTrace();
		}//catch
		System.exit(0);//end program
	}//main

	
	/**
	 * Creates a graphical representation of the population where cooperators are red circles, 
	 * defectors are black circles, and partial cooperators are orange circles.
	 * @param population, a Population
	 */
	public static void makeGraphicArray(Population population){
		int maxDim = 800;//max height and width of drawing panel drawings, excluding the key
		int totalOrgs = population.getPetriDish().size();
		//I add one in the line below so that all organisms get printed, even if totalOrgs isn't a square number
		int numPerRow = (int)Math.sqrt((double)totalOrgs)+1;//number of organisms per row
		int numPerCol = numPerRow;//number of organisms per column
		DrawingPanel panel = new DrawingPanel(maxDim, maxDim);//make new drawing panel
		int keySpace = 100;//leave 100 pixels to make the key
		int max = maxDim-keySpace;//maximum amount of space for array to take up
		Graphics g = panel.getGraphics();//make graphics object
		int numOrgsPrinted = 0;//this will keep track of how many organisms have been printed
		int startx = 0;//start x is x value of where organism will be printed
		int starty = 0;// start y is y value of where organism will be printed
		int width = max/numPerRow;//width of organism
		int height = max/numPerCol;//height of organism
		g.setFont(new Font("Serif", Font.BOLD, height/2));
		//blue will never be used, just initializing it to blue so I can see if an error occurs
		Color originalColor = Color.BLUE;
		for(int i = 0; i < numPerCol; i++) {//make columns
			for(int j = 0; j<numPerRow && numOrgsPrinted<totalOrgs; j++) {//make rows
				String type = population.getPetriDish().get(numOrgsPrinted).getType();//save type of organism as String
				switch(type) {//change color of circle based on type of organism
					case "Cooperator": {
						originalColor = Color.RED;
						break;
					}//Cooperator case
					case "Defector": {
						originalColor = Color.BLACK;
						break;
					}//Defector case
					case "PartialCooperator": {
						originalColor = Color.ORANGE;
						break;
					}//Partial Cooperator case
					/*no default because the only Strings in the PetriDish 
					 * are the three cases, as I assured in the constructor)*/
				}//switch 
				g.setColor(originalColor);//set color to color decided by switch
				g.fillOval(startx, starty, height, width);//draw circle
				startx += width;//increment x value
				g.setColor(Color.WHITE);//change color to white to draw energy level
				//draw energy level inside of organism's circle
				g.drawString((""+population.getPetriDish().get(numOrgsPrinted).getEnergy()), (startx-width+(width/10)), (starty+height-(height/3)));
				g.setColor(originalColor);//reset color to original color
				numOrgsPrinted++;//increment number of organism printed (this is the value that will stop the loops)
			}//for rows
			startx=0;//reset x value to 0
			starty+=height;//increment y value by height of organism
		}//for cols
		//Make key
		g.setColor(Color.BLACK);
		int numLines = 5;//because there are 5 lines of text in the key
		int fontHeight = keySpace/numLines;//set font height
		int startKeyx=0;//set start x as 0 (doesn't change)
		int startKeyy = max+fontHeight;//set start y as end of allotted space for array + font height
		g.drawString("KEY", startKeyx, startKeyy);//Write KEY
		//Alternate incrementing start y and writing information in key
		startKeyy+=fontHeight;
		g.drawString("Cooperators are red", startKeyx, startKeyy);
		startKeyy+=fontHeight;
		g.drawString("Defectors are black", startKeyx, startKeyy);
		startKeyy+=fontHeight;
		g.drawString("PartialCooperators are orange", startKeyx, startKeyy);
		startKeyy+=fontHeight;
		g.drawString("Numbers are organism energy levels", startKeyx, startKeyy);
	}//makeGraphicArray
	


}//ALifSim
