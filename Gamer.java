import java.util.Scanner;

public class Gamer { // �������� �ʿ��� ���� ���������
	private String m_strName = "";
	private int m_iBalance = 0;
	public String m_horseName = "";
	public int m_bettingMoney = 0;
	private Scanner m_scan;

	private Gamer() { // ���ʿ��� �⺻ �������� ȣ���� ���´�

	}

	public Gamer(String strName, int iBalance) {
		m_strName = strName;
		m_iBalance = iBalance;

		m_scan = new Scanner(System.in);
	}

	public int getNum() { // �Է¹��� ������ ��ȯ
		int input = 0;
		try {
			input = m_scan.nextInt();
		} catch (Exception e) {
			System.out.println("getNum of gamer : ���ڸ� ���� �����մϴ�. �ٽ� �Է��ϼ���.");
			m_scan = new Scanner(System.in);
		}
		return input;
	}

	public String getGamerName() { // ���� �̸� ��ȯ
		return m_strName;
	}

	public int getBalance() { // ������ �ܾ� ��ȯ
		return m_iBalance;
	}

	public int deposit(int iMoney) { // ���¿� �Ա�
		return m_iBalance += iMoney;
	}

	public int withdrawal(int iMoney) { // ���¿��� ���
		if (m_iBalance >= iMoney) // �ܰ� ��ݾ� �̻��� ��쿡�� ����� ����Ѵ�
		{
			return m_iBalance -= iMoney;
		} else {
			return -1;
		}
	}

	public void betting(int bettingMoney) { // �Է¹��� �׼��� �����Ѵ�
		if (m_iBalance >= bettingMoney) { // �ܾ��� �ʰ��Ͽ� ������ �� ����
			m_bettingMoney = bettingMoney; // ���� �ݾ� ���
		} else {
			System.out.println("�ܾ� �ʰ�. ������ �� ����.");
			State.bettingGamerCount--; // �ش� Gamer�� �ٽ� ������ �� �ֵ���
		}
	}

}
