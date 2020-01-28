package parsing;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Hanimain {

	public static void main(String[] args) {
		// TASK SUMMARY!  한겨레 신문사 웹 페이지에서 우한으로 검색된 기사 내용 전부 파일에 저장하기
		
		// TASK1.우한으로 검색된 기사 개수 파악하기
		
		int cnt = 0;
		
		try
		{
			//1.1. 반드시 한글로 검색하는 것이기 때문에 사전 인코딩을 해야 한다!
			String keyword = URLEncoder.encode("우한", "utf-8");
			
			//1.2. 주소를 분석해서 이렇게 넣어준다
			String addr = "http://search.hani.co.kr/Search?command=query&keyword=" + keyword + "&media=news&submedia=&sort=d&period=all&datefrom=2000.01.01&dateto=2020.01.28&pageseq=0";
			
			//1.3. (공통) 연결 옵션 설정
			URL url = new URL(addr);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setConnectTimeout(30000);
			con.setUseCaches(false);
			
			//1.4) 데이터를 다운로드 한다
			StringBuilder sb = new StringBuilder(); //많은 양일땐 string대신 빌더를 쓴다.
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			while(true) {
				String line = br.readLine();
				if(line == null) {
					break;
				}
				sb.append(line+ "\n");
			}
			
			// 1.5 사용한 스트림 정리 (다 닫아주기)
			String html = sb.toString();
			br.close();
			con.disconnect();
			//System.out.println(html);  //<< 제대로 다운로드 됐는지 확인하기
			
			
			//2.1 읽어온 html을 dom으로 펼쳐야 한다.
			Document document = Jsoup.parse(html);
			// 2.2 dom으로 가져온 걸 select해서 총 개수를 가져온다
			Elements sec = document.select("#contents > div.search-result-section.first-child > div > span");
			
			String temp = sec.get(0).text();
			//396 건 >> 396으로 어떻게 정수 추출해서 cnt에 저장할까?
			// 띄어쓰기가 있으니까 split 공백단위로 구분 가능하다.
			// (혼자 공부) 396건이었으면? split 기준을 "건"으로 했으면 같은 결과가 나올 것!
			String[] ar = temp.split(" ");
			cnt = Integer.parseInt(ar[0]);
			
			/* 참고1. 만약 태그로 찾아야 했다면?
			Element tag = document.getElementsByTag("span");
			// 참고2. 만약 클래스로 찾아야 했다면?
			Element tag = document.getElementsByClass("total");
			*/

		}catch(Exception e) {
			System.err.println("개수파악 에러 : " + e.getMessage());
			e.getStackTrace();
		}
		
		// TASK2. 검색된 데이터의 링크를 전부 찾아와서 list에 삽입해야 한다.
		if(cnt ==0) {
			System.out.println("검색된 기사가 없습니다.");
			return;
		}
		
		// 2.0. 내용을 넣어줄 리스트를 만들어보자
		ArrayList <String> list = new ArrayList<String>();
		
		try {
			//2.1 페이지 개수 구하기
			int pageno = cnt/10;
			if(pageno%10 != 0) {
				pageno= pageno+1;
			}
			
			//2.2. 페이지 단위로 순회
			for(int i =0; i<pageno; i=i+1) {
				//반드시 한글로 검색하는 것이기 때문에 사전 인코딩을 해야 한다!
				String keyword = URLEncoder.encode("우한", "utf-8");
				
				//주소를 분석해서 이렇게 넣어준다
				String addr = "http://search.hani.co.kr/Search?command=query&keyword=" + keyword + "&media=news&submedia=&sort=d&period=all&datefrom=2000.01.01&dateto=2020.01.28&pageseq="+i;
				
				// (공통) 연결 옵션 설정
				URL url = new URL(addr);
				HttpURLConnection con = (HttpURLConnection)url.openConnection();
				con.setConnectTimeout(30000);
				con.setUseCaches(false);			
				
				// 2.3. 특정 페이지에서 데이터를 못읽어도 다음 페이지를 계속 읽을수있게 try-catch로 삽입
				try {
					StringBuilder sb = new StringBuilder();
					BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
					while(true) {
						String line = br.readLine();
						if(line==null) {
							break;
						}
						sb.append(line + "\n");
					}
					
					//2.4(공통) 사용했으면 닫아주기
					String html = sb.toString();
					br.close();
					con.disconnect();
					
					//2.5 html을 dom으로 펼치고 원하는 선택자를 갖다 넣자
					Document document = Jsoup.parse(html);
					Elements links = document.select("#contents > div.search-result-section.first-child > ul > li.first-child > dl > dt > a");
					
					//2.6 주소를 가져왔으면 리스트에 전부 넣어주자 (get().attr() 이용★)
					for(int j=0; j<links.size(); j=j+1) {
						//System.out.println(links.get(j).attr("href")); 
						//참고3. 제목은 links.get(j.).text();
						list.add(links.get(j).attr("href")); //attr은 링크를 가져오는것. 그래서 리스트에 다 갖다 넣어준것
					}
					
				}
				catch(Exception e) {
					System.err.println("읽어오기 에러: " + e.getMessage());
					e.printStackTrace();
				}			
			}
			
		}catch(Exception e) {
			System.err.println("링크 수집 예외 : " + e.getMessage());
			e.getStackTrace();
		}
		
		//TASK3. list에 저장된 링크의 데이터를 전부 읽어서 파일에 저장하기
		try {
			for(String addr : list) {
				try {
					//3.1 연결하기
					URL url  = new URL(addr);
					HttpURLConnection con = (HttpURLConnection)url.openConnection();
					con.setUseCaches(false);
					con.setConnectTimeout(10000);
					
					//3.2 (공통 구문) 읽어오기
					StringBuilder sb = new StringBuilder();
					BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
					while(true) {
						String line = br.readLine();
						if(line==null) {
							break;
						}
						sb.append(line + "\n");
					}
					
					//3.3 (공통 구문) 연결 끊기. 제대로 읽었는지 확인하기
					String html=sb.toString();
					br.close();
					con.disconnect();
					//System.out.println(html);
					
					// 오만 코드 다 읽어버리기 때문에 필요한 부분 일단은 타이틀만 긁어보자
					Document document = Jsoup.parse(html);
					Elements articles = document.getElementsByClass("title");
					for(int i=0; i<articles.size(); i=i+1) {
					
					PrintWriter pw = new PrintWriter(new FileOutputStream("./우한title.txt"));
					pw.println(html);
					pw.flush();
					pw.close();
					}
					
					//실제 여러개 페이지에서 스크래핑 할 때는 딜레이를 주자
					//Thread.sleep(1000); 너무 많을때 쉬는시간 주기
					
				}
				catch(Exception e) {
					System.err.println("기사 읽기 예외 : " + e.getMessage());
					e.getStackTrace();
				}
			}
		}
		catch(Exception e) {
			System.err.println("파일 저장 에러 : " + e.getMessage());
			e.getStackTrace();
		}

	}

}
