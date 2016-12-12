package fr.mad.youare.system;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientNetSystem extends NetSystem{
	public class NetListener extends Listener {
		@Override
		public void received(Connection connection, Object object) {
			
		}
	}

	private Client client;

	public ClientNetSystem(InetAddress addr, int port) throws UnknownHostException, IOException {
		client = new Client();
		client.start();
		client.connect(1000, addr, 1535);
		client.addListener(new NetListener());
	}
}
