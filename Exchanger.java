
public class Exchanger {  // Exchanger의 멤버변수들 
	private int m_iBalance = 0;
	private String m_strName;

	private Exchanger() {  // 불필요한 기본 생성자의 호출을 막는다

	}

	public Exchanger(String name, int iBalance) {
		m_strName = name;
		m_iBalance = iBalance;
	}

	public String getExchangerName() {  // Exchanger 이름 반환
		return m_strName;
	}

	public int getBalance() {  // Exchanger의 잔고 반환
		return m_iBalance;
	}

	public int deposit(int iMoney) {  // Exchanger 계좌에 입금
		return m_iBalance += iMoney;
	}

	public int withdrawal(int iMoney) {  // Exchanger 계좌에서 출금 
		if (m_iBalance >= iMoney) {  // 잔고가 출금액 이상일 경우에만 출금 허용
			return m_iBalance -= iMoney;
		} else {
			return -1;
		}
	}
}
