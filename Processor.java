import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Processor {
	Scanner m_scan = null;
	private int m_iGamerCnt = 0;
	public int m_bettingMoney = 0;
	private int m_selectedNum = 0;

	// Gamer�� ���� ��̸���Ʈ
	private ArrayList<Gamer> m_arrGamer = null;
	// ���ð� ��÷�� ������ ������ Exchanger
	Exchanger exchanger = null;

	// ���� ������ ��ü�� ���� ����
	Horse h1 = null;
	Horse h2 = null;
	Horse h3 = null;
	Thread t1 = null;
	Thread t2 = null;
	Thread t3 = null;

	// ���� ���⿡ �ʿ��� ������
	PrintWriter pw = null;
	BufferedReader br = null;
	int raceCount = 1;

	public Processor() {
		exchanger = new Exchanger("Exchanger", 999999999); 
		m_arrGamer = new ArrayList<Gamer>();

		// ���� ����� ���Ͽ� �Է��� PrintWriter
		try {
			pw = new PrintWriter("RacingResult.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// ���Ͽ� �Էµ� ���� ����� �о�� BufferedReader
		try {
			br = new BufferedReader(new FileReader("RacingResult.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		showMessage();
		
	}

	// ȯ�� �޼��� and �۾� ����
	private void showMessage() {
		m_scan = new Scanner(System.in);
		for (;;) {
			System.out.println("�渶�忡 ���� ���� ȯ���մϴ�. �����Ͻ� �۾��� �����ϼ���.");
			System.out.println("1 : ���� ����");
			System.out.println("2 : ���� ����");
			System.out.println("3 : ���� �߰�");
			System.out.println("4 : ����");
			System.out.println("5 : �ܾ� Ȯ��");
			System.out.println("6 : ���� ȸ�� ��� Ȯ��");
			System.out.println("7 : ������");

			m_selectedNum = getNum();
			selectWork(m_selectedNum);

			// 7�� �����ϸ� ����
			if (m_selectedNum == 7)
				break;
		}
		System.out.println("�渶 ������ �����մϴ�.");
	}

	// �۾� ����
	private void selectWork(int num) {
		for (;;) {
			switch (num) {
			case 1:
				racing();
				// ���� �������� �ʾҰų�, Gamer�� �� �� ���ų�, ������ �� �ϸ� ���� ���� �Ұ�
				if ((h1 == null) || (h2 == null) || (h3 == null) || (m_arrGamer == null) || (m_bettingMoney == 0)) {
					return; // line 41��
				}

				for (;;) { // ���ָ� �����ϸ� ��¸��� �������� ��� Ȯ��
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (State.isFinish) {
						resultAndReset(); // ��¸��� ������ ���ð���� ���� ������ϰ� �ֿ� �������� reset
						break; // line 70����
					}
				}
				break; // line 94��
			case 2:
				System.out.println("���ֿ� ������ ������ �����ϼ���.");
				System.out.println("[1] �糪��");
				System.out.println("[2] ��踻");
				System.out.println("[3] ���ָ�");
				selectHorse(getNum());
				break; // line 94��
			case 3:
				addGamer(); // Gamer �߰�
				break; // line 94��
			case 4:
				choiceMyHorse(); // ������ ���鿡 ����
				break; // line 94��
			case 5:
				checkBalance(); // exchanger�� Gamer���� �ܾ� ��ȸ
				break; // line 94��
			case 6:
				confirmResult();
				break; // line 94��
			case 7:
				return; // line 41��
			default:
				System.out.println("�ٽ� �������ּ���.");
				return; // line 41��
			}

			return; // line 41��
		}
	}

	// ���� ���� ��� Ȯ��. ���Ͽ��� �о�� ����� ����Ѵ�
	public void confirmResult() {
		for (;;) {
			try {
				String result = br.readLine();
				if(result == null) 
					break;
				System.out.println(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Exchanger�� Gamer���� �ܾ� ��ȸ
	public void checkBalance() { 
		System.out.println(exchanger.getExchangerName() + "���� �ܾ� " + exchanger.getBalance() + "��");
		for (int i = 0; i < m_iGamerCnt; i++)
			System.out.println(m_arrGamer.get(i).getGamerName() + "���� �ܾ� " + m_arrGamer.get(i).getBalance() + "��");
	}

	// ������ ���鿡 ����
	private void choiceMyHorse() { 
		if ((h1 == null) || (h2 == null) || (h3 == null)) {
			System.out.println("���� ������ �����ϼ���.");
		} else if (m_iGamerCnt == 0) {
			System.out.println("������ �����ϴ�.");
		} else {  // bettingGamerCount�� ���� ���� : ��ư�� �߸� �����ų�, �ܾ��� �ʰ��Ͽ� �����ϸ� �ٽ� �����ϰ� �� �� �ֵ���
			for (State.bettingGamerCount = 0; State.bettingGamerCount < m_iGamerCnt; State.bettingGamerCount++) {
				System.out.println(m_arrGamer.get(State.bettingGamerCount).getGamerName() + "���� �����մϴ�.");

				System.out.println("��� ���� �����Ͻðڽ��ϱ�?");
				System.out.println(h1.m_hName);
				System.out.println(h2.m_hName);
				System.out.println(h3.m_hName);

				switch (getNum()) { // Gamer�� m_horseName���� ���� ������ ���� �̸��� ����Ѵ�
				case 1:
					// 1�� ���� ����
					m_arrGamer.get(State.bettingGamerCount).m_horseName = h1.m_hName;
					System.out.println("���þ׼��� �Է��ϼ���. (���߸� 2��)");
					m_bettingMoney = getNum();
					m_arrGamer.get(State.bettingGamerCount).betting(m_bettingMoney);
					break;
				case 2:
					// 2�� ���� ����
					m_arrGamer.get(State.bettingGamerCount).m_horseName = h2.m_hName;
					System.out.println("���þ׼��� �Է��ϼ���. (���߸� 2��)");
					m_bettingMoney = getNum();
					m_arrGamer.get(State.bettingGamerCount).betting(m_bettingMoney);
					break;
				case 3:
					// 3�� ���� ����
					m_arrGamer.get(State.bettingGamerCount).m_horseName = h3.m_hName;
					System.out.println("���þ׼��� �Է��ϼ���. (���߸� 2��)");
					m_bettingMoney = getNum();
					m_arrGamer.get(State.bettingGamerCount).betting(m_bettingMoney);
					break;
				default:
					System.out.println("default of betting : �߸��� �����Դϴ�.");
					m_bettingMoney = 0;
					// �ٽ� ������ ������ �� �ֵ��� bettingGamerCount (
					State.bettingGamerCount--;
					break;
				}

			}
			System.out.println("������ �Ϸ� �Ǿ����ϴ�.");
		}
	}

	private void addGamer() { // Gamer �߰�
		m_arrGamer.add(new Gamer("��" + (m_iGamerCnt + 1), 10000));
		System.out.println(m_arrGamer.get(m_iGamerCnt).getGamerName() + "���� �߰��Ǿ����ϴ�." + "\n������ �ܾ��� "
				+ m_arrGamer.get(m_iGamerCnt).getBalance() + "���Դϴ�.");
		m_iGamerCnt++;
	}

	private void resultAndReset() { // ����� ���� �����, ���� ������ ���� ������ �ʱ�ȭ
		for (int i = 0; i < m_iGamerCnt; i++) {
			// �ϴ� ������ �ݾ� ����
			m_arrGamer.get(i).withdrawal(m_arrGamer.get(i).m_bettingMoney);
			exchanger.deposit(m_arrGamer.get(i).m_bettingMoney);

			// ���� Gamer���� ���ñݾ� 2�� �Ա�
			if (m_arrGamer.get(i).m_horseName.equals(State.winner)) {
				System.out.println(m_arrGamer.get(i).getGamerName() + " : �����մϴ�! "
						+ m_arrGamer.get(i).m_bettingMoney * 2 + "�� �ԱݵǾ����ϴ�!");
				m_arrGamer.get(i).deposit(m_arrGamer.get(i).m_bettingMoney * 2);
				exchanger.withdrawal(m_arrGamer.get(i).m_bettingMoney * 2);
			} else { // ���ڿ��� �ɽ��� ������ ����..
				System.out.println(m_arrGamer.get(i).getGamerName() + " : ���� ��ȸ��..");
			}
			
			m_arrGamer.get(i).m_bettingMoney = 0;
			m_arrGamer.get(i).m_horseName = null;
		}

		m_bettingMoney = 0;

		h1 = null;
		h2 = null;
		h3 = null;

		State.isFinish = false;
		State.winner = null;
	}

	// ������ �����ϸ� ���� ����
	private void racing() { 
		if ((h1 == null) || (h2 == null) || (h3 == null)) {
			System.out.println("���� ������ �����ϼ���.");
			return;
		} else if (m_arrGamer == null) {
			System.out.println("������ ������ �����ϴ�.");
			return;
		} else if (m_bettingMoney == 0) {
			System.out.println("������ �ؾ� ��Ⱑ ����˴ϴ�.");
			return;
		} else { // ���� ����

			// ������ ����� ������ ����
			t1 = new Thread(h1);
			t2 = new Thread(h2);
			t3 = new Thread(h3);

			// ������ ���� 
			t1.start();
			t2.start();
			t3.start();

			for (;;) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (State.isFinish) {  // ���ְ� ������ ��� ���
					System.out.println("Winner is " + State.winner);
					pw.println(raceCount + "ȸ�� ��� : Winner is " + State.winner);
					pw.flush();
					raceCount++;

					// ������ �ߴ�
					t1.suspend();
					t2.suspend();
					t3.suspend();

					break;
				}
			}
			return;
		}
	}

	// �Է¹��� ������ ��ȯ
	public int getNum() { 
		int input = 0;
		try {
			input = m_scan.nextInt();
		} catch (Exception e) {
			System.out.println("���ڸ� ���� �����մϴ�. �ٽ� �Է��ϼ���.");
			m_scan = new Scanner(System.in);
		}
		return input;
	}

	// ���ֿ� ������ ������ �����Ѵ�
	private void selectHorse(int num) { 
		switch (num) {
		case 1:
			// �糪�� 3���� ����
			h1 = new Donkey("1�� �糪��");
			h2 = new Donkey("2�� �糪��");
			h3 = new Donkey("3�� �糪��");
			break;
		case 2:
			// ��踻 3���� ����
			h1 = new Zebra("1�� ��踻");
			h2 = new Zebra("2�� ��踻");
			h3 = new Zebra("3�� ��踻");
			break;
		case 3:
			// ���ָ� 3���� ����
			h1 = new RaceHorse("1�� ���ָ�");
			h2 = new RaceHorse("2�� ���ָ�");
			h3 = new RaceHorse("3�� ���ָ�");
			break;
		default:
			System.out.println("�ٽ� �����ϼ���.");
			return;
		}
		System.out.println("������ �����߽��ϴ�.");

		// ������ �ٽ� �����ϸ� ���õ� �ٽ� �ؾ��ϹǷ� ���þ� �ʱ�ȭ
		m_bettingMoney = 0;

	}
}