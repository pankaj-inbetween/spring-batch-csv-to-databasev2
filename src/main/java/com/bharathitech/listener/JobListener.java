package com.bharathitech.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bharathitech.dto.EmployeeDTO;

/**
 * The listener interface for receiving job events.
 * The class that is interested in processing a job
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addJobListener<code> method. When
 * the job event occurs, that object's appropriate
 * method is invoked.
 *
 * @see JobEvent
 */
@Component
public class JobListener extends JobExecutionListenerSupport {

    /** The jdbc template. */
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            jdbcTemplate.query("select EMPLOYEE_ID,EMPOYEE_NAME,EMP_ROLE,SALARY,EXPERIENCE from EMMPLOYEE ",
                (rs,rowNum)->{
                    return new EmployeeDTO(rs.getLong(1), rs.getString(2),rs.getString(3),rs.getString(4),
                        rs.getInt(5));
                }
                    );
        }
    }
}
