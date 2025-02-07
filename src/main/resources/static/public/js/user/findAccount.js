// 아이디 찾기
function findAccountId() {
    const inputName = document.getElementById('inputName');
    const name = inputName.value;

    const inputPhone = document.getElementById('inputPhone')
    const phone = inputPhone.value;

    const findIdFailInfo = document.getElementById("findIdFailInfo");

    const url = `/api/user/getFindAccountId?name=${name}&phone=${phone}`;
    fetch(url)
        .then(response => response.json())
        .then((response) => {
            // console.log(response.data.findAccountId);
            if (response.data.findAccountId == null) {
                inputName.value = '';
                inputPhone.value = '';

                findIdFailInfo.classList.remove('d-none');
            } else {
                findIdFailInfo.classList.add('d-none');

                const findAccountId = document.getElementById('findAccountId');
                findAccountId.classList.toggle('d-none');

                const findAccountIdDone = document.getElementById('findAccountIdDone');
                findAccountIdDone.classList.toggle('d-none');


                const userNameSpan = document.getElementById('userNameSpan');
                userNameSpan.innerText = name;

                const userAccountIdSpan = document.getElementById('userAccountIdSpan');
                userAccountIdSpan.innerText = response.data.findAccountId;
            }
        });
}