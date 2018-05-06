package Arr;

import java.io.FileOutputStream;
import java.io.IOException;

public class Arr{
	private int[] arr;
	private int capacity;
	private int vec_val;
	public Arr(int num){
		capacity = num;
		arr = new int[capacity];
		vec_val = 999999;
	}
	public void setVVal(int num){
		this.vec_val = num;
	}
	public int getVVal(){
		return vec_val;
	}
	public void print(){
		//System.out.print(vec_val+" ");
		for(int i = 0 ; i != capacity; ++i)
			System.out.print(arr[i]+" ");
		//System.out.println();
	}
	
	public void print(FileOutputStream os) throws IOException{
		
		for(int i = 0 ; i != capacity; ++i){
			Integer temp = new Integer(arr[i]);
			String x = temp.toString() + " ";
						os.write(x.getBytes());
		}	
	
}
	public int get(int index){
		return arr[index];
	}
	public void set(int index, int data){
		arr[index] = data;
	}
	public int getCapacity(){
		return capacity;
	}
	public int sum_element(){
		int result = 0;
		for(int i = 0 ; i != capacity; ++i)
			result += arr[i];
		return result;
		
	}
	//*****************************************************************************
	public int locate(int target){
		//target이 해당 arr[result]<=target인 result를 찾는다.
		//배열에 2,4,6,8이 있고 target이 7이라하자. 그러면 2를 출력한다.
		int result = 0;
		for(int i = 0 ; i != this.capacity; ++i){
			if(this.arr[i] > target){
				result = i;
				break;
			}
		}
		return result-1;
	}
	public boolean check(int index, int num){
		//arr[index]>=num이면 true
		return (this.arr[index] == num) || (this.arr[index] > num);
		
	}
	
	public void set_zero(){
		for(int i = 0 ; i != capacity; ++i)
			arr[i] = 0;
	}
	public void inc(int index){
		arr[index]++;
	}
	
	public void set(Arr other){
		for(int i = 0 ; i != capacity; ++i)
			arr[i] = other.get(i);
		
	}

}