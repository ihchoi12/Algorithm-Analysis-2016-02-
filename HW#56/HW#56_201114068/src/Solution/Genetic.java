package Solution;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import Data.AdjTable;
import Data.VertexList;


class Chromo{
	private Vector<Integer> chromo;
	private double fit;
	public int getChromosize(){
		return chromo.size();
	}
	public boolean is_in_it(int k){
		for(int i = 0 ; i != chromo.size(); ++i){
			if(chromo.get(i) == k) return true;
		}
		return false;
		
	}
	public Vector<Integer> getChromo(){
		return chromo;
	}
	public Chromo(){
		chromo = new Vector<Integer>();
		fit = 0;
	}
	public Chromo(Chromo other){
		chromo = new Vector<Integer>();
		fit = other.fit;
		for(int i = 0 ; i != other.getChromosize(); ++i){
			chromo.add(other.get(i));
		}

		
	}
	public int get(int index){
		return chromo.get(index);
	}
	public double getfit(){
		return fit;
	}
	public void setfit(double fit){
		this.fit = fit;
	}
	public void set_Chromo(int index, Integer NODEID){
		chromo.add(index, NODEID);
	}
	public void set_Chromo_append(Integer NODEID){
		chromo.add(NODEID);
	}
	public void update_Chromo(int index, int i){
		this.chromo.set(index, i);
	}
	public void print(){
		
		for(int i = 0 ; i != chromo.size(); ++i)
			System.out.printf("%d ", chromo.get(i).intValue());
		
		System.out.printf(" FIT VALUE : %f ", this.fit);
		
		System.out.println();
	}
}





class Generation{
	private Vector<Chromo> population;
	public Chromo get_best(){
		double min = Double.MAX_VALUE;
		int first = 0 ;
		for(int i = 0 ; i != population.size(); ++i){
			if(min > population.get(i).getfit()){
				min = population.get(i).getfit();
				first = i;				
			}
		}
		return population.get(first);
		
	}
	public void switch_Population(Vector<Chromo> target){
		this.population = target;
	}
	public void set(int i, Chromo target){
		population.set(i, target);
	}
	public Generation(){
		population = new Vector<Chromo>();
	}
	public Generation(int capacity){
		population = new Vector<Chromo>();
		population.setSize(capacity);
	}
	public Vector<Chromo> getPopulation(){
		return this.population;
	}
	public void set_Generation(Chromo NODEID){
		population.add( NODEID);
	}
	public void add_Generation(Chromo NODEID){
		population.add( NODEID);
	}
	public void change_Generation(int i, Chromo target){ // i번째 chromo를 target으로 교체한다
		population.set(i, target);
	}
	public int length_p(){
		return population.size();
	}
	public void print(){		
		System.out.println("*****PRINT GENERATION*****");

		for(int i = 0 ; i != population.size(); ++i)
			population.get(i).print();
		
	}
	public Chromo get(int index){
		return population.get(index);
	}
	public double get_sum_of_reversefit(){
		double ret = 0;
		for(int i = 0 ; i != population.size(); ++i){
			ret += 1/population.get(i).getfit();
		}
		return ret;
	}
}
public class Genetic extends Solution {
	/*
	 * 
	 * Genetic algorithm with Pc =1.0, Pm =1/the input size, and the binary
		tournament selection; choose one crossover operation from those discussed
		in class. 
		PC : crossover rate
		Pm : mutation rate
	 */
	
