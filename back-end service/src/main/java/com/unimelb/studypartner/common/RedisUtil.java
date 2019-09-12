package com.unimelb.studypartner.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by xiyang on 2019/9/10
 */
@Component
public class RedisUtil {
    private static Logger logger = Logger.getLogger(RedisUtil.class);
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key){
        try {
            return stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            logger.error("error occurred in RedisUtil.hasKey(String key) ---> ",e);
            return false;
        }
    }

    /**
     * 指定key的过期时间为time，单位是秒
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public boolean setExpire(String key, long time){
        try {
            if(time > 0){
                stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            logger.error("error occurred in RedisUtil.setExpire(String key, long time) ---> ",e);
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永不过期
     */
    public long getExpire(String key){
        return stringRedisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * 删除key
     * @param key 可以传一个值 或多个
     */
    public void del(String... key){
        if(key != null && key.length > 0){
            if(key.length == 1){
                stringRedisTemplate.delete(key[0]);
            }else{
                stringRedisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    //============================String类型的方法=============================
    //存储格式为： key = value
    /**
     * 获取指定键的值
     * @param key 键
     * @return 值
     */
    public Object get(String key){
        try {
            return key == null ? null : stringRedisTemplate.opsForValue().get(key);
        } catch (Exception ex){
            logger.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 设置指定键的值，如果存在就是更新操作
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key,String value) {
        try {
            stringRedisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            logger.error("error occurred in RedisUtil.set(String key,String value) ---> ",e);
            return false;
        }

    }

    /**
     * 设置指定键的值，同时设置过期时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key,String value,long time){
        try {
            if(time > 0){
                stringRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }else{
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            logger.error("error occurred in RedisUtil.set(String key,String value,long time) ---> ",e);
            return false;
        }
    }
}
