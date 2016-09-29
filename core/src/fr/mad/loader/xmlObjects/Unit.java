package fr.mad.loader.xmlObjects;

import com.badlogic.gdx.utils.XmlReader.Element;

public class Unit implements XmlSerial {
	
	private Element e;
	private String name;
	private String fullName;
	private String type;
	
	public Unit(Element e) {
		this.e = e;
	}
	
	@Override
	public Element save(Element parent) {
		if (e != null)
			load(e);
		e = new Element("Unit", parent);
		e.setAttribute("name", name);
		e.setAttribute("fullName", fullName);
		e.setAttribute("type", type);
		return e;
	}
	
	@Override
	public void load(Element e) {
		if (e == null && this.e == null)
			return;
		if (e == null)
			e = this.e;
		
		name = e.getAttribute("name");
		fullName = e.getAttribute("fullName");
		type = e.getAttribute("type");
		
		this.e = null;
		
		if (name == null || fullName == null || type == null)
			throw new Error(name + ":" + fullName + ":" + type);
	}
	
}
