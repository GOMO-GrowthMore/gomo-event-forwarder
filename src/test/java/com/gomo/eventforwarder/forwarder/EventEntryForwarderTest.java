package com.gomo.eventforwarder.forwarder;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.gomo.eventforwarder.event.EventEntry;
import com.gomo.eventforwarder.event.EventEntryRepository;
import com.gomo.eventforwarder.event.EventStatus;

@DisplayName("[unit]: 이벤트 포워더 스케줄링 테스트")
@ExtendWith(MockitoExtension.class)
public class EventEntryForwarderTest {

	@InjectMocks
	private EventEntryForwarder sut;

	@Mock
	private EventEntryRepository eventEntryRepository;

	@Mock
	private RabbitTemplate rabbitTemplate;

	@DisplayName("데이터 베이스에서 EventEntry 목록을 조회한 후, 메시지 큐로 전달한다.")
	@Test
	void forward_event_entry() {
		List<EventEntry> eventEntries = List.of(EventEntry.of("ScoreQuestCompletedEvent", "payload", 1L));
		doReturn(eventEntries).when(eventEntryRepository).findByEventStatus(eq(EventStatus.PENDING.name()), eq(0));

		try (MockedStatic<TypeConvertor> mockedStatic = mockStatic(TypeConvertor.class)) {
			mockedStatic.when(() -> TypeConvertor.toExchange("ScoreQuestCompletedEvent")).thenReturn("quest");
			mockedStatic.when(() -> TypeConvertor.toRoutingKey("ScoreQuestCompletedEvent")).thenReturn("quest.event.completed.score");

			sut.publishPendingEvents();

			verify(rabbitTemplate, times(eventEntries.size())).convertAndSend(anyString(), anyString(), anyString());
		}
	}
}
