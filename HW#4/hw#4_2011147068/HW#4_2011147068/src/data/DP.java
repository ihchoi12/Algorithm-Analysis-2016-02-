package data;

import java.io.FileOutputStream;
import java.io.IOException;

public class DP {
	private int[][] dp;
	public DP(Data d){
		this.dp = new int[d.getNumCol()][d.getNumPlacement()];

	}
	
	public void makeDP(Data d){
		for(int index = 0 ; index < d.getNumCol(); index++ ) {
			for(int placement = 0 ; placement < d.getNumPlacement(); placement++)
				if(index == 0 )
					dp[index][placement] = d.getPlacementSum(index, placement);
				else{	
					for(int i = 0 ; i < d.getNumPlacement(); i++) {
						int max = 0;
						for(int j = 0 ; j < d.getNumPlacement(); j++){
							if(d.getNeighborGraph(i, j)){ // if i-th placement can be adjacent to j-th placement.
								if(max < dp[index-1][j]){
									max = dp[index-1][j]; // then get the biggest dp value of previous index
								}
							}
						}
						
						dp[index][i] = d.getPlacementSum(index, i) + max; // dp recursive equation.
					}	
				}
		}
	}
	
	public void printResult(Data d){
		int max = 0 ;
		
		for(int i = 0 ; i < d.getNumPlacement(); i++ ){
//			System.out.print(this.dp[d.getNumRow()][i]+"  ");
			if(max < this.dp[d.getNumCol()-1][i]){
				max = this.dp[d.getNumCol()-1][i];
				
			}
			
		}
//		System.out.println("");
		System.out.println(max);
	}
	public void printResult(FileOutputStream os,Data d) throws IOException{
		int max = 0 ;
		
		for(int i = 0 ; i < d.getNumPlacement(); i++ ){
//			System.out.print(this.dp[d.getNumRow()][i]+"  ");
			if(max < this.dp[d.getNumCol()-1][i]){
				max = this.dp[d.getNumCol()-1][i];
				
			}
			
		}
		Integer temp = new Integer(max);
		String x = temp.toString() + "\n";
		os.write(x.getBytes());
		
	}
	
	
	
	
	
}
