package com.radiostation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableCaching
public class RadioStationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RadioStationApplication.class, args);
    }

    // You can add additional configuration beans here if needed
    // For example:
    /*
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Radio Station Application is starting...");
            // You can add any startup logic here
        };
    }
    */

    @Bean
    public AsyncTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        return executor;
    }
}
