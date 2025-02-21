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
                inputAccount.value = '';
                findIdFailInfo.classList.remove('d-none');
            } else {
                findIdFailInfo.classList.add('d-none');
                findAccountPwById.classList.add('d-none');
                findPasswordQuestion.classList.remove('d-none');
                question.value = response.data.findAccountPw.question;
                userId = response.data.findAccountPw.id;
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
        // response.data.isAnswer 이 true 이면 비밀번호 찾기 답변 맞은거임....
        // 비밀번호 새로 입력하는 창 만들어서 보이게 하고 update?
        });
}