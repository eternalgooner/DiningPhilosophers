package com.david;

import java.util.ArrayList;

public class Philosopher implements Runnable{
	private boolean isEating;
	private boolean hasLeftFork;
	private boolean hasRightFork;
	private int amountEaten;
	private boolean isFinished;
	private boolean isSnoozing;
	private String name;
	private ArrayList<Fork> forks = new ArrayList<>();
	
	public Philosopher(int name, Fork leftFork, Fork rightFork) {
		super();
		this.name = "Philosopher: " + name;
		forks.add(leftFork);
		forks.add(rightFork);
	}

	public synchronized boolean isSnoozing() {
		return isSnoozing;
	}

	public synchronized void setSnoozing(boolean isSnoozing) {
		this.isSnoozing = isSnoozing;
	}

	public synchronized boolean isEating() {
		return isEating;
	}

	public synchronized void setEating(boolean isEating) {
		this.isEating = isEating;
	}

	public synchronized boolean hasLeftFork() {
		return hasLeftFork;
	}

	public synchronized boolean isFinished() {
		return isFinished;
	}

	public synchronized void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public synchronized void hasLeftFork(boolean hasLeftFork) {
		this.hasLeftFork = hasLeftFork;
	}
	
	public synchronized boolean hasRightFork() {
		return hasRightFork;
	}

	public synchronized void hasRightFork(boolean hasRightFork) {
		this.hasRightFork = hasRightFork;
	}

	public int getAmountEaten() {
		return amountEaten;
	}

	public void setAmountEaten(int amountEaten) {
		this.amountEaten = amountEaten;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		while (amountEaten < 51) {
			checkIfLeftForkAvailable();
			if(hasLeftFork)checkIfRightForkAvailable();
			if (hasLeftFork && hasRightFork) {
				isEating = true;
				startEating();
			} else{
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		isFinished = true;
	}

	private void startEating() {
		for(int i = 0; i < 10; ++i){
			++amountEaten;
			System.out.println(this.name + " is now eating, amount is now: " + amountEaten);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(Fork fork : forks){
			fork.setAvailable(true);
		}
		isEating = false;
		try {
			isSnoozing = true;
			Thread.sleep(5000);
			isSnoozing = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void checkIfRightForkAvailable() {
		System.out.println(this.name + " checking if right fork available");
		if(forks.get(1).isAvailable()){
			hasRightFork = true;
			forks.get(1).setAvailable(false);
			System.out.println(this.name + "right fork is available & now setting it to: " + forks.get(1).isAvailable());
			return;
		}
		System.out.println(this.name + "right fork was not available & status is: " + forks.get(1).isAvailable());
		forks.get(0).setAvailable(true);
		hasRightFork = false;
		hasLeftFork = false;
	}

	private void checkIfLeftForkAvailable() {
		System.out.println(this.name + " checking if left fork available");
		if(forks.get(0).isAvailable()){
			hasLeftFork = true;
			forks.get(0).setAvailable(false);
			System.out.println(this.name + ": left fork is available & now setting it to: " + forks.get(0).isAvailable());
			return;
		}
		System.out.println(this.name + ": left fork was not available & status is: " + forks.get(0).isAvailable());
		hasLeftFork = false;
	}
	
	public String toString(){
		return name + " has forks " + forks;
	}
}
