package fr.mad.youare.ressource;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

import fr.mad.youare.YouAre;

public class Ressource {
	
	public final YouAre ya;
	public final AssetManager am;
	public final FileHandleResolver resolver;
	
	public Ressource(YouAre ya) {
		this.ya = ya;
		this.resolver = new SFileHandleResolver();
		this.am = new AssetManager(resolver, false);
	}
	
	public FileHandle resolve(String path) {
		return resolver.resolve(path);
		
	}
	
	public void addToDispose(Disposable d) {
		// TODO Auto-generated method stub
		
	}
	
	private ObjectMap<Class<?>, Array<?>> objects = new ObjectMap<>();
	
	@SuppressWarnings("unchecked")
	public <T> Array<T> getObjects(Class<T> c) {
		if (!objects.containsKey(c))
			objects.put(c, new Array<T>(false,16));
		return (Array<T>) objects.get(c);
	}
}
