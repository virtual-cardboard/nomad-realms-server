package context.bootstrap;

import static java.lang.System.currentTimeMillis;
import static networking.protocols.BootstrapProtocol.BOOTSTRAP_RESPONSE;

import java.util.Random;

import common.event.GameEvent;
import context.input.networking.packet.PacketModel;
import context.logic.GameLogic;
import event.BootstrapRequestEvent;

public class BootstrapServerLogic extends GameLogic {

	private long nonce = new Random().nextLong();

	@Override
	protected void init() {
		addHandler(BootstrapRequestEvent.class, event -> true, bootstrapRequestEvent -> {
			BootstrapServerData data = (BootstrapServerData) context().data();
			System.out.println("Received bootstrap request");
			if (!data.gotFirstPacket) {
				data.lan = bootstrapRequestEvent.getLan();
				data.wan = bootstrapRequestEvent.source().address();
				data.gotFirstPacket = true;
			} else {
				System.out.println("Giving out nonce: " + nonce);
				PacketModel packet1 = BOOTSTRAP_RESPONSE.builder(data.wan)
						.consume(currentTimeMillis())
						.consume(nonce)
						.consume(bootstrapRequestEvent.getLan().ip())
						.consume((short) bootstrapRequestEvent.getLan().port())
						.consume(bootstrapRequestEvent.source().address().ip())
						.consume((short) bootstrapRequestEvent.source().address().port())
						.build();
				context().sendPacket(packet1);
				PacketModel packet2 = BOOTSTRAP_RESPONSE.builder(bootstrapRequestEvent.source().address())
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
