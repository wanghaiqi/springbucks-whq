package geektime.spring.springbucks.waiter;

import geektime.spring.springbucks.waiter.service.SpringBeanTest;
import org.springframework.beans.factory.annotation.Autowired;


public class Test {

	@Autowired
	public SpringBeanTest springBeanTest;

	@org.junit.Test
	public void test1(){
		springBeanTest.springBeanTest();
	}

}
