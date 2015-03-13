package gnete.card.timer.impl;

import gnete.card.timer.TimerHandler;
import gnete.card.timer.TimerManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TimerManagerImpl implements TimerManager {
	private List handlerList = new ArrayList();

	public List getHandlerList() {
		return handlerList;
	}

	public void setHandlerList(List handlerList) {
		this.handlerList = handlerList;
	}
	
	public synchronized void register(TimerHandler handler) {
		handlerList.add(handler);
	}
	
	public void doTasks() {
		Iterator iter = handlerList.iterator();
		while(iter.hasNext()) {
			TimerHandler handler = (TimerHandler)iter.next();
			handler.handle();
		}
	}

}
