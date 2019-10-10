package com.shorturl.server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shorturl.server.model.ShortCallLog;

public interface ShortCallLogRepository extends JpaRepository<ShortCallLog,Long> {
	
	
	
	Page<ShortCallLog> queryByShortId(long shortId, Pageable pageable);
	
	

}
