package may.code.crazy_chat.api.factories;

import may.code.crazy_chat.api.domains.Chat;
import may.code.crazy_chat.api.domains.Participant;
import may.code.crazy_chat.api.dto.ChatDto;
import may.code.crazy_chat.api.dto.ParticipantDto;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ChatDtoFactory {

    public ChatDto makeChatDto(Chat chat) {
        return ChatDto.builder()
                .id(chat.getId())
                .name(chat.getName())
                .createdAt(Instant.ofEpochMilli(chat.getCreatedAt()))
                .build();
    }
}
