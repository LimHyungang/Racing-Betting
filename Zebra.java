// HorseŬ������ ��ӹް� Runnable�������̽��� ����
public class Zebra extends Horse implements Runnable {
	public Zebra(String name) {
		m_maxSpeed = 7;  // Zebra�� �ְ� �ӵ�
		m_position = 0;
		m_goal = 100;
		m_hName = name; 
	}

	@Override
	void race() {  // �θ�Ŭ�������� ��ӹ��� �߻�޼��� ������
		for (;;) {
			if (m_position < m_goal) {  // ��ǥ���� �����ϱ� ������ ��� �޸���
				m_position += m_rand.nextInt(m_maxSpeed) + 5; // �� ȸ���� ���ο� �ӵ��� ���� 
				System.out.println(m_hName + " : " + m_position + "m");
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {  // ��ǥ���� �������� ���
				State.winner = m_hName;
				State.isFinish = true;

				break;
			}
		}
	}

	@Override  // �����带 start�ϸ� ���۵� run�޼����� ������
	public void run() {
		race();
	}

}
