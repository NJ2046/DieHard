package org.nj.sj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;


@SpringBootApplication
public class  Main {

    public static void main(String[] args) {
        SpringApplication.run(org.nj.sj.Main.class, args);
    }
}
