package com.makotan.example;

import jodd.petite.PetiteContainer;
import jodd.petite.config.AutomagicPetiteConfigurator;

/**
 * User: makotan
 * Date: 2016/03/12
 * Time: 18:40
 */
public class AppCore {

    public void start() {
        //AppUtil.resolveDirs();
        //initLogger();
        initPetite();
        //initDb();
        // init everything else
    }

    public void stop() {
        // close everything
    }

    protected PetiteContainer petite;

    void initPetite() {
        petite = new PetiteContainer();
        AutomagicPetiteConfigurator pcfg = new AutomagicPetiteConfigurator();
        pcfg.setIncludedEntries(this.getClass().getPackage().getName() + ".*");
        pcfg.configure(petite);
    }

    public PetiteContainer getPetite() {
        return petite;
    }    
}
