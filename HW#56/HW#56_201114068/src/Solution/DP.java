package Solution;

import java.util.Iterator;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;



import Data.*;


class Double_val{
	private double x;
	public Double_val(){
		Double x = new Double(0);
	}
	public double getX(){
		return x;
	}
	public void setX(double a){
		this.x = a;
	}
	
}
class DP_Node{
	private int start;
	private ArrayList<Integer> set;
	private ArrayList<Integer> path;
	private double result;
	public void switch_path(ArrayList<Integer> target){
		Iterator<Integer> i = target.iterator();
		path.clear();
		while(i.hasNext()){
			int temp = i.next();
			path.add(temp);
		}
	}
	public DP_Node(){
		this.start = -1;
		set = new ArrayList<Integer>();
		path = new ArrayList<Integer>();
		result = -1;
	}
	public DP_Node(int startpos, DP_Node other){
		this.start = startpos;
		set = new ArrayList<Integer>();
		
		Iterator<Integer> i = other.set.iterator();
		while(i.hasNext()){
			int temp = i.next();
			set.add(temp);
		}
		path = new ArrayList<Integer>();
		i = other.path.iterator();
		while(i.hasNext()){
			int temp = i.next();
			path.add(temp);
		}
		result = other.result;
	}
	
	public DP_Node(int _s, ArrayList<Integer> _set){
		start = _s;
		set = new ArrayList<Integer>();
		path = new ArrayList<Integer>();
		Iterator<Integer> i = _set.iterator();
		while(i.hasNext()){
			int temp = i.next();
			set.add(temp);
		}	
	}
	public DP_Node(int _s, ArrayList<Integer> _set, 
			ArrayList<Integer> _path){
		start = _s;
		set = new ArrayList<Integer>();
		path = new ArrayList<Integer>();
		Iterator<Integer> i = _set.iterator();
		while(i.hasNext()){
			int temp = i.next();
			set.add(temp);
		}	
		path = new ArrayList<Integer>();
		i = _path.iterator();
		while(i.hasNext()){
			int temp = i.next();
			path.add(temp);
		}
	}
	public DP_Node(int _s){
		start = _s;
		set = new ArrayList<Integer>();
		path = new ArrayList<Integer>();
		result = -1;
	}
	public int getStart(){
		return start;
	}
	public ArrayList<Integer> getSet(){
		return set;
	}
	public ArrayList<Integer> getPath(){
		return path;
	}
	public void print(){
		System.out.print("\nstart : " + start +"  , ");
		if(set == null){		
			System.out.print("{ ");		System.out.print(" }");

		}
		else {
			Iterator<Integer> i = set.iterator();
			System.out.print("{ ");
	
			while(i.hasNext()){
				int temp  = i.next().intValue();
				System.out.print(temp + " , ");
			}
			System.out.print(" }     ");
			i = path.iterator();
			
			while(i.hasNext()){
				int temp  = i.next().intValue();
				System.out.print(temp + " <- ");
			}
			System.out.print(" result : " + this.result);

			System.out.println();
	
		}
	
	}
	public void setResult(double ret){
		this.result = ret;
	}
	public double getResult(){
		return result;
	}
}
	
	
class Step{
	private int step_num;
	private ArrayList<DP_Node> step;
	public Step(){
		this.step_num = -1;
		step = null;
		
	}
	public Step(int setnum){
		this.step_num = setnum;
		step = new ArrayList<DP_Node>();
	}
	
	
	public void print(){
		System.out.println("\n Set의 개수 : " + step_num+ " ");
		for(int i = 0 ; i != step.size() ;++i){
			step.get(i).print();	
		}
	}
	public ArrayList<DP_Node> getStep(){
		return step;
	}	
	public void add(DP_Node node){
		this.step.add(node);
	}
	public void add_dpset(ArrayList<DP_Node> DP_set){
		Iterator<DP_Node> i = DP_set.iterator();
		while(i.hasNext()){
			DP_Node temp = i.next();
			this.step.add(temp);
		}
		
	}
}

