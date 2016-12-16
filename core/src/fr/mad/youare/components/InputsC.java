package fr.mad.youare.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.BooleanArray;

import fr.mad.youare.system.net.InputPacket;

public class InputsC implements Component {
	public Bits keyState = new Bits();
	public void set(InputPacket in) {
		keyState = in.keys;
	}
	
}
