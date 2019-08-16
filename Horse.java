import java.util.Random;
// 추상클래스 Horse
abstract public class Horse implements Runnable{
	
	// 자식클래스에게 물려줄 랜덤 객체와 각종 변수들
	Random m_rand = new Random();
	
	public int m_maxSpeed = 0;
	public int m_position = 0;
	public int m_goal = 100;
	public String m_hName = null;
	
	// 추상 메서드. 자식클래스에서 오버라이딩 한다.
	abstract void race();
	
}