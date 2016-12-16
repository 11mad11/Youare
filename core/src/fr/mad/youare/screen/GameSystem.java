package fr.mad.youare.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;

import fr.mad.youare.system.PhysicSystem;
import fr.mad.youare.system.game.map.MapObjectType;
import fr.mad.youare.system.game.map.MapOrientation;

public class GameSystem extends EntitySystem {
	private PhysicSystem physic;
	public TiledMap map;
	public String name;
	public int mapWidth;
	public int mapHeight;
	public MapOrientation mapOrientation;
	public Integer tileWidth;
	public Integer tileHeight;
	public Integer hexSideLength;
	public String staggerAxis;
	public String staggerIndex;
	public int mapWidthInPixels;
	public int mapHeightInPixels;
	
	public GameSystem() {
		
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		physic = engine.getSystem(PhysicSystem.class);//TODO remove si non nedded dans loadObj()
		
	}
	
	public void reload() {
		if (map == null) {
			name = null;
			mapHeight = 0;
			mapWidth = 0;
			mapHeightInPixels = 0;
			mapWidthInPixels = 0;
			mapOrientation = null;
			tileWidth = 0;
			tileHeight = 0;
			hexSideLength = null;
			staggerAxis = null;
			staggerIndex = null;
			return;
		}
		MapLayers layers = map.getLayers();
		MapProperties prop = map.getProperties();
		//TiledMapTileSets tileSets = map.getTileSets();
		
		//Basic prop
		name = prop.get("Name", "error", String.class);
		mapWidth = prop.get("width", Integer.class);
		mapHeight = prop.get("height", Integer.class);
		mapOrientation = MapOrientation.valueOf(prop.get("orientation", String.class));
		tileWidth = prop.get("height", Integer.class);
		tileHeight = prop.get("height", Integer.class);
		hexSideLength = prop.get("height", Integer.class);
		staggerAxis = prop.get("Name", "error", String.class);
		staggerIndex = prop.get("Name", "error", String.class);
		mapWidthInPixels = mapWidth * tileWidth;
		mapHeightInPixels = mapHeight * tileHeight;
		if (mapOrientation != null) {
			if (MapOrientation.Staggered.equals(mapOrientation)) {
				if (mapHeight > 1) {
					mapWidthInPixels += tileWidth / 2;
					mapHeightInPixels = mapHeightInPixels / 2 + tileHeight / 2;
				}
			}
		}
		
		//TODO player model or something like that
		
		//load object like entitys(NPC) and spawns points
		for (MapLayer mapLayer : layers) {
			MapObjects objs = mapLayer.getObjects();
			for (MapObject mapObject : objs) {
				loadObj(mapObject);
			}
		}
		
	}
	
	private void loadObj(MapObject o) {
		MapObjectType type = MapObjectType.valueOf(o.getProperties().get("type", "", String.class));
		switch (type) {
			case NPC:
				//TODO
				break;
			case Portal:
				//TODO
				break;
			case Spawn:
				//TODO
				break;
		}
	}
	
	public String[] getAllRefMap(TiledMap map) {
		Array<String> rep = new Array<>();
		MapLayers layers = map.getLayers();
		for (MapLayer mapLayer : layers) {
			MapObjects objs = mapLayer.getObjects();
			for (MapObject mapObject : objs) {
				if (mapObject.getProperties().get("type", "empty", String.class).equals("portal"))
					rep.add(mapObject.getProperties().get("destination", null, String.class));
			}
		}
		while (rep.removeValue(null, true))
			;
		return rep.shrink();
	}
}
