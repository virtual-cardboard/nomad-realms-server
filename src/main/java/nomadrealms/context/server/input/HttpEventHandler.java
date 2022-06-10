package nomadrealms.context.server.input;

import static java.util.Arrays.copyOfRange;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import engine.common.networking.packet.address.PacketAddress;
import event.network.NomadRealmsC2SNetworkEvent;

public abstract class HttpEventHandler<T extends NomadRealmsC2SNetworkEvent, R extends NomadRealmsC2SNetworkEvent> implements HttpHandler {

	private final Constructor<T> requestConstructor;
	private final byte[] bytes = new byte[65536];

	public HttpEventHandler(Class<T> request) {
		try {
			requestConstructor = request.getConstructor(byte[].class);
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("Request class must have a constructor that takes in a byte[].");
		}
	}

	@Override
	public final void handle(HttpExchange t) throws IOException {
		PacketAddress clientAddress = new PacketAddress(t.getRemoteAddress().getAddress(), t.getRemoteAddress().getPort());
		int numRead = t.getRequestBody().read(bytes);
		try (OutputStream os = t.getResponseBody()) {
			T request = requestConstructor.newInstance((Object) copyOfRange(bytes, 0, numRead));
			R response = handle(request, clientAddress);
			byte[] serializedResponse = response.serialize();
			t.sendResponseHeaders(200, serializedResponse.length);
			os.write(serializedResponse);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			System.err.println("Could not create C2S event from the HTTP request body. 400 Bad Request");
			e.printStackTrace();
			// 400 Bad Request
			t.sendResponseHeaders(400, 0);
		} catch (Exception e) {
			System.err.println("Failed to handle " + requestConstructor.getDeclaringClass().getSimpleName());
			e.printStackTrace();
			t.sendResponseHeaders(400, 0);
		}
	}

	public abstract R handle(T request, PacketAddress clientAddress);

}
