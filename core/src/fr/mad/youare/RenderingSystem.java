package fr.mad.youare;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import fr.mad.youare.item.AnimationComponent;
import fr.mad.youare.item.SpriteComponent;
import fr.mad.youare.item.StateComponent;

public class RenderingSystem extends IteratingSystem {
	
	private ComponentMapper<AnimationComponent> ma = ComponentMapper.getFor(AnimationComponent.class);
	private ComponentMapper<StateComponent> ms = ComponentMapper.getFor(StateComponent.class);
	private ComponentMapper<SpriteComponent> msprite = ComponentMapper.getFor(SpriteComponent.class);
	private Array<Entity> entitys = new Array<>();
	private SpriteBatch batch = new SpriteBatch();
	
	public RenderingSystem() {
		super(Family.all(SpriteComponent.class).get());
		
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		for (Entity e:entitys) {
			batch.begin();
			SpriteComponent s = msprite.get(e);
			s.draw(batch,deltaTime,ms.get(e));
			batch.end();
		}
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		entitys.add(entity);
	}
	
}
