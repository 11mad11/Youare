package fr.mad.loader;

import java.io.IOException;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import fr.mad.youare.Item;

public class ItemLoader extends AsynchronousAssetLoader<Item, ItemLoader.Parameter> {
	public static class Parameter extends AssetLoaderParameters<Item> {
		
	}
	
	private Element root;
	private ObjectMap<String, String> sprites = new ObjectMap<String, String>();
	
	public ItemLoader(FileHandleResolver resolver) {
		super(resolver);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, Parameter parameter) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Item loadSync(AssetManager manager, String fileName, FileHandle file, Parameter parameter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, Parameter parameter) {
		XmlReader xml = new XmlReader();
		Array<AssetDescriptor> dep = new Array<>();
		try {
			root = xml.parse(file);
		} catch (IOException e) {
			throw new Error(e);
		}
		if (!root.getName().equals("Item"))
			throw new Error("must be an item");
		Array<Element> texs = root.getChildrenByName("Sprite");
		for (Element tex : texs) {
			if(tex.getAttribute("path") != null){
				dep.add(new AssetDescriptor<>(tex.getAttribute("path"), Sprite.class));
			}else{
				
			}
		}
		return null;
	}
}
