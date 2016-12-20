package fr.mad.youare.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;

public class BodyHelper {
	public static boolean is(Contact c,Body body){
		return is(c.getFixtureA().getBody(),c.getFixtureB().getBody(),body);
	}
	
	public static boolean is(Body bodyA, Body bodyB, Body body) {
		return bodyA == body || bodyB == body;
	}
	
	public static Body get(Contact c,Body body){
		return get(c.getFixtureA().getBody(),c.getFixtureB().getBody(),body);
	}
	
	public static Body get(Body bodyA, Body bodyB, Body body) {
		if(bodyA==body)
			return bodyA;
		if(bodyB==body)
			return bodyB;
		return null;
	}
	
	public static Body get(Body bodyA,Body bodyB,Fixture f){
		Array<Fixture> fs = bodyA.getFixtureList();
		for(Fixture i:fs){
			if(i==f)
				return bodyA;
		}
		if(bodyB==null)
			return null;
		fs = bodyB.getFixtureList();
		for(Fixture i:fs){
			if(i==f)
				return bodyB;
		}
		return null;
	}
	
	public static boolean is(Body bodyA,Body bodyB,Class<?> c,boolean identity){
		if(identity)
			return bodyA.getUserData().getClass()==c||bodyB.getUserData().getClass()==c;
		return bodyA.getUserData().getClass().isAssignableFrom(c)||bodyB.getUserData().getClass().isAssignableFrom(c);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getUserData(Body bodyA,Body bodyB,Class<T> c,boolean identity){
		if(identity){
			if(bodyA.getUserData().getClass()==c)
				return (T) bodyA.getUserData();
			if(bodyB.getUserData().getClass()==c)
				return (T) bodyB.getUserData();
		}
		if(bodyA.getUserData().getClass().isAssignableFrom(c))
			return (T) bodyA.getUserData();
		if(bodyB.getUserData().getClass().isAssignableFrom(c))
			return (T) bodyB.getUserData();
		return null;
	}
	
	public static <T> T getUserData(Contact contact,Class<T> c,boolean identity){
		return getUserData(contact.getFixtureA().getBody(), contact.getFixtureB().getBody(), c, identity);
	}
	
	public static Body getBody(Body bodyA,Body bodyB,Class<?> c,boolean identity){
		if(identity){
			if(bodyA.getUserData().getClass()==c)
				return bodyA;
			if(bodyB.getUserData().getClass()==c)
				return bodyB;
		}
		if(bodyA.getUserData().getClass().isAssignableFrom(c))
			return bodyA;
		if(bodyB.getUserData().getClass().isAssignableFrom(c))
			return bodyB;
		return null;
	}
}
