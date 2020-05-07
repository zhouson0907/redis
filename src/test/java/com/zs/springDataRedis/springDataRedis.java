package com.zs.springDataRedis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zhouson
 * @create 2020-05-07 22:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-redis.xml" )
public class springDataRedis {
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void test(){
//      System.out.println(redisTemplate);
        //springDataRedis每种类型都有两种操作方法（如下）
        //String
        redisTemplate.opsForValue().set("name","zs");
        System.out.println(redisTemplate.opsForValue().get("name"));
        System.out.println("----------------------------------------");
        redisTemplate.boundValueOps("name1").set("zs1");
        System.out.println(redisTemplate.boundValueOps("name1").get());
        System.out.println("----------------------------------------");
        //set
        redisTemplate.boundSetOps("a").add("1","2","3");
        System.out.println(redisTemplate.boundSetOps("a").members());
        System.out.println("----------------------------------------");
        //zset(一次只能添加一个元素，不能像set一样能添加多个元素，第二个参数是指定他的顺序)
        redisTemplate.boundZSetOps("b").add("a",0);
        redisTemplate.boundZSetOps("b").add("c",1);
        redisTemplate.boundZSetOps("b").add("b",2);
        System.out.println(redisTemplate.boundZSetOps("b").range(0, -1));
        System.out.println("----------------------------------------");
        //list
        redisTemplate.boundListOps("c").leftPushAll("a","b","c");
        redisTemplate.boundListOps("c").rightPush("d");
        System.out.println(redisTemplate.boundListOps("c").range(0, -1));
        System.out.println("----------------------------------------");
        //hash
        redisTemplate.boundHashOps("d").put("1","a");
        redisTemplate.boundHashOps("d").put("2","b");
        redisTemplate.boundHashOps("d").put("3","c");
        System.out.println(redisTemplate.boundHashOps("d").keys());
        System.out.println(redisTemplate.boundHashOps("d").values());
        //删掉某个集合、数值、hash表
        redisTemplate.delete("name");
    }
}
