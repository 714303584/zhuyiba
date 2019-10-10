package com.shorturl.server.controller;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.shorturl.server.controller.Response.RestBody;
import com.shorturl.server.model.ShortCallLog;
import com.shorturl.server.model.ShortUrl;
import com.shorturl.server.repository.ShortCallLogRepository;
import com.shorturl.server.repository.ShortUrlRepository;
import com.shorturl.server.service.ShortCallLogService;
import com.shorturl.server.service.ShortUrlService;
import com.shorturl.util.InviteCodeDigest;

/**
 * 短连接服务
 */
@Controller
public class ShortUrlController extends PublicController {

	@Value("${project.domain}")
	public String Domain;
	
	
	@Autowired
	private  ShortUrlRepository shortUrlRepository;
	
	@Autowired 
	ShortCallLogRepository shortCallLogRepository;
	
	@Autowired
	private  ShortUrlService shortUrlService;
	
	@Autowired
	private  ShortCallLogService shortCallLogService;
	
	
	private RestBody<ShortUrl> longUrlEmpty = new RestBody<ShortUrl>("-1", "需要转链的网址不能为空", null);
	private RestBody<ShortUrl> isNotUrl = new RestBody<ShortUrl>("-1", "需要转链的网址格式不正确", null);
	
	 /**
     * 用于将长连接转短连接
     * @title 长连接转短连接
	 * @param appId|用户的appId|String|必填
	 * @param longUrl|用户的长连接|String|必填
	 * @param duration|保存时间|int|选填
	 */
	@RequestMapping(value = "/stol/api", method = RequestMethod.POST)
	@ResponseBody
	public RestBody<ShortUrl> defaultLogin(String appId, String longUrl,Integer duration) {
		
		if(duration == null || duration == 0) {
			duration = 15;
		}
		
		if(StringUtils.isEmpty(longUrl)) {
			return longUrlEmpty;
		}
		
		if(!longUrl.startsWith("http")) {
			return isNotUrl;
		}
		
		List<ShortUrl> shortUrls = shortUrlRepository.findByLongUrl(longUrl);
		if(shortUrls == null || shortUrls.size() == 0) {
			ShortUrl su = new ShortUrl();
			su.setLongUrl(longUrl);
			su.setCreateTime(new Date());
			su.setDuration(duration);
			su.setExpired(false);
			su = shortUrlRepository.save(su);
			String code = InviteCodeDigest.idToCode(su.getId());
			shortUrlService.setCodeCacheLongUrl(code.toLowerCase(), longUrl);
			System.out.println("cache set -- code:" + code +" longUrl:"+longUrl);
			su.setShortUrl(Domain+InviteCodeDigest.idToCode(su.getId()).toLowerCase());
			return  new RestBody<ShortUrl>(su);
		}else {
			ShortUrl shortUrl = shortUrls.get(0);
			shortUrl.setShortUrl(Domain+InviteCodeDigest.idToCode(shortUrls.get(0).getId()).toLowerCase());
			return  new RestBody<ShortUrl>(shortUrl);
		}
	}
	
    @RequestMapping("/{code}")
    public String redirectView(@PathVariable("code") String code, HttpServletRequest request){
    	
    	String ip = getIpAddr(getRequest());
    	
    	String longUrl =  shortUrlService.getLongUrlByCode(code);
    	
    	Long id = InviteCodeDigest.codeToId(code);
    	
    	if(!shortUrlRepository.existsById(id)) {
    		return "redirect:/static/404.html";
    	}
    	
    	shortCallLogService.save(ip, id);

    	if(!StringUtils.isEmpty(longUrl)) {
    		System.out.println("cache get-- code:" + code +" longUrl:"+longUrl);
    		 return "redirect:"+longUrl;
    	}
    	
    	ShortUrl su = shortUrlRepository.getOne(id);
    	if(su != null) {
    		 return "redirect:"+su.getLongUrl();
    	}else {
    		return "redirect:/static/404.html";
    	}
    	
        //这种跳转都是302跳转，通过一个redirect前缀告诉请求，要跳转到首页
        //所有的redirect请求都会跳转到首页
       
    }
    
    
      
    
    
    

}
