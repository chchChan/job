let loginId = null;

// 유저 / 기업 로그인 판별을 위해 한 함수에 패치 두 개 넣기 -> 패치가 비동기라서 순서대로 작동 x
// 세션체크 함수 나누고 판별 함수 따로 두기 -> 마찬가지로 패치가 비동기라서 판별 함수를 먼저 실행 후 패치, 콜백함수 이용하면 해결
// 최종 : rest api 새로 만든 후, alert api를 이용하여 로그인 화면 분기
function setSessionId() {
    const url = '/api/main/getSessionId';

    fetch(url)
        .then(response => response.json())
        .then((response) => {
            loginId = response.data.id;

            if (loginId === null) {
                Swal.fire({
                    title: '로그인 후 이용 가능한 페이지입니다.',
                    text: '로그인 유형을 선택해주세요.',
                    icon: 'info',
                    allowOutsideClick: false, // 배경 클릭 금지
                    allowEscapeKey: false, // ESC 키 금지
                    showCancelButton: false,
                    showDenyButton: true,
                    confirmButtonText: '개인 회원',
                    denyButtonText: '기업 회원',
                    reverseButtons: false // 버튼 순서 반대로 (취소 오른쪽)
                }).then((result) => {
                    if (result.isConfirmed) {
                        location.href = '/';
                    } else if (result.isDenied) {
                        location.href = '/company/loginPage';
                    }
                });
            }
        });

}

// 공고 목록
function reloadReviewList() {
    // loading = true;

    const url = `/api/main/getRecruitList`;
    fetch(url)
        .then(response => response.json())
        .then(response => {
            // const reviewCount = document.getElementById('reviewCount');
            // reviewCount.innerText = response.data.searchCount;

            // const searchNoResult = document.getElementById('searchNoResult');
            // // 이거 없으면 페이지 무한으로 ++..
            // if (response.data.reviewList.length == 0) {
            //     hasMore = false;
            //     loading = false;
            //     searchNoResult.classList.remove('d-none');
            //     return;
            // }
            // searchNoResult.classList.add('d-none');
            const recruitCountDiv = document.getElementById('recruitCountDiv');
            recruitCountDiv.innerText = response.data.recruitCount + ' 건';

            const recruitListBox = document.getElementById('recruitListBox');
            const recruitListWrapperTemplate = document.querySelector('#recruitListTemplate .recruitListWrapper');

            for (const e of response.data.recruitList) {
                const recruitListWrapper = recruitListWrapperTemplate.cloneNode(true);

                const recruitHref = recruitListWrapper.querySelector('.recruitHref');
                recruitHref.href = `/recruitDetailPage?id=${e.id}`;

                const companyNameDiv = recruitListWrapper.querySelector('.companyNameDiv');
                companyNameDiv.innerText = e.companyName;

                const createdAtDiv = recruitListWrapper.querySelector('.createdAtDiv');
                createdAtDiv.innerText = changeCreatedAt(e.createdAt);

                const titleDiv = recruitListWrapper.querySelector('.titleDiv');
                titleDiv.innerText = e.title;

                const startTimeDiv = recruitListWrapper.querySelector('.startTimeDiv');
                startTimeDiv.innerText = changeTime(e.startTime);

                const endTimeDiv = recruitListWrapper.querySelector('.endTimeDiv');
                endTimeDiv.innerText = changeTime(e.endTime);

                const companyRoadAddressDiv = recruitListWrapper.querySelector('.companyRoadAddressDiv');
                companyRoadAddressDiv.innerText = changeAddress(e.companyRoadAddress);

                const hourlyRateDiv = recruitListWrapper.querySelector('.hourlyRateDiv');
                hourlyRateDiv.innerText = e.hourlyRate;

                recruitListBox.appendChild(recruitListWrapper);

            }
            // pageNumber ++;
            // loading = false;
        });
}

// 시간 초 제거
function changeTime(params) {
    return params.substring(0, 5);
}

// 등록 시간
function changeCreatedAt(params) {
    const date = new Date(params);
    const now = new Date();
    const diffMs = now - date; // 두 날짜의 차이 (밀리초 단위)

    const diffMinutes = Math.floor(diffMs / (1000 * 60)); // 1분 = 60,000ms
    const diffHours = Math.floor(diffMs / (1000 * 60 * 60)); // 1분 * 60 = 1시간
    const diffDays = Math.floor(diffMs / (1000 * 60 * 60 * 24)); // 1시간 * 24 = 1일

    if (diffMinutes < 60) {
        return `${diffMinutes}분 전`;
    } else if (diffHours < 24) {
        return `${diffHours}시간 전`;
    } else {
        return `${diffDays}일 전`;
    }
}

// 주소 '00구'
function changeAddress(params) {
    const clean = params.trim(); // 문자열 앞뒤 공백 제거
    const regex = /^(.+?(?:시|도|광역시|특별시)?\s.+?구)/;
    const match = clean.match(regex);
    return match ? match[1] : clean;
}

window.addEventListener("DOMContentLoaded", () => {
    setSessionId();
    reloadReviewList();
});

// 뒤로가기 등으로 돌아왔을 때 재확인
window.addEventListener('pageshow', function (event) {
    if (event.persisted || window.performance?.navigation?.type === 2) {
        setSessionId();
    }
});