package parsing;

import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Daumlogin {

	public static void main(String[] args) {
				// 1. 크롬 드라이버의 위치를 프로퍼티에 추가
				System.setProperty("webdriver.chrome.driver", "C:\\Users\\admin\\Downloads\\chromedriver.exe"); //크롬드라이버의 위치를 정확히 적어야 한다.
				WebDriver driver = new ChromeDriver(); //크롬 실행하는 인스턴스 만들기
				driver.get("https://logins.daum.net/accounts/signinform.do?url=https%3A%2F%2Fwww.daum.net%2F"); // 인스턴스에 주소를 넣어주고 사이트에 접속하기
				//System.out.println(driver.getPageSource()); // << 크롬이 자동으로 실행되고 get 명령어의 사이트로 이동하게 됨
				
			
				// 2.아이디와 비밀번호 입력 받기
				Scanner sc = new Scanner(System.in);
				System.out.print("ID : ");
				String id = sc.nextLine();
				System.out.print("PW : ");
				String pw = sc.nextLine();
				
				
				// 3. ID PW를 받아서 입력 내용을 전달하기
				driver.findElement(By.xpath("//*[@id=\"id\"]")).sendKeys(id); //크롬 개발자도구 >> copy > xpath
				driver.findElement(By.xpath("//*[@id=\"inputPwd\"]")).sendKeys(pw);
				driver.findElement(By.xpath("//*[@id=\"loginBtn\"]")).click(); //입력받은 id,pw를 click에 전달해주기
				
				//로그인이 되어 있는 다음에 할 액션 >> 카페 어디 들어가줘 같은 것
				
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				//카페에 들어가줘
				driver.get("http://cafe.daum.net/samhak7");
				
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				// driver를 down이라는 프레임으로 전환 
				driver.switchTo().frame(driver.findElement(By.id("down")));
				System.out.print("입력할 메모: ");
				String memo = sc.nextLine();
				driver.findElement(By.xpath("//*[@id=\"memoForm__memo\"]/div/table/tbody/tr[1]/td[1]/div/textarea")).sendKeys(memo);
				driver.findElement(By.xpath("//*[@id=\"memoForm__memo\"]/div/table/tbody/tr[1]/td[2]/a[1]/span[2]")).click();
				
				sc.close();
				
	}

}
