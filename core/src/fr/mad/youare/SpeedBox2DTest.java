package fr.mad.youare;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.ControllerManagerStub;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.brashmonkey.spriter.SCMLReader;

import fr.mad.youare.screen.MenuSkin;
import fr.mad.youare.system.RenderingS;

public class SpeedBox2DTest implements ApplicationListener {
	public static final float WORLD_TIME_STEP = 1 / 60f;
	public static final int VELOCITY_ITERATIONS = 5;
	public static final int POSITION_ITERATIONS = 6;
	
	private Box2DDebugRenderer r;
	private OrthographicCamera cam;
	private FitViewport vp;
	private World world;
	private float accumulator;
	private ShapeRenderer sr;
	private FitViewport vp2;
	private BitmapFont font;
	private SpriteBatch sb;
	private Body body;
	java.text.DecimalFormat df = new java.text.DecimalFormat("#.####");
	private int step;
	private float mainAccu;
	private int ups;
	private float lastAccu;
	private Vector2 dir = new Vector2();
	private RenderingS re;
	private RealInputProcessor input;
	
	@Override
	public void create() {
		//MenuSkin.custom(null);
		input = new RealInputProcessor();
		Array<Controller> c = Controllers.getControllers();
		input.setInputs(Gdx.input, c.size > 0 ? c.first() : null, null);
		r = new Box2DDebugRenderer();
		cam = new OrthographicCamera();
		cam.position.set(0, 0, 0);
		vp = new FitViewport(10, 10, cam);
		vp2 = new FitViewport(400, 400);
		font = new BitmapFont();
		sr = new ShapeRenderer();
		sb = new SpriteBatch();
		world = new World(new Vector2(), true);
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;
		body = world.createBody(def);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = new CircleShape();
		fdef.shape.setRadius(.5f);
		body.createFixture(fdef);
		re = new RenderingS(0, 0, 1, 1);
	}
	
	@Override
	public void resize(int width, int height) {
		vp.update(width, height);
		vp2.update(width, height);
		re.resize(0, 0, width, height);
	}
	
	@Override
	public void render() {
		re.update(0);
		if (false)
			return;
		float delta = Gdx.graphics.getRawDeltaTime() / 1;
		
		mainAccu += delta;
		float next = mainAccu - lastAccu;
		if (next > 1) {
			lastAccu = mainAccu;
			ups = step;
			step = 0;
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			body.setAngularVelocity(0);
			body.setLinearVelocity(0, 0);
			body.setTransform(0, 0, 0);
			MassData m = new MassData();
			m.mass = 0;
			body.setMassData(m);
			body.setLinearDamping(0);
		}
		
		body.setLinearDamping(0);
		dir.set(input.getXAxis(), input.getYAxis());
		boolean slo = dir.isZero(0.00001f);
		if (!slo)
			body.applyForceToCenter(dir.scl(50), true);
		else
			body.setLinearDamping(20f);
		
		if (Gdx.input.justTouched()) {
			int x = Gdx.input.getX();
			int y = Gdx.input.getY();
			touch(x, y);
		}

		if (body.getLinearVelocity().len() > 10)
			body.setLinearVelocity(body.getLinearVelocity().limit(10));
		
		accumulator += delta;
		while (accumulator >= WORLD_TIME_STEP) {
			world.step(WORLD_TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
			accumulator -= WORLD_TIME_STEP;
			step++;
		}
		
		Gdx.gl.glClearColor(.1f, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cam.position.set(body.getPosition(), 0);
		
		vp.apply();
		cam.update();
		
		sr.setProjectionMatrix(cam.combined);
		sr.begin(ShapeType.Line);
		Vector3 pos = cam.position.cpy();
		//pos.rotate(-mainAccu*10, 0, 0, 1);
		//sr.rotate(0, 0, 1, mainAccu*10);
		sr.setColor(1, 0, 0, 1);
		sr.circle(0, 0, 1f, 20);
		sr.setColor(1, 1, 0, 1);
		for (int i = -10; i < 10; i++) {
			for (int j = -10; j < 10; j++) {
				int x = (int) (j + pos.x);
				int y = (int) (i + pos.y);
				sr.rect(x, y, 1, 1);
				sr.line(x, y, x + 0.5f, y + 1);
			}
		}
		sr.end();
		
		sr.begin(ShapeType.Filled);
		sr.setColor(1, 0, 0, .1f);
		sr.circle(0, 0, 1f, 20);
		sr.end();
		
		sr.begin(ShapeType.Line);
		sr.setColor(1, 0, 1, 1);
		sr.setTransformMatrix(new Matrix4());
		sr.circle(body.getPosition().x, body.getPosition().y, next / 2, 20);
		sr.end();
		
		r.render(world, cam.combined);
		
		sb.setProjectionMatrix(vp2.getCamera().projection);
		sb.begin();
		int y = 198;
		
		font.draw(sb, "speed: ", -200, y);
		font.draw(sb, body.getLinearVelocity().len() + "", -100, y);
		y -= 13;
		
		font.draw(sb, "Main Accu: ", -200, y);
		font.draw(sb, df.format(next), -100, y);
		y -= 13;
		
		font.draw(sb, "Update Accu: ", -200, y);
		font.draw(sb, df.format(accumulator), -100, y);
		y -= 13;
		
		font.draw(sb, "FPS: ", -200, y);
		font.draw(sb, "" + Gdx.graphics.getFramesPerSecond(), -100, y);
		y -= 13;
		
		font.draw(sb, "UPS: ", -200, y);
		font.draw(sb, "" + ups, -100, y);
		y -= 13;
		
		sb.end();
	}
	
	private void touch(int x, int y) {
		Vector2 v = vp.unproject(new Vector2(x, y));
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;
		def.position.set(v);
		final Body b = world.createBody(def);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = new CircleShape();
		fdef.shape.setRadius(.5f);
		fdef.isSensor = true;
		b.createFixture(fdef);
		Timer.instance().scheduleTask(new Task() {
			
			@Override
			public void run() {
				world.destroyBody(b);
				
			}
		}, 5);
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
	public void dispose() {
		world.dispose();
	}
}
