package context;

import static java.lang.System.currentTimeMillis;
import static protocol.BootstrapProtocol.BOOTSTRAP_RESPONSE;

import java.util.Random;

import common.event.GameEvent;
import context.input.networking.packet.PacketModel;
import context.logic.GameLogic;
import event.BootstrapRequestEvent;

public class BootstrapLogic extends GameLogic {

	private long nonce = new Random().nextLong();

	@Override
	public void update() {
		BootstrapData data = (BootstrapData) context().data();
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
					PacketModel packet1 = BOOTSTRAP_RESPONSE.builder(data.wan)
							.consume(currentTimeMillis())
							.consume(nonce)
							.consume(bootstrapRequestEvent.getLan().ip())
							.consume((short) bootstrapRequestEvent.getLan().port())
							.consume(bootstrapRequestEvent.getSource().getAddress().ip())
							.consume((short) bootstrapRequestEvent.getSource().getAddress().port())
							.build();
					context().sendPacket(packet1);
					PacketModel packet2 = BOOTSTRAP_RESPONSE.builder(bootstrapRequestEvent.getSource().getAddress())
							.consume(currentTimeMillis())
							.consume(nonce)
							.consume(data.lan.ip())
							.consume((short) data.lan.port())
							.consume(data.wan.ip())
							.consume((short) data.wan.port())
							.build();
					context().sendPacket(packet2);
					System.out.println("Done sending bootstrap response packet");
				}
			} else {
				throw new RuntimeException("Was expecting Bootstrap Request");
			}
		}
	}

}
