package fr.mad.youare.system;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntMap.Entry;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import fr.mad.youare.YouAre;
import fr.mad.youare.components.BodyC;
import fr.mad.youare.components.Components;
import fr.mad.youare.components.IDC;
import fr.mad.youare.components.InputsC;
import fr.mad.youare.components.MovementC;
import fr.mad.youare.components.TransformC;
import fr.mad.youare.system.net.AskPacket;
import fr.mad.youare.system.net.EntityUpdate;
import fr.mad.youare.system.net.InputPacket;

public class ServerNetSystem extends NetSystem {
	
	private class NetListener extends Listener {
		private ExecutorService exe = Executors.newFixedThreadPool(3);
		
		public NetListener() {
		}
		
		@Override
		public void connected(Connection connection) {
			//TODO send map and add listener
		}
		
		@Override
		public void received(final Connection connection, final Object object) {
			exe.submit(new Runnable() {
				public void run() {
					if (object instanceof InputPacket) {
						Entity player = players.get(connection.getID());
						if (player == null)
							return;
						InputPacket in = (InputPacket) object;
						Components.inputsC.get(player).set(in);
						AkaPacket n;
						connection.sendUDP(n = AkaPacket.get(in.id));
						AkaPacket.free(n);
					} else if (object instanceof AskPacket) {
						
					}
				}
			});
		}
		
	}
	
	private Server server;
	private IntMap<Entity> players = new IntMap<>();
	private IntMap<TiledMap> connectionMap = new IntMap<>();
	//private ObjectMap<String,WeakReference<TiledMap>> maps = new 
	private float updateTime = 1 / 20f;
	private float accumulator = 0f;
	private ImmutableArray<Entity> entitys;
	private EntityUpdate tmp;
	private YouAre youAre;
	private boolean quit;
	
	public ServerNetSystem(YouAre youAre, int port) throws IOException {
		this.youAre = youAre;
		//Setup maps //TODO
		
		//Start Server
		server = new Server();
		super.init(server.getKryo());
		if (port == 0)
			try {
				server.bind(1535, 1536);
			} catch (Throwable e) {
				quit = true;
			}
		else
			server.bind(0, 0);
		server.start();
		server.addListener(new NetListener());
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		entitys = engine.getEntitiesFor(Family.all(TransformC.class, IDC.class).get());
		
		Entity tmp = new Entity();
		tmp.add(BodyC.square(0, 0, 10, 10));
		tmp.add(new MovementC(new MovementC.PlayerMovement(5, tmp.getComponent(BodyC.class), youAre.input)));
		tmp.add(new InputsC());
		players.put(1, tmp);
		engine.addEntity(tmp);
		
		tmp = new Entity();
		tmp.add(BodyC.square(10, 10, 10, 10));
		tmp.add(new MovementC(new MovementC.PlayerMovement(5, tmp.getComponent(BodyC.class), youAre.input)));
		tmp.add(new InputsC());
		players.put(2, tmp);
		engine.addEntity(tmp);
	}
	
	@Override
	public void update(float deltaTime) {
		if (quit)
			return;
		for (Entry<Entity> e : players) {
			Components.movementC.get(e.value).update(deltaTime, Components.inputsC.get(e.value).keyState);
		}
		
		accumulator += deltaTime;
		if (accumulator > updateTime) {
			accumulator -= updateTime;
			Connection[] cons = server.getConnections();
			for (Entity entity : entitys) {
				TransformC t = Components.transformC.get(entity);
				t.begin();
				tmp.vals = t.vals;
				tmp.id = Components.idC.get(entity).id;
				for (Connection con : cons) {
					con.sendUDP(tmp);
				}
			}
		}
	}
}
