package com.fireforce.fStation.ctr;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fireforce.fStation.model.fStation;
import com.fireforce.fStation.serv.fStationServ;

@RestController
public class fStationCtr {

    @Autowired
    fStationServ fSService;
    
    @RequestMapping(method=RequestMethod.POST,value="/createStation")
    public void addStation(@RequestBody fStation fStation) {
        fSService.add(fStation);
    }
    
    @RequestMapping(value="/createSample")
    public void createSample() {
        fStation station1 = new fStation("Montgomery");
        fStation station2 = new fStation("Rina");
        
        fSService.add(station1);
        fSService.add(station2);
    }
    
    @RequestMapping(value="/getStation/{id}")
    public fStation getStation(@PathVariable Integer id) {
    	return fSService.getStation(id);
    }
    
    @RequestMapping(value="/test/{id}")
    public double test(@PathVariable Integer id) {
    	return fSService.getStation(id).getLat();
    }
    @RequestMapping(value="/editStationName/{id}/{name}")
    public void editStationName(@PathVariable String name, @PathVariable Integer id) {
        ;
    }
}

