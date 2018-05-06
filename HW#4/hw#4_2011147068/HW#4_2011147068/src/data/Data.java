package data;

import java.util.Scanner;

public class Data {

	private final int num_row = 4;
	private final int num_placement = 8;
	private int[][] board;
	private int num_col;
	private int [][] placement_table; // 
	private boolean [][] neighbor_graph;
	public Data(Scanner s) {
		this.num_col = s.nextInt();
		this.board = new int[num_row][num_col];	
		this.placement_table = new int[this.num_placement][2];
		this.neighbor_graph = new boolean[this.num_placement][this.num_placement];
		for(int i = 0 ; i < num_row; i++)
			for(int j = 0 ; j < num_col; j++){
				int temp = s.nextInt();
				if(temp < 0)
					temp = -temp;
				this.board[i][j] = temp;
			}
	}
	
	public int getNumPlacement() {
		return num_placement;
	}
	
	public void setNeighborGraph(){
		for(int i = 0 ; i <this.num_placement; i++){
			for(int j = 0; j < this.num_placement; j++) { // for all rows and columns in the graph
				this.neighbor_graph[i][j] = true; // at first set all trues
				for(int k = 0 ; k < 2; k++){
					for(int l = 0; l<2; l++){
						if(this.placement_table[i][k] != -1){ // except the case that placement table have same value of -1
							if(this.placement_table[i][k] == this.placement_table[j][l]){  
								// if two placement case have same location where stone is placed (=have same value in placement table )
								this.neighbor_graph[i][j] = false; // then they couldn't be neighbor ( set false )
								this.neighbor_graph[j][i] = false;
							}
						}	
					}
				}
			}
		}
	}
	public boolean getNeighborGraph(int x, int y){
		return this.neighbor_graph[x][y];
	}
	
	
	public void setPlacementTable(){
		for(int i = 0 ; i < this.num_placement; i++){
			this.placement_table[i][0] = -1; // initiates the table values with -1s
			this.placement_table[i][1] = -1;
			switch(i){
			case 0: // placement 0 : choose nothing in the column
				break;
			case 1:// placement 1 : choose 1st low in the column
				this.placement_table[i][0] = 0;
				break;
			case 2:// placement 2 : choose 2nd low in the column
				this.placement_table[i][0] = 1;
				break;
			case 3:// placement 3 : choose 3rd low in the column
				this.placement_table[i][0] = 2;
				break;
			case 4:// placement 4 : choose 4th low in the column
				this.placement_table[i][0] = 3;
				break;
			case 5: // placement 5 : choose 1st and 3rd lows in the column
				this.placement_table[i][0] = 0;
				this.placement_table[i][1] = 2;
				break;
			case 6: // placement 6 : choose 1st and 4th lows in the column
				this.placement_table[i][0] = 0;
				this.placement_table[i][1] = 3;
				break;
			case 7:// placement 7 : choose 2nd and 4th lows in the column
				this.placement_table[i][0] = 1;
				this.placement_table[i][1] = 3;
				break;
			default:
				System.out.print("out of board index range");
				
			}
		}
		
	}
	public int getPlacementSum(int index, int placement){
		int sum = 0 ;
		for(int i = 0 ; i <2; i++){
			if(this.placement_table[placement][i] != -1)
			sum += board[this.placement_table[placement][i]][index];
		}
		return sum;
		
	}
	
	
	public void print_board(){
		for(int i = 0 ; i < num_row; i++){	
			for(int j = 0 ; j < num_col; j++)
				System.out.print(board[i][j]+" ");
			System.out.println(" ");
		}	
	}
	public void printPlacementTable(){
		for(int i = 0; i< this.num_placement; i++){
			for(int j = 0; j < 2; j++){
				System.out.print(this.placement_table[i][j]+" ");
			}
			System.out.println(" ");
		}
	}
	public void printNeighborGraph(){
		System.out.println("--------------- ");
		for(int i = 0; i< this.num_placement; i++){
			for(int j = 0; j< this.num_placement; j++){
				System.out.print(this.neighbor_graph[i][j]+" ");
			}
			System.out.println(" ");
		}
	}
	
	
	public int getNumCol() {
		return num_col;
	}
	public void setNumCol(int num_col) {
		this.num_col = num_col;
	}
	public int getNumRow() {
		return num_row;
	}
	
	
}
