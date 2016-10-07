package fr.mad.youare.storage;

import com.badlogic.gdx.utils.ObjectMap;

import fr.mad.youare.Item;

public class StorgaeConstant {
	public static class V1{
		
		public ObjectMap<String, Class<?>> getItems() {
			ObjectMap<String, Class<?>> map = new ObjectMap<>();
			map.put("Item", Item.class);
			map.put("", value)
			return map;
		}
	}
}
