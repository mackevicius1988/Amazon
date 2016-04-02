package com.mm.amazon.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mm.amazon.inventory.services.ImageService;
import com.mm.amazon.inventory.services.InventoryService;
import com.mm.amazon.inventory.services.impl.ImageServiceImpl;
import com.mm.amazon.inventory.services.impl.InventoryServiceImpl;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    ImageService imageService() {
    	return new ImageServiceImpl();
    }
    
    @Bean
    InventoryService inventoryService() {
    	return new InventoryServiceImpl();
    }
    
//    @Bean
//    @ExportMetricWriter
//    MetricWriter metricWriter(MetricExportProperties export) {
//    	return new RedisMetricRepository(connectionFactory,
//          export.getRedis().getPrefix(), export.getRedis().getKey());
//    }

}
