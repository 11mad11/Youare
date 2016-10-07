package fr.mad.loader.xmlObjects;

import com.badlogic.gdx.utils.XmlReader.Element;

public interface XmlSerial {
	public Element save(Element parent);
	public void load(Element e);
	
}
