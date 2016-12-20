package fr.mad.storage.xmlObjects;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader.Element;


public class Effect implements XmlSerial {
	
	public class Behavior {

		public String type;
		
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
			//e.setAttribute("type", b.type);
			
		}
		return e;
	}
	
	@Override
	public void load(Element e) {
		if (e == null && this.e == null)
			return;
		if (e == null)
			e = this.e;
		
		name = e.getAttribute("name");
		animationKey = e.getAttribute("animationKey");
		Array<Element> b = e.getChildrenByName("Behavior");
		behavior = null;
		if(b.size>0){
			behavior = new Behavior();
		}
	}
}
