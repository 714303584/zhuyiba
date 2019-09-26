package com.shorturl.server.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "short_url_call_log")
public class ShortCallLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "shortId")
	private Long shortId;
	
	@Column(name = "callIp")
	private String callIp;
	
	@Column(name = "callTime")
	private Date callTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getShortId() {
		return shortId;
	}

	public void setShortId(Long shortId) {
		this.shortId = shortId;
	}

	public String getCallIp() {
		return callIp;
	}

	public void setCallIp(String callIp) {
		this.callIp = callIp;
	}

	public Date getCallTime() {
		return callTime;
	}

	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}
	
	

}
