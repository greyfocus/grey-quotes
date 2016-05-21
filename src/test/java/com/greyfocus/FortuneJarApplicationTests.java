package com.greyfocus;

import com.greyfocus.rest.RestValidationConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = QuotesApplication.class)
@WebAppConfiguration
public class FortuneJarApplicationTests {

	@Test
	public void contextLoads() {
	}

}
