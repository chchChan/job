let companyId = null;

// 아이디 세팅
function setSessionCompanyId() {
    const url = '/api/company/getSessionId';

    fetch(url)
        .then(response => response.json())
        .then((response) => {
            companyId = response.data.id;

            if (companyId === null) {
                Swal.fire({
                    title: '로그인 후 이용 가능한 페이지입니다.',
                    icon: 'info',
                    showCancelButton: false,
                    showDenyButton: false,
                    confirmButtonText: '로그인',
                    reverseButtons: false // 버튼 순서 반대로 (취소 오른쪽)
                }).then((result) => {
                    if (result.isConfirmed) {
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
    // setSessionCompanyId();
});
