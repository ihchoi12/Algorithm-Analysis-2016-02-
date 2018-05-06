package Arr;

public class Cal {
	
	static public Arr plus(Arr s1, Arr s2){
		if(s1.getCapacity() != s2.getCapacity())return null;
		Arr result = new Arr(s1.getCapacity());
		for(int i = 0 ; i != result.getCapacity(); ++i)
			result.set(i, s1.get(i) + s2.get(i));

		return result;
	}
	static public int inner_product(Arr s1, Arr s2){
		if(s1.getCapacity() != s2.getCapacity())return -Integer.MAX_VALUE;
		int result = 0;
		for(int i = 0 ; i != s1.getCapacity(); ++i)
			result += s1.get(i) * s2.get(i);
		return result;
	}
	
	static public Arr minus(Arr s1, Arr s2){
		if(s1.getCapacity() != s2.getCapacity())return null;
		Arr result = new Arr(s1.getCapacity());
		for(int i = 0 ; i != result.getCapacity(); ++i)
			result.set(i, s1.get(i) - s2.get(i));

		return result;
	}
	//*****************************************************************************
}
