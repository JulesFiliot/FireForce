package com.fireforce.inter.ctr;

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

import com.fireforce.inter.model.inter;
import com.fireforce.inter.serv.interServ;

@RestController
public class interCtr {

    @Autowired
    interServ iService;
    
  /*  @RequestMapping(method=RequestMethod.POST,value="/card")
    public void addCard(@RequestBody inter inter) {
        iService.addCard(inter);
    }*/
}

