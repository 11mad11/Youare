package fr.mad.loader;

import java.io.IOException;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import fr.mad.loader.SpriteLoader.Parameters;
import fr.mad.loader.SpriteLoader.Type;
import fr.mad.youare.item.SpriteComponent;

public class SpriteLoader extends AsynchronousAssetLoader<SpriteComponent, Parameters> {
	
	public static enum Type {
		Texture("texture"),Animation("animation");
		
		public String type;
		
		Type(String type){
			this.type = type;
		}
		
		
	}

	public static class Parameters extends AssetLoaderParameters<SpriteComponent> {
		
	}

	private Element root;
	private Type type;
	private String path;
	
	public SpriteLoader(FileHandleResolver resolver) {
		super(resolver);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, Parameters parameter) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public SpriteComponent loadSync(AssetManager manager, String fileName, FileHandle file, Parameters parameter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, Parameters parameter) {
		Array<AssetDescriptor> dep = new Array<>();
		XmlReader xml = new XmlReader();
		try {
			root = xml.parse(file);
			if(!root.getName().equals("sprite"))
				throw new Error("bad format");
			type = Type.valueOf(root.getAttribute("type"));
			switch (type) {
				case Texture:
					path = root.getAttribute("path");
					dep.add(new AssetDescriptor<>(path, Texture.class));
					break;
				case Animation:
					loadAnimation(dep);
					break;
				default:
					break;
			}
		} catch (Exception e) {
			throw new Error(e);
		}
		return dep;
	}

	private void loadAnimation(Array<AssetDescriptor> dep) {
		// TODO Auto-generated method stub
		throw new Error("to be implented");
	}
	
}
