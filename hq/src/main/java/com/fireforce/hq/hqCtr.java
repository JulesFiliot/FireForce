package com.fireforce.hq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fireforce.fStation.model.fStation;


@RestController
public class hqCtr {
    @Autowired
    hqServ hqServ;
    
    
    @RequestMapping(method=RequestMethod.POST,value="/createHQ")
    public void addHQ(@RequestBody hq hq) {
        hqServ.add(hq);
    }
    
    @RequestMapping("/affectOptiStraight/{fId}")
    public void (@PathVariable Integer fId) {
    	return;
    	
    }
    
}
