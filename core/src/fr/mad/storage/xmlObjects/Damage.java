package fr.mad.loader.xmlObjects;

import com.badlogic.gdx.utils.XmlReader.Element;

public class Damage implements XmlSerial{

	private Element e;

	public Damage(Element e) {
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
