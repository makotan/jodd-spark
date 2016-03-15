package com.makotan.example;

import jodd.petite.PetiteContainer;
import jodd.petite.config.AutomagicPetiteConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: makotan
 * Date: 2016/03/12
 * Time: 18:40
 */
public class AppCore {
    private static Logger logger = LoggerFactory.getLogger(AppCore.class);
    static AppCore instance = new AppCore();
    
    public static AppCore getInstance() {
        return instance;
    }

    private Thread shutdownHook;
    
    public void start() {
        logger.info("start AppCore");
        //AppUtil.resolveDirs();
        //initLogger();
        initPetite();
        //initDb();
        // init everything else
        if (shutdownHook == null) {
            shutdownHook = new Thread(this::stop);
            Runtime.getRuntime().addShutdownHook(shutdownHook);
        }
    }

    protected PetiteContainer petite;

    void initPetite() {
        petite = new PetiteContainer();
        AutomagicPetiteConfigurator pcfg = new AutomagicPetiteConfigurator();
        String basePackage = this.getClass().getPackage().getName();
        pcfg.setIncludedEntries(basePackage + ".*");
        pcfg.configure(petite);
        petite.wire(this);
    }

    public PetiteContainer getPetite() {
        return petite;
    }

    public void stop() {
        // close everything
        terminate();
    }

    private void terminate() {
        logger.info("terminate AppCore");

        shutdownHook = null;
        petite.shutdown();
    }
}
