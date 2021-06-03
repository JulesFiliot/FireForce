package com.fireforce.fire.serv;

import com.fireforce.fire.model.fire;
import com.fireforce.fire.repo.fireRepo;

public class DisplayRunnable implements Runnable {

	
	private fireRepo fRepo;
	boolean isEnd = false;

	public DisplayRunnable(fireRepo fRepo) {
		this.fRepo = fRepo;
	}

	@Override
	public void run() {
		while (!this.isEnd) {
			try {
				Thread.sleep(10000);
				for (fire h : this.fRepo.findAll()) {
					System.out.println(h.toString());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Runnable DisplayRunnable ends.... ");
	}

	public void stop() {
		this.isEnd = true;
	}

}
