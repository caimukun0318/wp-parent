package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NoFriendDao extends JpaRepository<NoFriend,String> {
    public NoFriend findByUseridAndFriendid(String userid, String friendid);


//    @Modifying
//    @Query(value = "UPDATE tb_friend SET islike =? where userid = ? and friendid = ?",nativeQuery = true)
//    public void updateIslike(String islike, String userid, String friendid);
}
