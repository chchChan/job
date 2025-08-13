let loginId = null;
let loginRole = null;

const params = new URLSearchParams(location.search);
const postId = params.get('id');

function setSessionId() {
    const url = '/api/main/getSessionId';

    fetch(url)
        .then(response => response.json())
        .then((response) => {
            loginId = response.data.id;
            // 무조건 user가 나오긴 하는데.. 혹시모르니
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
            }
        });

}

function applyButton() {
    Swal.fire({
        title: '공고 지원',
        text: '해당 공고에 지원 하시겠습니까?',
        icon: 'question',
        allowOutsideClick: false,
        allowEscapeKey: false,
        showCancelButton: true,
        confirmButtonText: '지원하기',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {
            checkRoom(loginId, postId).then(chatRoomCount => {
                console.log('여기', chatRoomCount);
                if (chatRoomCount === 0) {
            //     게시글id와 유저id를 select count해서 결과가 0이면 방 생성
            //     결과가 1이며 그 방으로 연결
                    console.log('방 생성 로직 실행');
                } else {
                    console.log('방 연결 로직 실행');
                }
            });
        }
    });
//     2. 결과가 0이면 insert 쿼리 후 채팅방 연결
//     백단에서 insert 후 select 로 roomid 리턴값으로 넘겨서 가져오기
//     * 중복 생성 방지를 위해 db에 제약조건 걸기
//     ex) ALTER TABLE chat_room
//         ADD CONSTRAINT unique_chat_room_key UNIQUE (user_info_id, recruit_board_id);
//     jpa create 로직 할 때 try-catch문 사용
}

function checkRoom(loginId, postId) {
    const url = `/api/main/checkRoom?loginId=${loginId}&postId=${postId}`;

    return fetch(url)
        .then(response => response.json())
        .then(response => response.data.count);
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