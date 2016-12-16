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
		public void loadTMX(String fileName) throws IOException {
			convertObjectToTileSpace = true;
			flipY = true;
			FileHandle tmxFile = resolve(fileName);
			root = xml.parse(tmxFile);
			TiledMap map = loadTilemap(root, tmxFile, null);
		}
		
		protected TiledMap loadTilemap(Element root, FileHandle tmxFile, ImageResolver imageResolver) {
			TiledMap map = new TiledMap();
			
			String mapOrientation = root.getAttribute("orientation", null);
			int mapWidth = root.getIntAttribute("width", 0);
			int mapHeight = root.getIntAttribute("height", 0);
			int tileWidth = root.getIntAttribute("tilewidth", 0);
			int tileHeight = root.getIntAttribute("tileheight", 0);
			int hexSideLength = root.getIntAttribute("hexsidelength", 0);
			String staggerAxis = root.getAttribute("staggeraxis", null);
			String staggerIndex = root.getAttribute("staggerindex", null);
			String mapBackgroundColor = root.getAttribute("backgroundcolor", null);
			
			MapProperties mapProperties = map.getProperties();
			if (mapOrientation != null) {
				mapProperties.put("orientation", mapOrientation);
			}
			mapProperties.put("width", mapWidth);
			mapProperties.put("height", mapHeight);
			mapProperties.put("tilewidth", tileWidth);
			mapProperties.put("tileheight", tileHeight);
			mapProperties.put("hexsidelength", hexSideLength);
			if (staggerAxis != null) {
				mapProperties.put("staggeraxis", staggerAxis);
			}
			if (staggerIndex != null) {
				mapProperties.put("staggerindex", staggerIndex);
			}
			if (mapBackgroundColor != null) {
				mapProperties.put("backgroundcolor", mapBackgroundColor);
			}
			mapTileWidth = tileWidth;
			mapTileHeight = tileHeight;
			mapWidthInPixels = mapWidth * tileWidth;
			mapHeightInPixels = mapHeight * tileHeight;
			
			if (mapOrientation != null) {
				if ("staggered".equals(mapOrientation)) {
					if (mapHeight > 1) {
						mapWidthInPixels += tileWidth / 2;
						mapHeightInPixels = mapHeightInPixels / 2 + tileHeight / 2;
					}
				}
			}
			
			Element properties = root.getChildByName("properties");
			if (properties != null) {
				loadProperties(map.getProperties(), properties);
			}
			//			Array<Element> tilesets = root.getChildrenByName("tileset");
			//			for (Element element : tilesets) {
			//				loadTileSet(map, element, tmxFile, imageResolver);
			//				root.removeChild(element);
			//			}
			for (int i = 0, j = root.getChildCount(); i < j; i++) {
				Element element = root.getChild(i);
				String name = element.getName();
				if (name.equals("layer")) {
					loadTileLayer(map, element);
				} else if (name.equals("objectgroup")) {
					loadObjectGroup(map, element);
				} else if (name.equals("imagelayer")) {
					loadImageLayer(map, element, tmxFile, imageResolver);
				} else if (name.equals("tileset")) {
					
				}
			}
			return map;
		}
	}
}
