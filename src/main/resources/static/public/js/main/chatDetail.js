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
                updateIsReading();
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

// 채팅 내역 출력
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

// 채팅 ui 붙여넣기
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
    if (socket && socket.readyState === WebSocket.OPEN) {
        socket.close(); // 서버에 연결 종료 알림
        console.log('웹소켓 종료 요청 보냄');
    }

//     방 비활성 로직..
//     방 목록 db에 비활성화한 역할 넣고
//     유저 -> 회사목록엔 남아있고 채팅입장시 비활성화된 채팅이라고 안내?
//     회사 -> 유저목록엔 남아있고 채팅입장시 안내
//     각각 안내를 받은 상황이면 나간후 역할을 both로 변경.. 둘 목록에 다 안나오게..
//
}

window.chatExit = chatExit;

function updateIsReading() {
    // update url 만들기
    const url = `/api/main/getUpdateIsReadingByRoomId?id=${chatRoomId}`;
    fetch(url)
        .then(response => response.json())
        .then(response => {
            console.log('읽음처리~')
        });
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