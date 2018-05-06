package Input;

public class Ztest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double x = Double.MAX_VALUE;
		double y = Double.MAX_VALUE ;
		Vertex a = new Vertex(0,0);
		Vertex b = new Vertex(0,10);
		Edge e = new Edge(a,b);
		Vertex c = new Vertex(0,5);
		
		System.out.println(e.is_in_Edge(c));
		
		

	}

}
