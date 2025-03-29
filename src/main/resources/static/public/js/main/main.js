let userId = null;

// 아이디 세팅
function setSessionUserId() {
    const url = '/api/user/getSessionId';

    fetch(url)
        .then(response => response.json())
        .then((response) => {
            userId = response.data.id;
            calendar();

            if (userId === null) {
                if(confirm("로그인 후 이용 가능한 페이지입니다. 로그인 페이지로 이동하시겠습니까?")) {
                    location.href='/';
                }
            }
        });
}

// 풀캘린더 api
function calendar() {
    const calendarEl = document.getElementById('calendar');

    // const url = `/api/service/getMyAttendance?service_id=${serviceTeamMemberId}`
    // fetch(url)
    //     .then(response => response.json())
    //     .then(response => {
            // 데이터를 담을 빈 배열 생성
            const events = [];

            // for (const e of response.data.MyAttendance) {
            //     events.push({
            //         // 캘린더 형식에 맞게 넣어야함!! (title, start, end..)
            //         title: formatDateTime(e.created_at) + '  ' + e.status,
            //         start: e.created_at
            //     });
            // }

            // for (const e of response.data.MySchedule) {
            //     events.push({
            //         title: '주간 근무 스케줄',
            //         start: e.select_date,
            //         end: endDatePlus(e.end_date)
            //     });
            // }
            //
            // // 만들어둔 events 배열로 캘린더 생성
            const calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth',  // 월 단위로 표시
                height: '73vh', // 부모 요소의 높이에 맞도록 설정
                contentHeight: '100vh', // 콘텐츠 높이 조정
                expandRows: true, // 모든 행의 높이를 동일하게
                events: events,
                displayEventTime: false,  // 시간 표시
                locale: "ko" // 달력 한글로
            });

            calendar.render(); // 달력 렌더링
        // });
}

// 근무지 등록 모달
function openAddBusinessModal() {
    const payDaySelectBox = document.getElementById('payDaySelectBox');
    const paydayTemplate = document.querySelector('#paydayTemplate .paydayWrapper');

    const defaultInfo = payDaySelectBox.querySelector(".defaultInfo");

    // selectBox 초기화
    payDaySelectBox.innerHTML = "";

    // defaultInfo 옵션 다시 추가
    if (defaultInfo) {
        payDaySelectBox.appendChild(defaultInfo);
    }

    // 1일 ~ 31일
    for (var i = 1; i <= 31; i++) {
        const paydayTemplateWrapper = paydayTemplate.cloneNode(true); // true 안쪽까지 다 복사

        const paydayList = paydayTemplateWrapper.querySelector(".paydayList");
        // option에 value값 지정
        paydayList.value = i;
        paydayList.text = `${i}일`;

        payDaySelectBox.appendChild(paydayList);
    }
    // 모달 초기화
    const businessNameInput = document.getElementById('businessNameInput');
    businessNameInput.value = '';

    const hourlyRateInput = document.getElementById('hourlyRateInput');
    hourlyRateInput.value = '';

    const addBusinessModal = bootstrap.Modal.getOrCreateInstance('#addBusiness');
    addBusinessModal.show();
}

// 모달 닫기
function closeAddBusinessModal() {
    const addBusinessModal = bootstrap.Modal.getOrCreateInstance("#addBusiness");
    addBusinessModal.hide();
}

// 근무지 등록
function addBusinessSubmit() {
    const businessForm = document.getElementById('businessForm');

    // 유효성 검사
    const businessNameInput = document.getElementById('businessNameInput');
    if (businessNameInput.value === '') {
        alert('근무 장소를 등록해 주세요.');
        return;
    }

    const hourlyRateInput = document.getElementById('hourlyRateInput');
    if (hourlyRateInput.value === '') {
        alert('시급을 등록해 주세요.');
        return;
    } else {
        const hourlyRateHidden = document.getElementById('hourlyRateHidden')
        // 쉼표 제거 후 숫자로 변환
        hourlyRateHidden.value = hourlyRateInput.value.replace(/,/g, '');
    }

    const payDaySelectBox = document.getElementById('payDaySelectBox');
    const paydayValue = payDaySelectBox.options[payDaySelectBox.selectedIndex].value
    if (paydayValue === '0') {
        alert('월급일을 선택해 주세요.');
        return;
    }

    const formData = new FormData(businessForm);
    formData.append('userInfoId', userId);

    const url = '/api/main/createBusiness'
    fetch(url, {
        headers: {
            // 'Content-Type': 'multipart/form-data'
        },
        method: 'POST',
        cache: 'no-cache',
        body: formData
    })
        .then(response => response.json())
        .then((response) => {
            closeAddBusinessModal();
            showMessage(`'${businessNameInput.value}' 근무지 등록이 완료되었습니다.`);
            // location.reload();
        });
}

// 천 단위에 콤마
function formatNumber(input) {
    // 숫자만 남기기
    const value = input.value.replace(/[^0-9]/g, '');

    // 천 단위 쉼표 추가
    input.value = value.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
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
    setSessionUserId();
    // calendar();
});
