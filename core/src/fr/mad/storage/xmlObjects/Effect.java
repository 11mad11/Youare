package fr.mad.loader.xmlObjects;

import com.badlogic.gdx.utils.XmlReader.Element;

import fr.mad.loader.xmlObjects.Effect.Behavior;

public class Effect implements XmlSerial {
	
	public class Behavior {
		
	}

	private Element e;
	private String name;
	private String animationKey;
	private Behavior behavior;
	
	public Effect(Element e) {
		this.e = e;
	}
	
	@Override
	public Element save(Element parent) {
		if (e != null)
			load(e);
		e = new Element("Effect", parent);
		e.setAttribute("name", name);
		if (animationKey != null)
			e.setAttribute("animtionKey", animationKey);
		if(behavior!=null){
			Behavior b = behavior;
			
		}
		return e;
	}
	
	@Override
	public void load(Element e) {
		if (e == null && this.e == null)
			return;
		if (e == null)
			e = this.e;
		
	}
}
