package fr.mad.youare.message;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.utils.ObjectMap;

public class MessageCode {
	
	private static int lid = 0;
	public final ObjectMap<String,Integer> msg = new ObjectMap<>();
	
	private MessageCode() {
	}
}
