package Data;

public class Edge {
	private double weight;//dstance * distance
	public Edge(){
		weight = -1;
	}
	public Edge(int weight){
		this.weight = weight;
	}
	public void setWeght(double d){
		this.weight = d;
	}
	
	
	public double getWeight(){
		return weight;
	}
	
	public void print(){
		System.out.println(String.valueOf(weight));
	}
	
	
}
