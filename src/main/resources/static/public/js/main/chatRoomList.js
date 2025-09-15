let loginId = null;
let loginRole = null;

import {formatDate3} from '../common/common.js';

let socket;

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
            socketStart();
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

                const lastChatDiv = chatListWrapper.querySelector('.lastChatDiv');
                lastChatDiv.innerText = e.message;

                const chatNumSpan = chatListWrapper.querySelector('.chatNumSpan');

                if (e.unreadCount === 0) {
                    chatNumSpan.classList.add('d-none');
                    chatNumSpan.innerText = e.unreadCount;
                } else {
                    chatNumSpan.classList.remove('d-none');
                    chatNumSpan.innerText = e.unreadCount;
                }

                const createdAtDiv = chatListWrapper.querySelector('.createdAtDiv');
                createdAtDiv.innerText = formatDate3(e.createdAt);

                chatListBox.appendChild(chatListWrapper);

            }
        });
}

// 웹소켓 오픈
function socketStart() {
    socket = new WebSocket(`ws://localhost:8888/chatList?id=${loginId}`);

    socket.onopen = () => {
        console.log('웹소켓 서버 연결');
    };

    socket.onmessage = (event) => {
        // const chatMessage = JSON.parse(event.data);
        // appendChat(chatMessage);
    };
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

// 페이지 떠날 때 소켓 종료
window.addEventListener('beforeunload', () => {
    if (socket && socket.readyState === WebSocket.OPEN) {
        socket.close();
    }
});