package fr.mad.youare;

import com.badlogic.gdx.utils.XmlReader.Element;

import fr.mad.storage.xmlObjects.XmlSerial;
import fr.mad.youare.item.AnimationComponent;
import fr.mad.youare.item.SpriteComponent;

public class Item implements XmlSerial{

	private Element e;
	
	public int max;
	public String name;
	public SpriteComponent sprite;
	
	public Item(Element e) {
		this.e =e;
	}

	@Override
	public Element save(Element parent) {
		if(e!=null)
			load(e);
		
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
