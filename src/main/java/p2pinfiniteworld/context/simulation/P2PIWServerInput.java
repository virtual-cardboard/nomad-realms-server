package p2pinfiniteworld.context.simulation;

import context.input.GameInput;
import nomadrealms.protocols.NomadRealmsServerProtocolDecoder;

public class P2PIWServerInput extends GameInput {

	@Override
	protected void init() {
		addPacketReceivedFunction(new NomadRealmsServerProtocolDecoder());
	}

}
