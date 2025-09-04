package com.home.job.main.service;

import com.home.job.company.dto.RecruitBoardDto;
import com.home.job.company.projections.RecruitBoardSelectProjections;
import com.home.job.company.repository.RecruitBoardRepository;
import com.home.job.main.dto.ActualWorkDto;
import com.home.job.main.dto.BusinessDto;
import com.home.job.main.dto.ChatDetailDto;
import com.home.job.main.entity.ActualWork;
import com.home.job.main.entity.Business;
import com.home.job.main.entity.ChatDetail;
import com.home.job.main.entity.ChatRoom;
import com.home.job.main.projections.ActualWorkProjections;
import com.home.job.main.projections.ChatRoomProjections;
import com.home.job.main.projections.UserChatRoomListProjections;
import com.home.job.main.repository.ActualWorkRepository;
import com.home.job.main.repository.BusinessRepository;
import com.home.job.main.repository.ChatDetailRepository;
import com.home.job.main.repository.ChatRoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {
    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private ActualWorkRepository actualWorkRepository;

    @Autowired
    private RecruitBoardRepository recruitBoardRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatDetailRepository chatDetailRepository;

//    근무지 등록 (insert)
    public void businessCreate(BusinessDto businessDto) {
//        DTO → Entity 변환
        Business business = businessDto.toEntity();

        businessRepository.save(business);
    }

//    근무지 목록 가져오기
    public List<Business> getAllBusiness(int userId) {
        return businessRepository.findAllByUserInfoId(userId);
    }

//    근무시간 등록 (insert)
    public void actualWorkCreate(ActualWorkDto actualWorkDto) {
        ActualWork actualWork = actualWorkDto.toEntity();

        actualWorkRepository.save(actualWork);
    }

//    근무시간 가져오기
    public List<ActualWorkProjections> getAllActualWork(int userId) {
        return actualWorkRepository.findAllByUserInfoId(userId);
    }

//    근무시간 수정 (update)
    @Transactional // 트랜잭션 범위를 정해주는 어노테이션 update, delete 는 필요
    public void actualWorkUpdate(ActualWorkDto actualWorkDto) {
        ActualWork actualWork = actualWorkRepository.findById(actualWorkDto.getId())
                .orElseThrow(() -> new RuntimeException("근무시간 정보가 없습니다."));
//     findById()는 객체가 아닌 Optional<T>를 리턴하므로 .orElseThrow / .orELse 예외처리 필요

        actualWork.setStartTime(actualWorkDto.getStartTime());
        actualWork.setEndTime(actualWorkDto.getEndTime());
    }

//    근무시간 삭제 (delete)
    @Transactional
    public void actualWorkDelete(int actualWorkId) {
        ActualWork actualWork = actualWorkRepository.findById(actualWorkId)
                .orElseThrow(() -> new RuntimeException("근무시간 정보가 없습니다."));

        actualWorkRepository.delete(actualWork);
    }

//    공고 목록 가져오기
    public List<RecruitBoardSelectProjections> getAllRecruitBoard() {
        return recruitBoardRepository.selectAllRecruitBoard();
//        return recruitBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));  // (Sort.by(Sort.Direction.DESC : 정렬)
    }

    public long getAllRecruitBoardCount() {
        return recruitBoardRepository.count();
    }

//    채팅방 검색
//    public long countByCheckRoom(int loginId, int postId) {
//        return chatRoomRepository.countByUserInfoIdAndRecruitBoardId(loginId, postId);
//    }

//    채팅방 생성
    public Integer chatRoomCreate(int loginId, int postId) {
//        JPA 엔티티는 보통 기본 생성자를 protected.. dto 안만들어서 빌더로 처리
//        빌더로 할 경우 초기값 세팅해둔거 무시하고 null로 들어감. 엔티티에서 Default 설정
        ChatRoom room = ChatRoom.builder()
                .userInfoId(loginId)
                .recruitBoardId(postId)
                .build();

        chatRoomRepository.save(room);

        return room.getId();
    }

//    채팅방 검색 (아이디 가져오기)
    public Integer findRoomId(int loginId, int postId) {
        return chatRoomRepository
                .findIdByUserInfoIdAndRecruitBoardId(loginId, postId)
                .orElseThrow(() -> new IllegalStateException("채팅방이 존재하지 않습니다."));
    }

//    채팅방 정보 가져오기
    public ChatRoomProjections findChatRoomById(int roomId) {
        return chatRoomRepository.findChatRoomByRoomId(roomId);
    }

//    채팅목록 (유저)
    public List<UserChatRoomListProjections> getChatRoomListByUserId(int userId) {
        return chatRoomRepository.findChatListByUserId(userId);
    }

//    채팅
    public List<ChatDetailDto> getChatListByRoomId(int roomId) {
        List<ChatDetail> chatDetail = chatDetailRepository.findChatListByRoomId(roomId);

        return chatDetail.stream()
                .map(ChatDetailDto::toDto)
                .toList();
    }

//    isReading N -> Y
    public void updateIsReadingByRoomId(int roomId) {
//        update jpa...
    }

}
