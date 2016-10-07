package fr.mad.youare.damage;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import fr.mad.youare.box2d.BodyHelper;
import fr.mad.youare.box2d.ContactListeners;

public class Damage {
	
	public class Modifier {
		//TODO
	}
	
	public static class Effect {
		//TODO
	}
	
	public class Cast implements ContactListener {
		public class ContactP {
			
			public Body bodyA;
			public Body bodyB;
			public Entity entity;
			
			public ContactP(Contact contact, Entity entity) {
				bodyA = contact.getFixtureA().getBody();
				bodyB = contact.getFixtureB().getBody();
				this.entity = entity;
			}
			
		}
		
		private World world;
		private boolean active;
		private Body body;
		private int left;
		private Task task;
		public Array<ContactP> contacts = new Array<>();
		
		private Cast(Vector2 cible, World world) {
			this.world = world;
			ContactListeners.add(world, this);
			
			BodyDef def = new BodyDef();
			def.type = BodyType.DynamicBody;
			def.position.set(cible);
			body = world.createBody(def);
			FixtureDef fdef = new FixtureDef();
			fdef.shape = new CircleShape();
			fdef.shape.setRadius(radius);
			fdef.isSensor = true;
			body.createFixture(fdef);
			
			task = Timer.instance().scheduleTask(task(), timeout);
			active = true;
		}
		
		private Task task() {
			return new Task() {
				
				@Override
				public void run() {
					world.destroyBody(body);
					
				}
			};
		}
		
		public boolean isActive() {
			return active;
		}
		
		public void setPosition(Vector2 v) {
			body.setTransform(v, 0);
		}
		
		public void setPosition(float x, float y) {
			body.setTransform(x, y, 0);
		}
		
		public int getLeft() {
			return left;
		}
		
		public float getPercentTimeLeft() {
			return (task.getExecuteTimeMillis()-System.currentTimeMillis())/timeout;
		}
		
		public Damage getDamage() {
			return Damage.this;
		}
		
		public void up() {
			if (contacts == null)
				return;
			while (contacts.size > 0) {
				left--;
				ContactP c = contacts.pop();
				c.bodyA.getClass();
				//TODO
				if (left <= 0) {
					world.destroyBody(body);
					ContactListeners.remove(this, world);
					contacts = null;
				}
			}
		}
		
		@Override
		public void beginContact(Contact contact) {
			if (!BodyHelper.is(contact, body))
				return;
			Entity entity = BodyHelper.getUserData(contact, Entity.class, true);
			if (entity == null)
				return;
			contacts.add(new ContactP(contact, entity));
		}
		
		@Override
		public void endContact(Contact contact) {
		}
		
		@Override
		public void preSolve(Contact contact, Manifold oldManifold) {
		}
		
		@Override
		public void postSolve(Contact contact, ContactImpulse impulse) {
		}
		
	}
	
	private Effect effect;
	private Array<Modifier> modifiers = new Array<>();
	private float radiusS;
	private float radius;
	private float timeout;
	private int max;
	private String animationKey;
	
	public Damage(Effect e, float radius,int max,float timeout,String animationKey) {
		this.effect = e;
		this.radiusS = (float) Math.pow(radius, 2);
		this.radius = radius;
		this.animationKey = animationKey;
		this.max = max;
		this.timeout = timeout;
	}
	
	public Cast cast(Vector2 cible, World world) {
		Cast cast = new Cast(cible, world);
		return cast;
	}
	
	public boolean isReachable(Vector2 cible, Vector2 from) {
		return from.dst2(cible) > radiusS;
	}
	
	/**
	 * @return the effect
	 */
	public Effect getEffect() {
		return effect;
	}
	
	/**
	 * @return the modifiers
	 */
	public Array<Modifier> getModifiers() {
		return modifiers;
	}
	
	/**
	 * @return the radiusS
	 */
	public float getRadiusS() {
		return radiusS;
	}
	
	/**
	 * @return the radius
	 */
	public float getRadius() {
		return radius;
	}
	
	/**
	 * @return the timeout
	 */
	public float getTimeout() {
		return timeout;
	}
	
	/**
	 * @return the max
	 */
	public float getMax() {
		return max;
	}
	
	/**
	 * @return the animationKey
	 */
	public String getAnimationKey() {
		return animationKey;
	}
	
	public Damage clone() {
		Damage damage = new Damage(effect, radius, max, timeout, animationKey);
		for(Modifier m:modifiers){
			damage.modifiers.add(m);
		}
		return damage;
	}
	
	public Damage clearClone() {
		return new Damage(effect, radius, max, timeout, animationKey);
	}
}
