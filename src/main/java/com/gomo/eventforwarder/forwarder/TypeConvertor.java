package com.gomo.eventforwarder.forwarder;

public class TypeConvertor {

	public static String toExchange(String eventType) {
		return switch (eventType) {
			case "ScoreQuestCompletedEvent", "StreakQuestCompletedEvent", "PointQuestCompletedEvent" -> "quest";
			default -> throw new IllegalStateException("Unexpected value: " + eventType);
		};
	}

	public static String toRoutingKey(String eventType) {
		return switch (eventType) {
			case "ScoreQuestCompletedEvent" -> "quest.event.completed.score";
			case "StreakQuestCompletedEvent" -> "quest.event.completed.streak";
			case "PointQuestCompletedEvent" -> "quest.event.completed.point";
			default -> throw new IllegalStateException("Unexpected value: " + eventType);
		};
	}
}
