import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;
import java.util.Objects;
import java.util.InputMismatchException;

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
	  public Client(){
			// 계좌 신설 시 bankAccount의 중복을 검사하기 위한 빈 생성자
	 }
	public void setClientName(String name){	// 계좌 신설 후 고객의 이름 저장 메소드
		clientName = name;
	}
	public void setClientBankAccount(String bankAccount){	// 계좌 신설 후 고객의 계좌번호(ID) 저장 메소드
		this.bankAccount = bankAccount;
	}
	public void setClientPassword(String password){
		this.password = password;
	}
	public void showInfoOFClient(){
		System.out.println("성명: "+clientName);
		System.out.println("계좌번호: "+bankAccount);
		System.out.println("현재 잔액:"+balance+"원");		
	}

	public String getClientBankAccount(){
		return bankAccount;
	}
	public String getClientPassword(){
		return password;
	}

	@Override
	public boolean equals(Object obj){
		if(bankAccount.equals(((Client)obj).bankAccount))
			return true;
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
			String openingName;
			Set<Client> clientDatabase = new HashSet<>();
			Scanner sc = new Scanner(System.in);
		
		while (true) {
			try{
				System.out.println("1. SIGN IN\t\t\t2. SIGN UP\t\t\t3. END OF PROGRAM");
				select = sc.nextInt();
				switch (select) {
				case 1:
					System.out.print("ID: ");
					sc.nextLine(); // 입력버퍼비우기
					trialId = sc.nextLine();
					System.out.print("PW: ");
					trialPw = sc.nextLine();
					Client connectedClient = new Client();
					for(Client c : clientDatabase){
						if(c.getClientBankAccount().equals(trialId) && c.getClientPassword().equals(trialPw)){
							connectedClient = c;
							while(true){
								System.out.println("1. Deposit\t\t\t2. Withdraw\t\t\t3. Check Info\t\t\t4. Logout");
								select = sc.nextInt();
								switch (select) {
									case 1:
									case 2:
									case 3:
									case 4:
										break;
									default:
								}
								break;
							}
						}	
					}
					
					break;
					//if( )
				case 2: // 가입 메소드 호출
					System.out.print("개설 할 계좌 번호를 입력하십시오: ");
					sc.nextLine(); // 입력버퍼비우기
					openingBankAccount = sc.nextLine();
					Client newClient = new Client();
					newClient.setClientBankAccount(openingBankAccount);
					if(clientDatabase.add(newClient)){
						System.out.print("개설 할 계좌의 비밀번호를 입력하십시오: ");
						openingPassword = sc.nextLine();
						System.out.print("고객님의 이름을 입력하십시오: ");
						openingName = sc.nextLine();
						newClient.setClientPassword(openingPassword);
						newClient.setClientName(openingName);
						Client.numberOfClient++;
						// for(Client c:clientDatabase)
						// 	c.showInfoOFClient();
					}
					else
						System.out.println("중복 된 계좌번호(ID)입니다.");
					break;
				case 3:
					return;
				default:
					System.out.println("잘못된 입력입니다.");
				}
			}
			catch(InputMismatchException e){
				System.out.println("잘못된 입력입니다.");
				sc = new Scanner(System.in);
			}
		}
	}
}
