let loginId = null;
let loginRole = null;

const params = new URLSearchParams(location.search);
const chatRoomId = params.get('id');

// 서버와 웹소켓 연결
let socket;

import {formatTime} from '../common/common.js';

function setSessionId() {
    const url = '/api/main/getSessionId';

    fetch(url)
        .then(response => response.json())
        .then((response) => {
            loginId = response.data.id;
            loginRole = response.data.role;

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
            } else {
                socketStart();
                reloadChat();
            }
        });

}

function socketStart() {
    // console.log('아이디', loginId);
    // console.log('역할', loginRole);
    // console.log('방번호', chatRoomId);

    // 선언 직후 곧바로 핸들러 지정해야 안전
    socket = new WebSocket(`ws://localhost:8888/chatRoom?id=${chatRoomId}`);

    socket.onopen = () => {
        console.log('서버에 연결됨 ✅');
    };

    socket.onmessage = (event) => {
        const chatMessage = JSON.parse(event.data);
        appendChat(chatMessage);
    };

    // socket.onmessage = (event) => {
    //     const chatMessage = JSON.parse(event.data);
    //     if(chatMessage.senderId !== loginId || chatMessage.senderType !== loginRole) {
    //         appendChat(chatMessage);
    //     }
    // };

}

function sendMessage() {
    const input = document.getElementById('messageInput');
    const message = input.value;

    socket.send(JSON.stringify({
        // type: "TALK",
        roomId: chatRoomId,
        senderType: loginRole,
        senderId: loginId,
        message: message
    }));

    input.value = "";
}

// 모듈 안에서 전역으로 붙이기 .. 이거 해줘야 onclick 가능
window.sendMessage = sendMessage;

function reloadChat() {
    const url = `/api/main/getChatListByRoomId?id=${chatRoomId}`;
    fetch(url)
        .then(response => response.json())
        .then(response => {
            const chatBox = document.getElementById('chatBox');
            // const chatWrapperTemplate = document.querySelector('#chatTemplate .chatWrapper');

            // 초기화
            chatBox.innerText = '';

            for (const e of response.data.chatList) {
                appendChat(e);
            }
        });
}

function appendChat(e) {
    const chatWrapperTemplate = document.querySelector('#chatTemplate .chatWrapper');
    const chatWrapper = chatWrapperTemplate.cloneNode(true);

    if (e.senderId === loginId && e.senderType === loginRole) {
        // 내가 보낸 채팅
        chatWrapper.querySelector('.yourChatDiv').remove();
        chatWrapper.querySelector('.myChat').innerText = e.message;
        chatWrapper.querySelector('.myTime').innerText = formatTime(e.createdAt);
    } else {
        // 상대방 채팅
        chatWrapper.querySelector('.myChatDiv').remove();
        chatWrapper.querySelector('.yourChat').innerText = e.message;
        chatWrapper.querySelector('.yourTime').innerText = formatTime(e.createdAt);
    }

    document.getElementById('chatBox').appendChild(chatWrapper);
}

function chatExit() {
    console.log('체팅 종료 할거임?');
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