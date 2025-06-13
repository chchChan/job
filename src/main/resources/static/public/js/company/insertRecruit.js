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
                    allowOutsideClick: false, // 배경 클릭 금지
                    allowEscapeKey: false, // ESC 키 금지
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

// 유효성 검사 + 공고 등록
function checkRecruit() {
    const recruitForm = document.getElementById('recruitForm');
    const formData = new FormData(recruitForm);
    formData.append('companyInfoId', companyId);

    const title = document.getElementById('titleInput');
    if (title.value === '') {
        alert('제목을 입력해주세요.')
        title.focus();
        return;
    }

    const hourlyRate = document.getElementById('hourlyRateInput');
    if (hourlyRate.value === '') {
        alert('비밀번호를 입력해주세요.')
        hourlyRate.focus();
        return;
    }

    const period = document.getElementById('periodSelect');
    if (period.value === '') {
        alert('근무 기간을 선택해주세요.')
        period.focus();
        return;
    }

    const workday = document.getElementById('workdaySelect');
    if (workday.value === '') {
        alert('근무 요일을 선택해주세요.')
        workday.focus();
        return;
    }

    const startTime = document.getElementById('startTimeInput');
    if (startTime.value === '') {
        alert('출근 시간을 입력해주세요.')
        startTime.focus();
        return;
    }

    const endTime = document.getElementById('endTimeInput');
    if (endTime.value === '') {
        alert('퇴근 시간을 입력해주세요.')
        endTime.focus();
        return;
    }

    const text = document.getElementById('textInput');
    if (text.value === '') {
        alert('상세 내용을 입력해주세요.')
        text.focus();
        return;
    }

    const url = '/api/company/createRecruit'
    fetch(url, {
        method: 'POST',
        body: formData
    })
        .then((response) => response.json())
        .then((data) => {
            Swal.fire({
                title: '공고 등록이 완료되었습니다.',
                icon: 'success',
                timer: 1500,
                showConfirmButton: false
            }).then(() => {
                window.location.replace('/jobBoardPage');
            });
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
    setSessionCompanyId();
});
