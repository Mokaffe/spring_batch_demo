package com.toy.spring_batch_demo;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Date;

import static java.lang.String.format;

@SpringBootApplication
public class SpringBatchDemoApplication {

	public static void main(String[] args) {
		String jobName = "try-job-one";
		try {
			ConfigurableApplicationContext context = SpringApplication.run(SpringBatchConfiguration.class, args);
			JobRegistry jobRegistry = context.getBean(JobRegistry.class);
			Job job = jobRegistry.getJob(jobName);
			JobLauncher jobLauncher = context.getBean(JobLauncher.class);
			JobExecution jobExecution = jobLauncher.run(job, createJobParams());
			if (!jobExecution.getExitStatus().equals(ExitStatus.COMPLETED)){
				throw new RuntimeException(format("%s Job execution failed.", jobName));
			}
		}catch (Exception e){
			throw new RuntimeException(format("%s Job execution failed.", jobName));
		}


		SpringApplication.run(SpringBatchDemoApplication.class, args);
	}

	private static JobParameters createJobParams() {
		return new JobParametersBuilder().addDate("date", new Date()).toJobParameters();
	}
}
