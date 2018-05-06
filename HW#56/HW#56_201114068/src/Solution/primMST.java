package Solution;

import java.util.ArrayList;

import Data.*;

public class primMST {
	private int vnum;
	private int start;
	private int val;
	private int vertexNum;
	private AdjTable table;
	private VertexList vlist;
	private ArrayList<Integer> primTour;
	
	public primMST(AdjTable table, VertexList vlist, int vertexNum, int vnum)
	{
		this.table = table;
		this.vlist = new VertexList(vnum);
		this.vlist.init_flag();
		
		for(int i=0; i<vlist.getLength(); i++)
		{
		//	System.out.println(vl.get(i).getFlag());
			if(vlist.get(i).getFlag() == true)
				this.vlist.get(i).set_on_flag();
			
		}// prim¾Ë°í¸®ÁòÀ» ¿äÃ»ÇÑ ±×·¡ÇÁ¿¡¼­ ÀÌ¹Ì Áö³ª¿Â vertex¸¦ Ç¥½ÃÇØÁÜ. ±× vertexµéÀ» Á¦¿ÜÇÑ MST¸¦ Ã£´Â´Ù.
		
	
		
		this.vnum = vnum;
		
		for(int i=0; i < this.vnum; i++)
		{
			if(!this.vlist.get(i).getFlag())
			{
				this.start = i;
				break;
			}
		}//prim¾Ë°í¸®ÁòÀ» ½ÃÀÛÇÒ vertex¸¦ Ã£´Â´Ù. ÀÌ ½ÃÀÛvertex´Â ÁÖ¾îÁø ±×·¡ÇÁ(ÀÌ¹Ì Áö³­ vertex¸¦ Á¦¿ÜÇÑ)¿¡¼­ ¾Æ¹« vertex¸¦ ¼±ÅÃÇØµµ µÈ´Ù.
		
		this.vlist.get(start).set_on_flag();
		this.vertexNum = vertexNum;
		primTour = new ArrayList<Integer>();
		primTour.add(this.start); // ¿©±â¼­ primtour´Â MST¸¦ Ã£´Â Áß°£°úÁ¤¿¡ »ç¿ëÇÒ °æ·ÎÀÌ´Ù. ÀÌ °æ·ÎÀÇ Å©±â°¡ Æ÷ÇÔÇØ¾ßµÉ vertex¼ö ¸¸Å­ Ä¿Áö¸é MST°¡ ¸¸µé¾îÁø´Ù. 
		
		this.val = 0;

	}
	public double solve()
	{
		int sourceIndex=0, targetIndex=0;
		double min = 9999999;
	
		while(true)
		{
			if(primTour.size() == this.vertexNum)
			{
				//System.out.println("BREAK!!");
				break;
			} // while¹®À» ¹Ýº¹ÇÒ ¶§¸¶´Ù ¸ðµç Á¤Á¡ÀÌ Æ÷ÇÔµÇ¾î MST°¡ ¿Ï¼ºµÇ¾ú´ÂÁö È®ÀÎÇÏ°í, ¿Ï¼ºµÇ¾úÀ¸¸é while¹®À» Á¾·áÇÑ´Ù.
			
			sourceIndex = 0;
			targetIndex = 0;
			min = 9999999;
			for(int i=0; i<this.primTour.size(); i++)
			{
				
				for(int j=0; j<this.vnum; j++)
				{
					if(!this.vlist.get(j).getFlag() && min >= table.getVal(primTour.get(i), j) && i!=j)
					{
						min = table.getVal(primTour.get(i), j);
					//	vlist.get(j).set_on_flag();
						sourceIndex = primTour.get(i);
						targetIndex = j;
					}
				}
			}//ÇöÀç MST¿¡ Æ÷ÇÔµÇ¾îÀÖ´Â vertex·ÎºÎÅÍ Æ÷ÇÔµÇÁö ¾ÊÀº vertex·Î ¿¬°áµÇ¾î ÀÖ´Â edgeµé Áß¿¡¼­ °¡Àå ÂªÀº edge¸¦ Ã£´Â´Ù.
			primTour.add(targetIndex); // ¼±ÅÃµÈ °¡Àå ÂªÀº edge·Î ÀÎÇØ¼­ »õ·Î ¿¬°áµÈ targetIndex¸¦ »õ·Î¿î vertex·Î primTour¿¡ Ãß°¡ÇÑ´Ù.
		
	
			this.val += this.table.getVal(sourceIndex, targetIndex);//MST°ª¿¡ »õ·Î Ãß°¡µÈ edgeÀÇ °ªÀ» ´õÇÑ´Ù.
			this.vlist.get(targetIndex).set_on_flag();	 // »õ·Î Ãß°¡µÈ vertex¸¦ ÀÌ¹Ì µµ´ÞÇÑ Á¤Á¡À¸·Î flag_on ÇÑ´Ù.

		}
		//this.vlist.init_flag();
		return this.val;
	}
}
