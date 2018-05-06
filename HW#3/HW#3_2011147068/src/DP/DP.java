package DP;
import Arr.*;

public class DP {
	private Arr[] dataset;
	private int capacity;
	private Arr coin_type;
	private Arr range;

	public DP(int capacity, Arr coin_Type, Arr range){
		this.capacity = capacity;
		dataset = new Arr[this.capacity];
		this.range = range;
		this.coin_type = coin_Type;
		for(int i = 0 ; i != this.capacity; ++i)
			dataset[i] = new Arr(range.getCapacity());
	}
	public int getCapacity(){
		return capacity;
	}
	public void print(int index){
		System.out.print(" index는 " + index);dataset[index].print();
	}
	public void print(){
		for(int i = 0 ; i != capacity; ++i)
			print(i);
	}
	public void makeDP(){
		//range, coin_Type을 고려하며 
		dataset[0].setVVal(0);

		for(int coin_index = 0; coin_index != coin_type.getCapacity(); ++coin_index){
			makeDP(coin_index);	
		//	this.print();
			//coin_index가 n이면 coin_type[0]부터 coin_type[n-1]까지 써서 최적해를 구하겠다.
		}

	}

	public void makeDP(int coin_index){
		for(int money = this.coin_type.get(coin_index); money != capacity; ++money){
			if(this.coin_type.get(coin_index) < capacity || this.coin_type.get(coin_index) == capacity){
				int min = Integer.MAX_VALUE;
				int min_coin_index=0;
				Arr min_before=null;
				int check = -1;
				for(int j = 0; j != coin_index+1; ++j){
					Arr before = dataset[money - coin_type.get(j)];
					
					if(dataset[money].getVVal() > before.getVVal()+1){ // 처음엔 dataset의 값들을 MAX(999999)로 초기화, 그리고 그걸 계속 더 작은값으로 갱신해나감
						if(range.get(j) > before.get(j)){//동전의 개수제한에 위배되는지 검사. 통과하면 아직 그 동전을 사용할 수 있음을 의미.
							if(min>before.getVVal()+1)//위의 조건들을 만족하는 경우중에 가장 최적의 해를 찾아가야 하므로 min을 찾는다.
							{
								min =before.getVVal()+1;//최소값 기억
								min_coin_index = j;//최소값이 나온 인덱스 기억
								min_before = before;//최소값이 나온 dataset기억
								check=1;// 한번이라도 min값이 나온경우에 값을 갱신해준다.
							}
							

						}
					}
					
				}
				if(check!=-1) // min값이 갱신되었음.
				{
					dataset[money].setVVal(min); // val값 갱신
					dataset[money].set(min_before);//Coin vector을 이전 vector와 같게 만들어준 후
					dataset[money].inc(min_coin_index);//이번에 사용한 coin의 개수를 1 더해준다.
				}
			}
			
			
			
		}
		

	}
	
	public Arr get(int index){
		return dataset[index];
	}
	//*****************************************************************************
	public void makeDP_change(){
		//range, coin_Type을 고려하며 
		dataset[0].setVVal(0);

		for(int coin_index = 0; coin_index != coin_type.getCapacity(); ++coin_index){
			makeDP_change(coin_index);	
		//	this.print();
			//coin_index가 n이면 coin_type[0]부터 coin_type[n-1]까지 써서 최적해를 구하겠다.
		}

	}

	public void makeDP_change(int coin_index){//위의 makeDP와 전체적인 구조는 똑같지만 동전 개수에대한 검사를 따로 진행하지 않는다.
		for(int money = this.coin_type.get(coin_index); money != capacity; ++money){
			if(this.coin_type.get(coin_index) < capacity || this.coin_type.get(coin_index) == capacity){
					Arr before = dataset[money - coin_type.get(coin_index)];
					if(dataset[money].getVVal() > before.getVVal()+1){
						if(range.get(coin_index) > before.get(coin_index)){
							dataset[money].setVVal(before.getVVal()+1);
							dataset[money].set(before);
							dataset[money].inc(coin_index);
						}
					}
			}
		}
	}
}
