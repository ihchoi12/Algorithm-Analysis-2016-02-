package Input;
import java.util.ArrayList;
import java.util.ListIterator;

public class InstanceList {
	private ArrayList<Instance> li;
	private int num;
	public InstanceList(){
		num = 0;
		li = new ArrayList<Instance>();
	}
	public void put(Instance a){
		li.add(a);
		
		++num;
	}
	public ArrayList<Instance> getData(){
		return li;
	}
	public void print(){
		ListIterator<Instance> i = li.listIterator();
		while(i.hasNext()){
			Instance t = i.next();
			t.print();
		}
	}
	
	public boolean is_Highest(Vertex i){
		double target = i.getY();
		ListIterator<Instance> iter = li.listIterator();
		while(iter.hasNext()){
			Instance x = iter.next();
			if(x.getPos().getY()> target) return false;
		}
		return true;
		
	}
	public Vertex find_highest(Vertex i){
		//i의 y값과 같지 않으면서 제일 큰 것을 찾기
		ListIterator<Instance> iter = li.listIterator();
		double max = -Double.MAX_VALUE;
		Vertex result = null;
		while(iter.hasNext()){
			Instance temp = iter.next();
			if(!Cal.isSame(i.getY(), temp.getPos().getY())
					&& Cal.isGreater(temp.getPos().getY(), max)){
				result = temp.getPos();
				max = temp.getPos().getY();
			}
		}
		return result;
	}
	public Vertex find_lowest(Vertex i){//i보다 더 높은 위치에 있지만 가장 낮은거
		ListIterator<Instance> iter = li.listIterator();
		double min = Double.MAX_VALUE;
		Vertex result = null;
		while(iter.hasNext()){
			Instance temp = iter.next();
			if(!Cal.isSame(i.getY(), temp.getPos().getY())
					&& Cal.isGreater(temp.getPos().getY(), i.getY())
					&& Cal.isLess(temp.getPos().getY(), min)){
				result = temp.getPos();
				min = temp.getPos().getY();
			}
		}
		return result;
	}
	public Vertex find_right(Vertex v){//v보다 더 오른쪽 에있는거 return
		ListIterator<Instance> iter = li.listIterator();
		double min = Double.MAX_VALUE;
		Vertex result = null;
		while(iter.hasNext()){
			Instance temp = iter.next();
			if(!Cal.isSame(v.getX(), temp.getPos().getX())
					&& Cal.isGreater(temp.getPos().getX(), v.getX())
					&& Cal.isLess(temp.getPos().getX(), min)){
				result = temp.getPos();
				min = temp.getPos().getX();
			}
		}
		return result;
	}
	
	public Vertex minDistance_imp(Vertex v, boolean r_a){//INstancelist의 점들과 v의 거리가 가장 가까운 것을 선택한다.
		//right면 true, acute면 false
		ListIterator<Instance> iter = li.listIterator();
		double min = Double.MAX_VALUE;
		Instance result = null;
		
		if(r_a){
			if(this.is_Highest(v))//v가 가장 높은 점인 경우, 그저 최고의 y 값을 찾으면 됨.
				return this.find_highest(v);
			else//v가 가장 높은 점이 아닌경우, v보다 크되 가장 작은 점을 찾으면 됨.
				return this.find_lowest(v);
		}
		else
			return find_right(v);
		
		
		
	}
		
		
}
	

	
	


