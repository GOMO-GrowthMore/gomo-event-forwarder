package com.gomo.eventforwarder.forwarder;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.assertj.core.api.Assertions;
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

@DisplayName("[unit]: 타입 변환 테스트")
@ExtendWith(MockitoExtension.class)
public class TypeConvertorTest {

	@DisplayName("ScoreQuestCompletedEvent 타입의 exchange는 quest이다.")
	@Test
	void convert_ScoreQuestCompletedEvent_type_to_exchange() {
		String exchange = TypeConvertor.toExchange("ScoreQuestCompletedEvent");

		assertThat(exchange).isEqualTo("quest");
	}

	@DisplayName("StreakQuestCompletedEvent 타입의 exchange는 quest이다.")
	@Test
	void convert_StreakQuestCompletedEvent_type_to_exchange() {
		String exchange = TypeConvertor.toExchange("StreakQuestCompletedEvent");

		assertThat(exchange).isEqualTo("quest");
	}

	@DisplayName("PointQuestCompletedEvent 타입의 exchange는 quest이다.")
	@Test
	void convert_PointQuestCompletedEvent_type_to_exchange() {
		String exchange = TypeConvertor.toExchange("PointQuestCompletedEvent");

		assertThat(exchange).isEqualTo("quest");
	}

	@DisplayName("알수 없는 타입은 exchange로 변환할 수 없다.")
	@Test
	void cannot_convert_unknown_type_to_exchange() {
		assertThatThrownBy(() -> TypeConvertor.toExchange("unknown"))
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("Unexpected value: unknown");
	}

	@DisplayName("ScoreQuestCompletedEvent 타입의 routing key는 quest.event.completed.score이다.")
	@Test
	void convert_ScoreQuestCompletedEvent_type_to_routing_key() {
		String routingKey = TypeConvertor.toRoutingKey("ScoreQuestCompletedEvent");

		assertThat(routingKey).isEqualTo("quest.event.completed.score");
	}

	@DisplayName("StreakQuestCompletedEvent 타입의 routing key는 quest.event.completed.streak이다.")
	@Test
	void convert_StreakQuestCompletedEvent_type_to_routing_key() {
		String routingKey = TypeConvertor.toRoutingKey("StreakQuestCompletedEvent");

		assertThat(routingKey).isEqualTo("quest.event.completed.streak");
	}

	@DisplayName("PointQuestCompletedEvent 타입의 routing key는 quest.event.completed.point이다.")
	@Test
	void convert_PointQuestCompletedEvent_type_to_routing_key() {
		String routingKey = TypeConvertor.toRoutingKey("PointQuestCompletedEvent");

		assertThat(routingKey).isEqualTo("quest.event.completed.point");
	}

	@DisplayName("알수 없는 타입은 exchange로 변환할 수 없다.")
	@Test
	void cannot_convert_unknown_type_to_routing_key() {
		assertThatThrownBy(() -> TypeConvertor.toRoutingKey("unknown"))
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("Unexpected value: unknown");
	}
}
