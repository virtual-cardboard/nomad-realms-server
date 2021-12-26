package context.p2pinfiniteworld.simulation;

import context.input.GameInput;
import networking.protocols.NomadRealmsServerProtocolDecoder;

public class P2PIWServerInput extends GameInput {

	@Override
	protected void init() {
		addPacketReceivedFunction(new NomadRealmsServerProtocolDecoder());
	}

}
