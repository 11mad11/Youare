package fr.mad.youare;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Sort;

import fr.mad.youare.empty.EmptyController;
import fr.mad.youare.empty.EmptyInput;
import fr.mad.youare.empty.EmptyTouchPad;

public class RealInputProcessor implements Input, InputProcessor, Controller {
	public class InputProcessorC implements Comparable<InputProcessorC> {
		
		public InputProcessor p;
		public int l;
		
		public InputProcessorC(InputProcessor p, int priotiy) {
			this.p = p;
			this.l = priotiy;
		}
		
		@Override
		public int compareTo(InputProcessorC o) {
			return l - o.l;
		}
		
	}
	
	private Input input;
	private Array<InputProcessorC> stack = new Array<>();
	private Sort sort;
	private Controller controller;
	private Touchpad touchPad;
	public int UP_BUTTON = Keys.UP;
	public int CONTROLLER_X_AXIS = 3;
	public int DOWN_BUTTON = Keys.DOWN;
	public int RIGHT_BUTTON = Keys.RIGHT;
	public int LEFT_BUTTON = Keys.LEFT;
	public int CONTROLLER_Y_AXIS = 2;
	
	public RealInputProcessor() {
		input = new EmptyInput();
		controller = new EmptyController();
		touchPad = new EmptyTouchPad();
	}
	
	public void setInputs(Input input, Controller controller, Touchpad touchPad) {
		if (input != null)
			this.input = input;
		if (controller != null)
			this.controller = controller;
		if (touchPad != null)
			this.touchPad = touchPad;
	}
	
	private void sort() {
		if (sort == null)
			sort = new Sort();
		sort.sort(stack);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * @see com.badlogic.gdx.InputProcessor#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keycode) {
		for (InputProcessorC p : stack) {
			if (p.p.keyDown(keycode))
				break;
		}
		return false;
	}
	
	/**
	 * @see com.badlogic.gdx.InputProcessor#keyUp(int)
	 */
	@Override
	public boolean keyUp(int keycode) {
		for (InputProcessorC p : stack) {
			if (p.p.keyUp(keycode))
				break;
		}
		return false;
	}
	
	/**
	 * @see com.badlogic.gdx.InputProcessor#keyTyped(char)
	 */
	@Override
	public boolean keyTyped(char character) {
		for (InputProcessorC p : stack) {
			if (p.p.keyTyped(character))
				break;
		}
		return false;
	}
	
