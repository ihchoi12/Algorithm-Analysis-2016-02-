package Input;

public class Cal {
	final static double D = 0.01;
	public static boolean isSame(double a, double b){
		return Math.abs(a - b) < D;
	}
	public static boolean isGreater(double a, double b){
		return a - b >= D;
	}
	public static boolean isLess(double a, double b){
		return a - b < -D;
	}
}
