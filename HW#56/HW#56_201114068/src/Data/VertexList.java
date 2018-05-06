package Data;

public class VertexList {
	private int length;
	private int current;
	private Vertex[] list;
	public VertexList(){
		length = 0; list = null;
		this.current = 0;
	}
	public VertexList(int num){
		this.current = 0;
		this.length = num;
		list = new Vertex[num];
		for(int i = 0; i!= num; ++i) list[i]= new Vertex();
	}
	public Vertex[] getList(){
		return list;
	}
	public int getCurrent(){
		return current;
	}

	public int getLength(){
		return length;
	}
	public void insert(Vertex v){
		list[current] = v;
		++current;
	}
	public void print(){
		for(int i= 0 ; i != length; ++i){
			list[i].print();
		}
	}
	public void init_flag(){
		for(int i= 0 ; i != length; ++i){
			list[i].set_off_Flag();
		}
	}
	public Vertex get(int index){
		//if(index < current && index >-1)
		return list[index];
		//else return null;
	}


}
