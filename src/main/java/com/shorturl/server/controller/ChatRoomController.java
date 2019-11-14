package com.shorturl.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shorturl.server.controller.Response.RestBody;
import com.shorturl.server.model.ChatRoom;
import com.shorturl.server.repository.ChatRoomRepository;

@Controller
@RequestMapping("/chatroom")
public class ChatRoomController extends PublicController {
	
	@Autowired
	ChatRoomRepository chatRoomRepository;
	
	/**
	 * 创建一个聊天室
	 * @title 创建一个聊天室
	 * @param name|聊天室的名称|String|必填
	 * @param password|聊天室的密码|String|必填
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public RestBody<ChatRoom> create(String name, String password) {
		
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setName(name);
		chatRoom.setPassword(password);
		chatRoom = chatRoomRepository.save(chatRoom);
		
		return new RestBody<ChatRoom>(chatRoom);
	}
	
	
	
}
