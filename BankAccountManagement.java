import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;

class Client{
	static int numberOfClient=0;

	private String clientName;		// 고객 이름
	private String bankAccount;		// 계좌번호
	private String id;						// 로그인 시 id
	private String password;			// 로그인 시 pw
	private int balance=0;				// 잔액

	public Client(String clientName, String bankAccount){
		this.clientName = clientName;
		this.bankAccount = bankAccount;
	}



	public void deposit(int depositAmount){					// 예금 메소드
		balance += depositAmount;
	}

	public boolean withdraw(int withdrawAmount){		// 출금메소드
		if(withdrawAmount>balance)										// 출금금액이 잔액보다 클 경우 false반환
			return false;
		else{
		balance -= withdrawAmount;
		return true;
		}
	}

	public void checkBalance(){
		System.out.println("현재 잔액:"+balance+"원");
	}

}

class Main{
	public static void main(String[] args){
			int select;
			String trialId;
			String trialPw;
			Set<Client> clientDatabase = new HashSet<>();
			Scanner sc = new Scanner(System.in);
		
		
			System.out.println("1. SIGN IN\t\t\t2. SIGN UP\t\t\t3. END OF PROGRAM");
			select = sc.nextInt();
			switch(select){
				case 1:
					 System.out.print("ID: ");
					 trialId = sc.nextLine();
					 System.out.print("PW: ");
					 trialPw = sc.nextLine();
					

//로그인메소드 호출 -> 계좌 화면 출력
				case 2:
				//	가입 메소드 호출
				case 3:
					return;
				default:
				//	다시 입력해라
			}
	}
}
