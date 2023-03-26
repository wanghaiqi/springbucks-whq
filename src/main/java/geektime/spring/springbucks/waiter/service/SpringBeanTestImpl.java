package geektime.spring.springbucks.waiter.service;

import org.springframework.stereotype.Service;

public class SpringBeanTestImpl implements SpringBeanTest{


       @Override
       public void springBeanTest() {
              System.out.println("测试Spring Bean通过XML转载");
       }
}
