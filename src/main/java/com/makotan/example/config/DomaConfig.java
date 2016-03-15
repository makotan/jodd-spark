package com.makotan.example.config;

import jodd.petite.meta.PetiteBean;
import jodd.props.Props;
import jodd.props.PropsUtil;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.H2Dialect;
import org.seasar.doma.jdbc.tx.LocalTransactionDataSource;
import org.seasar.doma.jdbc.tx.LocalTransactionManager;
import org.seasar.doma.jdbc.tx.TransactionManager;

import javax.sql.DataSource;

/**
 * User: makotan
 * Date: 2016/03/13
 * Time: 19:45
 */
@PetiteBean
public class DomaConfig implements Config {
    //private static final DomaConfig CONFIG = new DomaConfig();

    private final Dialect dialect;

    private final LocalTransactionDataSource dataSource;

    private final TransactionManager transactionManager;

    private DomaConfig() {
        Props p = PropsUtil.createFromClasspath("/db.props");

        //String driver = p.getValue("db.driver" , "test");
        String url = p.getValue("db.url" , "test");
        String username = p.getValue("db.username" , "test");
        String password = p.getValue("db.password" , "test");

        dialect = new H2Dialect();
        dataSource = new LocalTransactionDataSource(
                url, username, password);
        transactionManager = new LocalTransactionManager(
                dataSource.getLocalTransaction(getJdbcLogger()));
    }

    @Override
    public Dialect getDialect() {
        return dialect;
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public TransactionManager getTransactionManager() {
        return transactionManager;
    }
}
