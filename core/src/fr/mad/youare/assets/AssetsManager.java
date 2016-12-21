package fr.mad.youare.assets;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncTask;

public class AssetsManager<R extends Resolver> {
	private static class Task implements Poolable, AsyncTask<Object> {

		public FileHandle fileHandle;
		public Class<?> clazz;
		public Loader<?, ?> loader;
		public boolean dependenciesLoaded;
		public boolean complete;
		public boolean running;

		public <T> Task set(FileHandle fh, Class<T> clazz) {
			this.fileHandle = fh;
			this.clazz = clazz;
			return this;
		}

		@Override
		public void reset() {
			this.fileHandle = null;
			this.clazz = null;
			this.loader = null;
			this.dependenciesLoaded = false;
			this.complete = false;
			this.running = false;
		}

		@Override
		public Object call() throws Exception {
			if (dependenciesLoaded) {
				loader.loadAsync();
				complete = true;
			} else {
				loader.loadAsyncDependencies();
				dependenciesLoaded = true;
			}
			running = false;
			return null;
		}

	}

	private R resolver;
	private Array<Throwable> errors = new Array<>();
	private ObjectMap<Class<?>, Loader<?, ?>> loaders = new ObjectMap<>();
	private ObjectMap<String, Asset> assets = new ObjectMap<>();
	private Queue<Task> toLoad = new Queue<>(10, Task.class);
	private Queue<Task> tasks = new Queue<>(10, Task.class);
	private AsyncExecutor executor = new AsyncExecutor(1);
	private Pool<Task> taskPool = new Pool<Task>(10) {

		@Override
		protected Task newObject() {
			return new Task();
		}
	};

	public AssetsManager(R fhr) {
		this.resolver = fhr;
	}

	public <T extends Asset> void registerLoader(Loader<T, ?> loader, Class<T> clazz) {
		this.loaders.put(clazz, loader);
	}

	public <T> void load(FileHandle fh, Class<T> clazz) {
		toLoad.addFirst(taskPool.obtain().set(fh, clazz));
	}

	public <T> void load(String path, Class<T> clazz) {
		toLoad.addFirst(taskPool.obtain().set(resolver.resolve(path), clazz));
	}

	public boolean update() {
		if (tasks.size == 0) {
			if (toLoad.size == 0)
				return true;
			tasks.addFirst(toLoad.removeLast());
		}
		runTask();
		return false;
	}

	private void runTask() {
		Task task = tasks.first();
		if (task.loader == null)
			task.loader = loaders.get(task.clazz);
		if (task.loader == null)
			errors.add(new Error("no loader for " + task.clazz.getName()));
		if (task.running)
			return;
		if (task.complete) {
			tasks.removeFirst();
			loadAsset(task);
		} else {
			task.running = true;
			try {
				submitAsync(task);
			} catch (Throwable e) {
				errors.add(new Error("couldn submit async task", e));
				tasks.clear();
			}
		}
	}

	private void loadAsset(Task task) {
		Asset asset = task.loader.loadSync();
		FileHandle fh = task.fileHandle;
		if(fh instanceof FileHandleKey){
			assets.put(((FileHandleKey) fh).key(), asset);
		}else{
			assets.put(fh.path(), asset);
		}
	}

	private void submitAsync(Task task) {
		executor.submit(task);
	}
}
