import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;
import java.util.Objects;
import java.util.InputMismatchException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.util.Random;
import java.util.Base64;
import java.security.SecureRandom;

interface MakingBankAccount{
	public String setAccount();
}

class Client{
	static int numberOfClient=0;
	protected String id;
	protected String password;			// 로그인 시 pw
	protected String salt;					// password 랑 같이 해싱하는 salt
	protected String name;					// 고객 이름
	protected String bankAccount;		// 계좌번호 == 로그인 시 id
	protected int balance=0;				// 잔액

//예금, 출금, 잔액확인 등 통장 주요 메소드-----------------------------------------------------
	public void deposit(int depositAmount){					// 입금 메소드
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
	public void checkBalance(){											// 잔액확인메소드
		System.out.println("현재 잔액:"+balance+"원");
	}
//예금, 출금, 잔액확인 등 통장 주요 메소드-----------------------------------------------------
//계좌 신설 시 멤버 초기화에 관한 메소드(Setter)-------------------------------------------------
	public Client(){
	 //BLANK
	 }
	public void setName(String name){	// 계좌 신설 후 고객의 이름 저장 메소드
		this.name = name;
	}
	public void setId(String id){	// 계좌 신설 후 고객의 계좌번호(ID) 저장 메소드
		this.id = id;
	}
	public void setPassword(String password){
		try{
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			byte[] bytes = new byte[16];
			random.nextBytes(bytes);
			String salt = new String(Base64.getEncoder().encode(bytes));
			this.salt = salt;
			
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(password.getBytes());
			String hex = String.format("%128x", new BigInteger(1, md.digest()));
			this.password = hex;
		}
		catch(NoSuchAlgorithmException e){
			System.out.println("Something is wrong");
		}
	}
//계좌 신설 시 멤버 초기화에 관한 메소드(Setter)-------------------------------------------------
//계좌 이용 시 멤버 참조에 관한 메소드(Getter)---------------------------------------------------
	public String getId(){
		return id;
	}
	protected String getPassword(){
		return password;
	}
	public String getName(){
		return name;
	}
	public String getBankAccount(){
		return bankAccount;
	}
	public int getBalance(){
		return balance;
	}
//계좌 이용 시 멤버 참조에 관한 메소드(Getter)---------------------------------------------------
//계좌 이용 시 사용자의 정보를 출력하는 메소드----------------------------------------------------	
	public void showInfoOFClient(){
		System.out.println("성명: "+getName());
		System.out.println("계좌번호: "+getBankAccount());
		System.out.println("현재 잔액:"+getBalance()+"원");		
	}
//계좌 이용 시 사용자의 정보를 출력하는 메소드----------------------------------------------------	


	@Override
	public boolean equals(Object obj){
		if(id.equals(((Client)obj).id))
			return true;
		else
			return false;
	}
	@Override
	public int hashCode(){
		return ((id.hashCode())+100)%17;
	}
}



class Main {
	public static void main(String[] args){
			int select;
			String trialId;
			String trialPw;
			String openingId;
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
						if(c.getBankAccount().equals(trialId) && c.getPassword().equals(trialPw)){
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
					System.out.print("개설 할 ID를 입력하십시오: ");
					sc.nextLine(); // 입력버퍼비우기
					openingId = sc.nextLine();
					Client newClient = new Client();
					newClient.setId(openingId);
					if(clientDatabase.add(newClient)){
						System.out.print("개설 할 계좌의 비밀번호를 입력하십시오: ");
						openingPassword = sc.nextLine();
						System.out.print("고객님의 이름을 입력하십시오: ");
						openingName = sc.nextLine();
						newClient.setPassword(openingPassword);
						newClient.setName(openingName);
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
