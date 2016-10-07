package fr.mad.youare.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Transform;

public class TransformC extends Transform implements Component {
	public Body body;
	public Transform transform;
	
	public TransformC(Body body) {
		this.body = body;
	}
	
	public void begin() {
		transform = body.getTransform();
	}
	
	public void end() {
		body.setTransform(getPosition(), getRotation());
	}
	
	/**
	 * @param v
	 * @return
	 * @see com.badlogic.gdx.physics.box2d.Transform#mul(com.badlogic.gdx.math.Vector2)
	 */
	public Vector2 mul(Vector2 v) {
		return transform.mul(v);
	}

	/**
	 * @return
	 * @see com.badlogic.gdx.physics.box2d.Transform#getPosition()
	 */
	public Vector2 getPosition() {
		return transform.getPosition();
	}

	/**
	 * @param angle
	 * @see com.badlogic.gdx.physics.box2d.Transform#setRotation(float)
	 */
	public void setRotation(float angle) {
		transform.setRotation(angle);
	}

	/**
	 * @return
	 * @see com.badlogic.gdx.physics.box2d.Transform#getRotation()
	 */
	public float getRotation() {
		return transform.getRotation();
	}

	/**
	 * @return
	 * @see com.badlogic.gdx.physics.box2d.Transform#getOrientation()
	 */
	public Vector2 getOrientation() {
		return transform.getOrientation();
	}

	/**
	 * @param orientation
	 * @see com.badlogic.gdx.physics.box2d.Transform#setOrientation(com.badlogic.gdx.math.Vector2)
	 */
	public void setOrientation(Vector2 orientation) {
		transform.setOrientation(orientation);
	}

	/**
	 * @param pos
	 * @see com.badlogic.gdx.physics.box2d.Transform#setPosition(com.badlogic.gdx.math.Vector2)
	 */
	public void setPosition(Vector2 pos) {
		transform.setPosition(pos);
	}

	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return transform.toString();
	}
}
