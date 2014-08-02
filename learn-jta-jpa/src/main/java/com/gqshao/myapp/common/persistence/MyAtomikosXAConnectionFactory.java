package com.gqshao.myapp.common.persistence;

import java.sql.SQLException;

import javax.sql.XAConnection;
import javax.sql.XADataSource;

import com.atomikos.datasource.pool.ConnectionFactory;
import com.atomikos.datasource.pool.ConnectionPoolProperties;
import com.atomikos.datasource.pool.CreateConnectionException;
import com.atomikos.datasource.pool.XPooledConnection;
import com.atomikos.datasource.xa.jdbc.JdbcTransactionalResource;
import com.atomikos.jdbc.AtomikosXAPooledConnection;
import com.atomikos.logging.Logger;
import com.atomikos.logging.LoggerFactory;

public class MyAtomikosXAConnectionFactory  implements ConnectionFactory {
    private static final Logger LOGGER = LoggerFactory.createLogger(MyAtomikosXAConnectionFactory.class);

    //TODO user and password to get new connections
    private JdbcTransactionalResource jdbcTransactionalResource;
    private XADataSource xaDataSource;
    private ConnectionPoolProperties props;
    
    public MyAtomikosXAConnectionFactory ( XADataSource xaDataSource, JdbcTransactionalResource jdbcTransactionalResource, ConnectionPoolProperties props ) 
    {
        this.xaDataSource = xaDataSource;
        this.jdbcTransactionalResource = jdbcTransactionalResource;
        this.props = props;
    }

    public XPooledConnection createPooledConnection() throws CreateConnectionException
    {
        try {
            XAConnection xaConnection = xaDataSource.getXAConnection();
            return new AtomikosXAPooledConnection ( xaConnection, jdbcTransactionalResource, props );
        } catch ( SQLException e ) {
            String msg = "XAConnectionFactory: failed to create pooled connection - DBMS down or unreachable?";
            LOGGER.logWarning ( msg , e );
            throw new CreateConnectionException ( msg , e );
        }
    }
}
