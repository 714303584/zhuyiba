/**
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the 'License'); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an 'AS IS' BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
$(document).ready(function(){
  var client, destination;

  
  
  var ChatosExamle = {
			Message : {
				add : function(message, type) {
					var chat_body = $('.layout .content .chat .chat-body');
					if (chat_body.length > 0) {
						type = type ? type : '';
						message = message ? message : 'Lorem ipsum dolor sit amet.';
						
						
						var msgType = message.tweet.type;
						
						if(msgType == 1){
							$('.layout .content .chat .chat-body .messages')
							.append(
									'<div class="message-item '
											+ type
											+ '">'
											+'<div>'
											+'<div class="chat-header" style="padding-bottom: 0px;">'
											+ '<div class="chat-header-user">'
											+'<figure class="avatar avatar-lg">'
											+'	<img src="'+message.user.avatar+'" class="rounded-circle">'
											+'</figure>'
											+'<div>'
											+	'<h5>'+message.user.nickname+'</h5>'
											+	'<small class="text-muted"> <i>Online</i>'
											+	'</small>'
											+'</div>'
										+ '</div>'
									+'</div>'
											
											+'<div class="message-content">'
											+ message.tweet.txt
											+ '</div>'
											+ '</div>'
											+'<div class="message-action">PM 14:25 '
											+ (type ? '<i class="ti-check"></i>'
													: '') + '</div></div>');
							
						}else if(msgType == 2){
							
							$('.layout .content .chat .chat-body .messages')
							.append(
									'<div class="message-item '
											+ type
											+ '">'
											+'<div>'
											+'<div class="chat-header" style="padding-bottom: 0px;">'
											+ '<div class="chat-header-user">'
											+'<figure class="avatar avatar-lg">'
											+'	<img src="'+message.user.avatar+'" class="rounded-circle">'
											+'</figure>'
											+'<div>'
											+	'<h5>'+message.user.nickname+'</h5>'
											+	'<small class="text-muted"> <i>Online</i>'
											+	'</small>'
											+'</div>'
										+ '</div>'
											+'</div>'
													
											+'<div class="message-content">'
											+ '<img src="'
											+message.tweet.txt
											+'">'
											+ '</div>'
											+'</div>'
											+'<div class="message-action">PM 14:25 '
											+ (type ? '<i class="ti-check"></i>'
													: '') + '</div></div>');
							
						}
						
						
						//<div class="message-item outgoing-message" style="">
//					    <div>
//					    <div class="chat-header" style="padding-bottom: 0px;">
//										<div class="chat-header-user">
//											<figure class="avatar avatar-lg">
//												<img src="./Soho - Chat and Discussion Platform_files/man_avatar3.jpg" class="rounded-circle">
//											</figure>
//											<div>
//												<h5>Karl Hubane</h5>
//												<small class="text-muted"> <i>Online</i>
//												</small>
//											</div>
//										</div>
//										
//									</div>
//					        
//					    		<div class="message-content">I know how important this file
//													is to you. You can trust me ;)</div>
//					    </div>
//												<div class="message-action">
//													Pm 14:50 <i class="ti-double-check"></i>
//												</div>
//											</div>
//						
				
						
						
						
						
						
						chat_body.scrollTop(chat_body.get(0).scrollHeight, -1)
								.niceScroll({
									cursorcolor : 'rgba(66, 66, 66, 0.20)',
									cursorwidth : "4px",
									cursorborder : '0px'
								});
					}
				}
			}
		};

    // the client is notified when it is connected to the server.
    var onConnect = function(frame) {
      debug("connected to MQTT");
//      $('#connect').fadeOut({ duration: 'fast' });
//      $('#disconnect').fadeIn();
//      $('#send_form_input').removeAttr('disabled');
      client.subscribe(destination);
    };  

    // this allows to display debug logs directly on the web page
    var debug = function(str) {
    	console.log(str);
    };  

  $('#disconnect_form').submit(function() {
    client.disconnect();
    $('#disconnect').fadeOut({ duration: 'fast' });
    $('#connect').fadeIn();
    $('#send_form_input').addAttr('disabled');
    return false;
  });

  $('#send_form').submit(function() {
    var text = $('#send_form_input').val();
    if (text) {
      message = new Messaging.Message(text);
      message.destinationName = destination;
      client.send(message);
      $('#send_form_input').val("");
    }
    return false;
  });

  function onFailure(failure) {
    debug("failure");
    debug(failure.errorMessage);
  }  

  function onMessageArrived(message) {
	  var data =  $.parseJSON(message.payloadString);
	  
	  ChatosExamle.Message.add(data, 'outgoing-message');
  }

  function onConnectionLost(responseObject) {
    if (responseObject.errorCode !== 0) {
      debug(client.clientId + ": " + responseObject.errorCode + "\n");
    }
  }

  
  var host ="ifreeshare.com";    
  var port = 61614;
  var clientId = "test-01-zhuss";
  destination = "/topic/14teh4revoms51rfv1pbme4qg1";
  
  client = new Messaging.Client(host, Number(port), clientId);

  client.onConnect = onConnect;

  client.onMessageArrived = onMessageArrived;
  client.onConnectionLost = onConnectionLost;            

  client.connect({onSuccess:onConnect, onFailure:onFailure}); 

});