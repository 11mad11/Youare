package fr.mad.youare.item;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteComponent implements Component {
	
	public Sprite sprite;
	public AnimationComponent animation;
	
	public boolean isAnimate = false;
	
	public SpriteComponent() {
		
	}
	
	public void draw(SpriteBatch batch, float deltaTime, StateComponent state) {
		if (sprite == null)
			sprite = new Sprite();
		if (isAnimate && animation != null && state != null) {
			animation.set(state.state, deltaTime, sprite);
		}
		sprite.draw(batch);
	}
	
}
