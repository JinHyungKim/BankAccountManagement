import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;
import java.util.Objects;

class Client{
	static int numberOfClient=0;

	private String clientName;		// 고객 이름
	private String bankAccount;		// 계좌번호 == 로그인 시 id
	private String password;			// 로그인 시 pw
	private int balance=0;				// 잔액

//-----------------------------------예금, 출금, 잔액확인 등 통장 주요 메소드------------------
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
//-----------------------------------예금, 출금, 잔액확인 등 통장 주요 메소드------------------
//-----------------------------------계좌번호, password 등 개인정보에 관한 메소드--------------
	 public Client(String password, String bankAccount){			// 계좌 신설 시 호출 생성자(반드시 1회 호출)
	 	this.password = password;
	 	this.bankAccount = bankAccount;
	}
	public void setClientName(String name){
		clientName = name;
	}


	@Override
	public boolean equals(Object obj){
		if(bankAccount.equals(((Client)obj).bankAccount)){
			System.out.println("같음");
			return true;
		}
		else
			return false;
	}
	@Override
	public int hashCode(){
		return ((bankAccount.hashCode())+100)%17;
	}
}

class Main {
	public static void main(String[] args){
			int select;
			String trialId;
			String trialPw;
			String openingBankAccount;
			String openingPassword;
			Set<Client> clientDatabase = new HashSet<>();
			Scanner sc = new Scanner(System.in);
		
		while (true) {
			System.out.println("1. SIGN IN\t\t\t2. SIGN UP\t\t\t3. END OF PROGRAM");
			select = sc.nextInt();
			switch (select) {
			case 1:
				System.out.print("ID: ");
				sc.nextLine(); // 입력버퍼비우기
				trialId = sc.nextLine();
				System.out.print("PW: ");
				sc.nextLine(); // 입력버퍼비우기
				trialPw = sc.nextLine();
				// 로그인메소드 호출 -> 계좌 화면 출력
			case 2:
				System.out.print("개설 할 계좌 번호를 입력하십시오: ");
				sc.nextLine(); // 입력버퍼비우기
				openingBankAccount = sc.nextLine();
				System.out.print("개설 할 계좌의 비밀번호를 입력하십시오: ");
				sc.nextLine(); // 입력버퍼비우기
				openingPassword = sc.nextLine();
				Client newClient = new Client(openingBankAccount, openingPassword);
				clientDatabase.add(newClient);
				System.out.println(clientDatabase.size());

				// 가입 메소드 호출
			case 3:
				//return;
			default:
				// 다시 입력해라
			}
		}
	}
}
