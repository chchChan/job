// 회원가입 유효성
function checkCompanyInfo() {
    const joinCompany = document.getElementById('joinCompany');
    const formData = new FormData(joinCompany);

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
        alert('회사명을 입력해주세요.')
        name.focus();
        return;
    }

    const contact = document.getElementById('inputContact');
    if (contact.value === '') {
        alert('전화번호를 입력해주세요.')
        contact.focus();
        return;
    }

    const postcode = document.getElementById('inputPostcode');
    if (postcode.value === '') {
        alert('우편번호를 입력해주세요.')
        postcode.focus();
        return;
    }

    const roadAddress = document.getElementById('inputRoadAddress');
    if (roadAddress.value === '') {
        alert('도로명주소를 입력해주세요.')
        roadAddress.focus();
        return;
    }

    const detailAddress = document.getElementById('inputDetailAddress');
    if (detailAddress.value === '') {
        alert('상세주소를 입력해주세요.')
        detailAddress.focus();
        return;
    }

    const url = '/api/company/createCompanyInfo'
    fetch(url, {
        method: 'POST',
        body: formData
    })
        .then((response) => response.json())
        .then((data) => {
            window.location.replace('/company/joinSuccessPage');
        });
}

// 아이디 중복 검사
function checkAccount() {
    const account = document.getElementById('inputAccount');
    const accountId = account.value;

    const url = `/api/company/getAccountId?accountId=${accountId}`;
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

// 다음 postcode service api
function findAddressDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 선택된 주소 값 처리
            const postCode = document.getElementById('inputPostcode');
            postCode.value = data.zonecode; // zonecode : 우편번호

            const roadAddress = document.getElementById('inputRoadAddress');
            roadAddress.value = data.roadAddress; // roadAddress : 도로명 주소

            const detailAddress = document.getElementById('inputDetailAddress');
            detailAddress.focus();
        }
    }).open();
}

window.addEventListener("DOMContentLoaded", () => {
});