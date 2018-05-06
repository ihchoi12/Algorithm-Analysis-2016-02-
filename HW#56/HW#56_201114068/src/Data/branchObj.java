package Data;

import java.util.ArrayList;

public class branchObj {
	private double lowerBound;
	private ArrayList<Integer> partialTour;
	private VertexList vlist;
	
	public branchObj(double val, VertexList vl)
	{
		this.lowerBound = val;
		this.vlist = new VertexList(vl.getLength());
		partialTour = new ArrayList<Integer>();
		
		this.vlist.init_flag();
	
		for(int i=0; i<vl.getLength(); i++)
		{
			if(vl.get(i).getFlag() == true)
				this.vlist.get(i).set_on_flag();
		}
	}
	public VertexList getVertexList()
	{
		return this.vlist;
	}
	public double getLowerBound()
	{
		return this.lowerBound;
	}
	public void setLowerBound(double val)
	{
		this.lowerBound = val;
	}
	public void setPartialTour(ArrayList<Integer> tour)
	{
		this.partialTour = tour;
	}
	public ArrayList<Integer> getPartialTour()
	{
		return this.partialTour;
	}
}
