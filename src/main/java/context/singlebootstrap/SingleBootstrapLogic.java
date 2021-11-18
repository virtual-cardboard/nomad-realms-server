package context.singlebootstrap;

import static java.lang.System.currentTimeMillis;
import static protocol.BootstrapProtocol.BOOTSTRAP_RESPONSE;

import java.util.Random;

import common.event.GameEvent;
import context.input.networking.packet.PacketModel;
import context.logic.GameLogic;
import event.BootstrapRequestEvent;

public class SingleBootstrapLogic extends GameLogic {

	private long nonce = new Random().nextLong();

	@Override
	protected void init() {
		addHandler(BootstrapRequestEvent.class, event -> true, bootstrapRequestEvent -> {
			SingleBootstrapData data = (SingleBootstrapData) context().data();
			System.out.println("Received bootstrap request");
			if (!data.gotFirstPacket) {
				data.lan = bootstrapRequestEvent.getLan();
				data.wan = bootstrapRequestEvent.source().getAddress();
				data.gotFirstPacket = true;
			} else {
				System.out.println("Giving out nonce: " + nonce);
				PacketModel packet1 = BOOTSTRAP_RESPONSE.builder(data.wan)
						.consume(currentTimeMillis())
						.consume(nonce)
						.consume(bootstrapRequestEvent.getLan().ip())
						.consume((short) bootstrapRequestEvent.getLan().port())
						.consume(bootstrapRequestEvent.source().getAddress().ip())
						.consume((short) bootstrapRequestEvent.source().getAddress().port())
						.build();
				context().sendPacket(packet1);
				PacketModel packet2 = BOOTSTRAP_RESPONSE.builder(bootstrapRequestEvent.source().getAddress())
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
		}, true);
		addHandler(GameEvent.class, event -> {
			throw new RuntimeException("Was expecting Bootstrap Request");
		});
	}

	@Override
	public void update() {
	}

}
