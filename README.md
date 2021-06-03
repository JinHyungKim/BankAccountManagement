# BankAccountManagement
**2021.05.30**
 - HashSet의 사용: equals() method & hashCode() method의 Override 필요.
 - String class 의 equals() method 사용법 / String class간 == 연산자는 인스턴스의 주소 비교.

**2021.05.31**
 - 무한루프 안에 있는 Scanner class사용시 만일 try catch문을 사용한다면 catch영역에서 Scanner class를 새로 할당해야 함.

**2021.06.01**
 - setPassword() method 변경: java.security 패키지 사용(MessageDigest, SecureRandom)
 - 해싱을 통한 암호화(SHA-512)과 salt를 사용한 

**2021.06.02**
 - salt값 저장을 위한 공간 별도 필요
 - 해싱값과 별개로 salt는 완전 랜덤임
 - Client class를 상속하고, 계좌 생성 추상메소드를 가지는 MakingBankAccount interface를 구현하는 은행사 별 class를 만듦
 - 각 은행사 별로 BankSerialNumber를 부여하고, 그 기준으로 각각 개별의 계좌 생성 메소드를 구현함. 

**2021.06.03**
 - Main method에서 Printg method를 제거, 불필요한 변수 삭제로 간결화
 - switch문의 조건검사 방식을 바꾸고, try catch구문을 삭제(InputMismatchException)
