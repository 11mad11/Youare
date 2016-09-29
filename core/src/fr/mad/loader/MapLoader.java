package fr.mad.loader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.utils.Array;

import fr.mad.loader.MapLoader.Parameter;
import fr.mad.youare.map.Map;

public class MapLoader extends AsynchronousAssetLoader<Map, Parameter> {
	
	public class Parameter extends AssetLoaderParameters<Map> {
		public boolean generateMipMaps = false;
		public TextureFilter textureMinFilter = TextureFilter.Nearest;
		public TextureFilter textureMagFilter = TextureFilter.Nearest;
		public boolean convertObjectToTileSpace = false;
		public boolean flipY = true;
	}

	private TmxMapLoader tmxLoader;
	
	public MapLoader(FileHandleResolver resolver) {
		super(resolver);
		tmxLoader = new TmxMapLoader(resolver);
	}
	
	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, Parameter parameter) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Map loadSync(AssetManager manager, String fileName, FileHandle file, Parameter parameter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, Parameter parameter) {
		
		return null;
	}
}
