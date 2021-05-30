# spring-batch-csv-to-database
Simple spring batch load the data from csv to database

An example spring batch csv to database, built on Spring Boot.

## Features

- Data upload from csv to database.
- Models with proper relationships.
- Logger properly used
- cron Job Scheduled used
- For testing, H2 database used.

## Springbatch Concepts:
## Job 

 - It is the most important concept in Spring Batch. 
 - It needs a JobLauncher instance to be executed. • It can contain one or more steps, which can be executed in sequence or parallel.

## Step 
- It encapsulates an independent and sequential phase of a job. 
- It contains exactly a Reader, a Writer and optionally a Processor.

## Reader 
- It represents an abstraction which is responsible for recovering data from a source. 
- When it can not retrieve more data, it returns null.
- setLinesToSkip(1);, used for skip the header value.
- In setNames(), the given name should match with csv file header value.

## Writer 
- It represents the output for a step in chunk oriented processing. 
- There are various implementations out-of-the-box. 
- It has not any knowledge about the reader implementation.
- setSql(), VALUES (:name,:address1,:address2,:city,:country) - parmeter should match with DTO variable name.

## Processor 
- It is optional in the chunk oriented processing. 
- It represents the business process for each item, and it can return a null, when that item doesn’t need to be written.

## Listeners 

Bring us the possibility to perform some actions during the execution of a Job and/or Step.
There are some types of listeners: • StepExecutionListener • ChunkListener • ItemReadListener • ItemProcessListener • ItemWriteListener • SkipListener
Spring Batch Framework offers ‘TaskletStep Oriented’ and ‘Chunk Oriented’ processing style.

TaskletStep Tasklet is an interface, which will be called to perform a single task only, like clean or set up resources before or after any step execution.

## Chunk Oriented

Chunk Oriented Processing Feature has come with Spring Batch.# It refers to reading the data one at a time, and creating ‘chunks’ that will be written out, within a transaction boundary. One item is read from an ItemReader, handed to an ItemProcessor, and written. Once the number of items read equals the commit interval, the entire chunk is written out via the ItemWriter, and then the transaction is committed. Basically, this feature should be used if at least one data item’ s reading and writing is required. Otherwise, TaskletStep Oriented processing can be used if the data item’ s only reading or writing is required. Chunk Oriented Processing model exposes three important interface as ItemReader, ItemProcessor and ItemWriter via org.springframework.batch.item package.

## Implemented Spring batch using Springboot
## Maven dependencies
        <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-batch</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>

## For testing, H2 in-memrory db dependencies
	    <dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
		</dependency>
    To access in-memory DB console: http://localhost:8080/h2-console



