package fr.mad.youare.storage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader.Element;

public class XmlLoader {
	@Target(ElementType.CONSTRUCTOR)
	public @interface Attribute {
		String[] arg();
	}
	
	public XmlLoader() {
		
	}
	
	public <T extends BaseXml> ObjectMap<Class<?>, Array<Object>> load(AssetManager a, Element root, T c) {
		ObjectMap<String, Class<?>> reg = c.getItems();
		ObjectMap<Class<?>,Array<Object>> out = new ObjectMap<Class<?>,Array<Object>>();
		search(a, root, reg,out);
		return out;
	}
	
	private void search(AssetManager a, Element element, ObjectMap<String, Class<?>> reg, ObjectMap<Class<?>, Array<Object>> out) {
		for (int i = 0; i < element.getChildCount(); i++) {
			if (reg.containsKey(element.getChild(i).getName())) {
				put(out,reg.get(element.getChild(i).getName()),load0(a, element.getChild(i), reg.get(element.getChild(i).getName())));
			} else {
				search(a, element.getChild(i), reg, out);
			}
		}
	}
	
	private void put(ObjectMap<Class<?>, Array<Object>> out, Class<?> class1, Object o) {
		if(out.get(class1)!=null){
			out.put(class1, new Array<>());
		}
		out.get(class1).add(o);
	}

	private <T> T load0(AssetManager a, Element element, Class<T> c) {
		try {
			T instence = c.newInstance();
			Field[] fields = c.getFields();
			Array<Element> childsUsed = new Array<Element>();
			
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				String v = element.get(f.getName());
				
				if (f.getType().isAssignableFrom(int.class)) {
					f.setInt(instence, Integer.parseInt(v));
				} else if (f.getType().isAssignableFrom(float.class)) {
					f.setFloat(instence, Float.parseFloat(v));
				} else if (f.getType().isAssignableFrom(boolean.class)) {
					f.setBoolean(instence, Boolean.parseBoolean(v));
				} else if (f.getType().isAssignableFrom(byte.class)) {
					f.setByte(instence, Byte.parseByte(v));
				} else if (f.getType().isAssignableFrom(String.class)) {
					f.set(instence, v);
				} else {
					Element child = null;
					for (int j = 0; j < element.getChildCount(); j++) {
						if (!childsUsed.contains(element.getChild(j), true)) {
							if (!element.getChild(j).getName().equals(f.getName()))
								continue;
							child = element.getChild(j);
							childsUsed.add(child);
							break;
						}
					}
					if (child == null)
						continue;
					f.set(instence, load0(a, child, f.getType()));
				}
			}
			
			return instence;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	///
	//
	///
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	
}
