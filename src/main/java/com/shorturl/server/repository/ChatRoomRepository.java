package com.shorturl.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shorturl.server.model.ChatRoom;
import com.shorturl.server.model.ShortUrl;

 
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
	
	
}