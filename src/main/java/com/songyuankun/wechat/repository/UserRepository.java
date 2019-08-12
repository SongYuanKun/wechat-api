package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author songyuankun
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    /**
     * openId查用户
     *
     * @param uid openId
     * @return 用户
     */
    User findByUid(String uid);
/**
     * openId查用户
     *
     * @param phone phone
     * @param password password
     * @return 用户
     */
    User findByPhoneAndPassword(String phone,String password);

    /**
     * 更新用户名称和电话
     *
     * @param id       id
     * @param userName userName
     * @param phone    phone
     * @return 影响行数
     */
    @Modifying
    @Query(value = "update User u set u.userName=:userName,u.phone=:phone where u.id=:id")
    int updateUserNameAndPhone(@Param("id") int id, @Param("userName") String userName, @Param("phone") String phone);
}