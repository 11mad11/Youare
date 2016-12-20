package fr.mad.youare.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import fr.mad.youare.components.AnimtionC;
import fr.mad.youare.components.Components;
import fr.mad.youare.components.SpriteC;
import fr.mad.youare.components.TransformC;

public class RenderingSystem extends EntitySystem {
	
	private OrthographicCamera cam;
	private Batch batch;
	private ShapeRenderer sr;
	private BitmapFont font;
	private FitViewport fontVP;
	private ImmutableArray<Entity> sprites;
	private ImmutableArray<Entity> animations;
	private Box2DDebugRenderer debug;
	private PhysicSystem physic;
	private Body player;
	java.text.DecimalFormat df = new java.text.DecimalFormat("#.####");
	
	public RenderingSystem(OrthographicCamera cam, Batch batch) {
		this.cam = cam;
		this.batch = batch;
		this.sr = new ShapeRenderer();
		font = new BitmapFont();
		fontVP = new FitViewport(400, 400);
		this.debug = new Box2DDebugRenderer();
		debug.setDrawAABBs(true);
		debug.setDrawVelocities(true);
	}
	
	public void resize(int width, int height) {
		fontVP.update(width, height, false);
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		sprites = engine.getEntitiesFor(Family.all(TransformC.class, SpriteC.class).exclude(AnimtionC.class).get());
		animations = engine.getEntitiesFor(Family.all(TransformC.class, AnimtionC.class).get());
		physic = engine.getSystem(PhysicSystem.class);
	}
	
	@Override
	public void update(float deltaTime) {
		cam.update();
		
		sr.setProjectionMatrix(cam.combined);
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLUE);
		sr.circle(0, 0, cam.viewportHeight / 2);
		sr.end();
		
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		for (Entity e : sprites) {
			SpriteC s = Components.spriteC.get(e);
			TransformC t = Components.transformC.get(e);
			t.begin();
			s.apply(t);
			batch.draw(s.sprite, s.sprite.getX(), s.sprite.getY());
			//t.end(); //add this if you modify t(TransformC)
		}
		for (Entity e : animations) {
			AnimtionC a = Components.animationC.get(e);
			TransformC t = Components.transformC.get(e);
			t.begin();
			//TODO animation
			t.end();
		}
		batch.end();
		this.debug.render(physic.world, cam.combined);
		
		batch.setProjectionMatrix(fontVP.getCamera().combined);
		batch.begin();
		int y = 198;
		
//		font.draw(batch, "speed: ", -200, y);
//		font.draw(batch, player.player.getLinearVelocity().len() + "", -100, y);
//		y -= 13;
		
		//		font.draw(batch, "Main Accu: ", -200, y);
		//		font.draw(batch, df.format(next), -100, y);
		//		y -= 13;
		
		//		font.draw(batch, "Update Accu: ", -200, y);
		//		font.draw(batch, df.format(accumulator), -100, y);
		//		y -= 13;
		
		font.draw(batch, "FPS: ", -200, y);
		font.draw(batch, "" + Gdx.graphics.getFramesPerSecond(), -100, y);
		y -= 13;
		
		//		font.draw(batch, "UPS: ", -200, y);
		//		font.draw(batch, "" + ups, -100, y);
		//		y -= 13;
		
		batch.end();
	}
}
