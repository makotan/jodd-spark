package com.makotan.example;

import jodd.petite.meta.PetiteBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: makotan
 * Date: 2016/03/12
 * Time: 18:44
 */
@PetiteBean
public class SimpleService {
    Logger logger = LoggerFactory.getLogger(SimpleService.class);
    
    public void callService() {
        logger.info("SimpleService call !");
    }
}
