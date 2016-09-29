package fr.mad.youare.desktop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class XmlEditor {
	private static BufferedReader in;
	private static Element root;
	
	public static void main(String[] args) throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		first();
	}
	
	private static void first() throws IOException {
		System.out.println("path to xml (created if not existing):");
		String path = in.readLine();
		if (new File(path).exists()) {
			root = new XmlReader().parse(new FileInputStream(path));
		}
		if (root == null) {
			root = new Element("Root", null);
		}
		System.out.print("Type ? for help");
		start();
	}
	
	private static void start() throws IOException {
		System.out.print(":");
		String r = in.readLine();
		switch (r) {
			case "?":
				help();
				break;
			case "listItem":
				items();
				break;
			case "item":
				item();
			default:
				help();
				break;
		}
	}
	
	private static void item() throws IOException {
		Element list = root.getChildByName("Items");
		if (list == null)
			root.addChild(list = new Element("Item", root));
		Element item = new Element("Item", list);
		System.out.print("Identifiant unique:");
		String r = in.readLine();
		for (int i = 0; i < list.getChildCount(); i++) {
			Element c = list.getChild(i);
			if (c.getName().equals("Item")) {
				if (c.getAttribute("id").equals(r)) {
					item = c;
				}
			}
		}
		item.setAttribute("id", r);
		
	}
	
	private static void items() {
		Element list = root.getChildByName("Items");
		if (list == null) {
			System.err.println("0 item");
			return;
		}
		for (int i = 0; i < list.getChildCount(); i++) {
			System.out.println(list.getChild(i));
		}
	}
	
	private static void help() {
		System.out.println("listItem");
		
	}
}
