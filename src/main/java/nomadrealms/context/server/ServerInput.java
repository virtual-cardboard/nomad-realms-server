package nomadrealms.context.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Random;

import com.sun.net.httpserver.HttpServer;
import context.game.input.debugui.DebuguiKeyPressedFunction;
import context.input.GameInput;
import engine.common.math.Vector2f;
import networking.NetworkCluster;
import networking.protocols.NomadRealmsProtocolDecoder;
import nomadrealms.context.server.input.JoinClusterHttpHandler;
import nomadrealms.context.server.input.JoinClusterSuccessEventHandler;
import nomadrealms.model.NomadMini;

public class ServerInput extends GameInput {

	private ServerData data;

	@Override
	protected void init() {
		data = (ServerData) context().data();
		addMousePressedFunction(event -> true, event -> {
			List<NomadMini> minis = data.minis();
			for (int i = minis.size() - 1; i >= 0; i--) {
				NomadMini nomadMini = minis.get(i);
				if (cursor().pos().toVec2f().add(-32, -32).sub(nomadMini.pos()).lengthSquared() <= 30 * 30) {
					nomadMini.drag();
					data.selectedMini = nomadMini;
					data.selectedMini.setPos(cursor().pos().toVec2f().sub(new Vector2f(32, 32)));
					System.out.println(data.selectedMini.username());
					break;
				}
			}
			return null;
		}, false);
		addMouseMovedFunction(event -> data.selectedMini != null, event -> {
			data.selectedMini.setPos(cursor().pos().toVec2f().sub(new Vector2f(32, 32)));
			return null;
		}, false);
		addMouseReleasedFunction(event -> data.selectedMini != null && event.button() == 0, event -> {
			NomadMini n1 = data.selectedMini;
			n1.undrag();
			List<NomadMini> minis = data.minis();
			for (int i = minis.size() - 1; i >= 0; i--) {
				NomadMini n2 = minis.get(i);
				if (n2 != n1 && n1.pos().sub(n2.pos()).lengthSquared() <= 30 * 30) {
					System.out.println("Match!");
					minis.remove(n1);
					minis.remove(n2);
					long nonce = new Random().nextLong();
//					context().sendPacket(new BootstrapResponseEvent(nonce, n1.lanAddress(), n1.wanAddress(), n1.username()).toPacket(n2.wanAddress()));
//					context().sendPacket(new BootstrapResponseEvent(nonce, n2.lanAddress(), n2.wanAddress(), n2.username()).toPacket(n1.wanAddress()));
					NetworkCluster cluster = data.getCluster(0);
//					cluster.addPlayerSession(n1.address());
//					cluster.addPlayerSession(n2.address());
					break;
				}
			}
			data.selectedMini = null;
			return null;
		}, false);

		addKeyPressedFunction(new DebuguiKeyPressedFunction(data.tools()));

		addPacketReceivedFunction(new NomadRealmsProtocolDecoder());
		setupServer();
	}

	private void setupServer() {
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(45001), 0);
			server.createContext("/join", new JoinClusterHttpHandler(data));
			server.createContext("/joinSuccess", new JoinClusterSuccessEventHandler(data));
			server.setExecutor(null); // creates a default executor
			server.start();
			System.out.println("HTTP server started at " + server.getAddress());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
