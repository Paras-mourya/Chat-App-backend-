package com.chatapp.backend.payload;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessageRequest {

    private String content;
    private String sender;
    private String roomId;
    private LocalDateTime messageTime;
}
