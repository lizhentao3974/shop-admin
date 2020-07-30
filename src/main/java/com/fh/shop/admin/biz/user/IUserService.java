package com.fh.shop.admin.biz.user;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.user.UserSearchParam;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.vo.user.UserVo;

import java.util.Date;
import java.util.List;

public interface IUserService {
    DataTableResult queryUserList(UserSearchParam userSearchParam);

    void addUser(User user);

    void deleteUser(Long id);

    User editUser(Long id);

    void updateUser(User user);

    ServerResponse deleteUsers(Long[] ids);

    List<User> findUserParam(UserSearchParam userSearchParam);

    User findUserByName(String userName);

    List<UserVo> getUserVoList(List<User> userList);

    void updateUserLoginDate(Long userId, Date date);

    void updateLoginCount(Long userId);

    void incrLoginCount(Long userId);
}
