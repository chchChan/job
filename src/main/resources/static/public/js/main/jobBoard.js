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

// 확인창
function showMessage(text) {
    const showModal = bootstrap.Modal.getOrCreateInstance('#showModal');
    showModal.show();

    const testAlert = document.getElementById('testAlert')
    testAlert.innerText = text;

    setTimeout(() => {
        showModal.hide();
    }, 1500);
}

window.addEventListener("DOMContentLoaded", () => {
    setSessionId();
});
