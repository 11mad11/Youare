package fr.mad.youare.system.net;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Bits;

import fr.mad.youare.components.Components;

public class InputPacket {
	public static short lastId = 0;
	
	public short id;
	public Bits keys = new Bits(255);
	
	public InputPacket() {
	}

	public void reset() {
		id = lastId++;
		if(lastId==Short.MAX_VALUE)
			lastId=0;
	}
}
