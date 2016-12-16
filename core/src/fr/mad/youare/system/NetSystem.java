package fr.mad.youare.system;

import com.badlogic.ashley.core.EntitySystem;
import com.esotericsoftware.kryo.Kryo;

import fr.mad.youare.system.net.EntityUpdate;
import fr.mad.youare.system.net.InputPacket;

public abstract class NetSystem extends EntitySystem {
	protected void init(Kryo kryo) {
		kryo.setRegistrationRequired(false);
		kryo.register(AkaPacket.class);
		kryo.register(EntityUpdate.class);
		kryo.register(InputPacket.class);
	}
}
