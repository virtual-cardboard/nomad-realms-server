package p2pinfiniteworld.model;

import static java.time.LocalDateTime.now;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

public class LogMessages {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

	private Stack<LocalDateTime> timestamps = new Stack<>();
	private Stack<String> sources = new Stack<>();
	private Stack<String> messages = new Stack<>();

	private boolean showTimestamps = true;

	public void setShowTimestamps(boolean bool) {
		showTimestamps = bool;
	}

	public void addMessage(String source, String message) {
		addMessage(now(), source, message);
	}

	public void addMessage(LocalDateTime time, String source, String message) {
		timestamps.add(time);
		sources.add(source);
		messages.add(message);
	}

	public String get(int index) {
		String string = "";
		if (showTimestamps) {
			String time = timestamps.get(timestamps.size() - index - 1).format(DATE_TIME_FORMATTER);
			string += "[" + time + "]";
		}
		String source = sources.get(sources.size() - index - 1);
		string += "[" + source + "]: ";
		String message = messages.get(messages.size() - index - 1);
		string += message;
		return string;
	}

	public int size() {
		return messages.size();
	}

}
