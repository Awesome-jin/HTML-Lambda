package parsing;

import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class NaverLogin {

	public static void main(String[] args) {

		// 1. 크롬 드라이버의 위치를 프로퍼티에 추가
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\admin\\Downloads\\chromedriver.exe"); // 크롬드라이버의 위치를
																										// 정확히 적어야 한다.
		WebDriver driver = new ChromeDriver(); // 크롬 실행하는 인스턴스 만들기
		driver.get("https://nid.naver.com/nidlogin.login?mode=form&url=https%3A%2F%2Fwww.naver.com"); // 인스턴스에 주소를 넣어주고 사이트에 접속하기
		// System.out.println(driver.getPageSource()); // << 크롬이 자동으로 실행되고 get 명령어의 사이트로
		// 이동하게 됨

		// 2.아이디와 비밀번호 입력 받기
		Scanner sc = new Scanner(System.in);
		System.out.print("ID : ");
		String id = sc.nextLine();
		System.out.print("PW : ");
		String pw = sc.nextLine();

		// 3. ID PW를 받아서 입력 내용을 전달하기
		driver.findElement(By.id("id")).sendKeys(id); //id값은 중복이 안되기 때문에 있으면 Xpath보다 편하다!
		driver.findElement(By.id("pw")).sendKeys(pw);
		driver.findElement(By.xpath("//*[@id=\"log.login\"]")).click(); // 입력받은 id,pw를 click에 전달해주기

		//<참고> 로그인해서 가져와야 하는 부분은 이렇게 가져오면 된다
		String html = driver.getPageSource();
		Document document = Jsoup.parse(html); 
		
		sc.close();

	}

}
