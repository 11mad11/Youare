package fr.mad.youare.system.game.map;

import java.io.IOException;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.ImageResolver.AssetManagerImageResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader.Element;

public class TiledMapLoader {
	public static class TMX extends TmxMapLoader {
		public Element compile(String fileName) throws IOException {
			convertObjectToTileSpace = true;
			flipY = true;
			FileHandle tmxFile = resolve(fileName);
			root = xml.parse(tmxFile);
			
			Array<Element> tilesets = root.getChildrenByName("tileset");
			for (Element element : tilesets) {
				
			}
			
			return root;
		}
		
	}
}
