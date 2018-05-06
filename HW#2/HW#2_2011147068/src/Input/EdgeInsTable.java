package Input;

public class EdgeInsTable {
	
	private InstanceList[] arr;
	int capacity; // arr의 최대 용량
	int num;//현재 arr에 들어있는 개수
	public EdgeInsTable(){
		arr= null;
		capacity = 0;
		num = 0;
	}
	public EdgeInsTable(int n){
		this.capacity = n;
		num = 0;
		arr = new InstanceList [capacity];
		for(int i = 0 ; i != capacity; ++i)
			arr[i] = new InstanceList();
	}
	public void put(int index, Instance a){//arr[index]에 a를 넣는다.
		arr[index].put(a);
		++num;
	}
	public void print(){
		for(int i = 0 ; i != capacity ; ++i){
			System.out.println("Order : " + i);
			arr[i].print();
			System.out.println();
		}
	}
	public InstanceList getIList(int order){
		return arr[order];
	}

}
