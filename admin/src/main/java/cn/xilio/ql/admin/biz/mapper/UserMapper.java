package cn.xilio.ql.admin.biz.mapper;


import cn.xilio.ql.admin.common.domain.SearchReq;
import cn.xilio.ql.admin.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(User user);

    User selectByPrimaryKey(Integer id);

    int updateByPk(User record);

    List<User> listUser(SearchReq param);

    User findByNameAndPwd(User user);

    List<String> listApp();

    List<User> selectHkUserList(User user);

    User selectByUserName(String userName);

    List<String> listErpByApp(String app);
}
