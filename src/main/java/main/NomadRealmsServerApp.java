package main;

import java.net.SocketException;

import bootstrap.NomadRealmsBootstrapServer;

public class NomadRealmsServerApp {

	public static void main(String[] args) {
		try {
			NomadRealmsBootstrapServer bootstrap = new NomadRealmsBootstrapServer();
			bootstrap.start();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

}
