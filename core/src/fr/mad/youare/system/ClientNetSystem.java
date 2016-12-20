package fr.mad.youare.system;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntMap.Entry;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import fr.mad.youare.BetterInputProcessor.InputListener;
import fr.mad.youare.system.net.InputPacket;

public class ClientNetSystem extends NetSystem implements InputListener {
	public class NetListener extends Listener {
		private ExecutorService exe = Executors.newFixedThreadPool(3);
		
		@Override
		public void received(final Connection connection, final Object object) {
			exe.submit(new Runnable() {
				public void run() {
					if (object instanceof AkaPacket) {
						AkaPacket aka = (AkaPacket) object;
						inputs.remove(aka.id);
					}
				}
			});
		}
	}
	
	private Client client;
	private IntMap<Bits> inputs = new IntMap<>();
	private InputPacket in = new InputPacket();
	
	public ClientNetSystem(InetAddress addr, int port) throws UnknownHostException, IOException {
		client = new Client();
		super.init(client.getKryo());
		client.start();
		client.connect(1000, addr, 1535, 1536);
		client.addListener(new NetListener());
	}
	
	@Override
	public void update(float deltaTime) {
		if (client.isConnected()) {
			Bits n;
			inputs.put(in.id, n = new Bits());
			n.or(in.keys);
			Bits last = in.keys;
			for (Entry<Bits> e : inputs) {
				in.id = (short) e.key;
				in.keys = e.value;
				client.sendUDP(in);
			}
			in.keys = last;
			in.reset();
		}
	}
	
	@Override
	public void keyChange(int key, boolean state) {
		if (state)
			in.keys.set(key);
		else
			in.keys.clear(key);
	}
	
	@Override
	public void touchDown(int screenX, int screenY, int pointer, int button) {
		
	}
	
	@Override
	public void TouchUp(int screenX, int screenY, int pointer, int button) {
		
	}
	
	@Override
	public void touchDragged(int screenX, int screenY, int pointer) {
		
	}
	
	@Override
	public void scrolled(int amount) {
		
	}
	
	@Override
	public void controller(Controller c, boolean main) {
		//TODO send all controller input
	}
}
