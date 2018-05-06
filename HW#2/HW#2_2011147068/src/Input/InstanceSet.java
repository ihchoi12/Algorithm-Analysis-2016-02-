package Input;
import java.util.*;
public class InstanceSet {
	private HashMap<Vertex, Instance> is;
	public InstanceSet(){
		is = new HashMap<Vertex, Instance>();
	}
	public void put(Vertex pos, Instance e){
		is.put(pos, e);	
	}
	public Instance getInstance(Vertex v){
		return is.get(v);
	}
	public void print(){
		Iterator<Vertex> i = is.keySet().iterator();
		while(i.hasNext()){
			Vertex temp = i.next();
			System.out.println(" ******************************************************************************** ");
			System.out.print(" Vertex : ");
			temp.print();
			System.out.println("");

			System.out.println("*Instance* ");
			is.get(temp).print();
			System.out.println("");
		}
		

	}
	
}
