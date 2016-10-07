package fr.mad.youare.box2d;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class ContactListeners implements ContactListener {
	
	private static ObjectMap<World, ContactListeners> list = new ObjectMap<>();
	
	public static void add(World world, ContactListener listener) {
		if (list.get(world) == null)
			list.put(world, new ContactListeners(world));
		list.get(world).add(listener);
	}
	
	public static void remove(ContactListener cast, World world) {
		if (list.get(world) == null)
			list.put(world, new ContactListeners(world));
		list.get(world).remove(cast);
	}
	
	private World world;
	private Array<ContactListener> mlist = new Array<>();
	
	public ContactListeners(World world) {
		this.world = world;
		world.setContactListener(this);
	}
	
	private void add(ContactListener listener) {
		if(mlist.contains(listener, true))
			return;
		mlist.add(listener);
	}
	
	public void remove(ContactListener cast) {
		mlist.removeValue(cast, true);
	}
	
	@Override
	public void beginContact(Contact contact) {
		for (ContactListener l : mlist)
			l.beginContact(contact);
	}
	
	@Override
	public void endContact(Contact contact) {
		for (ContactListener l : mlist)
			l.endContact(contact);
		;
	}
	
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		for (ContactListener l : mlist)
			l.preSolve(contact, oldManifold);
		;
	}
	
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		for (ContactListener l : mlist)
			l.postSolve(contact, impulse);
		;
	}
	
}
