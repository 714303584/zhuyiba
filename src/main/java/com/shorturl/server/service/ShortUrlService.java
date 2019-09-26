package com.shorturl.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Component
public class ShortUrlService {
	
	public static final String LONGURL_CACHE_ID = "short_server_longurl_id";
	
	public static final String CODE_CACHE_LONGURL = "short_server_code_longurl";
	
	@Autowired
	RedisTemplate<String, String> redis;
	
	
	public Integer getCacheUrlByLongUrl(String longUrl) {
		return  Integer.parseInt(redis.opsForHash().get(LONGURL_CACHE_ID, longUrl).toString());
	}
	
	public String getLongUrlByCode(String code) {
		Object  obj = redis.opsForHash().get(CODE_CACHE_LONGURL, code);
		if(obj != null)
			return obj.toString();
		return null;
	}
	
	public void setCodeCacheLongUrl(String code, String longUrl) {
		redis.opsForHash().put(CODE_CACHE_LONGURL,code, longUrl);
	}

}
