package Solution;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import Data.branchObj;
import Data.AdjTable;
import Data.VertexList;
public class BranchAndBound {
	private AdjTable table;
	private ArrayList<branchObj> ob; // branchObj ¸¦ ÀúÀåÇÒ ArraylistÀÌ´Ù.
	private ArrayList<Integer> lastTour; // complete solution¿¡ µµ´ÞÇÒ °æ¿ì, ±× °æ·ÎÀÇ dist¿¡ µû¶ó ¾÷µ¥ÀÌÆ®µÈ´Ù. ´äÀ¸·Î Ãâ·ÂµÉ ÃÖÁ¾°æ·Î ÀúÀå.
	private int vnum; // vertexÀÇ °³¼ö
	private double val; // ÃÖÁ¾ dist°¡ ÀúÀåµÉ º¯¼ö.
	private FileOutputStream os;
	public BranchAndBound()
	{
		this.table = null;
	}
	public BranchAndBound(AdjTable table, VertexList vlist, FileOutputStream os)
	{
		this.os = os;
		ArrayList<Integer> tour = new ArrayList<Integer>();
		tour.add(0);
		this.table = table;

		this.val = 9999999; // °æ·Î°ªÀÇ ÃÖ¼Ò°ªÀ» ±¸ÇØ¾ß ÇÏ¹Ç·Î ÃÊ±â°ª 9999999·Î ¼³Á¤.
		ob = new ArrayList<branchObj>();
		
		
		branchObj tempBranchObj = new branchObj(9999999, vlist); //Ã³À½ ½ÃÀÛÇÒ object¸¦ »ý¼ºÇÑ´Ù. lowerBound´Â 9999999·Î ¼³Á¤.
		vnum=vlist.getLength();
		tempBranchObj.setPartialTour(tour);
		ob.add(tempBranchObj); // ÀÓ½Ã·Î ¸¸µé¾î µÎ¾ú´ø tempBranchObj¸¦ 'ob' Arraylist¿¡ Ãß°¡ÇÑ´Ù.
		
	}
	public long solve() throws IOException{
		long start = System.currentTimeMillis();
		BABtsp(); // Branch And Bound¸¦ ÀÌ¿ëÇØ¼­ TSP¸¦ ±¸ÇÑ´Ù.
		//System.out.println("!ÃÖÁ¾°æ·Î!BRANCHANDBOUND");
		System.out.print("BST   "); // ÃÖÁ¾ dist¸¦ Ãâ·ÂÇÑ´Ù.
		
		for(int i=0; i<this.lastTour.size(); i++)
		{
			System.out.print(this.lastTour.get(i)+1 + " ");
		} // lastTour¸¦ Ãâ·ÂÇÑ´Ù. 1À» ´õÇÑ°ÍÀº ¿ì¸® ÇÁ·Î±×·¥¿¡¼­ 0¹ø vertex°¡ 1¹ø vertex¸¦ °¡¸®Å°±â ¶§¹®.
		System.out.println("value : " + this.val); // ÃÖÁ¾ dist¸¦ Ãâ·ÂÇÑ´Ù.
		
		long end = System.currentTimeMillis();
		printFile(convert2(lastTour), val, os);
		//값은 dist, 경로는 lastTour
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
	public void BABtsp()
	{

		while(!ob.isEmpty()) // 'ob' Arraylist°¡ ºñ¾îÀÖÀ»¶§±îÁö ¹Ýº¹ÇÑ´Ù.
		{
		
			
			double minBound = this.val;
			int minIndex=0;
			int check=1; // Áö±Ý BranchAndBound Æ®¸®¿¡ ÇöÀç ±¸ÇØÁø ÃÖÁ¾ dist°ªº¸´Ù ÀÛÀº lowerBound¸¦ °®´Â branchObj°¡ ÀÖ´ÂÁö È®ÀÎÇÏ±â À§ÇÑ checkº¯¼ö. 
			for(int i=0; i<ob.size(); i++) 
			{
				if(minBound >= ob.get(i).getLowerBound())
				{
					minBound = ob.get(i).getLowerBound();
					minIndex = i;
					check=0;
				}
			}//'ob' Arraylist¸¦ µ¹¸é¼­ °¡Àå lowerBound°¡ ÀÛÀº branchObj¸¦ Ã£´Â´Ù.
		
			if(check==1) break; // check°ªÀÌ º¯ÇÏÁö ¾Ê¾Ò´Ù´Â °ÍÀº ÇöÀç ±¸ÇØÁø ÃÖÁ¾ dist°ªº¸´Ù ÀÛÀº lowerBound¸¦ °®´Â branchObj°¡ ¾ø´Ù´Â ¶æÀÌ°í
								// ±×·¯¸é Áö±Ý Arraylist¿¡ ³²Àº objectµéÀº evaluateÇØº¼ ÇÊ¿ä°¡ ¾ø´Ù. ¾îÂ÷ÇÇ Áö±Ý ÃÖÁ¾ distº¸´Ù Å©°Ô ³ª¿Ã °ÍÀÌ±â ¶§¹®.
		
			for(int i=1; i<vnum; i++)// ½ÃÀÛ vertex(¹®Á¦¿¡¼­1¹ø)Àº ÀÌ¹Ì Ã³À½¿¡ Áö³­ vertexÀÌ¹Ç·Î Á¦¿ÜÇÏ°í ´Ù¸¥ vertexÁß¿¡¼­ 
			{						 // ¼±ÅÃµÈ branchObj³»¿¡¼­ ¾ÆÁ÷ Áö³ªÁö ¾ÊÀº vertex¸¦ Ã£´Â´Ù.

				if(!ob.get(minIndex).getVertexList().get(i).getFlag()) // flag¸¦ ÀÌ¿ëÇØ ±× vertex¸¦ Áö³µ´ÂÁö È®ÀÎ.
				{
					if(ob.get(minIndex).getPartialTour().size() == this.vnum-1)// partialTourÀÇ size°¡ vnum-1ÀÌ¶ó´Â °ÍÀº
					{													// i vertexÇÏ³ª¸¸ Ãß°¡µÇ°í ½ÃÀÛvertex·Î µ¹¾Æ°¡¸é path¿Ï¼º.
						ArrayList<Integer> tour = new ArrayList<Integer>();
						
						for(int j=0; j<ob.get(minIndex).getPartialTour().size(); j++)
						{
							tour.add(ob.get(minIndex).getPartialTour().get(j));
						}
						tour.add(i);
						tour.add(0); // partialTour¿¡ ¸¶Áö¸· i vertexÃß°¡ ÈÄ ½ÃÀÛvertex·Î ´Ù½Ã µ¹¾Æ°¨.
						double dist=0;
						for(int j=0; j<tour.size()-1; j++)
						{
							dist+=table.getVal(tour.get(j), tour.get(j+1));
						}// ±× tour»ó¿¡ ÀÖ´Â ¸ðµç edgeÀÇ °¡ÁßÄ¡¸¦ ´õÇÑ´Ù.
						if(this.val>dist)
						{
							lastTour = new ArrayList<Integer>();
							for(int j=0; j<tour.size(); j++)
								lastTour.add(tour.get(j));
							this.val = dist;
						}// ±¸ÇØÁø dist°¡ Áö±Ý±îÁö ±¸ÇØÁø ÃÖÁ¾ valº¸´Ù ÀÛÀ¸¸é ¾÷µ¥ÀÌÆ®!, ÃÖÁ¾ °æ·Îµµ ¾÷µ¥ÀÌÆ®ÇÑ´Ù.

					}
					else // ¾ÆÁ÷ ¿ÏÀüÇÑ path¿¡ µµ´ÞÇÏÁö ¸øÇßÀ»°æ¿ì.
					{
						double lowerBound = calLowerBound(minIndex, i); // lowerBound °è»ê
						//System.out.println(minIndex + " " + i + "*" + lowerBound);
						if(lowerBound < this.val) // lowerBound°¡ Áö±Ý ±¸ÇØÁø ÃÖÁ¾ valº¸´Ù ÀÛÀº °æ¿ì¿¡¸¸ 'ob' Arraylist¿¡ Ãß°¡µÊ.
						{
							branchObj tempBranchObj = new branchObj(lowerBound, ob.get(minIndex).getVertexList());
							for(int j=0; j<ob.get(minIndex).getPartialTour().size(); j++)
							{
								tempBranchObj.getPartialTour().add(ob.get(minIndex).getPartialTour().get(j));
							}
							tempBranchObj.getPartialTour().add(i); // »õ·Î Ãß°¡µÈ i vertex¸¦ partialTour¿¡ Ãß°¡
							tempBranchObj.getVertexList().get(i).set_on_flag();// VertexList¿¡¼­ i vertexÀÇ flag¸¦ on ÇØÁØ´Ù.
							
							
							//tempBranchObj.getVertexList().get(i).set_on_flag();
							//ArrayList<Integer> tour = new ArrayList<Integer>();
							//tour = ob.get(minIndex).getPartialTour();
							//tour.add(i);
				
							ob.add(tempBranchObj);
						}
					}
				}
			}
			ob.remove(minIndex);
			
		}
	}
	public double calLowerBound(int minIndex, int add) // lowerBound ±¸ÇÏ´Â ÇÔ¼ö.
	{
		double lowerBound = 0;

		ob.get(minIndex).getVertexList().get(add).set_on_flag(); // add vertexµµ Áö³ª¿Â Á¡À¸·Î Ã³¸®. ³ªÁß¿¡ ÀÌ ÇÔ¼ö¸¦ ºüÁ®³ª°¥¶§ ´Ù½Ã offÇØÁÜ
																 // add´Â ÀÓ½Ã·Î Ãß°¡µÈ °ÍÀÌ±â ¶§¹®¿¡.
		branchObj tempOB = new branchObj(9999999, ob.get(minIndex).getVertexList());
		tempOB.getVertexList().get(add).set_on_flag();
		
		for(int i=0; i<ob.get(minIndex).getPartialTour().size(); i++)
		{
			tempOB.getPartialTour().add(ob.get(minIndex).getPartialTour().get(i));
		}
		tempOB.getPartialTour().add(add); // ½ÇÁ¦ ob¸¦ °Çµå¸®Áö ¾Ê±â À§ÇØ tempOB¸¦ ¸¸µé°í °Å±â¿¡ ob¿Í °°Àº °ªµéÀ» ¼³Á¤ÇØÁØ´Ù.
		
		primMST prim = new primMST(this.table, tempOB.getVertexList(), this.vnum-tempOB.getPartialTour().size(), this.vnum);
		//¿©±â¼­ 3¹øÂ° ÀÎÀÚÀÎ this.vnum-tempOB.getPartialTour().size()´Â MST¸¦ ±¸ÇÏ´Â °úÁ¤¿¡¼­ MST°¡ Æ÷ÇÔÇØ¾ßÇÒ vertexÀÇ °¹¼öÀÌ´Ù. 
		double valMST = prim.solve();//prim¾Ë°í¸®ÁòÀ¸·Î MST°æ·Î°ªÀ» ±¸ÇØ¼­ valMST¿¡ ÀúÀåÇÑ´Ù.
			
		
		lowerBound += valMST; // lowerBound¿¡ MST°æ·Î°ªÀ» ´õÇÑ´Ù.

		for(int j=0; j<tempOB.getPartialTour().size()-1; j++)
		{
			lowerBound += this.table.getVal(tempOB.getPartialTour().get(j), tempOB.getPartialTour().get(j+1));
		} // ÇöÀç Áö³ª¿Â °æ·Î»ó¿¡ ÀÖ´Â edgeÀÇ °ªµéÀ» ÀüºÎ ´õÇÑ´Ù.
		
		double min1=9999999, min2=9999999;
		int start = tempOB.getPartialTour().get(0);
		int end = add;
		//System.out.println("start: " + start + " end: " + end);
		for(int j=0; j < vnum; j++)
		{
			//System.out.print(ob.get(minIndex).getVertexList().get(j).getFlag() + " ");
			if(!ob.get(minIndex).getVertexList().get(j).getFlag() && min1>this.table.getVal(start, j) && start!=j)
			{
				min1 = this.table.getVal(start, j);
			}
			if(!ob.get(minIndex).getVertexList().get(j).getFlag() && min2>this.table.getVal(end, j) && end!=j)
			{
				min2 = this.table.getVal(end, j);
			}
		}//ÇöÀç Áö³ª¿Â °æ·ÎÀÇ ½ÃÀÛÁ¡°ú ³¡Á¡¿¡¼­, Áö³ª¿Â Á¡µéÀ» Á¦¿ÜÇÑ ³ª¸ÓÁö Á¡µé±îÁöÀÇ edge°ªÁß °¡Àå ÀÛÀº°ªÀ» Ã£´Â´Ù.
		lowerBound += min1;
		lowerBound += min2; // lowerBound¿¡ ±× µÎ°ªÀ» ´õÇÑ´Ù.
		//System.out.println(lowerBound);
		ob.get(minIndex).getVertexList().get(add).set_off_Flag(); // À§¿¡¼­ ¸»ÇßµíÀÌ add´Â lowerBound¸¦ ±¸ÇÏ±â À§ÇØ ÀÓ½Ã·Î Ãß°¡µÈ Á¡ÀÌ¹Ç·Î ´Ù½Ã flag_off ÇØÁØ´Ù.
		//System.out.println("partialTour : " + tempOB.getPartialTour() + " lowerBound : " + lowerBound);
		
		return lowerBound;
	}
}
