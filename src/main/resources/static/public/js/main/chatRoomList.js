let loginId = null;
let loginRole = null;

function setSessionId() {
    if (loginId !== null) return;

    const url = '/api/main/getSessionId';

    fetch(url)
        .then(response => response.json())
        .then((response) => {
            loginId = response.data.id;
            loginRole = response.data.role;

            if (loginId === null) {
                Swal.fire({
                    title: '로그인 후 이용 가능한 페이지입니다.',
                    // text: '로그인 유형을 선택해주세요.',
                    icon: 'info',
                    allowOutsideClick: false, // 배경 클릭 금지
                    allowEscapeKey: false, // ESC 키 금지
                    showCancelButton: false,
                    showDenyButton: false,
                    confirmButtonText: '로그인',
                    // denyButtonText: '기업 회원',
                    reverseButtons: false // 버튼 순서 반대로 (취소 오른쪽)
                }).then((result) => {
                    if (result.isConfirmed) {
                        location.href = '/';
                    } // else if (result.isDenied) {
                    //     location.href = '/company/loginPage';
                    // }
                });
            }

            reloadChatList();
        });
}

// 공고 목록
function reloadChatList() {
    // loading = true;

    const url = `/api/main/getChatRoomListByUser?userId=${loginId}`;
    fetch(url)
        .then(response => response.json())
        .then(response => {
            const chatListBox = document.getElementById('chatListBox');
            const chatListWrapperTemplate = document.querySelector('#chatListTemplate .chatListWrapper');

            // 초기화
            chatListBox.innerText = '';

            for (const e of response.data.chatList) {
                const chatListWrapper = chatListWrapperTemplate.cloneNode(true);

                // const profileImgDiv = chatListWrapper.querySelector('.profileImgDiv');
                // profileImgDiv.innerText = e.companyProfile;

                const chatRoomHref = chatListWrapper.querySelector('.chatRoomHref');
                chatRoomHref.href = `/chatDetailPage?id=${e.roomId}`;

                const titleDiv = chatListWrapper.querySelector('.titleDiv');
                titleDiv.innerText = e.title;

                // const createdAtDiv = chatListWrapper.querySelector('.createdAtDiv');
                // createdAtDiv.innerText = changeCreatedAt(e.createdAt);

                // const lastChatDiv = chatListWrapper.querySelector('.lastChatDiv');
                // lastChatDiv.innerText = e.title;

                // const chatNumSpan = chatListWrapper.querySelector('.chatNumSpan');
                // chatNumSpan.innerText = e.title;

                chatListBox.appendChild(chatListWrapper);

            }
        });
}

// 등록 시간
function changeCreatedAt(params) {
    const date = new Date(params);
    const now = new Date();
    const diffMs = now - date; // 두 날짜의 차이 (밀리초 단위)

    const diffMinutes = Math.floor(diffMs / (1000 * 60)); // 1분 = 60,000ms
    const diffHours = Math.floor(diffMs / (1000 * 60 * 60)); // 1분 * 60 = 1시간
    const diffDays = Math.floor(diffMs / (1000 * 60 * 60 * 24)); // 1시간 * 24 = 1일

    const month = date.getMonth() + 1;
    const day = date.getDate();

    if (diffMinutes < 60) {
        return `${diffMinutes}분 전`;
    } else if (diffHours < 24) {
        return `${diffHours}시간 전`;
    } else if (diffDays <10) {
        return `${diffDays}일 전`;
    } else {
        return `${month}/${day}`;
    }
}

window.addEventListener("DOMContentLoaded", () => {
    setSessionId();
});

// 뒤로가기 등으로 돌아왔을 때 재확인
window.addEventListener('pageshow', function (event) {
    if (event.persisted || window.performance?.navigation?.type === 2) {
        setSessionId();
    }
});