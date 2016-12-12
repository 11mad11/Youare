package fr.mad.youare.system;

import java.io.IOException;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.KryoSerialization;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import fr.mad.youare.YouAre;
import fr.mad.youare.components.BodyC;
import fr.mad.youare.components.Components;
import fr.mad.youare.components.IDC;
import fr.mad.youare.components.MovementC;
import fr.mad.youare.components.TransformC;
import fr.mad.youare.system.net.EntityUpdate;

public class ServerNetSystem extends NetSystem {
	
	private class NetListener extends Listener {
		@Override
		public void connected(Connection connection) {
			//TODO send all entity and map and add listener
		}
		
		@Override
		public void received(Connection connection, Object object) {
			new Thread(new Runnable() {
				public void run() {
					
				}
			}).start();
		}
		
	}
	
	private Server server;
	private float updateTime = 1 / 20f;
	private float accumulator = 0f;
	private ImmutableArray<Entity> entitys;
	private EntityUpdate tmp;
	private YouAre youAre;
	
	public ServerNetSystem(YouAre youAre,int port) throws IOException {
		this.youAre = youAre;
		server = new Server();
		if (port == 0)
			try {
				server.bind(1535, 1536);
			} catch (Throwable e) {
				server.bind(0,0);
			}
		else
			server.bind(0, 0);
		server.start();
		server.addListener(new NetListener());
		
		init(server.getKryo());
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		entitys = engine.getEntitiesFor(Family.all(TransformC.class, IDC.class).get());
		
		Entity tmp = new Entity();
		tmp.add(BodyC.square(0, 0, 10, 10));
		tmp.add(new MovementC(new MovementC.PlayerMovement(5, tmp.getComponent(BodyC.class), youAre.input)));
		
		engine.addEntity(tmp);
	}
	
	@Override
	public void update(float deltaTime) {
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
