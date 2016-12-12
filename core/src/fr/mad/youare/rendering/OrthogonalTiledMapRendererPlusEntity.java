package fr.mad.youare.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import fr.mad.youare.system.RenderingSystem;

public class OrthogonalTiledMapRendererPlusEntity extends OrthogonalTiledMapRenderer {
	
	public OrthogonalTiledMapRendererPlusEntity(TiledMap map, float unitScale, Batch batch) {
		super(map, unitScale, batch);
	}
	
	@Override
	public void render() {
		for (MapLayer layer : map.getLayers()) {
			if (layer.isVisible()) {
				renderTileLayer((TiledMapTileLayer) layer);
				renderImageLayer((TiledMapImageLayer) layer);
				renderObjects(layer);
			}
		}
	}
	
	@Override
	public void renderObject(MapObject object) {
		
	}
	
	@Override
	public void beginRender() {
		super.beginRender();
	}
	
	@Override
	public void endRender() {
		super.endRender();
	}
}
