package com.imooc.dao;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.imooc.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserUpdateTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void updateById(){
        User user = new User();
        user.setId(1088248166370832385L);
        user.setAge(28);
        user.setEmail("wtf@qq.com");

        int rows = userMapper.updateById(user);
        System.out.println("影响记录数："+rows);
    }

    @Test
    public void update(){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name","李艺伟").eq("age",28);

        User user = new User();
        user.setAge(30);
        user.setEmail("lyw2019@baomidou.com");

        int rows = userMapper.update(user, updateWrapper);
        System.out.println("影响数："+rows);
    }

    // 如果要更新的字段非常少，可以用这种方法
    @Test
    public void update2(){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name","李艺伟").eq("age",30)
                .set("age",28).set("email","lyw@baomidou.com");

        int rows = userMapper.update(null, updateWrapper);
        System.out.println("影响数："+rows);
    }

    @Test
    public void updateByLambda(){
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getName,"刘明强").set(User::getEmail,"lmq@baomidou.com");

        int rows = userMapper.update(null, lambdaUpdateWrapper);
        System.out.println("影响数："+rows);
    }
}
