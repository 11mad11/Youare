package fr.mad.youare.system;

import com.badlogic.gdx.utils.Array;

public class AkaPacket {
	
	private static Array<AkaPacket> free = new Array<AkaPacket>();
	private static Array<AkaPacket> used = new Array<AkaPacket>();
	static{
		for (int i = 0; i < 20; i++) {
			free.add(new AkaPacket());
		}
	}
	public short id;
	
	public AkaPacket set(short id) {
		this.id = id;
		return this;
	}
	
	public static AkaPacket get(short id) {
		AkaPacket n;
		synchronized (free) {
			n = free.pop();
		}
		used.add(n);
		return n.set(id);
	}
	
	public static void free(AkaPacket n) {
		used.removeValue(n, true);
		free.add(n);
	}
}