public class DP extends Solution{
	private int nodenum; // 전체 node가 몇개인지를 나타내는 변
	private ArrayList<Integer> nodeset;
	private FileOutputStream os;
	private ArrayList<Integer> output;
	
	public DP(){
		nodenum = 0;
		output = new ArrayList<Integer>();
	}
	
	public DP(AdjTable table, VertexList vlist, FileOutputStream os) {
		// TODO Auto-generated constructor stub
		
		super(table, vlist, os);
		nodenum = vlist.getLength();
		nodeset = new ArrayList<Integer>();
		for(int i = 1; i != vlist.getLength(); ++i){
			nodeset.add(i);
		}
		this.os = os;
	}
	public ArrayList<Integer> getNodeset(){
		return nodeset;
	}
	public long solve() throws IOException{
		long start = System.currentTimeMillis();
		//System.out.print("DP");
		/*
		 * body
		 */
		DP_Node out = _solve();

		long end = System.currentTimeMillis();
		out.print();
		printFile(convert2(out.getPath()), out.getResult(), os);
		return end - start;
	}

	public ArrayList<Integer> convert2(ArrayList<Integer> i){
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for(int x = 0 ; x != i.size(); ++x){
			ret.add(i.get(x)+1);
		}
		return ret;
	}
	public DP_Node _solve(){
		Step zero = make_init(); // [2, {}], [3, {}], [4, {}] 를 만든다.
		Step prev = null;
		Step current = zero;
		for(int set_size = 0; set_size != nodenum-1; ++set_size ){
			prev = current;
			current = make(set_size, current);
		}
		DP_Node ret = this.evaluate_last(prev);
		super.solpath = ret.getPath();
		super.val= ret.getResult();
		return ret;
	}
	
	public DP_Node evaluate_last(Step current){
		Iterator<DP_Node> a = current.getStep().iterator();
		//f(0, S) = min(c01 + f(1, {s-1});
		//
		
		int last = 0;
		double min = Double.MAX_VALUE;
		for(int i= 0 ; i != current.getStep().size(); ++i){
			Double v = table.getVal(0, current.getStep().get(i).getStart()) + current.getStep().get(i).getResult();
			if(v < min){
				min = v;
				last = i;
			}
			
		}
		current.getStep().get(last).setResult(min);
		current.getStep().get(last).getPath().add(0);
		return current.getStep().get(last);
		
	}
	public Step make(int size, Step prev){//size는 step에서의 |S|
		//prev는 [2, {3}], [2, {4}][3,{2}], [3, {4}] 등의 값이 들어가 있을 것
		Step ret = new Step(size+1);
		ArrayList<DP_Node> DP_set = make_DPSET(prev); // (startpos, {S}) 부분을 채워주는 함수
		ret.add_dpset(DP_set);// i번째에 들어갈 dp_set을 ret에 넣음
		
		//아마 끝나면 [2, {3,4}], [3, {2,4}, [4, {2,3}]]이 들어가 있을 것이다.
		return ret;
	}


	public ArrayList<DP_Node> make_DPSET(Step prev){
		ArrayList<ArrayList<Integer> > intlist = new ArrayList<ArrayList<Integer> >();
		ArrayList<DP_Node> ret = new ArrayList<DP_Node>();
		int prevstartpos = -1;
		int currentstartpos = -1;
		for(int i = 0 ; i != prev.getStep().size(); ++ i){
			DP_Node target = prev.getStep().get(i);
			currentstartpos = prev.getStep().get(i).getStart();
			if(currentstartpos != prevstartpos){
				intlist.clear();
				prevstartpos = currentstartpos;
			}

			ArrayList<Integer> candidate = this.difference(this.nodeset, target.getSet(), 
				target.getStart()); 
			for(int addint = 0; addint != candidate.size(); ++addint){
				ArrayList<Integer> temp = copy_arr(target.getSet());
				add_asc(temp, candidate.get(addint));
				if(is_in(intlist, temp)) continue;
				else intlist.add(temp);
				DP_Node next = new DP_Node(target.getStart(), temp);		
				fill_path_result(next, prev);// prev를 통해 next의 path와 result를 채우겠다는거
				ret.add(next);
			}
		}
		return ret;	
	}