	private static int max_iter;
	private static double PM ;
	private static double PC = 1;
	private static int candi_num = 2;//한 generation에서 pick하는 string(path) 수
	private static int repeat_num = 10;
	private Generation population;
	private int nodenum;
	private FileOutputStream os;
	public Genetic(AdjTable table, VertexList vlist, FileOutputStream os){
		
		super(table, vlist, os);
		this.os = os;
		PM = (1.0/vlist.getLength());
		population = new Generation();
		nodenum = vlist.getLength();
		max_iter = nodenum * 10;
	}
	public void switch_Genertation(Vector<Chromo> target){
		population.switch_Population(target);
	}
	public int[] random_shuffle(int limit){
		//1부터 limit-1 까지를 random shuffling 
		// using knuth shuffle, O(n)
		int n = limit-1;//node개수가 13이면 {1,2,3,4,5,.... 12}를 random shuffle
		int[] arr = new int[n];
		for(int i = 1 ; i != n+1 ; ++i)
			arr[i-1] = i;
		
		Random rand = new Random();
		
		for(int i = 0; i < n; ++i){
			int r = rand.nextInt(i+1);
			swap(arr, i, r);
		}
		return arr;
	}
	public void swap(int[] arr, int a, int b){
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}
	public void make_std_chromo(int i, Chromo temp){
		int [] ran = random_shuffle(nodenum);
		temp.set_Chromo(0, 0);
		temp.set_Chromo(1, i);
		int z = 0;
		for(int j = 2 ; j != nodenum ; ++j){
			int x = ran[z];
			if(temp.get(1) == x)	
				x = ran[++z];
			temp.set_Chromo_append(x);
			++z;
		}
		temp.set_Chromo_append(0);
	}
	public void make_first(){//개선 필요
		//make 1st population
		int num = this.nodenum;//총 6개면 0,1,2,3,4,5,0 이렇게 되겠지
		//0 1 2 3 4 5 0  이렇게 구성을 해야됨 , num 개 만큼
		//random shuffle로 했음.
		for(int i = 1 ; i != num; ++i){
			Chromo temp = new Chromo();
			make_std_chromo(i, temp);
			this.population.set_Generation(temp);
		}
	}

	public void get_fitness_generation(){
		//한 population의 모든 chromo들에게 fit 값을 넣어준다.
		for(int i = 0; i != this.population.getPopulation().size(); ++i)
			this.get_fitness_chromo(population.getPopulation().get(i));
			
	}
	public double get_fitness_chromo(Chromo i){
		double ret = 0 ;
		for(int a = 0 ; a!= this.nodenum-1; ++a)
			ret += super.table.getVal(i.get(a), i.get(a+1));
		ret += super.table.getVal(this.nodenum-1, 0);
		ret = Math.ceil(ret*1000)/1000;
		i.setfit(ret);
		return ret;
		
	}
	
	public double make_aim(){// aim valie는 전체 edge들의 합 /2
		//현재 table과 vlist를 통해, 적절한 목표치를 잡는다.
		double ret = 0;
		for(int source = 0; source != this.nodenum; ++source){
			for(int target = source+1; target < this.nodenum; ++target)
				ret += super.table.getVal(source, target);
		}
		ret = ret/(this.nodenum-1);
		//System.out.print("QWERQWER" + ret);
		return 0;
	}
	public Generation getGeneration(){
		return this.population;
	}
	public long solve() throws IOException{
		long start = System.currentTimeMillis();
		System.out.print("GENETEIC");
		
			
		Chromo out = _solve();

		long end = System.currentTimeMillis();
		System.currentTimeMillis();
		
		out.print();
		//this.printSOL();
		ArrayList<Integer> i = convert(out.getChromo());
		
		printFile(i, out.getfit(), os);
		return end - start;
	}
	public void printFile(ArrayList<Integer> path, double val, FileOutputStream os ) throws IOException{
		Iterator<Integer> i = path.iterator();
		while(i.hasNext()){
			Integer temp = new Integer(i.next());
			String x = temp.toString() + " ";
			os.write(x.getBytes());
		}
		Double q = new Double(val);
		String w = " "+ q.toString() + " ";
		os.write(w.getBytes());
		os.write("\n".getBytes());
	}
	public ArrayList<Integer> convert(Vector<Integer> i){
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for(int x = 0 ; x != i.size(); ++x){
			ret.add(i.get(x)+1);
		}
		return ret;
	}
	
	

	
	public Chromo pick_best(){
		double min = Double.MAX_VALUE;
		int first = 0 ;
		for(int i = 0 ; i != population.getPopulation().size(); ++i){
			if(min > population.get(i).getfit()){
				min = population.get(i).getfit();
				first = i;				
			}
		}
		return population.get(first);
	}
	

