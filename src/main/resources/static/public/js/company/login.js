// 로그인
function login() {
    const account = document.getElementById('accountInput');
    const accountId = account.value;

    const password = document.getElementById('passwordInput')
    const accountPw = password.value;

    const loginFailInfo = document.getElementById("loginFailInfo");

    const url = `/api/company/getCompanyInfo?accountId=${accountId}&accountPw=${accountPw}`;
    fetch(url)
        .then(response => response.json())
        .then((response) => {
            if (response.data.loginCompanyInfo == null) {
                account.value = '';
                password.value = '';
                loginFailInfo.classList.remove('d-none');
            } else {
                loginFailInfo.classList.add('d-none');

                const loginForm = document.getElementById('loginForm');
                loginForm.submit();
            }
        });
}