package com.imooc.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.imooc.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDeleteTest {

    @Autowired
    private UserMapper userMapper;

    // 根据 id 删除
    @Test
    public void deleteById(){
        int rows = userMapper.deleteById(1145241168745857026L);
        System.out.println("影响记录数："+rows);
    }

    // 根据 map 删除
    @Test
    public void deleteByMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","向阳");
        map.put("age",28);

        int rows = userMapper.deleteByMap(map);
        System.out.println("影响记录数："+rows);
    }

    // 批量删除
    @Test
    public void deleteBatchIds(){
        List<Long> ids = Arrays.asList(1094592041087729666L, 1145198425348792322L);

        int rows = userMapper.deleteBatchIds(ids);
        System.out.println("影响记录数："+rows);
    }

    // 按条件删除
    @Test
    public void deleteByWrapper(){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getName,"向阳").or().gt(User::getAge,45);

        int rows = userMapper.delete(lambdaQueryWrapper);
        System.out.println("影响记录数："+rows);

    }
}
