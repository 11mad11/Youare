package fr.mad.youare.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import fr.mad.youare.components.BodyC;
import fr.mad.youare.components.Components;
import fr.mad.youare.components.SpriteC;
import fr.mad.youare.components.TransformC;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PhysicSystem extends EntitySystem implements EntityListener {
	public static final float WORLD_TIME_STEP = 1 / 60f;
	public static final int VELOCITY_ITERATIONS = 5;
	public static final int POSITION_ITERATIONS = 6;
	
	public World world;
	private float accumulator;
	private Family family = Family.all(BodyC.class).get();
	private ImmutableArray<Entity> entitys;
	
	public PhysicSystem() {
		world = new World(new Vector2(), true);
		
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		entitys = engine.getEntitiesFor(Family.all(TransformC.class, BodyC.class).get());
		engine.addEntityListener(family, this);
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		engine.removeEntityListener(this);
	}
	
	@Override
	public void update(float deltaTime) {
		accumulator += deltaTime;
		while (accumulator >= WORLD_TIME_STEP) {
			world.step(WORLD_TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
			accumulator -= WORLD_TIME_STEP;
		}
	}
	
	@Override
	public void entityAdded(Entity entity) {
		BodyC bc = Components.bodyC.get(entity);
		bc.begin();
		bc.body = world.createBody(bc.bodyDef);
		for (FixtureDef f : bc.fixtureDefs.keys()) {
			bc.body.createFixture(f);
		}
		
		if (!bc.keepDef) {
			bc.bodyDef = null;
			//bc.fixtureDefs.clear();
			bc.fixtureDefs = null;
		}
		
		//entity.add(new TransformC(bc.body));
		
		if (bc.generateSpriteC) {
			//TODO
			//SpriteC.debug(f);
		}
		bc.body.resetMassData();
		bc.end();
	}
	
	@Override
	public void entityRemoved(Entity entity) {
	}
}
