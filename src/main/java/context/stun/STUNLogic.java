package context.stun;

import static protocol.STUNProtocol.STUN_RESPONSE;

import common.event.GameEvent;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.address.PacketAddress;
import context.logic.GameLogic;
import event.STUNRequestEvent;

public class STUNLogic extends GameLogic {

	@Override
	protected void init() {
		addHandler(STUNRequestEvent.class, event -> true, stunResponseEvent -> {
			System.out.println("Received STUN request from " + stunResponseEvent.source().description());
			PacketAddress address = stunResponseEvent.source().getAddress();
			PacketModel packet = STUN_RESPONSE.builder(address)
					.consume(System.currentTimeMillis())
					.consume(stunResponseEvent.getNonce())
					.consume(address.ip())
					.consume(address.shortPort())
					.build();
			System.out.println("Nonce: " + stunResponseEvent.getNonce());
			context().sendPacket(packet);
		}, true);
		addHandler(GameEvent.class, event -> {
			throw new RuntimeException("Was expecting Bootstrap Request");
		});
	}

	@Override
	public void update() {
	}

}
