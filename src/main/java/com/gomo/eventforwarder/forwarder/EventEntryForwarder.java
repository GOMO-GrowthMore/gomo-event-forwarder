package com.gomo.eventforwarder.forwarder;

import static com.gomo.eventforwarder.event.EventStatus.*;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gomo.eventforwarder.common.util.JsonParser;
import com.gomo.eventforwarder.event.EventEntry;
import com.gomo.eventforwarder.event.EventEntryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventEntryForwarder {

	private final EventEntryRepository eventEntryRepository;
	private final RabbitTemplate rabbitTemplate;

	@Value("${app.forward-size}")
	private int forwardSize;

	@Scheduled(initialDelay = 1000L, fixedDelay = 3000L)
	public void publishPendingEvents() {
		List<EventEntry> pendingEvents = eventEntryRepository.findByEventStatus(PENDING.name(), forwardSize);

		for (EventEntry entry : pendingEvents) {
			String exchange = TypeConvertor.toExchange(entry.getEventType());
			String routingKey = TypeConvertor.toRoutingKey(entry.getEventType());
			String eventJson = JsonParser.toJson(entry);

			rabbitTemplate.convertAndSend(exchange, routingKey, eventJson);
		}
	}
}
