package com.zhouson.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author zhouson
 * @create 2019-04-19 21:19
 */
public class JedisPoolUtils {
    private static JedisPool jedisPool;

    //用静态代码块加载配置文件
    static {
        //读取配置文件
        InputStream is = JedisPoolUtils.class.getClassLoader().getResourceAsStream("jedis.properties");
        //创建配置对象
        Properties properties=new Properties();
        //关联文件
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取数据
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(Integer.parseInt(properties.getProperty("maxTotal")));
        config.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle")));
        //初始化JedisPool
        jedisPool=new JedisPool(config,properties.getProperty("host"), Integer.parseInt(properties.getProperty("port")));
    }

    //获取连接方法
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }
}
