package Solution;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import Data.AdjTable;
import Data.VertexList;

public class Solution {
	//input
	protected AdjTable table;
	protected VertexList vlist;
	//output
	protected ArrayList<Integer> solpath;
	protected double val;
	protected FileOutputStream os;
	
	public Solution(){
		table = null;
		vlist = null;
		solpath = null;
		val = 0;
	}
	
	public Solution(AdjTable table, VertexList veclist, FileOutputStream os){
		this.table = table;
		this.vlist = veclist;
		solpath = new ArrayList<Integer>();
		val = 0;
	}
	public void printSOL(){
		Iterator<Integer> iter = solpath.iterator();
		while(iter.hasNext()){
			Integer path = iter.next();
			System.out.printf("%d ", path.intValue());
		}
		
		System.out.println(val);
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
		os.write("\n".getBytes());
	}
	public long solve() throws IOException{
		/*
		 * body
		 */
		System.out.print("Just Solve");
		
		return 0;
	}

}
