package com.br.projetandoo.vdigital.spring.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class ImportaProdutosMODIFICAR {
	
	private static Resource produtosResource = new FileSystemResource(
			"src/main/resources/spring/batch/input/produtos.txt");

	private static final Logger LOG = LoggerFactory.getLogger(ImportaProdutosMODIFICAR.class);

	public static void main(String[] args) {

		String[] springConfig = { 
				"spring/batch/job-database.xml",
				"spring/batch/job-context.xml",
				"spring/batch/jobs/importaProdutosJob.xml" };

		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);

		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("importaProdutosJob");

		try {
			JobParametersBuilder jPBuilber = new JobParametersBuilder();
			jPBuilber.addString("inputFile", produtosResource.getFile().getAbsolutePath());

			JobExecution execution = jobLauncher.run(job, jPBuilber.toJobParameters());
			LOG.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Exit Status : " + execution.getStatus());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		LOG.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Done.");
	}
	
}
