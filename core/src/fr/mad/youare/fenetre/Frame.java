package fr.mad.youare.fenetre;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class Frame extends Stage {
	
	public static abstract class Pane {
		public Viewport vp;
		public Batch batch;
		
		protected abstract Viewport initVP();
		
		protected abstract void render(float f);
		
		public abstract void dispose();
	}
	
	//TODO Transform to private variable editable
	private final static int gap = 3;
	private final static int upGap = 14 + (gap * 2);
	private final static int size = upGap - (gap * 2);
	private final static int xgap = gap;
	private final static int ygap = gap;
	
	protected TextureRegion blank;
	private Rectangle bounds = new Rectangle();
	private Pane pane;
	private Button exit;
	private Rectangle moveR;
	private boolean dragged;
	private TextureRegion corner;
	private Rectangle resizeR;
	private Rectangle childR;
	private Slider slider;
	private TextureRegion square;
	private Color c;
	private Texture texture;
	public boolean second = true;
	
	public Frame(Batch batch, Pane pane) {
		super(new ScreenViewport(), batch);
		this.pane = pane;
		pane.batch = batch;
		pane.vp = pane.initVP();
		Pixmap p = new Pixmap(100, 100, Format.RGBA8888);
		
		p.setColor(1, 1, 1, 1);
		p.fillRectangle(0, 0, 10, 10);
		
		p.setColor(1, 0, 0, 1);
		p.fillRectangle(0, 10, 20, 20);
		p.setColor(1, .2f, .2f, 1);
		p.drawCircle(10, 20, 5);
		
		p.setColor(.9f, .9f, .9f, 1);
		p.fillRectangle(20, 10, 20, 20);
		p.setColor(1, .2f, .2f, 1);
		p.drawCircle(30, 20, 5);
		
		p.setColor(1, .3f, .3f, 1);
		p.fillRectangle(40, 10, 20, 20);
		p.setColor(1, .1f, .1f, 1);
		p.drawCircle(50, 20, 5);
		
		p.setColor(1, .3f, .3f, 1);
		//p.fillTriangle(60, 30, 80, 30, 80, 10);
		Rectangle r = new Rectangle(60, 10, 20, 20);
		Rectangle rm = new Rectangle(60, 10, 20 - gap, 20 - (gap));
		for (int i = 0; i < r.width; i++) {
			for (int j = 0; j < r.height; j++) {
				int x = i + (int) r.x;
				int y = j + (int) r.y;
				if (rm.contains(x, y))
					continue;
				p.drawPixel(x, y);
			}
		}
		
		p.setColor(0, 0, 0, 1);
		p.drawRectangle(0, 30, 70, 70);
		
		texture = new Texture(p);
		blank = new TextureRegion(texture, 0, 0, 1, 1);
		corner = new TextureRegion(texture, 60, 10, 20, 20);
		square = new TextureRegion(texture, 0, 30, 70, 70);
		ButtonStyle style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(texture, 0, 10, 20, 20));
		style.down = new TextureRegionDrawable(new TextureRegion(texture, 20, 10, 20, 20));
		style.over = new TextureRegionDrawable(new TextureRegion(texture, 40, 10, 20, 20));
		exit = new Button(style);
		exit.setVisible(true);
		this.addActor(exit);
		SliderStyle sliderStyle = new SliderStyle();
		sliderStyle.knob = new TextureRegionDrawable(blank).tint(Color.RED);
		sliderStyle.background = new TextureRegionDrawable(blank).tint(Color.CORAL);
		sliderStyle.background.setMinHeight(4);
		sliderStyle.knob.setMinHeight(8);
		sliderStyle.knob.setMinWidth(4);
		this.addActor(slider = new Slider(0.2f, 1, 0.05f, false, sliderStyle));
		slider.setValue(.8f);
		getRoot().setTouchable(Touchable.enabled);
		
		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				close();
			}
		});
		getRoot().addListener(new ClickListener() {
			private Vector2 delta;
			private boolean resize;
			private boolean move;
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (event.isHandled())
					return false;
				Frame t = Frame.this;
				Viewport vp = t.getViewport();
				
				resize = resizeR.contains(x, y) && !childR.contains(x, y);
				move = moveR.contains(x, y) && !resize;
				if (move) {
					delta = vp.project(new Vector2(x, y));
					delta.sub(vp.getScreenX(), vp.getScreenY());
				} else if (resize) {
					delta = vp.project(new Vector2(x, y));
				}
				return move || resize;
			}
			
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				Frame t = Frame.this;
				Viewport vp = t.getViewport();
				
				if (move) {
					Vector2 v = t.getViewport().project(new Vector2(x, y));
					v.sub(delta);
					t.updateVP((int) v.x, (int) v.y, vp.getScreenWidth(), vp.getScreenHeight());
				} else if (resize) {
					Vector2 v = t.getViewport().project(new Vector2(x, y));
					
					Vector2 tmp = v.cpy();
					v.sub(delta);
					delta.set(tmp);
					
					v.y = -v.y;
					
					int screenHeight = Math.max((int) v.y + vp.getScreenHeight(), upGap - (ygap) + size);
					if (screenHeight != (int) v.y + vp.getScreenHeight())
						v.y = 0;
					
					t.updateVP(vp.getScreenX(), vp.getScreenY() - (int) v.y, (int) v.x + vp.getScreenWidth(), (int) v.y + vp.getScreenHeight());
				}
			}
		});
		c = Color.FIREBRICK.cpy();
	}
	
	protected abstract void close();
	
	@Override
	public void draw() {
		getViewport().apply(true);
		Batch batch = getBatch();
		batch.setProjectionMatrix(getViewport().getCamera().combined);
		c.a = slider.getValue();
		batch.setColor(c);
		batch.draw(blank, 0, 0, getViewport().getWorldWidth(), getViewport().getWorldHeight());
		batch.setColor(1, 1, 0, slider.getValue());
		batch.draw(square, gap, gap, pane.vp.getScreenWidth(), pane.vp.getScreenHeight());
		batch.draw(corner, getViewport().getWorldWidth() - 20, 0);
		getRoot().draw(batch, slider.getValue());
		batch.flush();
		
		pane.vp.apply(true);
		batch.setProjectionMatrix(pane.vp.getCamera().combined);
		pane.render(slider.getValue());
		batch.flush();
	}
	
	public void updateVP(int screenX, int screenY, int screenWidth, int screenHeight) {
		//System.out.println("Fenetre.updateVP()");
		
		//TODO export to constant/private variable
		screenWidth = Math.max(screenWidth, (size * 4) + gap);
		screenHeight = Math.max(screenHeight, upGap - (ygap) + size);
		
		pane.vp.update(screenWidth - (gap * 2), screenHeight - gap - upGap);
		pane.vp.setScreenPosition(screenX + gap, screenY + gap);
		
		getViewport().update(screenWidth, screenHeight);
		getViewport().setScreenPosition(screenX, screenY);
		
		bounds.set(screenX, screenY, screenWidth, screenHeight);
		
		exit.setBounds(screenWidth - size - xgap, screenHeight - size - ygap, size, size);
		slider.setBounds(xgap, screenHeight - size - ygap, size * 3, size);
		childR = new Rectangle(gap, gap, screenWidth - (gap * 2) - 1, screenHeight - gap - upGap - 1);
		moveR = new Rectangle(0, screenHeight - gap - upGap + ygap, screenWidth, upGap - (ygap));
		resizeR = new Rectangle(screenWidth - 20, 0, 20, 20);
		//		exit = new Rectangle();
		//		
		//		buttons[0] = new Rectangle(screenX + screenWidth - (size * 2) - (xgap * 2), screenY + screenHeight - size - ygap, size, size);
		//		buttons[1] = new Rectangle(screenX + screenWidth - (size * 1) - (xgap * 1), screenY + screenHeight - size - ygap, size, size);
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	@Override
	public void dispose() {
		pane.dispose();
		texture.dispose();
		super.dispose();
	}
}
