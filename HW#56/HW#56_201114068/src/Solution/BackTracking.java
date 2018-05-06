package Solution;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import Data.*;
public class BackTracking{
	private AdjTable table;
	private double dist = 9999999; // Ã³À½ dist °ª max·Î ÃÊ±âÈ­.
	private int vnum;
	private FileOutputStream os;
	private ArrayList<Integer> lastTour;
	private long time = 0;
	public BackTracking(){
		table = null;
	}
	public BackTracking(AdjTable table, VertexList vlist, FileOutputStream os) {
		// TODO Auto-generated constructor stub
		
		this.table = table;
		vnum = vlist.getLength();
		this.os =os;

	}
	public long solve(ArrayList<Integer> tour, double curDist, VertexList vlist) throws IOException{ // curDist ´Â Áö±Ý±îÁöÀÇ distance
		long start = System.currentTimeMillis();
		BacktrackTSP(tour, curDist, vlist);
		System.out.print("BACKTRACKING  ");
		for(int i=0; i<this.lastTour.size(); i++)
		{
			System.out.print(this.lastTour.get(i)+1 + " ");
		}
		//값은 dist, 경로는 lastTour
		System.out.println("val: " + this.dist);
		
		long end = System.currentTimeMillis();
		printFile(convert2(lastTour), dist, os);
		return end - start;
	}
	public ArrayList<Integer> convert2(ArrayList<Integer> i){
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for(int x = 0 ; x != i.size(); ++x){
			ret.add(i.get(x)+1);
		}
		return ret;
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
	public void BacktrackTSP(ArrayList<Integer> tour, double curDist, VertexList vlist)
	{
		/*
		System.out.print("BackTracking");
		System.out.println("***********Ã¼Å©**********" + curDist + "  " + this.dist);
		
		System.out.println(tour + " " + tour.size());
		for(int i=0; i<vnum; i++)
			System.out.print(vlist.get(i).getFlag()+ " ");
		System.out.println(this.vnum);
		*/
		//System.out.println("partialTour : " + tour + " curDist(ÇöÀç distance) : " + curDist);
		if(tour.size() == vnum) // ¸ðµç vertex¸¦ Áö³µ°í ½ÃÀÛÁ¡ÀÎ 1¹ø vertex·Î µ¹¾Æ¿À´Â°Í¸¸ ³²À½.
		{
			int lastVer = tour.get(tour.size()-1); // ÇöÀç °æ·ÎÀÇ ¸¶Áö¸· vertex
			
			
			curDist += this.table.getVal(lastVer, 0); // ½ÃÀÛÁ¡À¸·Î µ¹¾Æ¿À´Â edge°ª ´õÇÔ.
			if(curDist < this.dist) // »õ·Î ±¸ÇØÁø complete value°¡ Áö±Ý±îÁö ±¸ÇØÁø distÀÇ ÃÖ¼Ò°ªº¸´Ù ÀÛ´Ù
			{
				this.dist = curDist; // ÃÖ¼Ò°ª ¾÷µ¥ÀÌÆ®
				this.lastTour = new ArrayList<Integer>();
				this.lastTour.addAll(tour);
				this.lastTour.add(0);
				//System.out.println("*********°»½ÅµÊ!!!!**********" + curDist + "  " + this.dist);
			}
			

		}
		else
		{
			for(int i=0; i<vnum; i++)
			{
				if(!vlist.get(i).getFlag())
				{
					int lastVer = tour.get(tour.size()-1); 
					tour.add(i);
					vlist.get(i).set_on_flag();
					if(curDist+this.table.getVal(lastVer, i) < this.dist)
					{
						BacktrackTSP(tour, curDist+this.table.getVal(lastVer, i), vlist);
					}
					tour.remove(tour.size()-1);
					vlist.get(i).set_off_Flag();
				}
			}
		}
	}

}
