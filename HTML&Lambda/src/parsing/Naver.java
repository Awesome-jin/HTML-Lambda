package parsing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Naver {

	public static void main(String[] args) {
		// 크게는 2가지 1. 필요한 HTML을 다운로드 ▶ 2. HTML 파싱을 해서 원하는 자료구조를 만들기
		
		//1. 필요한 html 다운
		
		String html = null;
		
		try {
			
			// 1.1) 다운로드 받을 문자열 생성하기
			String address = "https://www.naver.com";
			URL url = new URL(address);
			
			//1.2) 연결을 해준다.
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setConnectTimeout(30000);
			con.setUseCaches(false);
			
			//1.3) 데이터를 다운로드 한다
			StringBuilder sb = new StringBuilder(); //많은 양일땐 string대신 빌더를 쓴다.
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			while(true) {
				String line = br.readLine();
				if(line == null) {
					break;
				}
				sb.append(line+ "\n");
			}
			
			// 1.4 사용한 스트림 정리 (다 닫아주기)
			br.close();
			con.disconnect();
			html = sb.toString();
			//System.out.println(html);  << 제대로 다운로드 됐는지 확인하기
					
		}catch(Exception e) {
			System.err.println("html다운 에러: " + e.getMessage());
			e.printStackTrace();
		}
		
		// 요건 그냥 에러로 안잡힐 수 있으니까 업무에 편하게 넣은 것
		if(html==null) {
			System.out.println("다운로드 받은 문자열이 없습니다.");
			return;
		}
		
		//2. 파싱 > 자료 구조 만들기 (모든 언어의 알고리즘이 동일하다!)
		
		try {
			
			//2.1 문자열을 메모리에 DOM(Document Object Model)로 펼치기
			Document document = Jsoup.parse(html);
			
			//2.2 원하는 태그를 가져오기 (예시는 a 태그로 찾아보자)
			Elements a = document.getElementsByTag("a");
			// 태그는 중복이 가능하기 때문에 여러개를 리턴하는 for문으로 찾아오면 된다.
			for(int i=0; i<a.size(); i=i+1)
			{
				Element element = a.get(i);
				System.out.println(element.text());
			}
			
			/* 참고1! 클래스 이름을 사용해서 찾아오기
			Elements class = document.getElementsByClass("section_footer");
			 */
			
			/*참고2. 선택자를 이용하기 (Devtool > 우클릭 > copyselector로 갖다 붙이면 됨)
			Elements s = document.select("#PM_ID_themecastBody > div > div > div > ul > li:nth-child(2) > a > span.td_tw > span");
			for(int i=0; i<s.size(); i=i+1)
			{
				Element element = s.get(i);
				System.out.println(element.text());
			}
			System.out.println(s.text());
			*/
			
		}catch(Exception e) {
			System.err.println("파싱 에러: " + e.getMessage());
		}

	}

}
