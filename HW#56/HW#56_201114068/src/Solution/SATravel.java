package Solution;

import java.util.ArrayList;
import java.util.Collections;

import Data.AdjTable;
import Data.VertexList;

public class SATravel {
	private ArrayList<Integer> path;
	
	public SATravel(){
		path = new ArrayList<Integer>();
	}
	
	public double getDistance(AdjTable table){
		double distance = table.get(0, this.path.get(0)).getWeight();
	
		for(int i = 0 ; i < this.path.size() ; i++){
			if(i+1 >= this.path.size()) {
				distance += table.get(this.path.get(i), 0).getWeight();
				break;	
			}
			
			distance += table.get(this.path.get(i), this.path.get(i+1)).getWeight();
		}
		return distance;
	}
	
	public void randomInit(int size){
		// Loop through all our destination cities and add them to our tour
		

		for(int i = 1; i<size; i++){
			this.path.add(i);
		}
		Collections.shuffle(this.path);
		
		
		
		// Randomly reorder the tour
		
		
	}
	
	public void copyPath(SATravel p){
		for (int i = 0 ; i < p.size(); i++){
			this.path.set(i, p.path.get(i));
		}
	}
	
	public void setAPath(int pos, int value){
		this.path.set(pos, value);
	}
	public int getAPath(int pos){
		return this.path.get(pos);
	}
	public int size(){
		return this.path.size();
	}
	public ArrayList<Integer> getPath (){
		return this.path;
	}
	
}