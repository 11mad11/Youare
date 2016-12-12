package fr.mad.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ImageActor extends Actor{
	
	private TextureRegion region;

	public ImageActor(TextureRegion region) {
		this.region = region;
	}
	
	public ImageActor(Texture tex) {
		this(new TextureRegion(tex));
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
	}
}
