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

import com.bharathitech.dto.EmployeeDTO;
import com.bharathitech.listener.JobListener;
import com.bharathitech.model.EmployeeModel;
import com.bharathitech.processor.EmployeeProcessor;


/**
 * The Class EmployeeJob.
 * <p>
 * Class description explaining the usage.
 * </p>
 *
 * @author Bharathidasan
 */
@Configuration
@EnableBatchProcessing
public class EmployeeJob {

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
    @Bean("employeeReader")
    public FlatFileItemReader<EmployeeModel> reader() {
        FlatFileItemReader<EmployeeModel> reader = new FlatFileItemReader<EmployeeModel>();
        reader.setResource(new FileSystemResource(filePath+"/employee_data.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<EmployeeModel>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "employee_id","emp_name","emp_role","salary","year_of_experience" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper() {{
                setTargetType(EmployeeModel.class);
            }});
        }});
        return reader;
    }


    /**
     * Processor.
     *
     * @return employee processor holds the
     */
    @Bean("employeeProcessor")
    public EmployeeProcessor processor() {
        return new EmployeeProcessor();
    }

    /**
     * Writer.
     *
     * @return Jdbc batch item writer holds the
     */
    @Bean("employeeWriter")
    public JdbcBatchItemWriter<EmployeeDTO> writer() {
        JdbcBatchItemWriter<EmployeeDTO> writer = new JdbcBatchItemWriter<EmployeeDTO>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO EMPLOYEE (EMP_ID,EMP_NAME,EMP_ROLE,SALARY,YEAR_OF_EXPR) " +
                "VALUES (:employeeId,:employeeName,:employeeRole,:salary,:yearOfExperience)");
        writer.setDataSource(dataSource);
        return writer;
    }

    /**
     * Import empoyeeJob job.
     *
     * @param listener
     *            the listener
     * @return Job holds the
     */
    @Bean("empoyeeJob")
    public Job empoyeeJob(JobListener listener) {
        return jobBuilderFactory.get("empoyeeJob")
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
    @Bean("EmpoyeeJobStep")
    public Step importData() {
        return stepBuilderFactory.get("EmpoyeeJobJobStep")
                .<EmployeeModel, EmployeeDTO> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

}
