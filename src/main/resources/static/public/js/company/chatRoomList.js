let companyId = null;
let loginRole = null;

// 아이디 세팅
function setSessionCompanyId() {
    const url = '/api/company/getSessionId';

    fetch(url)
        .then(response => response.json())
        .then((response) => {
            companyId = response.data.id;
            loginRole = response.data.role;

            if (companyId === null) {
                Swal.fire({
                    title: '로그인 후 이용 가능한 페이지입니다.',
                    icon: 'info',
                    allowOutsideClick: false, // 배경 클릭 금지
                    allowEscapeKey: false, // ESC 키 금지
                    showCancelButton: false,
                    showDenyButton: false,
                    confirmButtonText: '기업 로그인',
                    reverseButtons: false // 버튼 순서 반대로 (취소 오른쪽)
                }).then((result) => {
                    if (result.isConfirmed) {
                        location.href = '/company/loginPage';
                    }
                });
            }

            reloadChatDetailList();
        });
}

// 공고 목록
function reloadChatDetailList() {
    // loading = true;

    const url = `/api/company/getChatRoomListByCompany?id=${companyId}`;
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

                const nameDiv = chatListWrapper.querySelector('.nameDiv');
                nameDiv.innerText = e.name;

                chatListBox.appendChild(chatListWrapper);

            }
        });
}

window.addEventListener("DOMContentLoaded", () => {
    setSessionCompanyId();
});

// 뒤로가기 등으로 돌아왔을 때 재확인
window.addEventListener('pageshow', function (event) {
    if (event.persisted || window.performance?.navigation?.type === 2) {
        setSessionCompanyId();
    }
});