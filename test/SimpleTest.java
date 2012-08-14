package test;

import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class SimpleTest {
	
	@Test
	public void simpleCheck(){
		int a = 1 + 1;
		asserThat(a).isEqualTo(2);
	}

	@Test
	public void findById(){
		running(fakeApplication(), new Runnable(){
			public void run(){
				
			}
		})
	}

}