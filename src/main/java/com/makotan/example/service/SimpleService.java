package com.makotan.example.service;

import com.makotan.example.config.DomaConfig;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteDestroyMethod;
import jodd.petite.meta.PetiteInitMethod;
import jodd.petite.meta.PetiteInject;
import org.seasar.doma.jdbc.tx.LocalTransactionDataSource;
import org.seasar.doma.jdbc.tx.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * User: makotan
 * Date: 2016/03/12
 * Time: 18:44
 */
@PetiteBean
public class SimpleService {
    Logger logger = LoggerFactory.getLogger(SimpleService.class);
    
    @PetiteInject
    DomaConfig domaConfig;

    @PetiteInject("dataSource")
    LocalTransactionDataSource ds;

    @PetiteInject("transactionManager")
    TransactionManager tm;
    
    @PetiteInitMethod
    public void init() {
        logger.info("init service");
    }
    
    @PetiteDestroyMethod
    public void destory() {
        logger.info("destroy service");
    }
    
    public void callService() {
        logger.info("SimpleService call !");

        tm.required(() -> {
            try (Connection connection = ds.getConnection()){
                connection.createStatement().execute("create table t1(f1 VARCHAR );");
            } catch (SQLException e) {
                logger.error("database ",e);
                tm.setRollbackOnly();
            }
        });
    }
}
