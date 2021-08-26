package context;

import static protocol.STUNProtocol.STUN_RESPONSE;

import common.event.GameEvent;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.address.PacketAddress;
import context.input.networking.packet.block.PacketBlock;
import context.logic.GameLogic;
import event.STUNRequestEvent;

public class STUNLogic extends GameLogic {

	@Override
	public void update() {
		while (!getEventQueue().isEmpty()) {
			GameEvent event = getEventQueue().poll();
			if (event instanceof STUNRequestEvent) {
				STUNRequestEvent stunResponseEvent = (STUNRequestEvent) event;
				System.out.println("Received STUN request from " + stunResponseEvent.getSource().getDescription());
				PacketAddress address = stunResponseEvent.getSource().getAddress();
				PacketBlock packetBlock = STUN_RESPONSE.builder()
						.consume(System.currentTimeMillis())
						.consume(stunResponseEvent.getNonce())
						.consume(address.ip())
						.consume(address.port())
						.build();
				PacketModel packet = new PacketModel(address, packetBlock);
				getContext().sendPacket(packet);
			} else {
				System.out.println("Unhandled event type: " + event.getClass());
			}
		}

	}

}
