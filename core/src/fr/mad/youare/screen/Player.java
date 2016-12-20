package fr.mad.youare.screen;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import fr.mad.actor.BodyCActor;
import fr.mad.actor.ImageActor;
import fr.mad.youare.behavior.Behavior;
import fr.mad.youare.components.BodyC;

public class Player extends Entity {
	private Behavior behavior;
	private BodyC bodyC;
	private BodyCActor actor;

	public Player(Behavior b, BodyC bodyC) {
		behavior = b;
		this.add(bodyC);
	}
	
	@Override
	public Entity add(Component component) {
		if(component instanceof BodyC){
			bodyC = (BodyC) component;
			actor = new BodyCActor(bodyC);
		}
		return super.add(component);
	}

	/**
	 * @return the behavior
	 */
	public Behavior getBehavior() {
		return behavior;
	}

	/**
	 * @param behavior the behavior to set
	 */
	public void setBehavior(Behavior behavior) {
		this.behavior = behavior;
	}

	/**
	 * @return the bodyC
	 */
	public BodyC getBodyC() {
		return bodyC;
	}

	/**
	 * @param bodyC the bodyC to set
	 */
	public void setBodyC(BodyC bodyC) {
		this.bodyC = bodyC;
	}

	/**
	 * @return the actor
	 */
	public BodyCActor getActor() {
		return actor;
	}

	/**
	 * @param actor the actor to set
	 */
	public void setActor(BodyCActor actor) {
		this.actor = actor;
	}
	
	
}
