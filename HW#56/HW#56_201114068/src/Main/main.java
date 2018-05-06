package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import Data.*;
import Solution.*;
public class main {
	public static double getWeight(Vertex source, Vertex target){
		double val = Math.sqrt(
		(source.getX() - target.getX()) * (source.getX() - target.getX())+
		(source.getY() - target.getY()) * (source.getY() - target.getY())
		);
		return Math.floor(val*1000)/1000;
//		return Math.round(val * 1000000)/1000000;		
				
				
	}
	public static void makeAdjTable(AdjTable table, VertexList vList){
		for(int source = 0; source != vList.getLength(); ++source){
			Vertex s = vList.get(source);
			for(int target = source + 1; target != vList.getLength(); ++target){
				Vertex t = vList.get(target);
				table.insert(source, target, getWeight(s,t));
				table.insert(target, source, getWeight(s,t));
			}
		}
	}
	public static void print(long timelist[]){
		String[] word = {"DP ", "Backtracking ", "BFS "
				, "Genetic ", "Annealing "};
		System.out.println("**********Time cost********** ");
		
		for(int i = 0 ; i != timelist.length; ++i)
			System.out.printf("%-15s : %10d \n", word[i], timelist[i]);
		
	}
	public void printFile(ArrayList<Integer> path, double val, FileOutputStream os ) throws IOException{
		Iterator<Integer> i = path.iterator();
		while(i.hasNext()){
			Integer temp = new Integer(i.next());
			String x = temp.toString() + " ";
			os.write(x.getBytes());
		}
	}
	public static void main(String[] args) throws IOException{
		Scanner s = new Scanner(new File("C:\\hw56\\input.txt"));
		FileOutputStream os = new FileOutputStream("C:\\hw56\\2011147068.txt");
		
		int v_num = s.nextInt();
		VertexList vlist = new VertexList(v_num);
//		veclist.insert(new Vertex(-1, -1));
		AdjTable table = new AdjTable(v_num);// 
		
		for(int i = 0 ; i != v_num; ++i){
			int id = s.nextInt();
			Vertex temp = new Vertex(s.nextDouble(), s.nextDouble());
			vlist.insert(temp);
			
		}
		makeAdjTable(table, vlist);
		// Print
		//vlist.print();
		//table.print();
		
/*
 * vecList, Edge에 값이 다 들어간 상태이니 그대로 쓰면 될듯.
 * 가령 내가 class DP라는 것을 만든다 하면
 * DP dp = new DP(VertexList veclist, table);
 * dp.solve() 이런식으로 양식 지켜주셍
 * 그리고 자기 방법으로 해결 되었을 때 마지막에 flag 꼭 init 하는거 잊지 말길. flag를 solve 과정에서 건드리기 떄문에
 * 최종적으로는 바뀌어 있을거임. 그래서 자기 꺼하고 vertex list flag 초기화 해주기
 * weight는 소수점 3째 자리 까지표현, 3째 자리 까지 같으면 동일한걸로 
 * 그리고 시간 단위는 nano second로 했으니 참고하셈!
 */
		int x = vlist.getLength();
		long timelist[] = {-1,-1,-1,-1, -1};
		if(x <38){
		
		DP solDP = new DP(table, vlist, os);
		timelist[0] = solDP.solve();
		
		
		
		ArrayList<Integer> tour = new ArrayList<Integer>();
		tour.add(0);
		vlist.get(0).print();
		vlist.get(0).set_on_flag();
		BackTracking solBackTracking = new BackTracking(table, vlist, os);
		timelist[1]=solBackTracking.solve(tour, 0, vlist);
		vlist.get(0).set_on_flag();
		
		
			
		BranchAndBound solBranchAndBound = new BranchAndBound(table, vlist, os);
		timelist[2] = solBranchAndBound.solve();
		}
		if(x <2900){
		Genetic solGene = new Genetic(table, vlist, os);
		vlist.init_flag();
		timelist[3] = solGene.solve();
		}
		if(x <5000){
			
		SA sa  = new SA(table, vlist,os);
		timelist[4] = sa.solve();
		vlist.init_flag();
		vlist.init_flag();
		print(timelist);
		}
	}


}
