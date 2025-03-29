let userId;

function accountCheck() {
    // console.log('아이디 체크');
    const inputAccount = document.getElementById('inputAccount');
    const accountValue = inputAccount.value;

    const findAccountPwById = document.getElementById('findAccountPwById');
    const findPasswordQuestion = document.getElementById('findPasswordQuestion');

    const question = document.getElementById('question');

    const url = `/api/user/getFindAccountPw?accountId=${accountValue}`;
    fetch(url)
        .then(response => response.json())
        .then((response) => {
            const findIdFailInfo = document.getElementById('findIdFailInfo');

            if (response.data.findAccountPw === null) {
                // 아이디가 없는 경우
                inputAccount.value = '';
                findIdFailInfo.classList.remove('d-none');
            } else {
                findIdFailInfo.classList.add('d-none');
                findAccountPwById.classList.add('d-none');
                findPasswordQuestion.classList.remove('d-none');
                question.value = response.data.findAccountPw.question;
                userId = response.data.findAccountPw.id;

                const defaultInfo = document.getElementById('defaultInfo');
                defaultInfo.classList.add('d-none');

                const answerInfo = document.getElementById('answerInfo');
                answerInfo.classList.remove('d-none');

            }

        });
}

// 답변을 네트워크 트래픽에 공개하지 않기 위해..
function inputAnswer() {
    const inputPassWordAnswer = document.getElementById('inputPassWordAnswer');
    const answer = inputPassWordAnswer.value;
    // console.log(answer, 'userId : ' + userId);

    const url = `/api/user/isAnswer?answer=${answer}&userId=${userId}`;
    fetch(url)
        .then(response => response.json())
        .then((response) => {
            const pwAnswerFailInfo = document.getElementById('pwAnswerFailInfo');

            if (response.data.isAnswer === 'true' ) {
            //     비밀번호 답변이 정답인 경우
                pwAnswerFailInfo.classList.add('d-none');

                const passwordUpdate = document.getElementById('passwordUpdate');
                passwordUpdate.classList.remove('d-none');

                const findPasswordQuestion = document.getElementById('findPasswordQuestion');
                findPasswordQuestion.classList.add('d-none');

                const answerInfo = document.getElementById('answerInfo');
                answerInfo.classList.add('d-none');

                const changePasswordInfo = document.getElementById('changePasswordInfo');
                changePasswordInfo.classList.remove('d-none');
            } else {
            //     비밀번호 답변이 오답인 경우
                pwAnswerFailInfo.classList.remove('d-none');

                const inputPassWordAnswer = document.getElementById('inputPassWordAnswer');
                inputPassWordAnswer.value = '';
            }

        });
}

// 비밀번호 업데이트
function updatePassword() {
    const updatePasswordForm = document.getElementById('updatePasswordForm');
    const formData = new FormData(updatePasswordForm);
    formData.append('userId', userId);

    const newPasswordInput = document.getElementById('newPasswordInput');
    const checkPasswordInput = document.getElementById('checkPasswordInput');
    if (newPasswordInput.value !== checkPasswordInput.value) {
        const checkPasswordFailInfo = document.getElementById('checkPasswordFailInfo');
        checkPasswordFailInfo.classList.remove('d-none');
        newPasswordInput.value ='';
        checkPasswordInput.value = '';
        return;
    }

    const url = '/api/user/updatePassword'
    fetch(url, {
        method: 'POST',
        body: formData
    })
        .then((response) => response.json())
        .then((data) => {
            showMessage("비밀번호 변경이 완료되었습니다.");
            // console.log('비밀번호 수정 완료');
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
        window.location = '/user/login';
    }, 1500);
}