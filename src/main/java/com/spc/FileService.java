package com.spc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Async("threadPool")
    public void handle(File file)   {
        //假如文件处理时间为 n秒
        try {
            Thread.sleep(1000);
//            System.out.println("file handle finish");
            logger.info(file.getName()+"   handle finish" );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
