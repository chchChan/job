package com.home.job.company.service;

import com.home.job.company.dto.CompanyInfoDto;
import com.home.job.company.dto.RecruitBoardDto;
import com.home.job.company.entity.CompanyInfo;
import com.home.job.company.entity.RecruitBoard;
import com.home.job.company.projections.ChatRoomListProjections;
import com.home.job.company.projections.RecruitBoardSelectProjections;
import com.home.job.company.repository.CompanyInfoRepository;
import com.home.job.company.repository.RecruitBoardRepository;
import com.home.job.main.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @Autowired
    private RecruitBoardRepository recruitBoardRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

//    회원가입 insert
    public void accountCreate(CompanyInfoDto companyInfoDto) {
        CompanyInfo companyInfo = companyInfoDto.toEntity();

        companyInfoRepository.save(companyInfo);
    }

//    아이디 중복체크
    public int countByAccountId(String accountId) {
        return companyInfoRepository.countByAccountId(accountId);
    }

//    로그인
    public CompanyInfoDto getUserInfoByAccountIdAndPw(String accountId, String accountPw) {
        CompanyInfo companyInfo = companyInfoRepository.findByAccountIdAndAccountPw(accountId, accountPw);
        if (companyInfo == null) {
            return null;
        } else {
            return CompanyInfoDto.toDto(companyInfo);
        }
    }

//    공고 insert
    public void recruitCreate(RecruitBoardDto RecruitBoardDto) {
        RecruitBoard recruitBoard = RecruitBoardDto.toEntity();

        recruitBoardRepository.save(recruitBoard);
    }

//    공고 상세 select
    public RecruitBoardSelectProjections recruitDetailSelect(int id) {
        return recruitBoardRepository.selectRecruitBoardById(id);
    }

//    채팅방 목록
    public List<RecruitBoardDto> getChatListByCompany(int companyId) {
        List<RecruitBoard> chatList = recruitBoardRepository.findChatListByCompanyInfoId(companyId);

//        List<RecruitBoardDto> dtoList = new ArrayList<>();
//        for (RecruitBoard board : boards) {
//            dtoList.add(RecruitBoardDto.toDto(board));
//        }
//        return dtoList;
//        이 방식을 stream 으로 해결

        return chatList.stream()
                .map(RecruitBoardDto::toDto) // 엔티티 → DTO 변환 .map(chatList -> RecruitBoardDto.toDto(chatList))
                .toList(); // stream 연산 결과를 다시 list
    }

//    채팅목록 (회사)
    public List<ChatRoomListProjections> getChatRoomListByCompany(int companyId) {
        return chatRoomRepository.findChatListByCompanyId(companyId);
    }

}
