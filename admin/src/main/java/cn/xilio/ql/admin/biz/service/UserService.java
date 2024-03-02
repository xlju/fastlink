package cn.xilio.ql.admin.biz.service;




import cn.xilio.ql.admin.common.domain.SearchReq;
import cn.xilio.ql.admin.common.domain.req.PageReq;
import cn.xilio.ql.admin.model.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.Cookie;
import java.util.List;


public interface UserService {

    Page<User> pageUser(PageReq page, SearchReq dto);

    User findByNameAndPwd(User user);

    int insertUser(User user);

    int insertUserByErp(User user);

    Cookie loginErpUser(User user);

    int deleteByPrimaryKey(int id);

    User selectByPrimaryKey(int id);

    User selectByUserName(String userName);

    int updateUser(User user);

    List<String> listApp();

    /**
     * 初始化APP
     * @param app
     * @return
     */
    int initApp(String app);
}
