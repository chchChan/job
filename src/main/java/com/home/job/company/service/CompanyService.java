package com.home.job.company.service;

import com.home.job.company.dto.CompanyInfoDto;
import com.home.job.company.entity.CompanyInfo;
import com.home.job.company.repository.CompanyInfoRepository;
import com.home.job.user.dto.UserInfoDto;
import com.home.job.user.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    private CompanyInfoRepository companyInfoRepository;

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
}
