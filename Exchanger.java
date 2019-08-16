
public class Exchanger {  // Exchanger�� ��������� 
	private int m_iBalance = 0;
	private String m_strName;

	private Exchanger() {  // ���ʿ��� �⺻ �������� ȣ���� ���´�

	}

	public Exchanger(String name, int iBalance) {
		m_strName = name;
		m_iBalance = iBalance;
	}

	public String getExchangerName() {  // Exchanger �̸� ��ȯ
		return m_strName;
	}

	public int getBalance() {  // Exchanger�� �ܰ� ��ȯ
		return m_iBalance;
	}

	public int deposit(int iMoney) {  // Exchanger ���¿� �Ա�
		return m_iBalance += iMoney;
	}

	public int withdrawal(int iMoney) {  // Exchanger ���¿��� ��� 
		if (m_iBalance >= iMoney) {  // �ܰ� ��ݾ� �̻��� ��쿡�� ��� ���
			return m_iBalance -= iMoney;
		} else {
			return -1;
		}
	}
}
