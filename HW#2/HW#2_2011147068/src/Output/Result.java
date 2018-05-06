package Output;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import Input.Vertex;
public class Result {
	private ArrayList<Vertex> li;
	public Result(){
		li = new ArrayList<Vertex>();
	}
	
	public void put(Vertex v){
		li.add(v);
	}
	public void print(){
		ListIterator<Vertex> iter = li.listIterator();
		while(iter.hasNext()){
			iter.next().print();
		}
	}
	public void print(FileOutputStream os) throws IOException{
		ListIterator<Vertex> iter = li.listIterator();
		while(iter.hasNext()){
			iter.next().print(os);
			//iter.next().print(os);
		}
		os.write("\n".getBytes());
		
	}
}
