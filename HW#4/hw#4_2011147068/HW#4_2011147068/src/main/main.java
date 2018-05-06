package main;

import data.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class main {
	
	public static void main(String[] args) throws IOException {
		Scanner s = new Scanner(new File("C:\\hw4\\input.txt"));
		FileOutputStream os = new FileOutputStream("C:\\hw4\\2011147068.txt");
		//FileOutputStream os = new FileOutputStream("2011147068.txt");
		int num_test = s.nextInt();
//		System.out.print(num_test);
		for(int i = 0 ; i < num_test; i++){
			Data d = new Data(s);
			d.setPlacementTable();
			d.setNeighborGraph();
			d.print_board();
			
//			d.printPlacementTable();
//			d.printNeighborGraph();
			DP dp = new DP(d);
			
			dp.makeDP(d);
			//dp.printResult(d);
			dp.printResult(os, d);


		}
		
		
		
	}
	
	
	
}


