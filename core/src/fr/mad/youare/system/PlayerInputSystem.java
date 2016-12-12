package fr.mad.youare.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

import fr.mad.youare.RealInputProcessor;
import fr.mad.youare.YouAre;
import fr.mad.youare.components.BodyC;
import fr.mad.youare.components.Components;
import fr.mad.youare.components.TransformC;

public class PlayerInputSystem extends EntitySystem {
	private RealInputProcessor input;
	private ImmutableArray<Entity> entitys;
	public Body player;
	private Vector2 f = new Vector2(5, 5);
	
	public PlayerInputSystem(YouAre youAre) {
		input = youAre.input;
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		entitys = engine.getEntitiesFor(Family.all(BodyC.class).get());
	}
	
	@Override
	public void update(float deltaTime) {
		if (player == null) {
			try {
				player = Components.bodyC.get(entitys.first()).body;
			} catch (Throwable e) {
				return;
			}
		}
		if(player.getPosition().len2()>2500){
			Vector2 pos = player.getPosition();
			pos.clamp(-50, 50);
			player.setTransform(-pos.x, -pos.y, 0);
		}
		if (input.isKeyJustPressed(Keys.A))
			player.setTransform(0, 0, 0);
		if (input.isKeyPressed(Input.Keys.RIGHT))
			player.applyForceToCenter(f.x, 0, true);
		if (input.isKeyPressed(Input.Keys.LEFT))
			player.applyForceToCenter(-f.x, 0, true);
		if (input.isKeyPressed(Input.Keys.UP))
			player.applyForceToCenter(0, f.y, true);
		if (input.isKeyPressed(Input.Keys.DOWN))
			player.applyForceToCenter(0, -f.y, true);
	}
}