	public Chromo _solve(){
		double aim = this.make_aim();
		int gen_t = 0;
		this.make_first();
		this.get_fitness_generation();
		while(true){
			++gen_t;
			Chromo out = this.pick_best();
			if(gen_t > this.max_iter || aim > out.getfit()){
				System.out.print("   iteration num : " + gen_t );				
				return out;
			}
			population = this.evolution();//breed가 되면 G(t-1)에서 G(t)로 변하게 한다.
			
			
		}
	}
	public Chromo tournamentSelection(){
		//population에서 토너먼트 셀렉션을 하는 것.
		Generation tournament = new Generation();
		for(int i = 0 ; i != this.candi_num; ++i){
			Random rand = new Random();
			int randomid = rand.nextInt(population.length_p());
			tournament.add_Generation(population.get(randomid));
		}
		Chromo best = tournament.get_best();
		return best;
	}
	public Generation evolution(){ // population의 변화가 주어져야된다
		
		Generation newGeneration = new Generation();
		
		for(int i = 0 ; i != population.length_p(); ++i){
			Chromo p1 = tournamentSelection();
			Chromo p2 = tournamentSelection();
			Chromo cp1 = new Chromo(p1);
			Chromo child = crossover(cp1, p2);
			Random rand = new Random();
			int x = rand.nextInt(100);

			if(x < (int)(PM*100)) mutate(child);
			this.get_fitness_chromo(child);
			newGeneration.add_Generation(child);
		}
		return newGeneration;
	}
	
	

	public void mutate(Chromo target){
		// target의  ramdom 번째의 값이 바뀐다.
		Random random = new Random();
		int num = random.nextInt(this.nodenum-3) + 1;
		//num번째의 값이 1이라하자. 그럼 여기에 2를 넣을것
		//이때 2가 원래 있는 곳이 x번쨰라고 하자. x 번째에는 1을 넣는다.
		int x = target.get(num);
		int before = x;
		if(x == this.nodenum-2) x = 1;
		else x +=1;		
		if(x == this.nodenum) x = this.nodenum-1;
		target.update_Chromo(num, x);

		for(int i  = 1 ; i != target.getChromosize()-1; ++i){
			if(i != num && target.get(i) == x){
				target.update_Chromo(i, before);
				break;
			}

		}
		
	}
	public Chromo crossover(Chromo p1, Chromo p2){
		Chromo ret = new Chromo(p1);
		// 1~12중 서로다른 6개의 숫자를 뽑는다.
		// 1 ~ n+1 까지 중 서로 다른 mid 개의 숫자를 뽀는다.
		int n = p1.getChromosize()-3; //0 ~ 11, + 1
		int mid = (p1.getChromosize()-2)/2;
		int arr[] = new int[mid];//arr에는 바꿀 위치가 들어가있음.
		Random rand = new Random();
		int pos = 0;
		while(pos != mid){
			//arr에는 1~12 중 랜덤한 인덱스가 들어간다.
			int temp = rand.nextInt(n) + 1;
			if(!is_in(arr, temp))
				arr[pos++] = temp;
		} 
		//arr : 1 4 8 5 7 10 이렇게 들어갔다고 하자.
		//p1.get(arr[0]), p1.get(arr[1]), p1.get(arr[1]) ... p1.get(arr[6]) 은 이제 빠저나가는 것이다.
		int val[] = new int[mid];//arr에는 바꿀 위치가 들어가있음.
		for(int i = 0 ; i != mid; ++i)
			val[i] = p1.get(arr[i]);
		
		//val에는 이제 p1에서 바뀌는 값들이 쓰여 있다.
		//p1의 arr[i] 번째에 있는 값을 p2의 값으로 변경을 한다.
		for(int i = 0 ; i != mid; ++i)
			ret.update_Chromo(arr[i], 0);
		

		int i_ret = 1;
		int i_p2 = 1;
		while(i_p2 < p1.getChromosize()-1){
			if(this.is_in(val, p2.get(i_p2))){
				if(ret.get(i_ret) == 0){
					ret.update_Chromo(i_ret, p2.get(i_p2));
					i_ret++;
					i_p2++;
				}
				else i_ret++;	
			}
			else i_p2++;
		}
		return ret;
		
		
	}
	public boolean is_in(int[] arr, int target){
		for(int i = 0 ; i != arr.length; ++i){
			if(arr[i] == target) return true;
		}
		return false;
	}

	public void update(int alter, Chromo t){
		population.getPopulation().set(alter, t);
	}


}
