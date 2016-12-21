package fr.mad.youare.assets;

import com.badlogic.gdx.utils.Array;

public interface Loader<T extends Asset,P extends Parameter<T>> {
	T loadSync();
	void loadAsync();
	Array<? extends Asset> loadAsyncDependencies();
}
