package fr.mad.storage.xmlObjects;

import com.badlogic.gdx.utils.XmlReader.Element;

public class Conversion implements XmlSerial{

	private Element e;
	private String to;
	private String from;
	private float ratio;

	public Conversion(Element e) {
		this.e =e;
	}

	@Override
	public Element save(Element parent) {
		if(e!=null)
			load(e);
		e = new Element("Conversion", parent);
		e.setAttribute("from", from);
		e.setAttribute("to", to);
		e.setAttribute("ratio", ratio+"");
		return e;
	}

	@Override
	public void load(Element e) {
		if (e == null && this.e == null)
			return;
		if (e == null)
			e = this.e;
		from = e.getAttribute("from");
		to = e.getAttribute("to");
		ratio = Float.parseFloat(e.getAttribute("ratio"));
		
		if(from==null||to==null||Float.NaN==ratio||Float.NEGATIVE_INFINITY==ratio||Float.POSITIVE_INFINITY==ratio)
			throw new Error(from+":"+to+":"+ratio);
	}
	
}
