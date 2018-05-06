package Solution;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import Data.AdjTable;
import Data.VertexList;


public class SA{
	private SATravel best;
	private double coolingRate = 0.90;
	private double T = 1000000000;
	private AdjTable table;
	private int size;
	private FileOutputStream os;
	public SA(AdjTable table, VertexList vlist, FileOutputStream os){
		this.os = os;
		this.table = table;
		this.size = vlist.getLength();
		best = new SATravel();
		best.randomInit(this.size);
	}
	
	
	public double acceptanceProbability(double energy, double newEnergy, double temperature) {
        // If the new solution is better, accept it
        if (newEnergy < energy) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((energy - newEnergy) / temperature);
    }
	public void printFile(ArrayList<Integer> path, double val, FileOutputStream os ) throws IOException{
		Iterator<Integer> i = path.iterator();
		while(i.hasNext()){
			Integer temp = new Integer(i.next());
			String x = temp.toString() + " ";
			os.write(x.getBytes());
		}
		Double q = new Double(val);
		String w = " "+ q.toString() + " ";
		os.write(w.getBytes());
	}
	public void annealing(){
		
		 // Initialize initial solution
		SATravel currentSolution = new SATravel();
		SATravel newSolution = new SATravel();
		newSolution.randomInit(size);
		currentSolution.randomInit(this.size);
//		System.out.println("--------");
//		
		// Set as current best
		this.best.copyPath(currentSolution);
		
		
		while (T > 1) {
			newSolution.copyPath(currentSolution);
			// Get a random positions in the path
			int randomPos1 = (int) (currentSolution.size()-1 * Math.random());
			int randomPos2 = (int) (currentSolution.size()-1 * Math.random());
			
			
			// Swap them
			newSolution.setAPath(randomPos1, currentSolution.getAPath(randomPos2));
			newSolution.setAPath(randomPos2, currentSolution.getAPath(randomPos1));
			



			// Get energy of solutions
			double currentEnergy = currentSolution.getDistance(table);
			double neighbourEnergy = newSolution.getDistance(table);

			// Decide if we should accept the neighbor
			
			if (acceptanceProbability(currentEnergy, neighbourEnergy, T) > Math.random()) {
                currentSolution.copyPath(newSolution);
            }

            // Keep track of the best solution found
            if (currentSolution.getDistance(table) < this.best.getDistance(table)) {
                this.best.copyPath(currentSolution);
            }
            

			// Cooling system
			T *= coolingRate;
		}
		
		
	}
	public ArrayList<Integer> convert2(ArrayList<Integer> i){
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for(int x = 0 ; x != i.size(); ++x){
			ret.add(i.get(x)+1);
		}
		return ret;
	}
	public long solve() throws IOException{
		long start = System.currentTimeMillis();
		System.out.print("SA");
	
		this.annealing();
		long end = System.currentTimeMillis();

		System.out.print(this.best.getPath());
		double x = this.best.getDistance(table);
		System.out.println("  "+ this.best.getDistance(table));
		//best.getPath()가 경로
		//값이 best.getDistance(table)
		this.best.getPath().add(0,0);
		this.best.getPath().add(0);
		
		printFile(convert2(best.getPath()), x, os);
		
		os.write("\n".getBytes());
		return end - start;
	}
}