	/**
	 * @see com.badlogic.gdx.InputProcessor#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		for (InputProcessorC p : stack) {
			if (p.p.touchDown(screenX, screenY, pointer, button))
				break;
		}
		return false;
	}
	
	/**
	 * @see com.badlogic.gdx.InputProcessor#touchUp(int, int, int, int)
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		for (InputProcessorC p : stack) {
			if (p.p.touchUp(screenX, screenY, pointer, button))
				break;
		}
		return false;
	}
	
	/**
	 * @see com.badlogic.gdx.InputProcessor#touchDragged(int, int, int)
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		for (InputProcessorC p : stack) {
			if (p.p.touchDragged(screenX, screenY, pointer))
				break;
		}
		return false;
	}
	
	/**
	 * @see com.badlogic.gdx.InputProcessor#mouseMoved(int, int)
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		for (InputProcessorC p : stack) {
			if (p.p.mouseMoved(screenX, screenY))
				break;
		}
		return false;
	}
	
	/**
	 * @see com.badlogic.gdx.InputProcessor#scrolled(int)
	 */
	@Override
	public boolean scrolled(int amount) {
		for (InputProcessorC p : stack) {
			if (p.p.scrolled(amount))
				break;
		}
		return false;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void addInputProcessor(InputProcessor p, int priority) {
		stack.add(new InputProcessorC(p, priority));
		sort();
	}
	
	public void removeInputProcessor(InputProcessor p) {
		Array<InputProcessorC> toR = new Array<>();
		for (InputProcessorC i : stack)
			if (i.p == p)
				toR.add(i);
		for (InputProcessorC i : toR)
			stack.removeValue(i, true);
		sort();
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public float getXAxis() {
		float v = controller.getAxis(CONTROLLER_X_AXIS);
		if (Math.abs(v) < Math.abs(touchPad.getKnobPercentX()))
			v = touchPad.getKnobPercentX();
		if (input.isKeyPressed(RIGHT_BUTTON))
			v = 1;
		if (input.isKeyPressed(LEFT_BUTTON))
			v = -1;
		return v;
	}
	
	public float getYAxis() {
		float v = -controller.getAxis(CONTROLLER_Y_AXIS);
		if (Math.abs(v) < Math.abs(touchPad.getKnobPercentY()))
			v = touchPad.getKnobPercentY();
		if (input.isKeyPressed(UP_BUTTON))
			v = 1;
		if (input.isKeyPressed(DOWN_BUTTON))
			v = -1;
		return v;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * @param buttonCode
	 * @return
	 * @see com.badlogic.gdx.controllers.Controller#getButton(int)
	 */
	public boolean getButton(int buttonCode) {
		return controller.getButton(buttonCode);
	}
	
	/**
	 * @param axisCode
	 * @return
	 * @see com.badlogic.gdx.controllers.Controller#getAxis(int)
	 */
	public float getAxis(int axisCode) {
		return controller.getAxis(axisCode);
	}
	
	/**
	 * @param povCode
	 * @return
	 * @see com.badlogic.gdx.controllers.Controller#getPov(int)
	 */
	public PovDirection getPov(int povCode) {
		return controller.getPov(povCode);
	}
	
	/**
	 * @param sliderCode
	 * @return
	 * @see com.badlogic.gdx.controllers.Controller#getSliderX(int)
	 */
	public boolean getSliderX(int sliderCode) {
		return controller.getSliderX(sliderCode);
	}
	
	/**
	 * @param sliderCode
	 * @return
	 * @see com.badlogic.gdx.controllers.Controller#getSliderY(int)
	 */
	public boolean getSliderY(int sliderCode) {
		return controller.getSliderY(sliderCode);
	}
	
	/**
	 * @param accelerometerCode
	 * @return
	 * @see com.badlogic.gdx.controllers.Controller#getAccelerometer(int)
	 */
	public Vector3 getAccelerometer(int accelerometerCode) {
		return controller.getAccelerometer(accelerometerCode);
	}
	
	/**
	 * @param sensitivity
	 * @see com.badlogic.gdx.controllers.Controller#setAccelerometerSensitivity(float)
	 */
	public void setAccelerometerSensitivity(float sensitivity) {
		controller.setAccelerometerSensitivity(sensitivity);
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.controllers.Controller#getName()
	 */
	public String getName() {
		return controller.getName();
	}
	
	/**
	 * @param listener
	 * @see com.badlogic.gdx.controllers.Controller#addListener(com.badlogic.gdx.controllers.ControllerListener)
	 */
	public void addListener(ControllerListener listener) {
		controller.addListener(listener);
	}
	
	/**
	 * @param listener
	 * @see com.badlogic.gdx.controllers.Controller#removeListener(com.badlogic.gdx.controllers.ControllerListener)
	 */
	public void removeListener(ControllerListener listener) {
		controller.removeListener(listener);
	}
	
	public Controller getController() {
		return controller;
	}
	
	public boolean isController() {
		return controller instanceof EmptyController?false:true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * @return
	 * @see com.badlogic.gdx.scenes.scene2d.ui.Touchpad#getKnobPercentX()
	 */
	public float getKnobPercentX() {
		return touchPad.getKnobPercentX();
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.scenes.scene2d.ui.Touchpad#getKnobPercentY()
	 */
	public float getKnobPercentY() {
		return touchPad.getKnobPercentY();
	}
	
	/**
	 * @param listener
	 * @return
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#addListener(com.badlogic.gdx.scenes.scene2d.EventListener)
	 */
	public boolean addListener(EventListener listener) {
		return touchPad.addListener(listener);
	}
	
	public Touchpad getTouchPad() {
		return touchPad;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * @see com.badlogic.gdx.Input#getInputProcessor()
	 */
	@Override
	public InputProcessor getInputProcessor() {
		return this;
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
	public void setInputProcessor(InputProcessor processor) {
		System.err.println("RealInputProcessor.setInputProcessor() DISABLED");
	}
	
}
