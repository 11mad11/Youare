package fr.mad.youare.fenetre;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;

public class FrameManager implements InputProcessor {
	private DelayedRemovalArray<Frame> frames;
	private Frame back;
	private Frame front;
	
	public FrameManager() {
		frames = new DelayedRemovalArray<Frame>();
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenY = Gdx.graphics.getHeight() - screenY;
		for (int i = 0; i < frames.size; i++) {
			Frame f = frames.get(i);
			if (f.getBounds().contains(screenX, screenY)) {
				if (front == f)
					break;
				frames.removeIndex(i);
				frames.insert(0, f);
				front = f;
				break;
			}
		}
		if (front != null) {
			screenY = Gdx.graphics.getHeight() - screenY;
			front.touchDown(screenX, screenY, pointer, button);
		}
		return false;
	}
	
	public void drawAll() {
		if (back != null) {
			back.act();
			back.draw();
		}
		
		for (int i = frames.size - 1; i >= 0; i--) {
			frames.get(i).act();
			frames.get(i).draw();
		}
	}
	
	/**
	 * @param value
	 * @see com.badlogic.gdx.utils.Array#add(java.lang.Object)
	 */
	public void add(Frame value) {
		frames.add(value);
	}
	
	/**
	 * @param array
	 * @see com.badlogic.gdx.utils.Array#addAll(java.lang.Object[])
	 */
	public void addAll(Frame... array) {
		frames.addAll(array);
	}
	
	/**
	 * @param index
	 * @param value
	 * @see com.badlogic.gdx.utils.Array#insert(int, java.lang.Object)
	 */
	public void insert(int index, Frame value) {
		frames.insert(index, value);
	}
	
	/**
	 * @param value
	 * @param identity
	 * @return
	 * @see com.badlogic.gdx.utils.Array#contains(java.lang.Object, boolean)
	 */
	public boolean contains(Frame value, boolean identity) {
		return frames.contains(value, identity);
	}
	
	/**
	 * @param value
	 * @param identity
	 * @return
	 * @see com.badlogic.gdx.utils.Array#indexOf(java.lang.Object, boolean)
	 */
	public int indexOf(Frame value, boolean identity) {
		return frames.indexOf(value, identity);
	}
	
	/**
	 * @param value
	 * @param identity
	 * @return
	 * @see com.badlogic.gdx.utils.Array#removeValue(java.lang.Object, boolean)
	 */
	public boolean removeValue(Frame value, boolean identity) {
		return frames.removeValue(value, identity);
	}
	
	/**
	 * @param index
	 * @return
	 * @see com.badlogic.gdx.utils.Array#removeIndex(int)
	 */
	public Frame removeIndex(int index) {
		return frames.removeIndex(index);
	}
	
	/**
	 * @param start
	 * @param end
	 * @see com.badlogic.gdx.utils.Array#removeRange(int, int)
	 */
	public void removeRange(int start, int end) {
		frames.removeRange(start, end);
	}
	
	public Frame back() {
		return back;
	}
	
	public Frame front() {
		return front;
	}
	
	public Frame back(Frame back) {
		return this.back = back;
	}
	
	public Frame front(Frame front) {
		return this.front = front;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (front != null)
			return front.keyDown(keycode);
		return false;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if (front != null)
			return front.keyUp(keycode);
		return false;
	}
	
	@Override
	public boolean keyTyped(char character) {
		if (front != null)
			return front.keyTyped(character);
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (front != null)
			return front.touchUp(screenX, screenY, pointer, button);
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (front != null)
			return front.touchDragged(screenX, screenY, pointer);
		return false;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		if (front != null)
			return front.mouseMoved(screenX, screenY);
		return false;
	}
	
	@Override
	public boolean scrolled(int amount) {
		if (front != null)
			return front.scrolled(amount);
		return false;
	}
	
	public void cascade() {
		int i = 10;
		int j = 0;
		boolean t = false;
		for (int k = frames.size - 1; k >= 0; k--) {
			Frame frame = frames.get(k);
			frame.updateVP(j + i, 0 + i, 100, 100);
			i += 40;
			if ((i + 100) > Gdx.graphics.getHeight() || (i + j + 100) > Gdx.graphics.getWidth()) {
				j += 25;
				i = 10;
			}
			if ((j + 100) > Gdx.graphics.getWidth()) {
				
			}
		}
	}
	
	public void shuffle() {
		for (Frame frame : frames) {
			int h = MathUtils.random(10, Gdx.graphics.getWidth() - 100);
			int v = MathUtils.random(10, Gdx.graphics.getHeight() - 100);
			frame.updateVP(h, v, 100, 100);
		}
	}
	
	public void closeSecond() {
		frames.begin();
		for (Frame frame : frames) {
			if (frame.second) {
				frame.dispose();
				frames.removeValue(frame, true);
			}
		}
		frames.end();
	}
}
