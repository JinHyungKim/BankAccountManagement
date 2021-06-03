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
import java.util.Date;
import java.text.SimpleDateFormat;

interface MakingBankAccount {
	public void setAccount();
}

class PrintText {
	public static void firstMessage() {
		System.out.println("1. SIGN IN\t\t\t2. SIGN UP\t\t\t3. END OF PROGRAM");
	}

	public static void toLogInId() {
		System.out.print("ID: ");
	}

	public static void toLogInPw() {
		System.out.print("PW: ");
	}

	public static void logInSuccessAndWhatToDo() {
		System.out.println("1. Deposit\t\t\t2. Withdraw\t\t\t3. Check Info\t\t\t4. Logout");
	}

	public static void depositHowMuch() {
		System.out.println("입금할 금액: ");
	}

	public static void withdrawHowMuch() {
		System.out.println("출금할 금액: ");
	}

	public static void makeNewIdMessage() {
		System.out.print("개설 할 ID를 입력하십시오: ");
	}

	public static void makeNewPwMessage() {
		System.out.print("개설 할 계좌의 비번을 입력하십시오: ");
	}

	public static void makeNewNameMessage() {
		System.out.print("성명을 입력하십시오: ");
	}

	public static void chekingDuplicatedId() {
		System.out.println("중복 된 ID입니다.");
	}

	public static void wrongInputMessaage() {
		System.out.println("잘못된 입력입니다.");
	}
	public static void printSeparateLine(){
		System.out.println("==========================================================");
	}
}

class Client {
	static int numberOfClient = 0;
	protected String id;
	protected String password; // 로그인 시 pw
	// protected String salt; // password 랑 같이 해싱하는 salt
	protected String name; // 고객 이름
	protected String bankAccount; // 계좌번호 == 로그인 시 id
	protected int balance = 0; // 잔액

	// 예금, 출금, 잔액확인 등 통장 주요 메소드-----------------------------------------------------
	public void deposit(int depositAmount) { // 입금 메소드
		balance += depositAmount;
	}

	public boolean withdraw(int withdrawAmount) { // 출금메소드
		if (withdrawAmount > balance) // 출금금액이 잔액보다 클 경우 false반환
			return false;
		else {
			balance -= withdrawAmount;
			return true;
		}
	}

	public void checkBalance() { // 잔액확인메소드
		System.out.println("현재 잔액:" + balance + "원");
	}

	// 예금, 출금, 잔액확인 등 통장 주요 메소드-----------------------------------------------------
	// 계좌 신설 시 멤버 초기화에 관한
	// 메소드(Setter)-------------------------------------------------
	public Client() {
		// BLANK
	}

	public void setName(String name) { // 계좌 신설 후 고객의 이름 저장 메소드
		this.name = name;
	}

	public void setId(String id) { // 계좌 신설 후 고객의 계좌번호(ID) 저장 메소드
		this.id = id;
	}

	public void setPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(password.getBytes());
			String hex = String.format("%128x", new BigInteger(1, md.digest()));
			this.password = hex;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Something is wrong");
		}
	}

	// 계좌 신설 시 멤버 초기화에 관한
	// 메소드(Setter)-------------------------------------------------
	// 계좌 이용 시 멤버 참조에 관한
	// 메소드(Getter)---------------------------------------------------
	public String getId() {
		return id;
	}

	protected String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public int getBalance() {
		return balance;
	}

	static public String getPasswordHashing(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(password.getBytes());
			String hex = String.format("%128x", new BigInteger(1, md.digest()));
			return hex;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Something is wrong");
			return "Wrong";
		}
	}

	// 계좌 이용 시 멤버 참조에 관한
	// 메소드(Getter)---------------------------------------------------
	// 계좌 이용 시 사용자의 정보를 출력하는 메소드----------------------------------------------------
	public void showInfoOFClient() {
		System.out.println("성명: " + getName());
		System.out.println("계좌번호: " + getBankAccount());
		System.out.println("현재 잔액:" + getBalance() + "원");
		// System.out.println(getPassword());
	}
	// 계좌 이용 시 사용자의 정보를 출력하는 메소드----------------------------------------------------

	@Override
	public boolean equals(Object obj) {
		if (id.equals(((Client) obj).id))
			return true;
		else
			return false;
	}

	@Override
	public int hashCode() {
		return ((id.hashCode()) + 100) % 17;
	}
}

class ShinhanBank extends Client implements MakingBankAccount {
	private final int bankSerialNumber = 110;

	@Override
	public void setAccount() {
		Date date_now = new Date(System.currentTimeMillis());
		SimpleDateFormat fourteen_format = new SimpleDateFormat("ddHHmmss");
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		bankAccount = (new StringBuilder(String.valueOf(bankSerialNumber)).append("-")
				.append(fourteen_format.format(date_now)).append(rand.nextInt(10))).toString();
	}
}

class Main {
	public static void main(String[] args) {
		String select;
		String trialId;
		String trialPw;
		int money;
		Set<Client> clientDatabase = new HashSet<>();
		Scanner sc = new Scanner(System.in);
		while (true) {
			PrintText.printSeparateLine();
			PrintText.firstMessage();
			PrintText.printSeparateLine();

			select = sc.nextLine();
			switch (select) {
			case "1":
				PrintText.toLogInId();
				sc.nextLine(); // 입력버퍼비우기
				trialId = sc.nextLine();
				PrintText.toLogInPw();
				trialPw = sc.nextLine();
				Client connectedClient = new Client();
				for (Client c : clientDatabase) {
					if (c.getId().equals(trialId) && c.getPassword().equals(Client.getPasswordHashing(trialPw))) {
						connectedClient = c;
						while (true) {
							PrintText.printSeparateLine();
							PrintText.logInSuccessAndWhatToDo();
							PrintText.printSeparateLine();
							select = sc.nextLine();
							switch (select) {
							case "1":
								PrintText.depositHowMuch();
								money = sc.nextInt();
								connectedClient.deposit(money);
								connectedClient.getBalance();
								break;
							case "2":
								PrintText.withdrawHowMuch();
								money = sc.nextInt();
								connectedClient.withdraw(money);
								connectedClient.getBalance();
								break;
							case "3":
								connectedClient.showInfoOFClient();
								break;
							case "4":
								break;
							default:
							}
							if (select.equals("4"))
								break;
						}
					}
				}
				break;
			case "2": // 가입 메소드 호출
				PrintText.makeNewIdMessage();
				sc.nextLine(); // 입력버퍼비우기
				ShinhanBank newClient = new ShinhanBank();
				newClient.setId(sc.nextLine());
				if (clientDatabase.add(newClient)) {
					PrintText.makeNewPwMessage();
					newClient.setPassword(sc.nextLine());
					PrintText.makeNewNameMessage();
					newClient.setName(sc.nextLine());
					newClient.setAccount();
					Client.numberOfClient++;
				} else
					PrintText.chekingDuplicatedId();
				break;
			case "3":
				return;
			default:
				PrintText.wrongInputMessaage();
			}
		}
	}
}
