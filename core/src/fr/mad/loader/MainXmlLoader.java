package fr.mad.loader;

import java.io.IOException;
import java.io.InputStream;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import fr.mad.loader.xmlObjects.Conversion;
import fr.mad.loader.xmlObjects.Damage;
import fr.mad.loader.xmlObjects.Effect;
import fr.mad.loader.xmlObjects.Unit;
import fr.mad.youare.Item;
import fr.mad.youare.map.ressource.Ressource;

public class MainXmlLoader {
	
	public static void load(Element root, Ressource r) {
		Array<Element> gs = new Array<>();
		gs.addAll(root.getChildrenByName("Units"));
		gs.addAll(root.getChildrenByName("Effects"));
		gs.addAll(root.getChildrenByName("Items"));
		
		for (Element g : gs) {
			for (int i = 0; i < g.getChildCount(); i++) {
				Element e = g.getChild(i);
				switch (e.getName()) {
					case "Unit":
						r.getObjects(Unit.class).add(new Unit(e));
						break;
					case "Conversion":
						r.getObjects(Conversion.class).add(new Conversion(e));
						break;
					case "Item":
						r.getObjects(Item.class).add(new Item(e));
						break;
					case "Effect":
						r.getObjects(Effect.class).add(new Effect(e));
						break;
					case "Damage":
						r.getObjects(Damage.class).add(new Damage(e));
						break;
					default:
						break;
				}
			}
		}
		
	}
	
	public static void load(FileHandle file, Ressource r) throws IOException {
		load(new XmlReader().parse(file), r);
	}
	
	public void load(InputStream input, Ressource r) throws IOException {
		load(new XmlReader().parse(input), r);
	}
	
	public static void load(String xml, Ressource r) throws IOException {
		load(new XmlReader().parse(xml), r);
	}
}
