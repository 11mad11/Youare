package fr.mad.youare.map;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import fr.mad.youare.map.ressource.Ressource;

public class Map extends TiledMap {
	public final Engine engine = new Engine();
	public final TiledMapTileSets tilesets;
	public final MapLayers layers;
	public final MapProperties properties;
	private Ressource r;
	
	public Map(TiledMap map, Ressource r) {
		super();
		tilesets = map.getTileSets();
		layers = this.getLayers();
		properties = map.getProperties();
		this.r = r;
	}
	
	@Override
	public MapProperties getProperties() {
		return properties;
	}
	
	@Override
	public MapLayers getLayers() {
		return layers;
	}
	
	@Override
	public TiledMapTileSets getTileSets() {
		return tilesets;
	}
	
	@Override
	public void setOwnedResources(Array<? extends Disposable> resources) {
		for (Disposable d : resources) {
			r.addToDispose(d);
		}
	}
	
}
