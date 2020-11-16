package com.spc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableCaching
@EnableAsync
public class Application implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Resource()
    private ThreadPoolTaskExecutor threadPool;

    @Resource()
    private FileService fileService;

    @Bean("threadPool")
    public Executor toPdfTaskExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(2);
        threadPool.setMaxPoolSize(5);
        threadPool.setQueueCapacity(500);
        threadPool.setThreadNamePrefix("Test-");
        threadPool.initialize();
        return threadPool;
    }


    public void handleFile() {
        for (int i = 0; i < 5; i++) {
            fileService.handle(new File(i + ".txt"));
        }
    }


    public void printHandling_File() {
        BlockingQueue<Runnable> queue = threadPool.getThreadPoolExecutor().getQueue();
//    ================================我想获取文件信息，怎么样从线程池那获取信息？？？=====================================
        logger.info("现在等待处理文件数：" + queue.size() );
        logger.info("并且我想获取等待处理文件 名字  那该如何获取？？？？？？？？");
    }

    @Override
    public void run(String... args) throws Exception {
        handleFile();
        printHandling_File();
        Thread.sleep(2000);
        printHandling_File();
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
