package org.nj.sj.controller;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class function_sd {
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
    /*
    public static void main(String[] args) {
        String msgA = "Hello";
        String msgB = "World";
        String msgC = "Java";
        log(1,()->{
            System.out.println("lambda 是否执行了");
            return msgA + msgB + msgC;
        });
    }
     */
}


class ConsumerTest {
    /*
    public static void main(String[] args) {
        testConsumer();
        //testAndThen();
    }
*/
    /**
     * 一个简单的平方计算
     */
    public static void testConsumer(){
        Consumer<Integer> square = x -> System.out.println("print square : " + x * x);
        square.accept(2);
    }

    /**
     * 定义3个Consumer并按顺序进行调用andThen方法，其中consumer2抛出NullPointerException。
     */
    public static void testAndThen(){
        Consumer<Integer> consumer1 = x -> System.out.println("first x : " + x);
        Consumer<Integer> consumer2 = x -> {
            System.out.println("second x : " + x);
            throw new NullPointerException("throw exception test");
        };
        Consumer<Integer> consumer3 = x -> System.out.println("third x : " + x);

        consumer1.andThen(consumer2).andThen(consumer3).accept(1);
    }
}



class Runn {
    //lambda 作为参数
    private static void startThread(Runnable task) {
        new Thread(task).start();
    }
    /*
    public static void main(String[] args) {
        startThread(()->System.out.println("线程任务执行！"));
   }
   */
}


class lambda_Comparator {
    //lambda 作为返回值
    //下面给出 lambda 以及实际替代的内部类写法
    private static Comparator<String> newComparator(){
        return (a,b)->b.length()-a.length();
    }
    private static Comparator<String> newComparator1(){
        return new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.length()-a.length();
            }
        };
    }
    /*
    public static void main(String[] args) {
        String[] array={"abc","ab","abcd"};
        System.out.println(Arrays.toString(array));
        //tip: sort函数值得研究
        //Arrays.sort(array, newComparator1()); // 方式一
        Arrays.sort(array, newComparator()); // 方式二
        //Arrays.sort(array,(a,b)->b.length()-a.length());//更简单的方式
        System.out.println(Arrays.toString(array));
    }

     */
}


class MyComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        if (o1 < o2) {
            return 1;
        } else if (o1 > o2) {
            return -1;
        } else {
            return 0;
        }
    }
}


class Test_Supplier {
    private static String test_Supplier(Supplier<String> suply) {
        return suply.get(); //供应者接口
    }
    /*
    public static void main(String[] args) {
        // 产生的数据作为 sout 作为输出
        System.out.println(test_Supplier(()->"产生数据"));

        System.out.println(String.valueOf(new Supplier<String>() {
            @Override
            public String get() {
                return "产生数据";
            }
        }));
    }

     */
}



class use_Supplier_Max_Value {
    private static int getMax(Supplier<Integer> suply) {
        return suply.get();
    }
    /*
    public static void main(String[] args) {
        Integer [] data=new Integer[] {6,5,4,3,2,1};
        int reslut=getMax(()->{
            int max=0;
            for (int i = 0; i < data.length; i++) {
                max=Math.max(max, data[i]);
            }
            return max;
        });
        System.out.println(reslut);
    }

     */
}

class why{
    private static  int getMax(Integer [] data){
        int max = 0;
        for(int i = 0; i < data.length; i++){
            max = Math.max(max, data[i]);
        }
        return max;
    }
    /*
    public static void main(String[] args ){
        System.out.println(getMax(new Integer[]{1, 2, 4}));
    }
    */
}


class use_Consumer_FormattorName {
    public static void formattorPersonMsg(Consumer<String[]> con1, Consumer<String[]> con2) {
        // con1.accept(new String[]{ "迪丽热巴,女", "古力娜扎,女", "马尔扎哈,男" });
        // con2.accept(new String[]{ "迪丽热巴,女", "古力娜扎,女", "马尔扎哈,男" });
        // 一句代码搞定
        con1.andThen(con2).accept(new String[] { "迪丽热巴,女", "古力娜扎,女", "马尔扎哈,男" });
    }
    public static void main(String[] args) {
        formattorPersonMsg((s1) -> {
            for (int i = 0; i < s1.length; i++) {
                System.out.print(s1[i].split("\\,")[0] + " ");
            }
        }, (s2) -> {
            System.out.println();
            for (int i = 0; i < s2.length; i++) {
                System.out.print(s2[i].split("\\,")[1] + " ");
            }
        });
        System.out.println();

        printInfo(s->System.out.print(s.split("\\,")[0]),
                s->System.out.println(","+s.split("\\,")[1]),new String[]{ "迪丽热巴,女", "古力娜扎,女", "马尔扎哈,男"});
    }

    // 自身自销 有意思
    private static void printInfo(Consumer<String> one, Consumer<String> two, String[] array) {
        for (String info : array) { // 这里每次产生 {迪丽热巴。性别：女 } String 数据 逻辑那边顺序处理就行
            one.andThen(two).accept(info); // 姓名：迪丽热巴。性别：女。 } }
        }
    }
}


class pre{


    /**
     * 1. 必须为女生；
     * 2. 姓名为4个字。
     */
    /*
    public static void main(String[] args) {
        String[] array = { "迪丽热巴,女", "古力娜扎,女", "马尔扎哈,男", "赵丽颖,女" };
        getFemaleAndname((s) -> s.split("\\,")[0].length() == 4,
                (s) -> s.split("\\,")[1].equals("女"), array);
    }
     */
    private static void getFemaleAndname(Predicate<String> one,
                                         Predicate<String> two, String[] arr) {
        for (String string : arr) {
            if (one.and(two).test(string)) {
                System.out.println(string);
            }
        }
    }

}


class Main {

    public static void main(String[] args) {
        IntStream.of(200,45,89,10,-200,78,94)
                .filter(e->e>0) //过滤小于0的数
                .sorted() //自然顺序排序
                .limit(2) //取前两个
                .forEach(System.out::println);
    }
}
