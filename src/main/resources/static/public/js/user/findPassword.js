function accountCheck() {
    console.log('아이디 체크');
    const inputAccount = document.getElementById('inputAccount');
    const accountValue = inputAccount.value;

    const url = `/api/user/getFindAccountPw?accountId=${accountValue}`;
    fetch(url)
        .then(response => response.json())
        .then((response) => {
            response.data.findAccountPw.answer ==

        });
}