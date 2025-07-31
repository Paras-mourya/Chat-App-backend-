package com.chatapp.backend.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.backend.entity.Message;
import com.chatapp.backend.entity.Room;
import com.chatapp.backend.repository.RoomRepository;



@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {
    
    @Autowired
    private RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @PostMapping
public ResponseEntity<?> createRoom(@RequestBody String roomIdRaw) {
    String roomId = roomIdRaw.replaceAll("^\"|\"$", "").trim();  // 🧼 Clean string

    if (roomRepository.findByRoomId(roomId) != null) {
        return ResponseEntity.badRequest().body("Room already exists");
    }

    Room room = new Room();
    room.setRoomId(roomId);
    Room savedRoom = roomRepository.save(room);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
}


    // get room:join 

 @GetMapping("/{roomId}")
public ResponseEntity<?> joinRoom(@PathVariable String roomId) {
    roomId = roomId.trim(); // Just in case

    Room room = roomRepository.findByRoomId(roomId);
    if (room == null) {
        return ResponseEntity.badRequest().body("Room not found: " + roomId);
    }
    return ResponseEntity.ok(room);
}

// get messages
@GetMapping("/{roomId}/messages")
public ResponseEntity<?> getMessages(
    @PathVariable String roomId,
    @RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "size", defaultValue = "20") int size) {

    Room room = roomRepository.findByRoomId(roomId);

    if (room == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("Room not found: " + roomId);
    }

    List<Message> messages = room.getMessages();
    if (messages == null) messages = new ArrayList<>();

    int total = messages.size();
    int start = Math.max(0, total - (page + 1) * size);
    int end = Math.min(total, start + size);

    if (start >= total) {
        return ResponseEntity.ok(Collections.emptyList());
    }

    List<Message> paginated = messages.subList(start, end);
    return ResponseEntity.ok(paginated);
}


}
