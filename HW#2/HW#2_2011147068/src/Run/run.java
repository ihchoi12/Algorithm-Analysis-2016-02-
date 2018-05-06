package Run;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import Input.*;
import Output.Result;
class Traverse{
	private VertexSet vs;private EdgeSet es;private InstanceSet is;
	private EdgeInsTable EITable;private Result out;
	private Vertex start;private Vertex terminate;
	private Edge before;
	public Traverse(){
		vs= null; es = null; is = null; 
		EITable = null;out = null;
		start = null; terminate = null;before = null;
	}
	public Traverse(VertexSet vs, EdgeSet es, InstanceSet is,  
			EdgeInsTable eit, Result out){
		this.vs = vs; this.es = es; this.is = is;
		this.EITable = eit; this.out = out;
		start = vs.get_MinX_0();
		terminate = vs.get_MaxX_0();
		before = null;
	}
	
	public void action(){
		Vertex iter = this.start;		
		while(true){
			
			
			iter = action_iter(iter);//한도형이 그려지고, 그것의 end point를 출력하는 식이 되야됨.
			
			//도형의 마지막 점이 output에 들어간다.
			if(iter == terminate){// 출력한 것이 terminate와 같다면 종료를 한다.
				out.put(iter);
				break;
			}
			else{//아니라면 새로운 점을 찾는다.
				//바로 연결이 안된점이라면 넘어가기
				if(!is.getInstance(iter).can_go_Right()){
					out.put(iter);
					iter = vs.get_MinX_0(iter.getX());
				}
				//연결이 되어있는 거면 계속 이어가, 근데 두번 들어가면 안되니 넣지는않아.
					
				
			}
	
			
		}

		
	}
	
	public Vertex action_iter(Vertex iter){//start 점이 들어오면 이점으로부터 쭉 이어지는 도형의 end가 출력된다.
		//last점을 가지않고 return
		Instance step;
		do{
			
			//iter라는 점을 가지는 Instance로 접근
			step = is.getInstance(iter);
			//step.print();
			//접근한 Instance에서 가장 적절한 Edge를 선택
			Edge properEdge = step.get_properEdge_imp(iter, before);
			//접근한 Edge에서 iter와 가장 가까운 점을 선택
			
			
			if(properEdge ==null)//마지막이어서
				return iter;
			
					
			if(before == null || 
					before.getSlope() != properEdge.getSlope()) out.put(iter);
			
			before = properEdge;
			
			InstanceList li = EITable.getIList(properEdge.getOrder());
			boolean flag_a_r = properEdge.is_right_angle();
			iter = li.minDistance_imp(iter, flag_a_r);
			

			
		}while(iter.getY() != 0);
		return iter;
	}
}
public class run {
	
