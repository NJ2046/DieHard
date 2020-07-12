package org.nj.sj.controller;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/something")
public class SomeThingController
{

    @RequestMapping("/concurrent")
    public String storePatientDetail(@RequestBody String json)
    {
        return "hello world";
    }

}
