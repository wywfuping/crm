package com.yawei.service;

import com.yawei.mapper.RoleMapper;
import com.yawei.mapper.UserLogMapper;
import com.yawei.mapper.UserMapper;
import com.yawei.pojo.User;
import com.yawei.pojo.UserLog;
import com.yawei.util.ShiroUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class UserService {
    @Inject
    private UserMapper userMapper;
    @Inject
    private RoleMapper roleMapper;
    @Inject
    private UserLogMapper userLogMapper;

    /**
     * 创建用户登录日志
     * @param ip
     */
    public void saveUserLog(String ip){
        UserLog userLog = new UserLog();
        userLog.setLogip(ip);
        userLog.setLogintime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        userLog.setUserid(ShiroUtil.getCurrentUserId());

        userLogMapper.save(userLog);
    }

    /**
     * 修改用户密码
     * @param password
     */
    public void changeUserPassword(String password){
        User user = ShiroUtil.getCurrentUser();
        user.setPassword(DigestUtils.md5Hex(password));

        userMapper.updateUser(user);
    }

}
