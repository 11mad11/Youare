package fr.mad.actor;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import fr.mad.youare.fenetre.Frame;
import fr.mad.youare.rendering.OrthogonalTiledMapRendererPlusEntity;

public class Map extends Frame {
	private TiledMap map;
	private OrthogonalTiledMapRendererPlusEntity mapRenderer;
	private OrthographicCamera cam;
	private float animation;
	
	public Map(TiledMap map, Batch batch) {
		super(batch);
		this.map = map;
		mapRenderer = new OrthogonalTiledMapRendererPlusEntity(map, 1 / 16f, batch);
		cam = map.getProperties().get("cam", OrthographicCamera.class);
		if (cam == null) {
			cam = new OrthographicCamera();
			map.getProperties().put("cam", cam);
		}
		map.getProperties().put("vp", vp);
		
	}
	/**
	 * animation
	 * @param delta
	 */
	public void act(float delta) {
		animation+=delta;
	}
	
	public TiledMap getTiledMap() {
		return map;
	}
	
	@Override
	protected Viewport initVP() {
		return new FillViewport(10, 10, cam);
	}
	
	@Override
	protected void render0() {
		mapRenderer.render();
	}
}
