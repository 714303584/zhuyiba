package com.shorturl.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.data.domain.Sort.Order;

import com.shorturl.server.model.ShortCallLog;
import com.shorturl.server.repository.ShortCallLogRepository;
import com.shorturl.server.service.ShortCallLogService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerApplicationTests {
	
	@Autowired
	private  ShortCallLogService shortCallLogService;
	
	@Autowired 
	ShortCallLogRepository shortCallLogRepository;

	@Test
	public void contextLoads() {
		
//		
//		ShortCallLog scl = new ShortCallLog();
//		scl.setShortId();
//		shortCallLogRepository.count(scl);
		
		Pageable page = new PageRequest(0, 10, Sort.by(Order.desc("callTime")));
		
		ShortCallLog scl = new ShortCallLog();
		scl.setShortId(1L);
		
		Example<ShortCallLog> example = Example.of(scl); 
		
		
		Page<ShortCallLog> scls = shortCallLogRepository.findAll(example,page);
		
		
		
//		Page<ShortCallLog> scls = shortCallLogRepository.queryByShortId(1,page);
//		
		System.out.println(scls.getTotalPages());
		scls.getContent().forEach(item -> {
			System.out.println(item.toString());
		});
		
	}

}
