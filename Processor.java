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

	// Gamer를 담을 어레이리스트
	private ArrayList<Gamer> m_arrGamer = null;
	// 베팅과 당첨금 수령을 도와줄 Exchanger
	Exchanger exchanger = null;

	// 말과 스레드 객체를 담을 변수
	Horse h1 = null;
	Horse h2 = null;
	Horse h3 = null;
	Thread t1 = null;
	Thread t2 = null;
	Thread t3 = null;

	// 파일 입출에 필요한 변수들
	PrintWriter pw = null;
	BufferedReader br = null;
	int raceCount = 1;

	public Processor() {
		exchanger = new Exchanger("Exchanger", 999999999); 
		m_arrGamer = new ArrayList<Gamer>();

		// 경주 결과를 파일에 입력할 PrintWriter
		try {
			pw = new PrintWriter("RacingResult.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// 파일에 입력된 경주 결과를 읽어올 BufferedReader
		try {
			br = new BufferedReader(new FileReader("RacingResult.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		showMessage();
		
	}

	// 환영 메세지 and 작업 선택
	private void showMessage() {
		m_scan = new Scanner(System.in);
		for (;;) {
			System.out.println("경마장에 오신 것을 환영합니다. 진행하실 작업을 선택하세요.");
			System.out.println("1 : 게임 시작");
			System.out.println("2 : 마종 선택");
			System.out.println("3 : 유저 추가");
			System.out.println("4 : 베팅");
			System.out.println("5 : 잔액 확인");
			System.out.println("6 : 이전 회차 결과 확인");
			System.out.println("7 : 나가기");

			m_selectedNum = getNum();
			selectWork(m_selectedNum);

			// 7번 선택하면 종료
			if (m_selectedNum == 7)
				break;
		}
		System.out.println("경마 게임을 종료합니다.");
	}

	// 작업 선택
	private void selectWork(int num) {
		for (;;) {
			switch (num) {
			case 1:
				racing();
				// 말을 선택하지 않았거나, Gamer가 한 명도 없거나, 베팅을 안 하면 게임 시작 불가
				if ((h1 == null) || (h2 == null) || (h3 == null) || (m_arrGamer == null) || (m_bettingMoney == 0)) {
					return; // line 41로
				}

				for (;;) { // 경주를 진행하며 우승마가 나오는지 계속 확인
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (State.isFinish) {
						resultAndReset(); // 우승마가 나오면 베팅결과에 따라 입출금하고 주요 변수들을 reset
						break; // line 70으로
					}
				}
				break; // line 94로
			case 2:
				System.out.println("경주에 참가할 마종을 선택하세요.");
				System.out.println("[1] 당나귀");
				System.out.println("[2] 얼룩말");
				System.out.println("[3] 경주마");
				selectHorse(getNum());
				break; // line 94로
			case 3:
				addGamer(); // Gamer 추가
				break; // line 94로
			case 4:
				choiceMyHorse(); // 생성된 말들에 베팅
				break; // line 94로
			case 5:
				checkBalance(); // exchanger와 Gamer들의 잔액 조회
				break; // line 94로
			case 6:
				confirmResult();
				break; // line 94로
			case 7:
				return; // line 41로
			default:
				System.out.println("다시 선택해주세요.");
				return; // line 41로
			}

			return; // line 41로
		}
	}

	// 역대 경주 결과 확인. 파일에서 읽어온 결과를 출력한다
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

	// Exchanger와 Gamer들의 잔액 조회
	public void checkBalance() { 
		System.out.println(exchanger.getExchangerName() + "님의 잔액 " + exchanger.getBalance() + "원");
		for (int i = 0; i < m_iGamerCnt; i++)
			System.out.println(m_arrGamer.get(i).getGamerName() + "님의 잔액 " + m_arrGamer.get(i).getBalance() + "원");
	}

	// 생성된 말들에 베팅
	private void choiceMyHorse() { 
		if ((h1 == null) || (h2 == null) || (h3 == null)) {
			System.out.println("먼저 마종을 선택하세요.");
		} else if (m_iGamerCnt == 0) {
			System.out.println("유저가 없습니다.");
		} else {  // bettingGamerCount를 세는 이유 : 버튼을 잘못 누르거나, 잔액을 초과하여 베팅하면 다시 베팅하게 할 수 있도록
			for (State.bettingGamerCount = 0; State.bettingGamerCount < m_iGamerCnt; State.bettingGamerCount++) {
				System.out.println(m_arrGamer.get(State.bettingGamerCount).getGamerName() + "님이 베팅합니다.");

				System.out.println("어느 말에 베팅하시겠습니까?");
				System.out.println(h1.m_hName);
				System.out.println(h2.m_hName);
				System.out.println(h3.m_hName);

				switch (getNum()) { // Gamer의 m_horseName으로 내가 선택한 말의 이름을 기억한다
				case 1:
					// 1번 말에 베팅
					m_arrGamer.get(State.bettingGamerCount).m_horseName = h1.m_hName;
					System.out.println("베팅액수를 입력하세요. (맞추면 2배)");
					m_bettingMoney = getNum();
					m_arrGamer.get(State.bettingGamerCount).betting(m_bettingMoney);
					break;
				case 2:
					// 2번 말에 베팅
					m_arrGamer.get(State.bettingGamerCount).m_horseName = h2.m_hName;
					System.out.println("베팅액수를 입력하세요. (맞추면 2배)");
					m_bettingMoney = getNum();
					m_arrGamer.get(State.bettingGamerCount).betting(m_bettingMoney);
					break;
				case 3:
					// 3번 말에 베팅
					m_arrGamer.get(State.bettingGamerCount).m_horseName = h3.m_hName;
					System.out.println("베팅액수를 입력하세요. (맞추면 2배)");
					m_bettingMoney = getNum();
					m_arrGamer.get(State.bettingGamerCount).betting(m_bettingMoney);
					break;
				default:
					System.out.println("default of betting : 잘못된 선택입니다.");
					m_bettingMoney = 0;
					// 다시 베팅을 진행할 수 있도록 bettingGamerCount (
					State.bettingGamerCount--;
					break;
				}

			}
			System.out.println("베팅이 완료 되었습니다.");
		}
	}

	private void addGamer() { // Gamer 추가
		m_arrGamer.add(new Gamer("고객" + (m_iGamerCnt + 1), 10000));
		System.out.println(m_arrGamer.get(m_iGamerCnt).getGamerName() + "님이 추가되었습니다." + "\n고객님의 잔액은 "
				+ m_arrGamer.get(m_iGamerCnt).getBalance() + "원입니다.");
		m_iGamerCnt++;
	}

	private void resultAndReset() { // 결과에 따라 입출금, 다음 게임을 위해 변수들 초기화
		for (int i = 0; i < m_iGamerCnt; i++) {
			// 일단 베팅한 금액 차감
			m_arrGamer.get(i).withdrawal(m_arrGamer.get(i).m_bettingMoney);
			exchanger.deposit(m_arrGamer.get(i).m_bettingMoney);

			// 승자 Gamer에겐 베팅금액 2배 입금
			if (m_arrGamer.get(i).m_horseName.equals(State.winner)) {
				System.out.println(m_arrGamer.get(i).getGamerName() + " : 축하합니다! "
						+ m_arrGamer.get(i).m_bettingMoney * 2 + "원 입금되었습니다!");
				m_arrGamer.get(i).deposit(m_arrGamer.get(i).m_bettingMoney * 2);
				exchanger.withdrawal(m_arrGamer.get(i).m_bettingMoney * 2);
			} else { // 패자에겐 심심한 위로의 말을..
				System.out.println(m_arrGamer.get(i).getGamerName() + " : 다음 기회에..");
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

	// 조건을 충족하면 경주 시작
	private void racing() { 
		if ((h1 == null) || (h2 == null) || (h3 == null)) {
			System.out.println("먼저 마종을 선택하세요.");
			return;
		} else if (m_arrGamer == null) {
			System.out.println("생성된 유저가 없습니다.");
			return;
		} else if (m_bettingMoney == 0) {
			System.out.println("베팅을 해야 경기가 진행됩니다.");
			return;
		} else { // 경주 시작

			// 생성된 말들로 스레드 생성
			t1 = new Thread(h1);
			t2 = new Thread(h2);
			t3 = new Thread(h3);

			// 스레드 시작 
			t1.start();
			t2.start();
			t3.start();

			for (;;) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (State.isFinish) {  // 경주가 끝나면 결과 출력
					System.out.println("Winner is " + State.winner);
					pw.println(raceCount + "회차 경기 : Winner is " + State.winner);
					pw.flush();
					raceCount++;

					// 스레드 중단
					t1.suspend();
					t2.suspend();
					t3.suspend();

					break;
				}
			}
			return;
		}
	}

	// 입력받은 정수를 반환
	public int getNum() { 
		int input = 0;
		try {
			input = m_scan.nextInt();
		} catch (Exception e) {
			System.out.println("숫자만 선택 가능합니다. 다시 입력하세요.");
			m_scan = new Scanner(System.in);
		}
		return input;
	}

	// 경주에 참가할 마종을 선택한다
	private void selectHorse(int num) { 
		switch (num) {
		case 1:
			// 당나귀 3마리 생성
			h1 = new Donkey("1번 당나귀");
			h2 = new Donkey("2번 당나귀");
			h3 = new Donkey("3번 당나귀");
			break;
		case 2:
			// 얼룩말 3마리 생성
			h1 = new Zebra("1번 얼룩말");
			h2 = new Zebra("2번 얼룩말");
			h3 = new Zebra("3번 얼룩말");
			break;
		case 3:
			// 경주마 3마리 생성
			h1 = new RaceHorse("1번 경주마");
			h2 = new RaceHorse("2번 경주마");
			h3 = new RaceHorse("3번 경주마");
			break;
		default:
			System.out.println("다시 선택하세요.");
			return;
		}
		System.out.println("마종을 선택했습니다.");

		// 마종을 다시 선택하면 베팅도 다시 해야하므로 베팅액 초기화
		m_bettingMoney = 0;

	}
}