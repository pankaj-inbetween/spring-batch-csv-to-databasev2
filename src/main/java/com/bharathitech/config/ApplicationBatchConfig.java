package com.bharathitech.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.BatchConfigurationException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * <p>
 * Class description explaining the usage.
 * </p>
 * @author Bharathidasan
 * @see ApplicationBatchConfig
 */
@Configuration
@EnableBatchProcessing
public class ApplicationBatchConfig
{

    /** The Constant LOGGER. */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(ApplicationBatchConfig.class);

    /** The data source. */
    @Autowired
    public DataSource dataSource;

    /** The transaction manager. */
    @Autowired
    PlatformTransactionManager transactionManager;

    /**
     * Creates the job repository.
     *
     * @return Job repository holds the
     */
    protected JobRepository createJobRepository()
    {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.setIsolationLevelForCreate("ISOLATION_READ_UNCOMMITTED");
        factory.setTablePrefix("BATCH_");
        // factory.setMaxVarCharLength(1000);
        try
        {
            factory.afterPropertiesSet();
            return factory.getObject();
        } catch (Exception exception)
        {
            LOGGER.error("Exception message : {} ", exception.getMessage());
            throw new BatchConfigurationException(exception);
        }
    }




}
