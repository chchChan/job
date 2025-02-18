package com.home.job.user.projections;

// get이 없는 메서드는 자동 매핑x  리포지토리에 쓴 알리아스와 일치해야함
// 프로젝션 장점 : 유지보수 용이, 자동 매핑, 유연성
// 프로젝션 단점 : 성능 이슈, IDE 경고
public interface FindPwProjections {
    int getId();
    String getAccountId();
    String getQuestion();
}
