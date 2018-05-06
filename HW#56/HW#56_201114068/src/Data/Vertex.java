package Data;


public class Vertex {
	final boolean UP = true;
	
	private double x;
	private double y;
	private boolean flag;
	public Vertex(){
		this.setX(0);
		this.setY(0);
		flag= false;
	}
	public Vertex(double x, double y){
		this.setX(x);
		this.setY(y);
		flag = false;
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
	public void print(){
		System.out.println(String.valueOf(this.x)+ " " + String.valueOf(this.y)
);
	}
	public boolean getFlag() {
		return flag;
	}
	public void set_on_flag() {
		this.flag = true;
	}
	public void set_off_Flag() {
		this.flag = false;
	}
	
	

	
	
	
	

}
