package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * @author songyuankun
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    User findByUid(String uid);

    User findByUuidKey(String uuidKey);

    /**
     * 更新uuidKey
     *
     * @param uid  open id
     * @param uuid uuid
     * @return 影响行数
     */
    @Transactional(rollbackOn = Exception.class)
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User set uuid_key=?1 where uid=?2")
    int updateUuidKeyByUid(String uuid, String uid);

}
