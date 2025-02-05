// 로그인
function login() {
    const account = document.getElementById('accountInput');
    const accountId = account.value;

    const password = document.getElementById('passwordInput')
    const accountPw = password.value;

    const url = `/api/user/getUserInfo?accountId=${accountId}&accountPw=${accountPw}`;
    fetch(url)
        .then(response => response.json())
        .then((response) => {
            // console.log(response.data.loginUserInfo);
            if (response.data.loginUserInfo == null) {
                account.value = '';
                password.value = '';

                const loginFailInfo = document.getElementById("loginFailInfo");
                loginFailInfo.classList.toggle('d-none');
            } else {
                const loginForm = document.getElementById('loginForm');
                loginForm.submit();
            }
        });
}