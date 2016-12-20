package fr.mad.youare;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Scanner;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.utils.ObjectMap;

public class Widget {
	
	private static ReferenceQueue<? super Widget> q;
	private byte buff[];
	private int id;
	
	public Widget(int id) {
		//Each Widget object takes approximately 1MB
		this.buff = new byte[1024 * 1024];
		this.id = id;
	}
	
	@Override
	public String toString() {
		return id+"";
	}
	
	@Override
	protected void finalize() throws Throwable {
		System.out.println("f: "+id);
	}
	
	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		q = new ReferenceQueue<>();
		
		new Thread(new Runnable() {
			public void run() {
				while(true){
					try {
						Reference<?> wr = q.remove();
						System.out.println(wr);
						System.out.println(wr.get());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		// Ask the user how many Widget objects they want to create
		// Remember each Widget takes a little over 1MB
		Scanner scanner = new Scanner(System.in);
		System.out.println("How many objects do you want to create ?");
		int count = scanner.nextInt();
		
		ObjectMap<Integer, Reference<Widget>> weakWidgets = new ObjectMap<Integer, Reference<Widget>>();
		
		System.out.println("Creating " + count + " widgets as weak references.");
		
		for (int i = 0; i < count; i++) {
			weakWidgets.put(i, new SoftReference<Widget>(new Widget(i),q));
			//Thread.sleep(1);
		}
		Thread.sleep(5000);
		// Here's how we access items from a WeakReference
		for (int i = 0; i < count; i++) {
			Reference<Widget> weakRef = weakWidgets.get(i);
			Widget ww = weakRef.get();
			// Find out if the WeakWidget is still there or has it been GC'd
			if (ww == null) {
				//System.out.println("Oops WeakWidget " + i + " was garbage collected.");
			} else {
				//System.out.println("Awesome WeakWidget " + i + " is still around. Let's use it");
			}
		}
		
	}
	
}