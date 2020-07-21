package org.nj.sj.controller;

import org.springframework.web.bind.annotation.*;
import java.util.function.Consumer;

@RestController
@RequestMapping("/something")
public class SomeThingController
{

    @RequestMapping("/concurrent")
    public String concurrent(@RequestBody String json)
    {
        return "hello world";
    }


}

class Foo {
    private Integer first;

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }
/*
    public static void main(String[] args) {
        //创建一个Foo的实例
        Foo f = new Foo();
        //创建一个Consumer实例，并将
        Consumer<Foo> consumer_fun = foo->foo.setFirst(1);
        consumer_fun.accept(f);
        System.out.println();
    }
*/
}




@FunctionalInterface
interface MessageBuilder {
    String buildMessage();
}

class Demo02LoggerLambda {
    private static void log(int level, MessageBuilder builder) {
        if (level == 1) {
            System.out.println(builder.buildMessage());// 实际上利用内部类 延迟的原理,代码不相关 无需进入到启动代理执行
        }
    }
    public static void main(String[] args) {
        String msgA = "Hello";
        String msgB = "World";
        String msgC = "Java";
        log(1,()->{
            System.out.println("lambda 是否执行了");
            return msgA + msgB + msgC;
        });
    }
}
