package com.zhouson.jedis;

import com.zhouson.utils.JedisPoolUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhouson
 * @create 2019-04-19 19:48
 * jedis入门
 */
public class StartJedis {
    @Test
    public void test() {
        //1.获取链接(第一种方式)
        Jedis jedis = new Jedis("localhost", 6379);
        //2.操作
        //String
        //存储可以指定过期时间的key value
        jedis.setex("timeout", 200, "过期");
        jedis.set("username", "zhangsan");
        String username = jedis.get("username");
        System.out.println(username);
//        jedis.del("username","password");
        //hash
        jedis.hset("myhash", "username", "zhangsan");
        jedis.hset("myhash", "password", "123");
        String hget = jedis.hget("myhash", "username");
        System.out.println(hget);
        Map<String, String> myhash = jedis.hgetAll("myhash");
        Set<String> keySet = myhash.keySet();
        for (String key : keySet) {
            String s = myhash.get(key);
            System.out.println(s);
        }
//        jedis.hdel("username");
        //list
        jedis.rpush("mylist", "1", "2", "3");
        jedis.lpush("mylist", "0");
        List<String> list = jedis.lrange("mylist", 0, -1);
        for (String s1 : list) {
            System.out.print(s1);
        }
        System.out.println();
        //删除第一个，删除最后一个用rpop
//        jedis.lpop("mylist");
        //删除指定内容，count表示同样内容的数量
        jedis.lrem("mylist",20,"0");
        jedis.lrem("mylist",20,"1");
        jedis.lrem("mylist",20,"2");
        jedis.lrem("mylist",20,"3");
        //set
        jedis.sadd("myset", "a", "b", "c", "d");
        Set<String> myset = jedis.smembers("myset");
        for (String str : myset) {
            System.out.println(str);
        }
        //删除指定内容（可以指定多个）
//        jedis.srem("myset","a","b");
        //随机删除一个
//        jedis.spop("myset");
        //sorted set
        jedis.zadd("mysorted", 1, "a");
        jedis.zadd("mysorted", 3, "c");
        jedis.zadd("mysorted", 2, "b");
        Set<String> mysorted = jedis.zrange("mysorted", 0, -1);
        for (String str : mysorted) {
            System.out.println(str);
        }
        //删除指定内容
//        jedis.zrem("mysorted", "a", "b");
        //3.关闭链接
        jedis.close();
    }
    @Test
    public void test2(){
        //获取链接(第二种方式)
        //创建一个配置对象（可以设置连接池的属性）,可以将配置对象当作参数传进去
        JedisPoolConfig config=new JedisPoolConfig();
        //比如设置最大连接数
        config.setMaxTotal(50);
        //比如设置空闲连接数
        config.setMaxIdle(10);
        //创建Jedis连接池对象
        JedisPool jedisPool=new JedisPool(config,"localhost");
        //获取连接
        Jedis jedis= jedisPool.getResource();
        //进行操作
        //关闭链接，并返回到连接池
        jedis.close();
    }
    @Test
    public void test3(){
        //获取连接(第三种方式)，通过工具类，起到解耦的作用
        Jedis jedis= JedisPoolUtils.getJedis();
        //操作
        jedis.set("a","0");
        //关闭连接并返回连接池
        jedis.close();
    }
}
