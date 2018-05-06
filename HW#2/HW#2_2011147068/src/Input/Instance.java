package Input;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
public class Instance {
	private Vertex pos;
	private ArrayList<Edge> inter; // (x,y)를 지나가는 모든 선분들을 가지고 있음.
	public Instance(){
		pos = null;

		inter = null;
	}
	
	
	public Instance(Vertex pos){
		this.pos = pos;
		
		this.inter = new ArrayList<Edge>();
		
	}
	
	public void put_EdgeToEdgeSet(Edge e){
		ListIterator<Edge> i = this.inter.listIterator();
		boolean flag = false;
		while(i.hasNext()){
			if(i.next() == e){
				flag = true;
				break;
			}
		}
		if(!flag) inter.add(e);
		
		
	}
	public Vertex getPos(){
		return pos;
	}
	
	public ArrayList<Edge> getInter(){
		return inter;
	}
	public void print(){
		System.out.print("Pos : ");
		pos.print();
		System.out.print("\nbefore : ");

		
		System.out.print("\nEdgeList : ");

		Iterator<Edge> i = inter.iterator();
		while(i.hasNext()){
			Edge e = i.next();
			e.print();
		}
	}
	public boolean can_go_Right(){
		Iterator<Edge> i = inter.iterator();
		
		while(i.hasNext()){
			Edge iter = i.next();
			if(iter.getSlope() > 0) return true;
		}

		return false;
	}
	public Edge get_properEdge(Vertex v){
		//v보다 더 오른쪽에 있는선분만.
		Iterator<Edge> i = inter.iterator();
		Edge result = null;
		double max = -Double.MAX_VALUE;
		while(i.hasNext()){
			Edge iter = i.next();
			if(iter.bigXPos().getX() != v.getX()){
				if(Cal.isGreater(iter.getSlope(), max)){
				result = iter;
				max = iter.getSlope();
				}
			}
		}
		return result;
	}
	public Edge get_properEdge_Right(Vertex v){
		//v를 직각으로 가지고 있는 경우, 가장 적절한 것은 가장 y값이 높은 직각을 고르는것
		Iterator<Edge> i = inter.iterator();
		Edge result = null;
		double max = -Double.MAX_VALUE;
		while(i.hasNext()){
			Edge iter = i.next();
			if(iter.bigXPos().getX() == v.getX()){
				if(Cal.isSame(iter.bigXPos().getY(), v.getY()))continue;
				if(Cal.isGreater(iter.bigYPos().getY(), max)){
				result = iter;
				max = iter.bigYPos().getY();
				}
			}
		}
		return result;
	}
	public Edge get_properEdge_imp(Vertex v, Edge before){
		Edge acute = this.get_properEdge(v);
		Edge right = this.get_properEdge_Right(v);
		
		if(right == null) return acute;//예각만 있는 경우
		if(acute == null) return right;//직각만 있는경우
		else{//둘다 있는 경우, 직선의 y값이 더 위에 있으면 직선이 되고 직선이 점밑에 있으면 예각일떄를 고르는 것과 같다.
			if(before.is_right_angle()) return acute;
			if(right.bigYPos().getY()>v.getY()) return right;
			else return acute;
		}
		
	
	}
	

}
