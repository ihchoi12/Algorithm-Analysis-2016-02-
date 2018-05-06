package Input;

public class EdgeSet {//n은 전체 선분의 개수
	private Edge[] arr;
	private int capacity;
	private int num;
	public EdgeSet(){
		arr = null; 
		capacity = 0; num = 0;
	}
	public EdgeSet(int n){	
		capacity = n;
		arr = new Edge[capacity];
		 
		num = 0;	
	}
	
	public void put(int index, Edge t){
		arr[index] = t;
		++num;
	}
	public void print(){
		for(int i = 0; i != num ; ++i){
			arr[i].print();
			System.out.println();
		}
	}
	public int getCapacity(){
		return capacity;
	}
	public Edge getEdge(int index){
		return arr[index];
	}

}
