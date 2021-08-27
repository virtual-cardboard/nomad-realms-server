package context;

import static java.lang.System.currentTimeMillis;
import static protocol.BootstrapProtocol.BOOTSTRAP_RESPONSE;

import java.util.Random;

import common.event.GameEvent;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.block.PacketBlock;
import context.logic.GameLogic;
import event.BootstrapRequestEvent;

public class BootstrapLogic extends GameLogic {

	private long nonce = new Random().nextLong();

	@Override
	public void update() {
		BootstrapData data = (BootstrapData) getContext().getData();
		while (!getEventQueue().isEmpty()) {
			GameEvent event = getEventQueue().poll();
			if (event instanceof BootstrapRequestEvent) {
				BootstrapRequestEvent bootstrapRequestEvent = (BootstrapRequestEvent) event;
				System.out.println("Received bootstrap request");
				if (!data.gotFirstPacket) {
					data.lan = bootstrapRequestEvent.getLan();
					data.wan = bootstrapRequestEvent.getSource().getAddress();
					data.gotFirstPacket = true;
				} else {
					System.out.println("Giving out nonce: " + nonce);
					PacketBlock b1 = BOOTSTRAP_RESPONSE.builder()
							.consume(currentTimeMillis())
							.consume(nonce)
							.consume(bootstrapRequestEvent.getLan().ip())
							.consume(bootstrapRequestEvent.getLan().port())
							.consume(bootstrapRequestEvent.getSource().getAddress().ip())
							.consume(bootstrapRequestEvent.getSource().getAddress().port())
							.build();
					PacketModel packet1 = new PacketModel(data.wan, b1);
					getContext().sendPacket(packet1);
					PacketBlock b2 = BOOTSTRAP_RESPONSE.builder()
							.consume(currentTimeMillis())
							.consume(nonce)
							.consume(data.lan.ip())
							.consume(data.lan.port())
							.consume(data.wan.ip())
							.consume(data.wan.port())
							.build();
					PacketModel packet2 = new PacketModel(bootstrapRequestEvent.getSource().getAddress(), b2);
					getContext().sendPacket(packet2);
					System.out.println("Done sending bootstrap response packet");
				}
			} else {
				throw new RuntimeException("Was expecting Bootstrap Request");
			}
		}
	}

}
