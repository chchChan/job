let loginId = null;

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

function chatExit() {
    console.log('체팅 종료 할거임?');
}
// window.addEventListener("DOMContentLoaded", () => {
//     setSessionId();
// });
//
// // 뒤로가기 등으로 돌아왔을 때 재확인
// window.addEventListener('pageshow', function (event) {
//     if (event.persisted || window.performance?.navigation?.type === 2) {
//         setSessionId();
//     }
// });