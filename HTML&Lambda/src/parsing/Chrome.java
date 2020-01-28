package parsing;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Chrome {

	public static void main(String[] args) {
	
		// 1. 크롬 드라이버의 위치를 프로퍼티에 추가
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\admin\\Downloads\\chromedriver.exe"); //크롬드라이버의 위치를 정확히 적어야 한다.
		WebDriver driver = new ChromeDriver(); //크롬 실행 하는 인스턴스 만들기
		driver.get("https://www.naver.com"); // 인스턴스에 주소를 넣어주고 사이트에 접속하기
		System.out.println(driver.getPageSource()); // << 크롬이 자동으로 실행되고 get 명령어의 사이트로 이동하게 됨
	}

}
