// 질문 선택하면 답변창 활성화
function checkPwQuestion () {
    const passWordQuestion = document.getElementById('inputPassWordQuestion')
    const passWordAnswerDiv = document.getElementById('divPassWordAnswer')

    // input태그의 value는 항상 문자열이므로 문자열로 비교해야함
    if (passWordQuestion.value !== '0') {
        passWordAnswerDiv.classList.remove('d-none');
    }
}

// 회원가입 빈 칸 확인
function checkUserInfo() {
    const joinUser = document.getElementById("joinUser");
    const formData = new FormData(joinUser);

    const account = document.getElementById('inputAccount');
    if (account.value === '') {
        alert('아이디를 입력해주세요.')
        account.focus();
        return;
    }

    const password = document.getElementById('inputPassword');
    if (password.value === '') {
        alert('비밀번호를 입력해주세요.')
        password.focus();
        return;
    }

    const name = document.getElementById('inputName');
    if (name.value === '') {
        alert('이름을 입력해주세요.')
        name.focus();
        return;
    }

    const age = document.getElementById('inputAge');
    if (age.value === '') {
        alert('나이를 입력해주세요.')
        age.focus();
        return;
    }

    const phone = document.getElementById('inputPhone');
    if (phone.value === '') {
        alert('휴대전화번호를 입력해주세요.')
        phone.focus();
        return;
    }

    const passwordAnswer = document.getElementById('inputPassWordAnswer');
    if (passwordAnswer.value === '') {
        alert('비밀번호 찾기 답변을 입력해주세요.')
        passwordAnswer.focus();
        return;
    }

    const url = '/api/user/createUserInfo'
    fetch(url, {
        method: 'POST',
        body: formData
    })
        .then((response) => response.json())
        .then((data) => {
            // 현재 페이지의 기록을 남기지 않고 리다이렉트
            window.location.replace('/user/joinSuccess');
        });
}

// 아이디 중복 검사
function checkAccount() {
    const account = document.getElementById('inputAccount');
    const accountId = account.value

    const url = `/api/user/getAccountId?accountId=${accountId}`;
    fetch(url)
        .then(response => response.json())
        .then((response) => {
            // 0이 아니면 중복된 아이디가 있는 경우
            if(response.data.countByAccountId !== 0) {
                account.classList.add('text-danger');
                account.value = '중복된 아이디 입니다.';

                // 클릭시 값이 지워지게
                account.addEventListener('click', function() {
                    account.value = '';
                    account.classList.remove('text-danger');
                });
            }
        });
}

window.addEventListener("DOMContentLoaded", () => {
});