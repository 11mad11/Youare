package fr.mad.youare.components;

import org.luaj.vm2.lib.PackageLib.preload_searcher;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Bits;

public class MovementC implements Component {
	
	public abstract static class MovementBehavior {
		public abstract void update(float delta, Bits keyState);
	}
	
	private MovementBehavior mov;
	
	public MovementC(MovementBehavior mov) {
		this.mov = mov;
	}
	
	public void update(float delta, Bits keyState) {
		mov.update(delta,keyState);
	}
	
	public static class PlayerMovement extends MovementBehavior {
		private Vector2 f;
		private BodyC player;
		private Input input;
		
		public PlayerMovement(float acc, BodyC bodyC, Input input) {
			player = bodyC;
			f = new Vector2(acc, acc);
			this.input = input;
		}
		
		public void update(float delta,Bits keyState) {
			if (player.body == null)
				return;
			Body body = player.body;
			if (body.getPosition().len2() > 2500) {
				Vector2 pos = body.getPosition();
				pos.clamp(-50, 50);
				body.setTransform(-pos.x, -pos.y, 0);
			}
			//System.out.println(keyState.get(Keys.A));
			if (keyState.get(Keys.A))
				body.setTransform(0, 0, 0);
			if (keyState.get(Input.Keys.RIGHT))
				body.applyForceToCenter(f.x, 0, true);
			if (keyState.get(Input.Keys.LEFT))
				body.applyForceToCenter(-f.x, 0, true);
			if (keyState.get(Input.Keys.UP))
				body.applyForceToCenter(0, f.y, true);
			if (keyState.get(Input.Keys.DOWN))
				body.applyForceToCenter(0, -f.y, true);
		}
	}
}
