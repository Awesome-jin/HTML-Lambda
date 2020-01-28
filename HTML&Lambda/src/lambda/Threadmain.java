package lambda;

public class Threadmain {

	public static void main(String[] args) {

		// 2. Runnable 인터페이스를 구현한 클래스를 이용해서 스레드 생성 및 실행
		// ▶ 인스턴스를 1개만 만들어서 사용 하면 메모리 낭비 - 클래스는 메모리에서 삭제가 안되니까
		Thread t1 = new Thread(new RunnableImpl());
		t1.start();

		// 클래스를 만들지 않고 사용하는 Anonymous class 방법
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					for (int i = 0; i < 10; i = i + 1) {
						Thread.sleep(1000);
						System.out.println("Anonymous class 이용 " + i);
					}
				} catch (Exception e) {
					System.err.println("예외발생: " + e.getMessage());
					e.getStackTrace();
				}
			}
		});

		// 람다 이용
		// Runnable이라는 인스턴스도 별도로 없고 메소드 이름도 없다.
		
		Thread t3 = new Thread(
				( ) -> {
			try {
				for (int i = 0; i < 10; i = i + 1) {
					Thread.sleep(1000);
					System.out.println("Anonymous class 이용 " + i);
				}
			} catch (Exception e) {
				System.err.println("예외발생: " + e.getMessage());
				e.getStackTrace();
			}
		});
		t3.start();
	}
}
