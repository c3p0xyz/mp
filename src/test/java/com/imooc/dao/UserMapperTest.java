package com.imooc.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    // 查询所有
    @Test
    public void selectTest(){
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5,userList.size());
        userList.forEach(System.out::println);
    }

    // 新增
    @Test
    public void insertTest(){
        User user = new User();
        user.setName("向阳");
        user.setAge(28);
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDate.now());
        int result = userMapper.insert(user);
        Assert.assertEquals(1,result);
    }

    // 通过 id 查询
    @Test
    public void selectById(){
        User user = userMapper.selectById(1087982257332887553l);
        Assert.assertNotNull(user);
        System.out.println(user);
    }

    // 根据 id 查询
    @Test
    public void selectByIds(){
        List<Long> ids = Arrays.asList(1087982257332887553L, 1094590409767661570L, 1094592041087729666L);
        List<User> userList = userMapper.selectBatchIds(ids);
        Assert.assertEquals(3, userList.size());
        userList.forEach(System.out::println);
    }

    // 根据数据库字段查询，将条件封装到 map 中, put 的key是数据库列名
    @Test
    public void selectByMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("manager_id",1088248166370832385L);
        List<User> userList = userMapper.selectByMap(map);
        userList.forEach(System.out::println);
    }

    // 查询名字中包含雨，并且年龄小于40
    @Test
    public void selectByWrapper(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","雨").lt("age",40);

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    // 查询名字中包含雨，并且年龄大于等于 20 且小于等于40，并且 email 不能为空
    @Test
    public void selectByWrapper2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","雨").between("age",20,40).isNotNull("email");

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    // 名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按id 升序排列、
    @Test
    public void selectByWrapper3(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name","王").or().ge("age",25).orderByDesc("age")
                .orderByAsc("id");

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    // 创建日期为 2019 年2月14日并且直属上级为名字为王姓
    @Test
    public void selectByWrapper4(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("date_format(create_time,'%Y-%m-%d')={0}","2019-02-14")
            .inSql("manager_id","select id from user where name like '王%'");

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    // 名字为王姓，并且(年龄小于40或者邮箱不能为空)
    @Test
    public void selectByWrapper5(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name","王").and(e -> e.lt("age",40).or().isNotNull("email"));

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    // 名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不能为空）
    @Test
    public void selectByWrapper6(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name","王").or(e->e.lt("age",40).gt("age",20)
                .isNotNull("email"));

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    //  （年龄小于40 或邮箱不能为空）并且名字为王姓
    @Test
    public void selectByWrapper7(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.nested(e->e.lt("age",40).or().isNotNull("email"))
                .likeRight("name","王");

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    // 年龄为 30、31、34、35
    @Test
    public void selectByWrapper8(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age", Arrays.asList(30,31,34,35));

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    // 只返回满足条件的其中一条即可
    @Test
    public void selectByWrapper9(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age", Arrays.asList(28,30,31)).last("limit 1"); // 有sql注入风险、慎用

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    // 查询名字中包含雨，并且年龄小于40  只查询 id 和 name
    @Test
    public void selectWrapperSupper(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name").like("name","雨").lt("age",40);

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    // 查询名字中包含雨，并且年龄小于40  查询结果排除 manager_id 和 create_time 字段
    @Test
    public void selectWrapperSupper2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(User.class, info->!info.getColumn().equals("create_time") &&
            !info.getColumn().equals("manager_id"))
                .like("name","雨").lt("age",40);

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void testCondition(){
        String name = "王";
        String email = "";
        selectWithCondition(name,email);
    }

    private void selectWithCondition(String name, String email){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),"name",name)
                .like(StringUtils.isNotEmpty(email),"email",email);

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperEq(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String,Object> map = new HashMap<>();
        map.put("name","王天风");
        map.put("age",25);
        queryWrapper.allEq(map);

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperEq2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name","王天风");
        map.put("age",25);
        queryWrapper.allEq((k,v)->!k.equals("name"),map);   // 过滤掉map中key为name的条件

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    // 用 selectMaps查询的结果只会有需要的字段，不会有实体类如 email=null create_time=null 的情况
    @Test
    public void selectByWrapperMaps(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","王").lt("age",40).select("id","name");

        List<Map<String, Object>> mapList = userMapper.selectMaps(queryWrapper);
        mapList.forEach(System.out::println);
    }

    /*  按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄 并且只取年龄总和小于500的组
        select avg(age) avg_age,min(age) min_age,max(age) max_age from user group by manager_id having sum(age) < 500
    */
    @Test
    public void selectByWrapperMaps2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("avg(age) avg_age","min(age) min_age","max(age) max_age")
                .groupBy("manager_id").having("sum(age)<{0}",500);

        List<Map<String, Object>> mapList = userMapper.selectMaps(queryWrapper);
        mapList.forEach(System.out::println);
    }

    // selectObjs 查询出来的结果只返回一列
    @Test
    public void selectByWrapperObjs(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name").like("name","雨").lt("age",40);

        List<Object> objectList = userMapper.selectObjs(queryWrapper);
        objectList.forEach(System.out::println);
    }

    // 查询统计数
    @Test
    public void selectByWrapperCount(){
        Integer count = userMapper.selectCount(null);
        System.out.println("统计数为："+count);
    }

    // 查询带条件的统计数
    @Test
    public void selectByWrapperCount2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","雨");

        Integer count = userMapper.selectCount(queryWrapper);
        System.out.println("名字中带有雨字的数据："+count);
    }

    // 此方法预期返回结果只有一条或为空，如果查询结果有多条则报错
    @Test
    public void selectByWrapperOne(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
      //  queryWrapper.likeRight("name","向");   查到的数据有两条，报错
        queryWrapper.likeRight("name","王");

        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    // lamabda 表达式查询
    @Test
    public void selectByLambda(){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName,"雨").lt(User::getAge,40);

        List<User> userList = userMapper.selectList(lambdaQueryWrapper);
        userList.forEach(System.out::println);
    }

    //  用 lambda 表达式 （年龄小于40 或邮箱不能为空）并且名字为王姓
    @Test
    public void selectByLambda2(){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.nested(e->e.lt(User::getAge,40).or().isNotNull(User::getEmail))
                .likeRight(User::getName,"王");

        List<User> userList = userMapper.selectList(lambdaQueryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectMy(){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.nested(e->e.lt(User::getAge,40).or().isNotNull(User::getEmail))
                .likeRight(User::getName,"王");

        List<User> userList = userMapper.selectAll(lambdaQueryWrapper);
        userList.forEach(System.out::println);
    }

    // 分页查询
    @Test
    public void selectPage(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("name","25");

        Page<User> page = new Page<>(1,2);
        IPage<User> iPage = userMapper.selectPage(page, queryWrapper);
        System.out.println("总记录数："+iPage.getTotal());
        System.out.println("总页数："+iPage.getPages());

        List<User> records = iPage.getRecords();
        records.forEach(System.out::println);
    }

    @Test
    public void selectPage2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age",25);

        Page<User> page = new Page<>(2,2);
        IPage<User> iPage = userMapper.selectByUserPage(page, queryWrapper);
        System.out.println("总记录数："+iPage.getTotal());
        System.out.println("总页数："+iPage.getPages());

        List<User> records = iPage.getRecords();
        records.forEach(System.out::println);
    }
}