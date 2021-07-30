package may.code.crazy_chat.api.factories;

import may.code.crazy_chat.api.domains.Participant;
import may.code.crazy_chat.api.dto.ParticipantDto;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ParticipantDtoFactory {

    public ParticipantDto makeParticipantDto(Participant participant) {
        return ParticipantDto.builder()
                .id(participant.getId())
                .enterAt(Instant.ofEpochMilli(participant.getEnterAt()))
                .build();
    }
}
