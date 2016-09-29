package fr.mad.loader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import fr.mad.youare.Item;

public class LoaderTemplate extends AsynchronousAssetLoader<Item, LoaderTemplate.Parameter> {
	public class Parameter extends AssetLoaderParameters<Item> {
		
	}
	
	public LoaderTemplate(FileHandleResolver resolver) {
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
		// TODO Auto-generated method stub
		return null;
	}
}