	public static void make_data_about_intersection(VertexSet vs,
			InstanceSet InstSet, EdgeSet es, EdgeInsTable EItable){
		for(int i = 0 ; i != es.getCapacity(); ++i){
			for(int j = i+1; j != es.getCapacity(); ++j){
				Edge e1 = es.getEdge(i);Edge e2 = es.getEdge(j);
				Vertex inter = make_intersection_imp(e1, e2);//e1,e2교점이 만들어졌을 때, vertex안에 있는지를 봐야 될거아냐.
				
				boolean flag = false;
				if(inter != null){
					// 교점이 생긴 거
					//기존에 inter을 가진 Instance가 있따면 다시 INstance를 만들면 안되니 검사를 해줘야됨.
					Vertex key = vs.vertex_in_arr(inter);
					
					if(key == null){
						//vertexset안에 없는 거.
						Instance temp= InstSet.getInstance(inter);
						//vertex set에서 inter와 같은 값을 가지는 vertex가 있는지를 찾는식으로 해야됨.
						if(temp == null){
							temp = new Instance(inter);
							flag = true;
						}
						temp.put_EdgeToEdgeSet(e1);temp.put_EdgeToEdgeSet(e2);
						if(flag)InstSet.put(inter, temp);		
						
						EItable.put(e1.getOrder(), temp);
						EItable.put(e2.getOrder(), temp);

					}
					else{
						//vertexset안에 이미 있는 거야. intersection은 기존 값 다넣은뒤에 비교를 할 꺼니깐.
						//이미 만들어져있는 Instance에 대해서 접근을 할 것이니깐, edge만을 추가해주면됨.
						Instance temp = InstSet.getInstance(key);
						
						if(e1.is_in_Edge(key))//key가 e1의 중간에 있따면
							temp.put_EdgeToEdgeSet(e1);
						if(e2.is_in_Edge(key))
							temp.put_EdgeToEdgeSet(e2);
					}	
				}
			}
		}
		
	}
	public static Vertex make_intersection_imp(Edge e1, Edge e2){
		if(!e1.is_inf()&& !e2.is_inf())
			return make_intersection(e1, e2);
		else 
			return make_intersection_right(e1, e2);
		
	}
	public static Vertex make_intersection_right(Edge e1, Edge e2){
		if(e1.is_inf() && !e2.is_inf()){
			//e1이 직선이고 e2가 그냥 선분일때
			//만나지 않는다면 null을 return
			double x = e1.getHead().getX();
			double min = e2.smallXPos().getX(); double max = e2.bigXPos().getX();
			if(x >= min && x <=max) return new Vertex(x, e2.getValue(x));
			else return null;			
		}
		else if(!e1.is_inf() && e2.is_inf()){
			double x = e2.getHead().getX();
			double min = e1.smallXPos().getX(); double max = e1.bigXPos().getX();
			if(x >= min && x <=max) return new Vertex(x, e1.getValue(x));
			else return null;
		}
		else{
			//만나지 않는다면 null return
			if(!Cal.isSame(e1.getHead().getX(), e2.getHead().getX()))return null;
			else{
				if(e1.bigYPos() == e2.bigYPos())return null;
				else{
					if(e1.bigYPos().getY()>e2.bigYPos().getY())
						return new Vertex(e1.getHead().getX(), e2.bigYPos().getY());
					else
						return new Vertex(e1.getHead().getX(), e1.bigYPos().getY());
				}
			}
			//만난다면.. 1)두선분 길이 같을떄와 다를때를 비교
			
		}

	}
	public static Vertex make_intersection(Edge e1, Edge e2){
		//e1, e2의 각 head와 tail점이 아닌 교점을 출력
		//filter1, 두 선의 기울기가 같다면 null
		//filter2, 교점이 있더라고, e1의 범위내에 없다면 out
		
		if(e1.getSlope() == e2.getSlope()){//예각일 때의 경우
			Vertex u_min = e1.smallXPos(); Vertex u_max = e1.bigXPos();
			Vertex v_min = e2.smallXPos(); Vertex v_max = e2.bigXPos();
			//완전 교점이 없이 동떨어진 경우, 그냥 null
			//네점이 다 똑같이 있는 경우
			if(e1.getValue(0) != e2.getValue(0))
				return null;
			else{
				
				if(e1 == e2) return null;
				
				//x값작은 두개가 같거나 x가 큰 두개가 같거나
				if(u_min == v_min){
					if(Cal.isGreater(u_max.getX() ,v_max.getX())) // u가 더 큰경우
							return new Vertex(v_max.getX(), v_max.getY());
					else
						return new Vertex(u_max.getX(), u_max.getY());
				}
				else{// v_max == u_max인 경우
					if(!Cal.isGreater(u_min.getX() ,v_min.getX())) // v가 더 큰경우
						return new Vertex(v_min.getX(), v_min.getY());
				else
					return new Vertex(u_min.getX(), u_min.getY());
				}
			}
		}
		double slope1 = e1.getSlope(); double displacement1 = e1.getValue(0);
		double slope2 = e2.getSlope(); double displacement2 = e2.getValue(0);
		double insx = -(displacement1 - displacement2)/(slope1 - slope2);
		double insy = (slope1*displacement2 - displacement1*slope2)/(slope1 - slope2);

		
		
		Vertex result = new Vertex(insx, insy);			
		if(e1.is_in_Edge_include_headtail(result)&& e2.is_in_Edge_include_headtail(result))
			return result;
		return null;
	}
	public static void make_VertexSet(Scanner s, VertexSet v, int n){
		//VertexSet은 같은 값에 대해 같은 Vertex 객체를 저장해야 된다.
		int index = 0;
		
		while(index != n){
			//새로운 포인트를 만든다
			Vertex left = new Vertex(
					s.nextDouble(), s.nextDouble() );
			//기존에 있는거에서 같은 값이 있는지를 찾아본다.
			Vertex temp = v.vertex_in_arr(left);
			if(temp == null)v.put(index++, left);
			else v.put(index++, temp);
			
			Vertex top = new Vertex(
					s.nextDouble(), s.nextDouble());
			Vertex temp1 = v.vertex_in_arr(top);
			if(temp1 == null)v.put(index++, top);
			else v.put(index++, temp1);

			Vertex right = new Vertex(
					s.nextDouble(), s.nextDouble());
			Vertex temp2 = v.vertex_in_arr(right);
			if(temp2 == null)v.put(index++, right);
			else v.put(index++, temp2);
			
		}
		
	}

	
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//Scanner s = new Scanner(new File("input.txt"));
		Scanner s = new Scanner(new File("C:\\hw2\\input.txt"));
		FileOutputStream os = new FileOutputStream("C:\\hw2\\2011147068.txt");
		//FileOutputStream os = new FileOutputStream("2011147068.txt");
		
