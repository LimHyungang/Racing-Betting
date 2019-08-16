// 경주의 상태를 나타내는 스태틱 변수를 담는 클래스
public class State {  
	// 승자말의 이름을 담을 스태틱 변수
	public static String winner = null;
	
	// 경주의 종료 여부를 나타낼 스태틱 변수
	public static boolean isFinish = false;
	
	// 버튼을 잘못 누르거나, 잔액을 초과하여 베팅하면 다시 베팅하게 할 수 있도록 bettingGamerCount를 센다
	public static int bettingGamerCount = 0;
}
