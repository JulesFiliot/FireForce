package com.fireforce.fire.serv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.fireforce.fire.model.fire;
import com.fireforce.fire.repo.fireRepo;
import com.project.model.dto.FireType;


@Service
public class fireServ {
	
	@Autowired
	fireRepo fRepo;
	
	DisplayRunnable dRunnable;
	private Thread displayThread;
	
	public fireServ(fireRepo fRepo) {
		//Replace the @Autowire annotation....
		this.fRepo=fRepo;
		
		//Create a Runnable is charge of executing cyclic actions 
		this.dRunnable=new DisplayRunnable(this.fRepo);
		
		// A Runnable is held by a Thread which manage lifecycle of the Runnable
		displayThread=new Thread(dRunnable);
		
		// The Thread is started and the method run() of the associated DisplayRunnable is launch
		displayThread.start();
		
	}
	
	public void stopDisplay() {
		//Call the user defined stop method of the runnable
		this.dRunnable.stop();
		try {
			//force the thread to stop
			this.displayThread.join(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	@Bean(initMethod="init")
	public void init() {
		fire f1=new fire(FireType.B_Gasoline, 2, 30, 10,5);
		fire f2=new fire(FireType.C_Flammable_Gases, 3 , 40 , 11,6);
		fRepo.save(f1);
		fRepo.save(f2);
	}


}
