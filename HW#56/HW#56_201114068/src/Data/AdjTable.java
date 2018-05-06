package Data;

public class AdjTable {
	private int size;
	private Edge[][] table;
	public AdjTable(int size){
		this.size = size;
		table = new Edge[size][];
		for(int i = 0 ; i != size; ++i){
			table[i] = new Edge[size];
			for(int j = 0; j != size; ++j)
				table[i][j] = new Edge();
		}
	}
	public void insert(int source, int target, double d){
		table[source][target].setWeght(d);
	}
	public void print(){
	
		for(int row = 0; row != size; ++row){
			for(int col = 0 ; col != size; ++col)
				System.out.printf("%10.3f ", table[row][col].getWeight());
			System.out.println();
			
		}
	}
	public double getVal(int source, int target){
		return table[source][target].getWeight();
	}
	public Edge get(int x, int y){
		return table[x][y];
	}

}
