package com.chatapp.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatapp.backend.entity.Room;

public interface RoomRepository extends MongoRepository<Room, String> {
    // get room using roomId

    Room findByRoomId(String roomId);

}
