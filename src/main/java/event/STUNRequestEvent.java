package event;

import common.event.GameEvent;
import common.source.NetworkSource;

public class STUNRequestEvent extends GameEvent {

	private static final long serialVersionUID = 2193277329139319192L;

	private long nonce;
	private long timestamp;

	public STUNRequestEvent(long time, NetworkSource source, long timestamp, long nonce) {
		super(time, source);
		this.timestamp = timestamp;
		this.nonce = nonce;
	}

	public long getNonce() {
		return nonce;
	}

	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public NetworkSource source() {
		return (NetworkSource) super.source();
	}

}
