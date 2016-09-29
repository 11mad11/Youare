package fr.mad.youare.item;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class AnimationComponent implements Component {
	public final ObjectMap<String, Animation> frames = new ObjectMap<>();
	public final ObjectMap<String, Float> deltas = new ObjectMap<>();
	
	public Array<AnimationComponent> child = new Array<>();
	
	public AnimationComponent(FileHandle file) {
		
	}
	
	public void set(String key, float delta, Sprite sprite) {
		for (AnimationComponent c : child) {
			c.set(key, delta, sprite);
		}
		if (frames.containsKey(key)) {
			Float ndelta = delta + deltas.get(key);
			Animation a = frames.get(key);
			if(a.getAnimationDuration()<ndelta)
				ndelta-=a.getAnimationDuration();
			deltas.put(key, ndelta);
			sprite.setRegion(a.getKeyFrame(ndelta));
		}
	}
}
