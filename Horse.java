import java.util.Random;
// �߻�Ŭ���� Horse
abstract public class Horse implements Runnable{
	
	// �ڽ�Ŭ�������� ������ ���� ��ü�� ���� ������
	Random m_rand = new Random();
	
	public int m_maxSpeed = 0;
	public int m_position = 0;
	public int m_goal = 100;
	public String m_hName = null;
	
	// �߻� �޼���. �ڽ�Ŭ�������� �������̵� �Ѵ�.
	abstract void race();
	
}