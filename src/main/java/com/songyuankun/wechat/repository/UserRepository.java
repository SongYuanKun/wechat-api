package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.dao.User;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author songyuankun
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    User findByUid(String uid);

    @SQLUpdate(sql = "UPDATE user set uuid=#{uid} where uid=#{uid}")
    int updateUuidKeyByUid(String uid, String uuid);

}
