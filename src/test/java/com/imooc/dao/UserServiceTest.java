package com.imooc.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.imooc.domain.User;
import com.imooc.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getOne(){
        User one = userService.getOne(new LambdaQueryWrapper<User>().gt(User::getAge,25),false);
        System.out.println(one);
    }

    // 链式操作之查询
    @Test
    public void chain(){
        List<User> userList = userService.lambdaQuery().gt(User::getAge, 25).like(User::getName, "雨").list();
        userList.forEach(System.out::println);
    }

    // 链式操作之修改
    @Test
    public void chain2(){
        boolean update = userService.lambdaUpdate().eq(User::getName, "燕南飞").set(User::getName, "燕北飞").update();
        System.out.println("是否修改成功："+update);
    }

    // 链式操作之删除
    @Test
    public void chain3(){
        boolean remove = userService.lambdaUpdate().eq(User::getName, "刘明强").remove();
        System.out.println("是否删除成功："+remove);
    }
}
