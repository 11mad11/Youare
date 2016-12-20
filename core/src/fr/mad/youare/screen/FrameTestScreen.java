package fr.mad.youare.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import fr.mad.actor.Map;
import fr.mad.youare.YouAre;
import fr.mad.youare.fenetre.Frame;
import fr.mad.youare.fenetre.FrameManager;
import fr.mad.youare.fenetre.Test1;

public class FrameTestScreen implements Screen {
	
	private YouAre youAre;
	private Stage stage;
	private Map map;
	private Engine engine;
	private Frame test;
	private SpriteBatch batch;
	private ScreenViewport vp = new ScreenViewport();
	private Array<Rectangle> rects = new Array<>();
	private ShapeRenderer sr = new ShapeRenderer();
	private Frame test2;
	private FrameManager fm;
	
	public FrameTestScreen(YouAre youAre) {
		this.youAre = youAre;
		stage = new Stage();
		engine = new Engine();
		
		fm = new FrameManager();
		test = new Frame(batch = new SpriteBatch(), new Test1()) {
			@Override
			protected void close() {
				fm.removeValue(this, true);
				this.dispose();
			}
		};
		test2 = new Frame(batch, new Test1()) {
			@Override
			protected void close() {
				fm.removeValue(this, true);
				this.dispose();
			}
		};
		test.second = false;
		test2.second = false;
		fm.addAll(test, test2);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.1f, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
			fm.cascade();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
			fm.shuffle();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
			fm.closeSecond();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
			fm.add(new Frame(batch, new Test1()) {
				@Override
				protected void close() {
					fm.removeValue(this, true);
					this.dispose();
				}
			});
		}
		batch.enableBlending();
		batch.begin();
		fm.drawAll();
		batch.end();
	}
	
	@Override
	public void resize(int width, int height) {
		vp.update(width, height);
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
