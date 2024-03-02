package cn.xilio.ql.admin.biz.service.impl;


import cn.xilio.ql.admin.biz.mapper.UserMapper;
import cn.xilio.ql.admin.biz.service.UserService;
import cn.xilio.ql.admin.common.domain.SearchReq;
import cn.xilio.ql.admin.common.domain.req.PageReq;
import cn.xilio.ql.admin.common.eunm.ResultEnum;
import cn.xilio.ql.admin.common.ex.BizException;
import cn.xilio.ql.admin.model.User;
import cn.xilio.ql.admin.util.JwtTokenUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Value("${erp.defaultPwd:123}")
    private String defaultPwd;
    @Resource
    private UserMapper userMapper;

    @Override
    public User findByNameAndPwd(User user) {
        user.setPwd(DigestUtils.md5DigestAsHex(user.getPwd().getBytes()));
        return userMapper.findByNameAndPwd(user);
    }

    @Override
    public int insertUser(User user) {
        String name = user.getUserName();
        String app = user.getAppName();
        User dbUser = userMapper.selectByUserName(name);
        if (dbUser != null) {
            throw new BizException(ResultEnum.CONFLICT_ERROR);
        }
        if (StringUtil.isNotEmpty(app)) {
            this.initApp(app);
        }
        user.setCreateTime(new Date());
        user.setPwd(DigestUtils.md5DigestAsHex(user.getPwd().getBytes()));
        return userMapper.insertSelective(user);
    }

    @Override
    public int insertUserByErp(User user) {
        User userParam = new User();
        userParam.setUserName(user.getUserName());
        List<User> users = userMapper.selectHkUserList(userParam);
        if (users.size() == 0) {
            user.setCreateTime(new Date());
            user.setPwd(DigestUtils.md5DigestAsHex(defaultPwd.getBytes()));
            int ret = userMapper.insertSelective(user);
            System.out.println(user.getId());
            return ret;
        }
        return 0;
    }

    @Override
    public Cookie loginErpUser(User user) {
        User userParam = new User();
        userParam.setUserName(user.getUserName());
        List<User> users = userMapper.selectHkUserList(userParam);
        if (users.size() == 0) {
            user.setCreateTime(new Date());
            user.setPwd(DigestUtils.md5DigestAsHex(defaultPwd.getBytes()));
            userMapper.insertSelective(user);
            String token = JwtTokenUtil.createJWT(user.getId(), user.getUserName(), "", user.getAppName(), user.getNickName());
            Cookie cookie = new Cookie("token", JwtTokenUtil.TOKEN_PREFIX + token);
            cookie.setMaxAge(3600 * 24 * 7);
            //cookie.setDomain("localhost");
            cookie.setPath("/");
            return cookie;
        } else {
            user = users.get(0);
            String token = JwtTokenUtil.createJWT(user.getId(), user.getUserName(), "", user.getAppName(), user.getNickName());
            Cookie cookie = new Cookie("token", JwtTokenUtil.TOKEN_PREFIX + token);
            cookie.setMaxAge(3600 * 24 * 7);
            //cookie.setDomain("localhost");
            cookie.setPath("/");
            return cookie;
        }
    }

    @Override
    public int deleteByPrimaryKey(int id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User selectByPrimaryKey(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User selectByUserName(String userName) {
        return userMapper.selectByUserName(userName);
    }

    @Override
    public int updateUser(User user) {
        if (!StringUtils.isEmpty(user.getPwd())) {
            user.setPwd(DigestUtils.md5DigestAsHex(user.getPwd().getBytes()));
        }
        return userMapper.updateByPk(user);
    }

    @Override
    public List<String> listApp() {
        return userMapper.listApp();
    }


    @Override
    public int initApp(String app) {
        return 1;
    }

    @Override
    public Page<User> pageUser(PageReq param, SearchReq dto) {
        Page<User> page = new Page<>(param.getPageNum(), param.getPageSize());
        List<User> users = userMapper.listUser(dto);
        page.setRecords(users);
        return page;
    }


}
