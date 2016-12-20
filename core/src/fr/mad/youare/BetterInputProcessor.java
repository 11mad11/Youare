package fr.mad.youare;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;

import fr.mad.youare.empty.EmptyInputListener;

public class BetterInputProcessor implements InputProcessor, Input {
	
	private class Translator implements InputProcessor {
		
		private InputListener in;
		
		@Override
		public boolean keyDown(int keycode) {
			in.keyChange(keycode, true);
			return false;
		}
		
		@Override
		public boolean keyUp(int keycode) {
			in.keyChange(keycode, false);
			return false;
		}
		
		@Override
		public boolean keyTyped(char character) {
			return false;
		}
		
		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			return false;
		}
		
		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			return false;
		}
		
		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			return false;
		}
		
		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}
		
		@Override
		public boolean scrolled(int amount) {
			return false;
		}
		
		public void set(InputListener in) {
			if(in==null)
				in = new EmptyInputListener();
			this.in = in;
		}
		
	}
	
	public static interface InputListener {
		public void keyChange(int key, boolean state);
		
		public void touchDown(int screenX, int screenY, int pointer, int button);
		
		public void TouchUp(int screenX, int screenY, int pointer, int button);
		
		public void touchDragged(int screenX, int screenY, int pointer);
		
		public void scrolled(int amount);
		
		public void controller(Controller c, boolean main);
	}
	
	private InputProcessor inputProcessor;
	private Translator translator;
	private Input input;
	
	public BetterInputProcessor() {
		translator = new Translator();
		setInputProcessor(null);
		setBetterInput(null);
	}
	
	public void setLast(Input last) {
		if (this.input != null)
			return;
		this.input = last;
		last.setInputProcessor(this);
	}
	
	public void setBetterInput(InputListener in) {
		translator.set(in);
	}
	
	public void setInputProcessor(InputProcessor ip) {
		if (ip == null)
			ip = translator;
		inputProcessor = ip;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		return inputProcessor.keyDown(keycode);
	}
	
	@Override
	public boolean keyUp(int keycode) {
		return inputProcessor.keyUp(keycode);
	}
	
	@Override
	public boolean keyTyped(char character) {
		return inputProcessor.keyTyped(character);
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return inputProcessor.touchDown(screenX, screenY, pointer, button);
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return inputProcessor.touchUp(screenX, screenY, pointer, button);
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return inputProcessor.touchDragged(screenX, screenY, pointer);
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return inputProcessor.mouseMoved(screenX, screenY);
	}
	
	@Override
	public boolean scrolled(int amount) {
		return inputProcessor.scrolled(amount);
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#getAccelerometerX()
	 */
	public float getAccelerometerX() {
		return input.getAccelerometerX();
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#getAccelerometerY()
	 */
	public float getAccelerometerY() {
		return input.getAccelerometerY();
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#getAccelerometerZ()
	 */
	public float getAccelerometerZ() {
		return input.getAccelerometerZ();
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#getGyroscopeX()
	 */
	public float getGyroscopeX() {
		return input.getGyroscopeX();
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#getGyroscopeY()
	 */
	public float getGyroscopeY() {
		return input.getGyroscopeY();
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#getGyroscopeZ()
	 */
	public float getGyroscopeZ() {
		return input.getGyroscopeZ();
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#getX()
	 */
	public int getX() {
		return input.getX();
	}
	
	/**
	 * @param pointer
	 * @return
	 * @see com.badlogic.gdx.Input#getX(int)
	 */
	public int getX(int pointer) {
		return input.getX(pointer);
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#getDeltaX()
	 */
	public int getDeltaX() {
		return input.getDeltaX();
	}
	
	/**
	 * @param pointer
	 * @return
	 * @see com.badlogic.gdx.Input#getDeltaX(int)
	 */
	public int getDeltaX(int pointer) {
		return input.getDeltaX(pointer);
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#getY()
	 */
	public int getY() {
		return input.getY();
	}
	
	/**
	 * @param pointer
	 * @return
	 * @see com.badlogic.gdx.Input#getY(int)
	 */
	public int getY(int pointer) {
		return input.getY(pointer);
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#getDeltaY()
	 */
	public int getDeltaY() {
		return input.getDeltaY();
	}
	
	/**
	 * @param pointer
	 * @return
	 * @see com.badlogic.gdx.Input#getDeltaY(int)
	 */
	public int getDeltaY(int pointer) {
		return input.getDeltaY(pointer);
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#isTouched()
	 */
	public boolean isTouched() {
		return input.isTouched();
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#justTouched()
	 */
	public boolean justTouched() {
		return input.justTouched();
	}
	
	/**
	 * @param pointer
	 * @return
	 * @see com.badlogic.gdx.Input#isTouched(int)
	 */
	public boolean isTouched(int pointer) {
		return input.isTouched(pointer);
	}
	
	/**
	 * @param button
	 * @return
	 * @see com.badlogic.gdx.Input#isButtonPressed(int)
	 */
	public boolean isButtonPressed(int button) {
		return input.isButtonPressed(button);
	}
	
	/**
	 * @param key
	 * @return
	 * @see com.badlogic.gdx.Input#isKeyPressed(int)
	 */
	public boolean isKeyPressed(int key) {
		return input.isKeyPressed(key);
	}
	
	/**
	 * @param key
	 * @return
	 * @see com.badlogic.gdx.Input#isKeyJustPressed(int)
	 */
	public boolean isKeyJustPressed(int key) {
		return input.isKeyJustPressed(key);
	}
	
	/**
	 * @param listener
	 * @param title
	 * @param text
	 * @param hint
	 * @see com.badlogic.gdx.Input#getTextInput(com.badlogic.gdx.Input.TextInputListener,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public void getTextInput(TextInputListener listener, String title, String text, String hint) {
		input.getTextInput(listener, title, text, hint);
	}
	
	/**
	 * @param visible
	 * @see com.badlogic.gdx.Input#setOnscreenKeyboardVisible(boolean)
	 */
	public void setOnscreenKeyboardVisible(boolean visible) {
		input.setOnscreenKeyboardVisible(visible);
	}
	
	/**
	 * @param milliseconds
	 * @see com.badlogic.gdx.Input#vibrate(int)
	 */
	public void vibrate(int milliseconds) {
		input.vibrate(milliseconds);
	}
	
	/**
	 * @param pattern
	 * @param repeat
	 * @see com.badlogic.gdx.Input#vibrate(long[], int)
	 */
	public void vibrate(long[] pattern, int repeat) {
		input.vibrate(pattern, repeat);
	}
	
	/**
	 * 
	 * @see com.badlogic.gdx.Input#cancelVibrate()
	 */
	public void cancelVibrate() {
		input.cancelVibrate();
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#getAzimuth()
	 */
	public float getAzimuth() {
		return input.getAzimuth();
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#getPitch()
	 */
	public float getPitch() {
		return input.getPitch();
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#getRoll()
	 */
	public float getRoll() {
		return input.getRoll();
	}
	
	/**
	 * @param matrix
	 * @see com.badlogic.gdx.Input#getRotationMatrix(float[])
	 */
	public void getRotationMatrix(float[] matrix) {
		input.getRotationMatrix(matrix);
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#getCurrentEventTime()
	 */
	public long getCurrentEventTime() {
		return input.getCurrentEventTime();
	}
	
	/**
	 * @param catchBack
	 * @see com.badlogic.gdx.Input#setCatchBackKey(boolean)
	 */
	public void setCatchBackKey(boolean catchBack) {
		input.setCatchBackKey(catchBack);
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#isCatchBackKey()
	 */
	public boolean isCatchBackKey() {
		return input.isCatchBackKey();
	}
	
	/**
	 * @param catchMenu
	 * @see com.badlogic.gdx.Input#setCatchMenuKey(boolean)
	 */
	public void setCatchMenuKey(boolean catchMenu) {
		input.setCatchMenuKey(catchMenu);
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#isCatchMenuKey()
	 */
	public boolean isCatchMenuKey() {
		return input.isCatchMenuKey();
	}
	
	/**
	 * @param peripheral
	 * @return
	 * @see com.badlogic.gdx.Input#isPeripheralAvailable(com.badlogic.gdx.Input.Peripheral)
	 */
	public boolean isPeripheralAvailable(Peripheral peripheral) {
		return input.isPeripheralAvailable(peripheral);
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#getRotation()
	 */
	public int getRotation() {
		return input.getRotation();
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#getNativeOrientation()
	 */
	public Orientation getNativeOrientation() {
		return input.getNativeOrientation();
	}
	
	/**
	 * @param catched
	 * @see com.badlogic.gdx.Input#setCursorCatched(boolean)
	 */
	public void setCursorCatched(boolean catched) {
		input.setCursorCatched(catched);
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.Input#isCursorCatched()
	 */
	public boolean isCursorCatched() {
		return input.isCursorCatched();
	}
	
	/**
	 * @param x
	 * @param y
	 * @see com.badlogic.gdx.Input#setCursorPosition(int, int)
	 */
	public void setCursorPosition(int x, int y) {
		input.setCursorPosition(x, y);
	}
	
	@Override
	public InputProcessor getInputProcessor() {
		return inputProcessor;
	}
	
}
