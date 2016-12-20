package fr.mad.youare.behavior;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.Task;

import fr.mad.youare.behavior.TreeBehavior.Board;

public class TreeBehavior extends BehaviorTree<Board> implements Behavior {
	public static class Board {
		
	}
	
	private BehaviorTree<Board> tree;
	
	public TreeBehavior() {
		tree = new BehaviorTree<>();
	}
	
	public TreeBehavior(BehaviorTree<Board> t) {
		tree = t;
	}
	
	@Override
	public void update(float delta) {
		this.step();
	}
	
	///////////////////////////////////////////////////
	
	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return tree.hashCode();
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.ai.btree.BehaviorTree#getObject()
	 */
	public Board getObject() {
		return tree.getObject();
	}
	
	/**
	 * @param object
	 * @see com.badlogic.gdx.ai.btree.BehaviorTree#setObject(java.lang.Object)
	 */
	public void setObject(Board object) {
		tree.setObject(object);
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.ai.btree.BehaviorTree#getChildCount()
	 */
	public int getChildCount() {
		return tree.getChildCount();
	}
	
	/**
	 * @param i
	 * @return
	 * @see com.badlogic.gdx.ai.btree.BehaviorTree#getChild(int)
	 */
	public Task<Board> getChild(int i) {
		return tree.getChild(i);
	}
	
	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return tree.equals(obj);
	}
	
	/**
	 * @param runningTask
	 * @param reporter
	 * @see com.badlogic.gdx.ai.btree.BehaviorTree#childRunning(com.badlogic.gdx.ai.btree.Task,
	 *      com.badlogic.gdx.ai.btree.Task)
	 */
	public void childRunning(Task<Board> runningTask, Task<Board> reporter) {
		tree.childRunning(runningTask, reporter);
	}
	
	/**
	 * @param runningTask
	 * @see com.badlogic.gdx.ai.btree.BehaviorTree#childFail(com.badlogic.gdx.ai.btree.Task)
	 */
	public void childFail(Task<Board> runningTask) {
		tree.childFail(runningTask);
	}
	
	/**
	 * @param runningTask
	 * @see com.badlogic.gdx.ai.btree.BehaviorTree#childSuccess(com.badlogic.gdx.ai.btree.Task)
	 */
	public void childSuccess(Task<Board> runningTask) {
		tree.childSuccess(runningTask);
	}
	
	/**
	 * 
	 * @see com.badlogic.gdx.ai.btree.BehaviorTree#step()
	 */
	public void step() {
		tree.step();
	}
	
	/**
	 * 
	 * @see com.badlogic.gdx.ai.btree.BehaviorTree#run()
	 */
	public void run() {
		tree.run();
	}
	
	/**
	 * 
	 * @see com.badlogic.gdx.ai.btree.BehaviorTree#reset()
	 */
	public void reset() {
		tree.reset();
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.ai.btree.Task#getGuard()
	 */
	public Task<Board> getGuard() {
		return tree.getGuard();
	}
	
	/**
	 * @param guard
	 * @see com.badlogic.gdx.ai.btree.Task#setGuard(com.badlogic.gdx.ai.btree.Task)
	 */
	public void setGuard(Task<Board> guard) {
		tree.setGuard(guard);
	}
	
	/**
	 * @param listener
	 * @see com.badlogic.gdx.ai.btree.BehaviorTree#addListener(com.badlogic.gdx.ai.btree.BehaviorTree.Listener)
	 */
	public void addListener(com.badlogic.gdx.ai.btree.BehaviorTree.Listener<Board> listener) {
		tree.addListener(listener);
	}
	
	/**
	 * @param listener
	 * @see com.badlogic.gdx.ai.btree.BehaviorTree#removeListener(com.badlogic.gdx.ai.btree.BehaviorTree.Listener)
	 */
	public void removeListener(com.badlogic.gdx.ai.btree.BehaviorTree.Listener<Board> listener) {
		tree.removeListener(listener);
	}
	
	/**
	 * 
	 * @see com.badlogic.gdx.ai.btree.BehaviorTree#removeListeners()
	 */
	public void removeListeners() {
		tree.removeListeners();
	}
	
	/**
	 * @param control
	 * @return
	 * @see com.badlogic.gdx.ai.btree.Task#checkGuard(com.badlogic.gdx.ai.btree.Task)
	 */
	public boolean checkGuard(Task<Board> control) {
		return tree.checkGuard(control);
	}
	
	/**
	 * @param task
	 * @param previousStatus
	 * @see com.badlogic.gdx.ai.btree.BehaviorTree#notifyStatusUpdated(com.badlogic.gdx.ai.btree.Task,
	 *      com.badlogic.gdx.ai.btree.Task.Status)
	 */
	public void notifyStatusUpdated(Task<Board> task, com.badlogic.gdx.ai.btree.Task.Status previousStatus) {
		tree.notifyStatusUpdated(task, previousStatus);
	}
	
	/**
	 * @param task
	 * @param index
	 * @see com.badlogic.gdx.ai.btree.BehaviorTree#notifyChildAdded(com.badlogic.gdx.ai.btree.Task,
	 *      int)
	 */
	public void notifyChildAdded(Task<Board> task, int index) {
		tree.notifyChildAdded(task, index);
	}
	
	/**
	 * 
	 * @see com.badlogic.gdx.ai.btree.Task#start()
	 */
	public void start() {
		tree.start();
	}
	
	/**
	 * 
	 * @see com.badlogic.gdx.ai.btree.Task#end()
	 */
	public void end() {
		tree.end();
	}
	
	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return tree.toString();
	}
	
	/**
	 * @return
	 * @see com.badlogic.gdx.ai.btree.Task#cloneTask()
	 */
	public Task<Board> cloneTask() {
		return tree.cloneTask();
	}
}
