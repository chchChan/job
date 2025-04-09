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
                alert("로그인 후 이용 가능한 페이지입니다.");
                location.href = '/';
            }
        });
}

// 풀캘린더 api
function calendar() {
    const calendarEl = document.getElementById('calendar');

    const url = `/api/main/getActualWorkList?userId=${userId}`
    fetch(url)
        .then(response => response.json())
        .then(response => {
            // 데이터를 담을 빈 배열 생성
            const events = [];

            let totalWage = 0;

            for (const e of response.data.actualWorkList) {
                events.push({
                    title: e.businessName,
                    start: e.workDay + 'T' + e.startTime,
                    end: e.workDay + 'T' + e.endTime
                });
                const wage = getTimeDiff(e.startTime, e.endTime) * e.hourlyRate;
                totalWage += wage;
            }

            const totalWageDiv = document.getElementById('totalWageDiv');
            totalWageDiv.innerText = totalWage.toLocaleString();

            // // 만들어둔 events 배열로 캘린더 생성
            const calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth',  // 월 단위로 표시
                height: '65vh', // 부모 요소의 높이에 맞도록 설정
                contentHeight: '100vh', // 콘텐츠 높이 조정
                expandRows: true, // 모든 행의 높이를 동일하게
                fixedWeekCount: false, // 다음 달 날짜 안보이게
                events: events,
                displayEventTime: false,  // 시간 표시
                locale: "ko", // 한글 달력

                // 특정 날짜의 이벤트 클릭시 해당 이벤트의 정보 출력
                // 이걸 console.log말고 모달 열어서 상세정보출력 및 수정 가능하게
                eventClick: function(info) {
                    console.log('클릭한 이벤트:', info.event);
                    console.log('제목:', info.event.title);
                    console.log('시작:', info.event.start);
                    console.log('종료:', info.event.end);
                }
            });

            calendar.render(); // 달력 렌더링
        });
}

// Time 문자열을 시간 객체로 변환
function getTimeDiff(start, end) {
    const today = new Date().toISOString().split('T')[0]; // 날짜는 아무거나
    const startTime = new Date(`${today}T${start}`);
    const endTime = new Date(`${today}T${end}`);

    const diffMs = endTime - startTime;
    const diffHours = diffMs / (1000 * 60 * 60); // 밀리초 → 시간

    return diffHours;
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

// 근무지 모달 닫기
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

// 근무시간 등록 모달
function openAddTimerModal() {
    const businessNameSelectBox = document.getElementById('businessNameSelectBox');
    const businessNameTemplate = document.querySelector('#businessNameTemplate .businessNameWrapper');

    const defaultInfo = businessNameSelectBox.querySelector(".defaultInfo");

    businessNameSelectBox.innerHTML = "";

    if (defaultInfo) {
        businessNameSelectBox.appendChild(defaultInfo);
    }

    const url = `/api/main/getBusinessList?userId=${userId}`;
    fetch(url)
        .then(response => response.json())
        .then((response) => {
            for (const e of response.data.businessList) {
                const businessNameWrapper = businessNameTemplate.cloneNode(true);

                const businessNameList = businessNameWrapper.querySelector(".businessNameList");
                businessNameList.value = e.id;
                businessNameList.text = e.businessName;

                businessNameSelectBox.appendChild(businessNameList);
            }
        });

    // 모달 초기화
    const dayInput = document.getElementById('dayInput');
    dayInput.value = '';

    const startTimeInput = document.getElementById('startTimeInput');
    startTimeInput.value = '';

    const endTimeInput = document.getElementById('endTimeInput');
    endTimeInput.value = '';

    const addTimerModal = bootstrap.Modal.getOrCreateInstance('#addTimer');
    addTimerModal.show();
}

// 근무시간 모달 닫기
function closeAddTimerModal() {
    const addTimerModal = bootstrap.Modal.getOrCreateInstance("#addTimer");
    addTimerModal.hide();
}

// 근무시간 등록
function addActualWorkSubmit() {
    const actualWorkForm = document.getElementById('actualWorkForm');

    // 유효성 검사
    const businessNameSelectBox = document.getElementById('businessNameSelectBox');
    if (businessNameSelectBox.value === '0') {
        alert('근무 장소를 선택해 주세요.');
        return;
    }

    const dayInput = document.getElementById('dayInput');
    if (dayInput.value === '') {
        alert('근무일을 선택해 주세요.');
        return;
    }

    const startTimeInput = document.getElementById('startTimeInput');
    if (startTimeInput.value === '') {
        alert('출근 시간을 등록해 주세요.');
        return;
    }

    const endTimeInput = document.getElementById('endTimeInput');
    if (endTimeInput.value === '') {
        alert('퇴근 시간을 등록해 주세요.');
        return;
    }

    const formData = new FormData(actualWorkForm);
    formData.append('userInfoId', userId);

    const url = '/api/main/createActualWork'
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
            closeAddTimerModal();
            showMessage('근무 시간 등록이 완료되었습니다.');
            calendar();
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
    setSessionUserId();
    // calendar();
});
