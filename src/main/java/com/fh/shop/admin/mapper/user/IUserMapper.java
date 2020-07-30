package com.fh.shop.admin.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.param.user.UserSearchParam;
import com.fh.shop.admin.po.user.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface IUserMapper extends BaseMapper<User> {
    List<User> queryUserList(UserSearchParam userSearchParam);

    Long queryUserCount(UserSearchParam userSearchParam);

    void addUser(User user);

    void deleteUser(Long id);

    User editUser(Long id);

    void updateUser(User user);

    void deleteUsers(List<Long> longs);

    List<User> findUserParam(UserSearchParam userSearchParam);

    User findUserByName(String userName);

    void updateUserLoginDate(@Param("userId") Long userId, @Param("date") Date date);

    void updateLoginCount(Long userId);

    void incrLoginCount(Long userId);

}
