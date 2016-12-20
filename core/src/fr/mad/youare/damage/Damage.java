package fr.mad.youare.damage;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
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
import com.kotcrab.vis.ui.widget.ListView.UpdatePolicy;

import fr.mad.youare.box2d.BodyHelper;
import fr.mad.youare.box2d.ContactListeners;
import fr.mad.youare.components.Components;
import fr.mad.youare.components.EffectsC;

public class Damage {
	
	public static class Modifier {
		public int modifyMax(int max){
			return max;
		}

		public float modifyRadius(float radius) {
			return radius;
		}

		public Effect modidyEffect(Effect effect) {
			return effect;
		}

		public float modifyTimeout(float timeout) {
			return timeout;
		}

		public String modifyAnimationKey(String animationKey) {
			return animationKey;
		}
	}
	
	public static class Effect {
		public String name;
		public String animationKey;
		public String description;
		public void run(Entity e){
			
		}
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
		public Array<Entity> entitys = new Array<>();
		
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
				
				ContactP c = contacts.pop();
				Components.effectsC.get(c.entity).effects.add(effect);
				left--;
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
			if(entitys.contains(entity, true))
				return;
			entitys.add(entity);
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
	
	public Damage clone(Modifier... modifiers) {
		Damage damage = new Damage(effect, radius, max, timeout, animationKey);
		float nradius;
		for(Modifier m:modifiers){
			damage.max = m.modifyMax(damage.max);
			nradius = m.modifyRadius(damage.radius);//TODO radiusR
			damage.effect = m.modidyEffect(damage.effect);
			damage.timeout = m.modifyTimeout(damage.timeout);
			damage.animationKey = m.modifyAnimationKey(damage.animationKey);
			if(nradius!=radius){
				damage.radiusS = (float) Math.pow(nradius, 2);
				damage.radius = nradius;
			}
		}
		return damage;
	}
	
	public Damage clone() {
		return new Damage(effect, radius, max, timeout, animationKey);
	}
}
