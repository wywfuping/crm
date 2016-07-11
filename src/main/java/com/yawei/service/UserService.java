package com.yawei.service;

import com.google.common.collect.Maps;
import com.yawei.mapper.RoleMapper;
import com.yawei.mapper.UserLogMapper;
import com.yawei.mapper.UserMapper;
import com.yawei.pojo.Role;
import com.yawei.pojo.User;
import com.yawei.pojo.UserLog;
import com.yawei.util.ShiroUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

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
        userLog.setLoginip(ip);
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

    /**
     * 获取当前用户的登录日志
     * @param start
     * @param length
     * @return
     */
    public List<UserLog> findCurrentUserLog(String start, String length) {
        Map<String,Object> param = Maps.newHashMap();

        param.put("userId",ShiroUtil.getCurrentUserId());
        param.put("start",start);
        param.put("length",length);

        return userLogMapper.findByParam(param);
    }

    /**
     * 获取当前用户的登录日志数量
     * @return
     */
    public Long findCurrentUserLogCount() {
        Map<String,Object> param = Maps.newHashMap();
        param.put("userId",ShiroUtil.getCurrentUserId());
        return userLogMapper.countByParam(param);
    }

    /**
     * 根据参数来获取用户的列表
     * @param params
     * @return
     */
    public List<User> findUserListByParam(Map<String, Object> params) {
        return userMapper.findByParam(params);
    }

    /**
     * 获取用户的总数
     * @return
     */
    public Long findUserCount() {
        return userMapper.count();
    }

    /**
     * 获取根据参数查询后的数量
     * @param params
     * @return
     */
    public Long findUserCountByParam(Map<String, Object> params) {
        return userMapper.countByParam(params);
    }

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    /**
     * 新增用户
     * @param user
     */
    @Transactional
    public void saveUser(User user) {
        user.setEnable(true);
        System.out.println(11111111);
        System.out.println(user);
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        System.out.println(3333333);
        //TODO 在微信公众平台创建账号
        userMapper.save(user);
    }

    /**
     * 获取所有的角色
     * @return
     */
    public List<Role> findAllRole() {
        return roleMapper.findAll();
    }
}
