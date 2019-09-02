package com.imooc.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ARTest {

    // AR 模式需要在User中继承 Model, 此处无需注入 userMapper

    @Test
    public void insert(){
        User user = new User();
        user.setName("白云飞");
        user.setAge(24);
        user.setEmail("byf@baomidou.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDate.now());

        boolean insert = user.insert();
        System.out.println("是否插入成功："+insert);
    }

    @Test
    public void selectById(){
        User user = new User();
        User userSelect = (User) user.selectById(1147354797289058306L);
        System.out.println("userSelect: "+userSelect);
    }

    @Test
    public void selectAll(){
        User user = new User();
        List list = user.selectAll();
        list.forEach(System.out::println);
    }

    @Test
    public void selectByPage(){
        User user = new User();
        Page<User> page = new Page<>(1,2);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age",25);
        IPage iPage = user.selectPage(page, queryWrapper);
        System.out.println("总记录数："+iPage.getTotal());
        System.out.println("总页数："+iPage.getPages());
        List records = iPage.getRecords();
        records.forEach(System.out::println);
    }


    @Test
    public void updateById(){
        User user = new User();
        user.setId(1147355192338001922L);
        user.setName("白眉鹰王");
        boolean b = user.updateById();
        System.out.println("是否修改成功："+b);
    }

    @Test
    public void deleteById(){
        User user = new User();
        boolean b = user.deleteById(1147355192338001922L);
        System.out.println("是否删除成功："+b);
    }
}
