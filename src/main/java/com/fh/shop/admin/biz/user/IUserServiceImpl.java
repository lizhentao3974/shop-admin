package com.fh.shop.admin.biz.user;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.mapper.user.IUserMapper;
import com.fh.shop.admin.param.user.UserSearchParam;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.util.DateUtil;
import com.fh.shop.admin.vo.user.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userService")
public class IUserServiceImpl implements IUserService {

    @Autowired
    private IUserMapper userMapper;

    @Override
    public DataTableResult queryUserList(UserSearchParam userSearchParam) {
        Long count = userMapper.queryUserCount(userSearchParam);

        List<User> userList = userMapper.queryUserList(userSearchParam);

        List<UserVo> userVoList = new ArrayList<>();
        for (User user : userList) {
            UserVo userVo = new UserVo();
            userVo.setId(user.getId());
            userVo.setUserName(user.getUserName());
            userVo.setRealName(user.getRealName());
            userVo.setPassword(user.getPassword());
            userVo.setSex(user.getSex());
            userVo.setEmail(user.getEmail());
            userVo.setBirthday(DateUtil.date2str(user.getBirthday(), DateUtil.Y_M_D));
            userVo.setTelePhone(user.getTelePhone());
            userVo.setUserImage(user.getUserImage());
            userVo.setAreaName(user.getProvincialName() + "---->" + user.getCityName() + "---->" + user.getCountyName());
            userVoList.add(userVo);
        }

        return new DataTableResult(userSearchParam.getDraw(), count, count, userVoList);
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.deleteUser(id);
    }

    @Override
    public User editUser(Long id) {
        User user = userMapper.editUser(id);
        return user;
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public ServerResponse deleteUsers(Long[] ids) {
        List<Long> longs = Arrays.asList(ids);
        userMapper.deleteUsers(longs);
        return ServerResponse.success();
    }

    @Override
    public List<User> findUserParam(UserSearchParam userSearchParam) {
        List<User> userList = userMapper.findUserParam(userSearchParam);
        return userList;
    }

    @Override
    public User findUserByName(String userName) {
        User user = userMapper.findUserByName(userName);
        return user;
    }

    public List<UserVo> getUserVoList(List<User> userList) {
        List<UserVo> userVoList = new ArrayList<>();
        for (User user : userList) {
            UserVo userVo = new UserVo();
            userVo.setId(user.getId());
            userVo.setUserName(user.getUserName());
            userVo.setRealName(user.getRealName());
            userVo.setPassword(user.getPassword());
            userVo.setSex(user.getSex());
            userVo.setEmail(user.getEmail());
            userVo.setBirthday(DateUtil.date2str(user.getBirthday(), DateUtil.Y_M_D));
            userVo.setTelePhone(user.getTelePhone());
            userVo.setAreaName(user.getProvincialName() + "---->" + user.getCityName() + "---->" + user.getCountyName());
            userVoList.add(userVo);
        }
        return userVoList;
    }

    @Override
    public void updateUserLoginDate(Long userId, Date date) {
        userMapper.updateUserLoginDate(userId, date);
    }

    @Override
    public void updateLoginCount(Long userId) {
        userMapper.updateLoginCount(userId);
    }

    @Override
    public void incrLoginCount(Long userId) {
        userMapper.incrLoginCount(userId);
    }

}
