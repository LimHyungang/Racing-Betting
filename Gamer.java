import java.util.Scanner;

public class Gamer { // 유저에게 필요한 각종 멤버변수들
	private String m_strName = "";
	private int m_iBalance = 0;
	public String m_horseName = "";
	public int m_bettingMoney = 0;
	private Scanner m_scan;

	private Gamer() { // 불필요한 기본 생성자의 호출을 막는다

	}

	public Gamer(String strName, int iBalance) {
		m_strName = strName;
		m_iBalance = iBalance;

		m_scan = new Scanner(System.in);
	}

	public int getNum() { // 입력받은 변수를 반환
		int input = 0;
		try {
			input = m_scan.nextInt();
		} catch (Exception e) {
			System.out.println("getNum of gamer : 숫자만 선택 가능합니다. 다시 입력하세요.");
			m_scan = new Scanner(System.in);
		}
		return input;
	}

	public String getGamerName() { // 유저 이름 반환
		return m_strName;
	}

	public int getBalance() { // 유저의 잔액 반환
		return m_iBalance;
	}

	public int deposit(int iMoney) { // 계좌에 입금
		return m_iBalance += iMoney;
	}

	public int withdrawal(int iMoney) { // 계좌에서 출금
		if (m_iBalance >= iMoney) // 잔고가 출금액 이상일 경우에만 출금을 허용한다
		{
			return m_iBalance -= iMoney;
		} else {
			return -1;
		}
	}

	public void betting(int bettingMoney) { // 입력받은 액수를 베팅한다
		if (m_iBalance >= bettingMoney) { // 잔액을 초과하여 베팅할 수 없다
			m_bettingMoney = bettingMoney; // 베팅 금액 기억
		} else {
			System.out.println("잔액 초과. 베팅할 수 없음.");
			State.bettingGamerCount--; // 해당 Gamer가 다시 베팅할 수 있도록
		}
	}

}
