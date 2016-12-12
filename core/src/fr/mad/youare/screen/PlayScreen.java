package fr.mad.youare.screen;

import java.io.IOException;
import java.net.InetAddress;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.esotericsoftware.minlog.Log;

import fr.mad.youare.YouAre;
import fr.mad.youare.components.BodyC;
import fr.mad.youare.components.MovementC;
import fr.mad.youare.system.ServerNetSystem;
import fr.mad.youare.system.ClientNetSystem;
import fr.mad.youare.system.MovementSystem;
import fr.mad.youare.system.NetSystem;
import fr.mad.youare.system.PhysicSystem;
import fr.mad.youare.system.PlayerInputSystem;
import fr.mad.youare.system.RenderingSystem;

public class PlayScreen implements Screen {
	
	private Engine engine;
	private RenderingSystem render;
	private OrthographicCamera cam;
	private SpriteBatch batch;
	private PhysicSystem physic;
	private NetSystem net;
	private MovementSystem movement;
	
	public PlayScreen(YouAre youAre, InetAddress addr, int port) {
		Log.DEBUG = true;
		Log.ERROR = true;
		Log.INFO = true;
		cam = new OrthographicCamera(100, 100);
		batch = new SpriteBatch();
		engine = new Engine();
		
		engine.addSystem(physic = new PhysicSystem());
		if (addr == null)
			try {
				engine.addSystem(net = new ServerNetSystem(youAre, port));
			} catch (IOException e) {
			}
		else
			try {
				engine.addSystem(net = new ClientNetSystem(addr,port));
			} catch (Throwable e) {
			}
		engine.addSystem(movement = new MovementSystem(youAre));
		engine.addSystem(render = new RenderingSystem(cam, batch));
		
		
	}
	
	@Override
	public void show() {
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.1f, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		engine.update(delta);
	}
	
	@Override
	public void resize(int width, int height) {
		render.resize(width, height);
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
