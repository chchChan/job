package com.home.job.user.dto;

import com.home.job.user.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FindPasswordDto {
    public int id;
    public String accountId;
    public String question;

//     object[] -> DTO 변환 메서드
//     object[] 는 배열이므로.. 배열에서 직접 값 추출.... 근데 별로 좋은 방법은 아님! 차라리 생성자 표현식이 낫다
//    public static FindPasswordDto toDto(Object[] findPassword) {
//        return FindPasswordDto.builder()
//                .id((int) findPassword[0])
//                .accountId((String) findPassword[1])
//                .question((String) findPassword[2])
//                .build();
//    }
}
