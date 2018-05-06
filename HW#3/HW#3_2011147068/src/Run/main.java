package Run;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import Arr.*;
import DP.DP;

public class main {
	public static int solve(int target_money, DP mypay, DP change){
		int min = 999999;
		int sol_index = 0;
		int check=1;
		if(mypay.getCapacity()-1 < target_money) // 최대 지불가능비용(capacity)가 target_money보다 작으면 불가능!
			return -1;
		for(int i = target_money; i != mypay.getCapacity(); ++i){
			int temp = mypay.get(i).getVVal() + change.get(i-target_money).getVVal();
			if(temp < min){
				min = temp; //최소값을 찾아야 하므로 갱신
				sol_index = i;// 더 최적해가 발견된 경우 sol_index를 그때 Customer이 지불한 가격으로 갱신.
				check = 0;// 한번이라도 최소값 갱신이 됐으면 불가능하지 않음을 알리기 위해 check값을 바꿔준다.
			}
			else if(temp == min) // 최적해 값이 같은경우
			{
				if(mypay.get(i).getVVal() < mypay.get(sol_index).getVVal())// 지불하는 사람이 더 적게 내면 갱신.
				{
					min = temp;
					sol_index = i;// 금액
					check = 0;
				}
			}
		}
		if(check==1) // 한번도 갱신되지 않았다 즉, 불가능한 경우
			return -1;
	//	System.out.println(min+"  "+sol_index);
		return sol_index;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//Scanner s = new Scanner(new File("input"));
		Scanner s = new Scanner(new File("C:\\hw3\\input.txt"));
		//FileOutputStream os = new FileOutputStream("2011147068.txt");
		
		FileOutputStream os = new FileOutputStream("C:\\hw3\\2011147068.txt");
		int casenum = s.nextInt();
		
		for(int i = 0; i != casenum; ++i){
			int ctype_num = s.nextInt();
			int target_money = s.nextInt();
			Arr ctype = new Arr(ctype_num);
			Arr range = new Arr(ctype_num);
			Arr inf_range = new Arr(ctype_num);
			for(int j = 0; j != ctype_num; ++j)
				ctype.set(j, s.nextInt());
			for(int j = 0; j != ctype_num; ++j)
				range.set(j, s.nextInt());
			for(int j = 0; j != ctype_num; ++j)
				inf_range.set(j, Integer.MAX_VALUE);
			//입력받기.
			int maxMoney = Cal.inner_product(ctype, range);
			//System.out.println(maxMoney);			
		
			DP mypay = new DP(maxMoney+1, ctype, range);
			DP change = new DP(maxMoney+1, ctype, inf_range);
			mypay.makeDP();//maxMoney만큼 DP를 만든다.
			change.makeDP_change();//maxMoney만큼 DP를 만든다.
			int sol_index = solve(target_money, mypay, change);
			if(sol_index==-1){ //불가능한 경우에는 sol_index로 -1을 리턴시켰다.
				Integer temp = new Integer(-1);
				String x = temp.toString() + " ";
				os.write(x.getBytes());
			}
			else
			{
				int min_answer = mypay.get(sol_index).sum_element() + change.get(sol_index-target_money).sum_element();
				//System.out.println(mypay.get(sol_index).sum_element() + " " + change.get(sol_index-target_money).sum_element());
				Arr min_pay = mypay.get(sol_index);	//Customer 는 sol_index만큼 돈을 지불하고,
				Arr min_changepay = change.get(sol_index-target_money);	// Clerk는 sol_index-target_money만큼 돈을 거슬러준다.
				Integer temp = new Integer(min_answer);
				String x = temp.toString() + " ";
				os.write(x.getBytes());
				
				min_pay.print(os);
				
				//System.out.println("*******************************************************");
				min_changepay.print(os);
				
			}
			os.write("\n".getBytes());
		}
		System.out.println("End");
	}

}
