package Run;
import Arr.*;
import DP.DP;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Arr test = new Arr(10);
		for(int i  = 0 ; i != 10 ; ++i)
		test.set(i, 2*i);
		test.print();
		System.out.print(test.locate(2));
	}

}
