package p2pinfiniteworld.context.simulation;

import context.input.GameInput;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocolDecoder;

public class P2PIWServerInput extends GameInput {

	@Override
	protected void init() {
		addPacketReceivedFunction(new P2PIWNetworkProtocolDecoder());
	}

}
