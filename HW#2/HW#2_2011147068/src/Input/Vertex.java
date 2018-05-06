package Input;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
public class Vertex {
	private double x;
	private double y;
	public boolean is_same(Vertex p){
		return x == p.getX() && y == p.getY();
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public Vertex(){
		x= -1.0; y = -1.0;
	}
	public Vertex(double x, double y){
		this.x = x; this.y = y;
	}
	public void print(){
		System.out.printf("( %f , %f )", x, y );
	}
	public void print(FileOutputStream os) throws IOException{
		
//		System.out.printf("( %f , %f )", x, y );
		/*
		 * 
		 * Integer xi = new Integer(x);
		Integer yi = new Integer(y);
		String ans = xi.toString() + "  " + yi.toString() + "  ";
		
		os.write(ans.getBytes());
		 */
		DecimalFormat format = new DecimalFormat("#.#");
		String strx = format.format(x);
		String stry = format.format(y);

		/*
		String strx = String.format(" %.1f ", x);
		String stry = String.format(" %.1f ", y);
		*/
		String ans = " "+strx+ " "+ stry;
		
		os.write(ans.getBytes());
		
	}
	public double distance(Vertex p){//현재 점과 p사이의 거리를 return
		double disx = p.getX() - x;
		double disy = p.getY() - y;
		return Math.sqrt(Math.pow(disx,2) + Math.pow(disy, 2));
	}
	
}
