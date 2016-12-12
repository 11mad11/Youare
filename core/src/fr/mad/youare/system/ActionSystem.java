package fr.mad.youare.system;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;

import fr.mad.youare.components.Components;

public class ActionSystem extends EntitySystem implements Telegraph{
	
	public ActionSystem() {
		MessageManager.getInstance().addListener(this, 0);
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		
	}
	
	@Override
	public void update(float deltaTime) {
		
	}

	@Override
	public boolean handleMessage(Telegram msg) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
