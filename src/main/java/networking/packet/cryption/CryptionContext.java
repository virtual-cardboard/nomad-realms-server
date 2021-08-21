package networking.packet.cryption;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

import model.NomadRealmsUser;

public class CryptionContext {

	private Map<NomadRealmsUser, PublicKey> clientPublicKeys;
	private PublicKey serverPublicKey;
	private PrivateKey serverPrivateKey;

	public Map<NomadRealmsUser, PublicKey> getClientPublicKeys() {
		return clientPublicKeys;
	}

	public void setClientPublicKeys(Map<NomadRealmsUser, PublicKey> clientPublicKeys) {
		this.clientPublicKeys = clientPublicKeys;
	}

	public PublicKey getServerPublicKey() {
		return serverPublicKey;
	}

	public void setServerPublicKey(PublicKey serverPublicKey) {
		this.serverPublicKey = serverPublicKey;
	}

	public PrivateKey getServerPrivateKey() {
		return serverPrivateKey;
	}

	public void setServerPrivateKey(PrivateKey serverPrivateKey) {
		this.serverPrivateKey = serverPrivateKey;
	}

}
