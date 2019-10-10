package com.shorturl.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shorturl.server.model.ShortCallLog;
import com.shorturl.server.repository.ShortCallLogRepository;
import com.shorturl.server.service.ShortCallLogService;
import com.shorturl.util.InviteCodeDigest;



@Controller
public class ShortCallLogController {
	
	
	@Autowired
	private  ShortCallLogService shortCallLogService;
	
	@Autowired 
	ShortCallLogRepository shortCallLogRepository;
	
	
	@RequestMapping(value = "/short/statistics/{code}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity statistics(@PathVariable("code") String code, int page, int pageSize) {
		
		Long id = InviteCodeDigest.codeToId(code);
		
		Page<ShortCallLog> pages = shortCallLogRepository.queryByShortId(id,new PageRequest(page, pageSize, Sort.by(Order.desc("callTime"))));
		return new ResponseEntity(pages, HttpStatus.OK);
		
	}

}
