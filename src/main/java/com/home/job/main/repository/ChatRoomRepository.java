package com.home.job.main.repository;

import com.home.job.company.projections.ChatRoomListProjections;
import com.home.job.main.entity.ChatRoom;
import com.home.job.main.projections.ChatRoomProjections;
import com.home.job.main.projections.UserChatRoomListProjections;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
//    JpaRepository.count() 메서드 자체가 long을 반환
//    long countByUserInfoIdAndRecruitBoardId(int loginId, int postId);

    @Query("select c.id from ChatRoom c where c.userInfoId = :userInfoId and c.recruitBoardId = :recruitBoardId")
    Optional<Integer> findIdByUserInfoIdAndRecruitBoardId(@Param("userInfoId") int userInfoId,
                                                          @Param("recruitBoardId") int recruitBoardId);

    @Query("select cr.id as roomId, cr.recruitBoardId as recruitBoardId, cr.isActive as isActive, " +
            "       rb.companyInfoId as companyInfoId, rb.title as title," +
            "       ci.name as name from ChatRoom cr " +
            "join RecruitBoard rb on cr.recruitBoardId = rb.id " +
            "join CompanyInfo ci on rb.companyInfoId = ci.id " +
            "where cr.id = :roomId")
    ChatRoomProjections findChatRoomByRoomId(@Param("userId") int roomId);

//    채팅목록 (유저)
    @Query("select cr.id as roomId, " +
            "rb.title as title, rb.companyInfoId as companyId, " +
            "ci.profileImg as companyProfile, " +
            "cd.message as message, cd.createdAt as createdAt, ( " +
            "select count(*) from ChatDetail cd3 " +
            "where cd3.roomId = cr.id and cd3.isReading = 'N') as unreadCount " +
            "from ChatRoom cr " +
            "join RecruitBoard rb on cr.recruitBoardId = rb.id " +
            "join CompanyInfo ci on rb.companyInfoId = ci.id " +
            "join ChatDetail cd on cr.id = cd.roomId " +
            "where cr.userInfoId = :userId " +
            "and cd.createdAt = ( " +
            "select max(cd2.createdAt) from ChatDetail cd2 " +
            "where cd2.roomId = cr.id )" +
            "order by cd.createdAt desc ")
    List<UserChatRoomListProjections> findChatListByUserId(@Param("userId") int userId);

//    채팅목록 (회사)
    @Query("select cr.id as roomId, cr.userInfoId as userId, " +
            "ui.name as name, ui.profileImg as userProfile from ChatRoom cr " +
            "join RecruitBoard rb on cr.recruitBoardId = rb.id " +
            "join UserInfo ui on cr.userInfoId = ui.id " +
            "where rb.companyInfoId = :companyId")
    List<ChatRoomListProjections> findChatListByCompanyId(@Param("companyId") int companyId);
}
