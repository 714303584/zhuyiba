package com.shorturl.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shorturl.server.model.ShortUrl;

 
public interface ShortUrlRepository extends JpaRepository<ShortUrl,Long> {
	
	
	@Query (value = "select su from  ShortUrl su where su.longUrl=:longUrl")
    public List<ShortUrl> findByLongUrl(@Param("longUrl") String longUrl);
	
	
}