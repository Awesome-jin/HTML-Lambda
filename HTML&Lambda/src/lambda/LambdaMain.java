package lambda;

import java.util.function.Consumer;
import java.util.function.Function;

// 매개변수와 리턴 둘다 없는 경우
interface NoArgNoReturn{
	public void method1();
}

//매개변수는 있고 리턴 값은 없는 경우
interface ArgNoReturn{
	public void method2(int x);
}

// 매개변수는 없고 리턴 타입만 있음 : 거의 안쓰임
interface NoArgReturn{
	public double method3();
}

// 가장 많은 케이스 - 매개변수가 있고 리턴타입도 둘 다 있는 경우
interface ArgReturn{
	public int method4(String str);
}


public class LambdaMain {

	public static void main(String[] args) {
		
		//매개 변수가 없고 리턴도 없는 인터페이스 활용
		NoArgNoReturn ob1 = ( ) -> {System.out.println("매개변수가 없로 리턴도 없는 람다식");};
		ob1.method1( );
		
		ArgNoReturn ob2 = (int x) -> {System.out.println(x+10);};
		ob2.method2(100);

		NoArgReturn ob3 = () -> {return 10.3;};
		double d = ob3.method3();
		System.out.println(d);
		
		// 데이터 전처리 할 때, 많이 함
		ArgReturn ob4 = (str) -> {return Integer.parseInt(str);};
		int i = ob4.method4("12341251");
		System.out.println(i);
		
		//Consumer - 인터페이스는 매개변수가 1개 리턴 타입이 없는 메소드 소유. Generic!
		Consumer <Integer> consumer = (t) -> {System.out.println(t);};
		consumer.accept(3214);
		
		//Function - 매개변수가 1개. 리턴타입이 있는 메소드를 소유
		// Generic에서 앞의 자료형은 매개변수의 자료형 뒤의 자료형은 리턴의 자료형
		// 데이터를 받아서 변환한 후 리턴해주는 메소드
		// Function <T, R> function = ( ) -> { }
		 Function <String, String> function = (str) -> { 
			 if(str.length() <= 3) return str;
			 else return str.substring(0, 3)+ "...";};
			String r = function.apply("hi");
			System.out.println(r);
			r = function.apply("hello lamda?");
			System.out.println(r);
		 }
	}

