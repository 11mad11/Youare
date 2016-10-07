package fr.mad.youare;

import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.badlogic.gdx.utils.XmlWriter;

public class YouAre extends Game {
	
	@Override
	public void create() {
		//setScreen(new TestScreen());
		try {
			XmlWriter xml = new XmlWriter(new FileWriter(Gdx.files.internal("big.xml").file()));
			xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			xml.element("Root");
			damage(xml);
			unit(xml);
			item(xml);
			comp(xml);
			xml.pop();
			xml.close();//TODO condition
			Element root = new XmlReader().parse(Gdx.files.internal("big.xml"));
			System.out.println(root.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	private void comp(XmlWriter xml) throws IOException {
		xml.element("weapons");
		{
			
		}
		xml.pop();
		
		xml.element("Competences");
		{
			xml.element("Classe").attribute("name", "barbare");//*
			{
				xml.element("Skill").attribute("name", "Charge");
				{
					xml.element("Passive");
					{
						xml.element("Effect").attribute("name", "SpeedI").pop();
					}
					xml.pop();
					xml.element("Active").attribute("need", "knif");
					{
						xml.element("Type");
						{
							//cibleAllier cibleEnemy cibleLanceur free
							xml.element("Zone").attribute("cible", "free");//TODO go in type attribute
							{
								xml.element("Size").attribute("raduis", 2).attribute("degree", 20).attribute("offset", 10).pop();
								xml.element("Limit").attribute("raduis", 10).pop();
							}
							xml.pop();
							
							//ally enemy
							xml.element("Direct").attribute("cible", "ally").attribute("number", 1);
							{
								xml.element("Transfer").attribute("type", "electrical").attribute("zoneMax", 30).attribute("zone", 2).pop();
								
							}
							xml.pop();
						}
						xml.pop();
						xml.element("Effect").attribute("name", "SpeedV").pop();
						xml.element("Damage").attribute("name", "Lethal").pop();
					}
					xml.pop();
				}
				xml.pop();
			}
			xml.pop();
		}
		xml.pop();
	}
	
	private void damage(XmlWriter xml) throws IOException {
		xml.element("Effects");
		{
			xml.element("Damage").attribute("name", "Lethal").pop();
			xml.element("Damage").attribute("name", "Fire").pop();
			xml.element("Effect").attribute("name", "Poison").attribute("animationKey", "poison");
			{
				xml.element("Behavior").attribute("type", "damage").attribute("damage", 1);
				{
					xml.element("Timer").attribute("delay", 0).attribute("interval", 1000).attribute("repeat", 5).pop();
				}
				xml.pop();
			}
			xml.pop();
			xml.element("Effect").attribute("name", "Speed");
			{
				for (int i = 1; i < 6; i++) {
					xml.element("Behavior").attribute("level", i).attribute("type", "speed").attribute("speed", i);
					{
						xml.element("OffTimer").attribute("delay", 5000).pop();
					}
					xml.pop();
				}
			}
			xml.pop();
		}
		xml.pop();
	}
	
	private void unit(XmlWriter xml) throws IOException {
		xml.element("Units");
		{
			xml.element("Unit").attribute("name", "L").attribute("fullName", "Litre").attribute("type", "Volume").pop();
			xml.element("Unit").attribute("name", "m^3").attribute("fullName", "Cubic Meter").attribute("type", "Volume").pop();
			xml.element("Unit").attribute("name", "lb").attribute("fullName", "Pound").attribute("type", "Weight").pop();
			xml.element("Unit").attribute("name", "kg").attribute("fullName", "KiloGramme").attribute("type", "Weight").pop();
			xml.element("Unit").attribute("name", "$").attribute("fullName", "Dollard").attribute("type", "Money").pop();
			xml.element("Unit").attribute("name", "£").attribute("fullName", "Pound").attribute("type", "Money").pop();
			xml.element("Conversion").attribute("from", "L").attribute("to", "m^3").attribute("ratio", 0.001f).pop();
			xml.element("Conversion").attribute("from", "lb").attribute("to", "kg").attribute("ratio", 0.453592f).pop();
			xml.element("Conversion").attribute("from", "$").attribute("to", "£").attribute("ratio", 0.770383381f).pop();
		}
		xml.pop();
	}
	
	private void item(XmlWriter xml) throws IOException {
		xml.element("Item").attribute("id", "un_Nom_Unique");
		{
			xml.element("Info");
			{
				xml.element("Name").attribute("color", "orange or ORANGE or 255,200,0 (r,g,b)").text("le nom afficher").pop();
				xml.element("Description").attribute("color", "blue").text("a long text that can be on multiple line.").pop();
				xml.element("Max_Stack").text("15").pop();
				xml.element("Volume").attribute("unit", "L").attribute("value", "100").pop();
				xml.element("Weight").attribute("unit", "lb").attribute("value", "30").pop();
				xml.element("Value").attribute("unit", "$").attribute("value", "5000").pop();
				xml.element("Effects").attribute("casting", 1000).attribute("timeout", 10000);
				{
					xml.element("Active");
					{
						xml.element("OnEnemy");
						{
							xml.element("Damage").attribute("name", "Lethal").attribute("value", 5).pop();
						}
						xml.pop();
						xml.element("OnPlayer");
						{
							xml.element("Effect").attribute("needHit", false).attribute("name", "Speed").pop();
							xml.element("Damage").attribute("needHit", true).attribute("value", -5).pop();
						}
						xml.pop();
					}
					xml.pop();
					xml.element("Passive");
					{
						xml.element("Damage").attribute("name", "Lethal").attribute("value", -10).pop();
						xml.element("Damage").attribute("name", "Fire").attribute("value", 100).pop();
					}
					xml.pop();
				}
				xml.pop();
			}
			xml.pop();
			sprtie(xml);
		}
		xml.pop();
	}
	
	private void sprtie(XmlWriter xml) throws IOException {
		xml.element("Sprite").attribute("type", "Animation").text("<!--can be an Animation or a Texture. In case of a texture just add one frame tag-->");
		{
			xml.element("Frames").attribute("frameDuration", 0.05f);
			{
				for (int i = 0; i < 24; i++) {
					xml.element("Frame").attribute("path", "../a/texture/animation.png");
					{
						xml.element("Offset").attribute("x", ((i % 10) * 16)).attribute("y", (i / 10) * 16).pop();
						xml.element("Size").attribute("Width", 16).attribute("Height", 16).pop();
					}
					xml.pop();
				}
			}
			xml.pop();
		}
		xml.pop();
	}
	
}
