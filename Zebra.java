// Horse클래스를 상속받고 Runnable인터페이스를 구현
public class Zebra extends Horse implements Runnable {
	public Zebra(String name) {
		m_maxSpeed = 7;  // Zebra의 최고 속도
		m_position = 0;
		m_goal = 100;
		m_hName = name; 
	}

	@Override
	void race() {  // 부모클래스에게 상속받은 추상메서드 재정의
		for (;;) {
			if (m_position < m_goal) {  // 목표점에 도달하기 전까지 계속 달린다
				m_position += m_rand.nextInt(m_maxSpeed) + 5; // 매 회마다 새로운 속도를 받음 
				System.out.println(m_hName + " : " + m_position + "m");
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {  // 목표점에 도달했을 경우
				State.winner = m_hName;
				State.isFinish = true;

				break;
			}
		}
	}

	@Override  // 스레드를 start하면 시작될 run메서드의 재정의
	public void run() {
		race();
	}

}
