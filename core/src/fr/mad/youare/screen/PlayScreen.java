package fr.mad.youare.screen;

import java.io.IOException;
import java.net.InetAddress;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.esotericsoftware.minlog.Log;

import fr.mad.youare.YouAre;
import fr.mad.youare.system.ClientNetSystem;
import fr.mad.youare.system.NetSystem;
import fr.mad.youare.system.PhysicSystem;
import fr.mad.youare.system.RenderingSystem;
import fr.mad.youare.system.ServerNetSystem;

public class PlayScreen implements Screen {
	
	private Engine engine;
	private RenderingSystem render;
	private OrthographicCamera cam;
	private SpriteBatch batch;
	private PhysicSystem physic;
	private ServerNetSystem server;
	private ClientNetSystem client;
	private YouAre youAre;
	private GameSystem game;
	
	public PlayScreen(YouAre youAre, InetAddress addr, int port) {
		//Log.DEBUG = true;
		Log.ERROR = true;
		Log.INFO = true;
		Log.WARN = true;
		this.youAre = youAre;
		cam = new OrthographicCamera(100, 100);
		batch = new SpriteBatch();
		engine = new Engine();
		
		engine.addSystem(physic = new PhysicSystem());
		engine.addSystem(render = new RenderingSystem(cam, batch));
		
		engine.addSystem(game = new GameSystem());
		
		start(addr,port);
	}
	
	private void start(InetAddress addr, int port) {
		if (addr == null)
			try {
				engine.addSystem(server = new ServerNetSystem(youAre, port));
				engine.addSystem(client = new ClientNetSystem(InetAddress.getLocalHost(), port));
				youAre.input.setBetterInput(client);
			} catch (IOException e) {
				throw new Error(e);
			}
		else
			try {
				engine.addSystem(client = new ClientNetSystem(addr, port));
				youAre.input.setBetterInput(client);
			} catch (Throwable e) {
				throw new Error(e);
			}
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
