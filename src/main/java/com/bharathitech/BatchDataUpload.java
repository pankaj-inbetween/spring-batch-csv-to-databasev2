package com.bharathitech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Class BatchDataUpload.
 * <p>
 * Using spring batch, data upload from csv to db.
 * </p>
 *
 * @author Bharathidasan
 */
@SpringBootApplication
public class BatchDataUpload {


    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(BatchDataUpload.class, args);
    }


}
