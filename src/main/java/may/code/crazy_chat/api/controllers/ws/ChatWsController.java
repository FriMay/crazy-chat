package may.code.crazy_chat.api.controllers.ws;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import may.code.crazy_chat.api.dto.ChatDto;
import may.code.crazy_chat.api.dto.MessageDto;
import may.code.crazy_chat.api.services.ChatService;
import may.code.crazy_chat.api.services.ParticipantService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
public class ChatWsController {

    ChatService chatService;

    ParticipantService participantService;

    SimpMessagingTemplate messagingTemplate;

    public static final String CREATE_CHAT = "/topic/chats.create";

    public static final String FETCH_CREATE_CHAT_EVENT = "/topic/chats.create.event";
    public static final String FETCH_DELETE_CHAT_EVENT = "/topic/chats.delete.event";

    public static final String SEND_MESSAGE_TO_ALL = "/topic/chats.{chat_id}.messages.send";
    public static final String SEND_MESSAGE_TO_PARTICIPANT = "/topic/chats.{chat_id}.participants.{participant_id}.messages.send";

    public static final String FETCH_MESSAGES = "/topic/chats.{chat_id}.messages";
    public static final String FETCH_PERSONAL_MESSAGES = "/topic/chats.{chat_id}.participants.{participant_id}";

    @MessageMapping(CREATE_CHAT)
    public void createChat(String chatName) {
        chatService.createChat(chatName);
    }

    @SubscribeMapping(FETCH_CREATE_CHAT_EVENT)
    public ChatDto fetchCreateChatEvent() {
        return null;
    }

    @SubscribeMapping(FETCH_DELETE_CHAT_EVENT)
    public ChatDto fetchDeleteChatEvent() {
        return null;
    }

    @MessageMapping(SEND_MESSAGE_TO_ALL)
    public void sendMessageToAll(
            @DestinationVariable("chat_id") String chatId,
            String message,
            @Header String simpSessionId) {

        sendMessage(
                getFetchMessagesDestination(chatId),
                simpSessionId,
                message
        );
    }

    @MessageMapping(SEND_MESSAGE_TO_PARTICIPANT)
    public void sendMessageToParticipant(
            @DestinationVariable("chat_id") String chatId,
            @DestinationVariable("participant_id") String participantId,
            String message,
            @Header String simpSessionId) {

        sendMessage(
                getFetchPersonalMessagesDestination(chatId, participantId),
                simpSessionId,
                message
        );
    }

    @SubscribeMapping(FETCH_MESSAGES)
    public MessageDto fetchMessages(@DestinationVariable("chat_id") String chatId) {
        return null;
    }

    @SubscribeMapping(FETCH_PERSONAL_MESSAGES)
    public MessageDto fetchPersonalMessages(
            @DestinationVariable("chat_id") String chatId,
            @DestinationVariable("participant_id") String participantId,
            @Header String simpSessionId) {

        participantService.handleJoinChat(simpSessionId, participantId, chatId);

        return null;
    }

    private void sendMessage(String destination, String sessionId, String message) {
        messagingTemplate.convertAndSend(
                destination,
                MessageDto.builder()
                        .from(sessionId)
                        .message(message)
                        .build()
        );
    }

    public static String getFetchMessagesDestination(String chatId) {
        return FETCH_MESSAGES.replace("{chat_id}", chatId);
    }

    public static String getFetchPersonalMessagesDestination(String chatId, String participantId) {
        return FETCH_PERSONAL_MESSAGES
                .replace("{chat_id}", chatId)
                .replace("{participant_id}", participantId);
    }
}