		int casenum = s.nextInt();
		
		for(int i = 0 ; i != casenum; ++i){
			int linenum = s.nextInt();
			//linenum*2 만큼 edge는 생길 것.
			//Vertex는 Set이어야 된다. 
			
			VertexSet vs = new VertexSet(3*linenum);
			EdgeSet es = new EdgeSet(2*linenum);
			InstanceSet InstSet = new InstanceSet();
			EdgeInsTable EItable =  new EdgeInsTable(2*linenum);
			Result out = new Result();
			int edgeindex = 0;
			//VertexSet을 만들어야됨. hashMap을 쓰기 위해선, 같은 Vertex객체가 접근되야되기 때문에
			make_VertexSet(s, vs, 3*linenum);
			//vs.print();
			
			//VertexSet을 토대로 es, InstaceSet, EdgeInsTable을 만들어 줘야된다.
			for(int iter = 0; iter != 3*linenum; iter +=3){
				Edge left_top = new Edge(vs.getVertex(iter), vs.getVertex(iter+1), edgeindex);
				es.put(edgeindex++, left_top);
				Edge top_right = new Edge(vs.getVertex(iter+1), vs.getVertex(iter+2), edgeindex);
				es.put(edgeindex++, top_right);
				
				//vs.getVertex(iter)가 이전에 있다면 새로 만들지 않고 EdgeSet만을 추가한다. 이 때 flag를 사용한다.
				boolean flag_0 = false;
				Instance temp0 = InstSet.getInstance(vs.getVertex(iter));
				if(temp0 ==null){//기존에 없는 것이라면
					temp0 = new Instance(vs.getVertex(iter));
					flag_0 = true;
				}
				temp0.put_EdgeToEdgeSet(left_top);
				EItable.put(left_top.getOrder(), temp0);//EITable의 선분 left-top위 의 점을 넣는다, 이때 넣는 다면 양쪽의 점들을 다 넣어야된다.
				if(flag_0)InstSet.put(vs.getVertex(iter), temp0);//iter번째 vertex를 index로 접근하여 vertex를 data로 하는 instance를 넣는다.
				
				boolean flag_1 = false;
				Instance temp1 = InstSet.getInstance(vs.getVertex(iter+1));
				if(temp1 ==null){
					temp1 = new Instance(vs.getVertex(iter+1));
					flag_1 = true;
				}
				temp1.put_EdgeToEdgeSet(left_top);
				temp1.put_EdgeToEdgeSet(top_right);
				EItable.put(left_top.getOrder(), temp1);
				EItable.put(top_right.getOrder(), temp1);
				if(flag_1)InstSet.put(vs.getVertex(iter+1), temp1);

				
				boolean flag_2 = false;
				Instance temp2 = InstSet.getInstance(vs.getVertex(iter+2));
				if(temp2 ==null){
					temp2 = new Instance(vs.getVertex(iter+2));
					flag_2 = true;
				}
				temp2.put_EdgeToEdgeSet(top_right);
				EItable.put(top_right.getOrder(), temp2);
				if(flag_2) InstSet.put(vs.getVertex(iter+2), temp2);


				
			}

			//교점들에 대한 InstSet, EdgeSet, EItable을 만들어준다.
			make_data_about_intersection(vs, InstSet, es, EItable);
			//traverse를 시
			Traverse traverse = new Traverse(vs, es, InstSet, EItable, out);
			traverse.action();
			//out.print();
			out.print(os);
		}
		

	}

}
