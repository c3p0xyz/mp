package com.imooc.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.imooc.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {

    /*@Select("select * from user ${ew.customSqlSegment}")*/
    List<User> selectAll(@Param(Constants.WRAPPER)Wrapper<User> wrapper);

    IPage<User> selectByUserPage(IPage<User> page, @Param(Constants.WRAPPER) Wrapper<User> wrapper);
}
