package fr.mad.youare.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import fr.mad.youare.RealInputProcessor;
import fr.mad.youare.YouAre;
import fr.mad.youare.components.BodyC;
import fr.mad.youare.components.Components;
import fr.mad.youare.components.MovementC;

public class MovementSystem extends EntitySystem {
	private RealInputProcessor input;
	private ImmutableArray<Entity> entitys;

	public MovementSystem(YouAre youAre) {
		input = youAre.input;
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		entitys = engine.getEntitiesFor(Family.all(MovementC.class).get());
	}
	
	@Override
	public void update(float deltaTime) {
		for (Entity entity : entitys) {
			Components.movementC.get(entity).update(deltaTime);
		}
	}
}
