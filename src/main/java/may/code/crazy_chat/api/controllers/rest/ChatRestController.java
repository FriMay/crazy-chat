package may.code.crazy_chat.api.controllers.rest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import may.code.crazy_chat.api.dto.ChatDto;
import may.code.crazy_chat.api.factories.ChatDtoFactory;
import may.code.crazy_chat.api.services.ChatService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class ChatRestController {

    ChatService chatService;

    ChatDtoFactory chatDtoFactory;

    public static final String FETCH_CHATS = "/api/chats";

    @GetMapping(value = FETCH_CHATS, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ChatDto> fetchChats() {
        return chatService
                .getChats()
                .map(chatDtoFactory::makeChatDto)
                .collect(Collectors.toList());
    }
}
