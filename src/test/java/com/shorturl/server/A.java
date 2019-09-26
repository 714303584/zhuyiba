package com.shorturl.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class A {
	
	
	public static void main(String[] args) {
		try {
			Settings settings = Settings.builder()
					.put("cluster.name", "elasticsearch")
					.put("client.transport.sniff", true)
					.build();
			TransportClient client = new PreBuiltTransportClient(settings)
			        .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
			
			
			
			IndexResponse response = client.prepareIndex("twitter", "_doc", "1").setSource(
					XContentFactory.jsonBuilder().startObject()
					.field("title", "中华人民共和国必将崛起!")
					.field("postDate", new Date()).field("content", "中华人们共和国是一个伟大的国家!").endObject()
					).get();
			
			
			System.out.println(response.getIndex());
			
			
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
