package Input;

public class Edge {
	//입력 순서대로, 먼저 들어온 것이 head, 나중에 들어온 것이 tail
	private Vertex head;
	private Vertex tail;
	//input.txt에서 받은 순서
	private int order;
	

	public Vertex bigXPos(){
		if(head.getX()>tail.getX())return head;
		else return tail;
	}
	public Vertex smallXPos(){
		if(head.getX()<tail.getX())return head;
		else return tail;
	}
	public Vertex bigYPos(){
		if(head.getY()>tail.getY())return head;
		else return tail;	
	}
	public Vertex smallYPos(){
		if(head.getY()>tail.getY())return tail;
		else return head;	
	}
	
	public Edge(Vertex a, Vertex b){
		head = a; tail = b;
	}
	public Edge(Vertex a, Vertex b, int o){
		head = a; tail = b;order = o;
	}
	public Edge(){
		head = null; tail = null;order = -1;
	}
	public boolean is_right_angle(){
		return head.getX() == tail.getX();
	}
	public void print(){
		head.print();
		tail.print();
		System.out.println(order);
	}
	public Vertex getHead(){
		return head;
	}
	public Vertex getTail(){
		return tail;
	}
	public int getOrder(){
		return order;
	}
	public boolean is_inf(){
		return this.getSlope() == Double.MAX_VALUE;
	}
	public double getSlope(){
		if(Cal.isSame(head.getX(), tail.getX())) return Double.MAX_VALUE;
		return (head.getY() - tail.getY())/(head.getX()-tail.getX());
	}
	public double getValue(double x){
		// edge를 가지고 직선의 방정식 y = ax +b를 나타냈을 때, y의 값
		return (this.getSlope()*x) + tail.getY() - this.getSlope()*tail.getX();		
	}
	public boolean is_in_Edge_include_headtail(Vertex p){
		return ((is_in_Edge(p)||this.getHead().is_same(p)|| this.getTail().is_same(p)));
	}
	public boolean is_in_Edge(Vertex p){
		double minx, maxx;
		double miny, maxy;
		if(head.getX() == tail.getX()){
			double bigy, smally;
			if(head.getY()> tail.getY()){
				bigy = head.getY();
				smally = tail.getY();
			}
			else{
				bigy = tail.getY();
				smally = head.getY();
			}
			if((p.getY()>smally ) && (p.getY() < bigy)) return true;
			else return false;
			
			
		}
		if(head.getX() > tail.getX()){
			maxx = head.getX(); 
			minx = tail.getX();
		}
		else{
			maxx= tail.getX(); 
			minx = head.getX();
		}
		if(head.getY() > tail.getY()){
			maxy = head.getY(); 
			miny = tail.getY();
		}
		else{
			maxy= tail.getY(); 
			miny = head.getY();
		}
		
		
		//double의 계산이므로 오차를 감안해야함. 너무 정교한 경우, 원하지 않는 결과가 나타날 수 있음.
		if((p.getX() < maxx && minx<p.getX()) 
				&& 
				Math.abs((p.getY() - (this.getSlope() * p.getX()+this.getValue(0)))) < 0.01 ) 
			return true;
		
		return false;
		
		
		

		
	}
	
}
