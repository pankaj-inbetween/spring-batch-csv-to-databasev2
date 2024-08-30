package com.bharathitech.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.bharathitech.dto.AddressDTO;
import com.bharathitech.listener.JobListener;
import com.bharathitech.model.AddressModel;
import com.bharathitech.processor.AddressProcessor;

/**
 * The Class UserAddressJob.
 * <p>
 * Class description explaining the usage.
 * </p>
 *
 * @author Bharathidasan
 */
@Configuration
@EnableBatchProcessing
public class UserAddressJob {

    /** The job builder factory. */
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    /** The step builder factory. */
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    /** The data source. */
    @Autowired
    public DataSource dataSource;

    /** The file path. */
    @Value("${csv.file.location}")
    public String filePath;

    /**
     * Reader.
     *
     * @return Flat file item reader holds the
     */
    @Bean("addressReader")
    public FlatFileItemReader<AddressModel> reader() {
        FlatFileItemReader<AddressModel> reader = new FlatFileItemReader<AddressModel>();
        reader.setResource(new FileSystemResource(filePath+"/user_address.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<AddressModel>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] {"name", "address1","address1","zip_code","city","country" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper() {{
                setTargetType(AddressModel.class);
            }});
        }});
        return reader;
    }


    /**
     * Processor.
     *
     * @return address processor holds the
     */
    @Bean("addressProcessor")
    public AddressProcessor processor() {
        return new AddressProcessor();
    }

    /**
     * Writer.
     *
     * @return Jdbc batch item writer holds the
     */
    @Bean("addressWriter")
    public JdbcBatchItemWriter<AddressDTO> writer() {
        JdbcBatchItemWriter<AddressDTO> writer = new JdbcBatchItemWriter<AddressDTO>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO ADDRESS (NAME,ADDRESS1,ADDRESS2,ZIP_CODE,CITY,COUNTRY) " +
                "VALUES (:name,:address1,:address2,:zipCode,:city,:country)");
        writer.setDataSource(dataSource);
        return writer;
    }

    /**
     * Import Address job.
     *
     * @param listener
     *            the listener
     * @return Job holds the
     */
    @Bean("addressJob")
    public Job addressJob(JobListener listener) {
        return jobBuilderFactory.get("addressJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(importData())
                .end()
                .build();
    }


    /**
     * Import data.
     *
     * @return Step holds the
     */
    @Bean("AddressJobStep")
    public Step importData() {
        return stepBuilderFactory.get("AddressJobStep")
                .<AddressModel, AddressDTO> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

}
