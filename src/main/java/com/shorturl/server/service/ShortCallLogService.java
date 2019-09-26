package com.shorturl.server.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shorturl.server.model.ShortCallLog;
import com.shorturl.server.repository.ShortCallLogRepository;

@Component
public class ShortCallLogService {
	
	@Autowired
	ShortCallLogRepository shortCallLogRepository;
	
	
	public void save(String ip, Long shortUrlId) {
		ShortCallLog shortCallLog = new ShortCallLog();
		shortCallLog.setCallTime(new Date());
		shortCallLog.setCallIp(ip);
		shortCallLog.setShortId(shortUrlId);
		shortCallLogRepository.save(shortCallLog);
		
	}
	
	
	

}
