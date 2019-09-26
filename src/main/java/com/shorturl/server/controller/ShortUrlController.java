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

@Controller
public class ShortUrlController {

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
	
	@RequestMapping(value = "/stol/api", method = RequestMethod.GET)
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
    
    
    /**
     * 获取Request
     */
    public HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getRequest();
    }
    
    public HttpServletResponse getResponse() {
    	ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
    	return servletRequestAttributes.getResponse();
    }
    
    private String getIpAddr(HttpServletRequest request) {   
           String ip = request.getHeader("x-forwarded-for");   
           if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
               ip = request.getHeader("Proxy-Client-IP");   
           }   
           if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
               ip = request.getHeader("WL-Proxy-Client-IP");   
           }   
           if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
               ip = request.getRemoteAddr();   
               if(ip.equals("127.0.0.1")){     
                   //根据网卡取本机配置的IP     
                   InetAddress inet=null;     
                   try {     
                       inet = InetAddress.getLocalHost();     
                   } catch (Exception e) {     
                       e.printStackTrace();     
                   }     
                   ip= inet.getHostAddress();     
               }  
           }   
           // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
           if(ip != null && ip.length() > 15){    
               if(ip.indexOf(",")>0){     
                   ip = ip.substring(0,ip.indexOf(","));     
               }     
           }     
           return ip;   
    }  
    
    
    

}
