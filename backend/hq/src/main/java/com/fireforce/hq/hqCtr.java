package com.fireforce.hq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class hqCtr {
    @Autowired
    hqServ hqServ;
    
    
    @RequestMapping(method=RequestMethod.POST,value="/createHQ")
    public void addHQ(@RequestBody hq hq) {
        hqServ.add(hq);
    }
    
    @RequestMapping("/affectVehicSimple")
    public void affectVehicSimple() {
    	hqServ.affectVehicSimple();
    }
    
    
    // affectOptiStraight permet d'assigner à chaque feu le véhicule le plus proche et avec le meilleur type
    @RequestMapping("/affectOptiStraight")
    public void affectOptiStraight() {
    	hqServ.affectOptiStraight();
    	return;
    	
    }
    
    
    
}
