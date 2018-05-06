package Input;
import java.util.*;
public class VertexSet {
	final double D = 0.001;
	private Vertex[] arr;
	private int capacity;
	private int num;
	public VertexSet(){
		arr = null;
		capacity = 0; num = 0;
	}
	public VertexSet(int n){
		capacity = n;
		arr = new Vertex[capacity];
		num = 0;
	}
	public void put(int index, Vertex t){
		Vertex temp;
		
		arr[index] = t;
		++num;
	}
	public void print(){
		for(int i = 0; i != num ; ++i){
			arr[i].print();
			System.out.println();
		}
	}
	public int getCapacity(){
		return capacity;
	}
	public Vertex getVertex(int index){
		return arr[index];
	}
	public Vertex vertex_in_arr(Vertex v){
		//같은것이 존재한다면, arr[i]를 return
		//다르다면 null을 return
		for(int i = 0; i != num ; ++i){
			if(v.getX() == arr[i].getX() && v.getY() == arr[i].getY())
				return arr[i];
		}
		return null;
	}


	public Vertex get_MinX_0(){
		int index = 0;
		double minx = Integer.MAX_VALUE;
		for(int i = 0 ; i != capacity; ++i){
			if(Cal.isSame(arr[i].getY(), 0)){//arr[i].y가 0과 같은 점들 중에
				if(Cal.isGreater(minx, arr[i].getX())){//arr[i].x가 minx 보다 작다면 그점이 index를 기억한다.
					minx = arr[i].getX();
					index = i;
				}			
			}
		}
		return arr[index];
	}
	public Vertex get_MinX_0(double t){
		//(t,0)과 크거나 같은 점에 대해서 
		int index = 0;
		double minx = Integer.MAX_VALUE;
		for(int i = 0 ; i != capacity; ++i){
			if(Cal.isSame(arr[i].getY(), 0)){//arr[i].y가 0과 같은 점들 중에
				if(Cal.isLess(arr[i].getX(), t)||Cal.isSame(arr[i].getX(), t))continue;//t보다 작은것은 바로 다음으로 넘어간다.
				if(Cal.isGreater(minx, arr[i].getX())){//arr[i].x가 minx 보다 작다면 그점이 index를 기억한다.
					minx = arr[i].getX();
					index = i;
				}			
			}
		}
		return arr[index];
	}
	public Vertex get_MaxX_0(){
		int index = 0;
		double maxx = -1;
		for(int i = 0 ; i != capacity; ++i){
			if(Cal.isSame(arr[i].getY(), 0)){//arr[i].y가 0과 같은 점들 중에
				if(Cal.isLess(maxx, arr[i].getX())){//arr[i].x가 maxx 보다 크면 그점의 index를 기억한다.
					maxx = arr[i].getX();
					index = i;
				}			
			}
		}
		return arr[index];
	}
	

}