	public void fill_path_result(DP_Node next, Step prev){
		Iterator<Integer> iter = next.getSet().iterator();
		//f(i, {S}) = min(cij + f(j, {S}-j)
		int i = next.getStart();
		double min = Double.MAX_VALUE;
		DP_Node ret = new DP_Node();
		while(iter.hasNext()){
			int j = iter.next();
			DP_Node a = get_PrevDP_Node(j, difference2(next.getSet(), j), prev);
			double x = table.getVal(i, j) + a.getResult();
			if(x < min){
				min = x;
				ret = a;
			}
		}
		next.switch_path(ret.getPath());
		next.getPath().add(next.getStart());
		next.setResult(min);


	}

	// **********************************************************************************************************************88
	public Step make_init(){
		Step ret = new Step(0);
		for(int source = 1; source != this.nodenum; ++source){		
			DP_Node temp = new DP_Node(source);
				ArrayList<Integer> temp_path = temp.getPath();
				temp_path.add(0);
				temp_path.add(source);
				temp.setResult(table.getVal(source, 0));
				ret.add(temp);
			}
		return ret;
	}
	
	public DP_Node get_PrevDP_Node(int startpos, ArrayList<Integer> set, Step prev){
		//prev에서 (startpos, {set})인 dp_node를 가져오는 역할.
		int chunk = prev.getStep().size()/(this.nodenum-1);
		int index  = chunk*(startpos-1);
		int sol = 0;
		for(int i = index; index != index + chunk; ++i){
			if(this.is_same(prev.getStep().get(i).getSet(), set)){
				sol = i;
				break;
			}			
		}
		return prev.getStep().get(sol);
	}
	public void add_asc(ArrayList<Integer> arr, int x){// 오름차순 유지 시키며 추가한다.
		Iterator<Integer> i = arr.iterator();
		//위치를 찾고 거기에 넣는다.
		int pos = -1;
		for(int j = 0 ; j != arr.size(); ++j){
			if(x<arr.get(j)){
				pos = j; break;
			}
		}
		if(pos == -1) pos = arr.size();
		//pos 자리에 넣으면 되는거
		arr.add(pos, x);
		
	}
	public ArrayList<Integer> difference2(ArrayList<Integer> a, int x){
		ArrayList<Integer>	ret = new ArrayList<Integer>();
		Iterator<Integer> i = a.iterator();
		
		while(i.hasNext()){
			Integer temp = i.next();
			if(temp != x) ret.add(temp);
		}
		return ret;
	}
	public ArrayList<Integer> difference(ArrayList<Integer> a, ArrayList<Integer> b, int x){
		ArrayList<Integer>	ret = new ArrayList<Integer>();
		Iterator<Integer> i = a.iterator();
		
		while(i.hasNext()){
			Integer temp = i.next();
			if(!b.contains(temp) && temp != x) ret.add(temp);
		}
		return ret;
	}
	public ArrayList<Integer> copy_arr(ArrayList<Integer> source){
		// source의 내용을 target으로 복사
		ArrayList<Integer> temp = new ArrayList<Integer>();
		Iterator<Integer> i = source.iterator();
		while(i.hasNext()){
			int  x= i.next();
			temp.add(x);
		}
		return temp;
		
	}
	public boolean is_same(ArrayList<Integer> s1, ArrayList<Integer> s2){
		Iterator<Integer> i = s1.iterator();
		Iterator<Integer> j = s2.iterator();
		while(i.hasNext()){
			int x = i.next();
			int y = j.next();
			if(x != y) return false;
		}
		return true;
		
	}
	public boolean is_in(ArrayList<ArrayList<Integer> > source, ArrayList<Integer> target){
		Iterator<ArrayList<Integer> > i = source.iterator();
		while(i.hasNext()){
			ArrayList<Integer> temp = i.next();
			if(is_same(temp, target)) return true;
		}
		return false;
	}
}



