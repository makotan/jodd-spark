package com.makotan.example;

import jodd.petite.PetiteContainer;
import jodd.petite.config.AutomagicPetiteConfigurator;
import jodd.props.Props;
import jodd.props.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

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
        initDb();
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
        pcfg.setIncludedEntries(this.getClass().getPackage().getName() + ".*");
        pcfg.configure(petite);
        petite.wire(this);
    }
    
    void initDb() {
        Props p = PropsUtil.createFromClasspath("/db.props");

        String driver = p.getValue("db.driver" , "test");
        String url = p.getValue("db.url" , "test");
        String username = p.getValue("db.username" , "test");
        String password = p.getValue("db.password" , "test");
        
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
