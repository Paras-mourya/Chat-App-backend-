package com.chatapp.backend.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.chatapp.backend.entity.Message;
import com.chatapp.backend.entity.Room;
import com.chatapp.backend.payload.MessageRequest;
import com.chatapp.backend.repository.RoomRepository;

@Controller
public class ChatController {
    
    @Autowired
    private RoomRepository roomRepository;

    public ChatController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    // for sending messages and receiving messages

    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public Message sendMessage(@DestinationVariable String roomId, @RequestBody MessageRequest request){

       Room  room= roomRepository.findByRoomId(request.getRoomId());
        Message message=new Message();
        message.setContent(request.getContent()); 
        message.setSender(request.getSender());
        message.setTimestamp(LocalDateTime.now());

         if(room !=null){
            room.getMessages().add(message);
            roomRepository.save(room);
         }
         else{
            throw new RuntimeException("room not found");
         }

         return message;
         
    }
}
