package may.code.crazy_chat.api.controllers.ws;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import may.code.crazy_chat.api.dto.ParticipantDto;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
public class ParticipantWsController {

    public static final String FETCH_PARTICIPANT_JOIN_IN_CHAT = "/topic/chats.{chat_id}.participants.join";
    public static final String FETCH_PARTICIPANT_LEAVE_FROM_CHAT = "/topic/chats.{chat_id}.participants.leave";

    @SubscribeMapping(FETCH_PARTICIPANT_JOIN_IN_CHAT)
    public ParticipantDto fetchParticipantJoinInChat() {
        return null;
    }

    @SubscribeMapping(FETCH_PARTICIPANT_LEAVE_FROM_CHAT)
    public ParticipantDto fetchParticipantLeaveFromChat() {
        return null;
    }

    public static String getFetchParticipantJoinInChatDestination(String chatId) {
        return FETCH_PARTICIPANT_JOIN_IN_CHAT.replace("{chat_id}", chatId);
    }

    public static String getFetchParticipantLeaveFromChatDestination(String chatId) {
        return FETCH_PARTICIPANT_LEAVE_FROM_CHAT.replace("{chat_id}", chatId);
    }
}
