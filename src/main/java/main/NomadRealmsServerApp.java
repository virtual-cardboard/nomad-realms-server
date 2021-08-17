package main;

import java.net.SocketException;

import bootstrap.STUNServer;

public class NomadRealmsServerApp {

	public static void main(String[] args) {
		try {
//			NomadRealmsBootstrapServer bootstrap = new NomadRealmsBootstrapServer();
//			bootstrap.start();
			STUNServer stun = new STUNServer();
			stun.start();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

}
