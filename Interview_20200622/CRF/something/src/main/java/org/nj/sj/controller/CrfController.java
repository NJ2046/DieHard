package org.nj.sj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@RestController
@RequestMapping("/something")
public class CrfController
{

    @RequestMapping("/concurrent")
    public String storePatientDetail(@RequestBody String json)
    {
        return "hello world";
    }

}
