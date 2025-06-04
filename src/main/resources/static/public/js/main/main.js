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
                Swal.fire({
                    title: '로그인 후 이용 가능한 페이지입니다.',
                    icon: 'info',
                    showCancelButton: false,
                    showDenyButton: false,
                    confirmButtonText: '로그인',
                    reverseButtons: false // 버튼 순서 반대로 (취소 오른쪽)
                }).then((result) => {
                    if (result.isConfirmed) {
                        location.href = '/';
                    }
                });
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
            const events = [];

            let totalWage = 0;

            for (const e of response.data.actualWorkList) {
                events.push({
                    // 커스텀 필드는 자동으로 event.extendedProps
                    actualId : e.id,
                    hourlyRate : e.hourlyRate,
                    title: e.businessName,
                    start: e.workDay + 'T' + e.startTime,
                    end: e.workDay + 'T' + e.endTime
                });
            }

            const calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth',  // 월 단위로 표시
                height: '65vh', // 부모 요소의 높이에 맞도록 설정
                contentHeight: '100vh', // 콘텐츠 높이 조정
                expandRows: true, // 모든 행의 높이를 동일하게
                fixedWeekCount: false, // 다음 달 날짜 안보이게
                events: events,
                displayEventTime: false,  // 시간 표시
                locale: "ko", // 한글 달력

                // datesSet.. 달이 바뀔 때마다 호출
                datesSet: function(info) {
                    // 현재 화면에 표시되는 월과 연도를 추출
                    const currentMonth = info.view.currentStart.getMonth();
                    const currentYear = info.view.currentStart.getFullYear();

                    totalWage = 0;  // 수익 초기화

                    for (const e of response.data.actualWorkList) {
                        const workDate = new Date(e.workDay);
                        const workMonth = workDate.getMonth();
                        const workYear = workDate.getFullYear();

                        // 연도, 월이 일치하는 근무일의 수익만 계산
                        if (currentMonth === workMonth && currentYear === workYear) {
                            const wage = getTimeDiff(e.startTime, e.endTime) * e.hourlyRate;
                            totalWage += wage;
                        }
                    }
                    const totalWageDiv = document.getElementById('totalWageDiv');
                    totalWageDiv.innerText = totalWage.toLocaleString();
                },

                // 특정 날짜의 이벤트 클릭시 해당 이벤트의 정보 출력
                eventClick: function(info) {
                    openActualWorkInfoModal(info)
                }
            });

            calendar.render(); // 달력 렌더링
        });
}

// 근무시간 상세 모달
function openActualWorkInfoModal(info) {
    // 월, 일만 뽑기 위해서
    const startDate = info.event.start;
    const month = startDate.getMonth() + 1;
    const day = startDate.getDate();

    const actualWorkTitle = document.getElementById('actualWorkTitle');
    actualWorkTitle.innerText = `${month}월 ${day}일 - ${info.event.title}`;

    // 02:10 << 같이 만들기 위해
    const startHours = String(startDate.getHours()).padStart(2, '0');
    const startMinutes = String(startDate.getMinutes()).padStart(2, '0');
    const startTime = `${startHours}:${startMinutes}`;

    const endDate = info.event.end;
    const endHours = String(endDate.getHours()).padStart(2, '0');
    const endMinutes = String(endDate.getMinutes()).padStart(2, '0');
    const endTime = `${endHours}:${endMinutes}`;

    // 몇 시간 일했는지
    const diffMs = endDate - startDate;
    const diffHours = (diffMs / (1000 * 60 * 60)).toFixed(1); // 밀리초 → 시간

    const actualWorkTime = document.getElementById('actualWorkTime');
    actualWorkTime.innerText = `${startTime} ~ ${endTime}  (총 ${diffHours}시간)`;

    const hourlyRate = info.event.extendedProps.hourlyRate;
    const actualWorkHourlyRate = document.getElementById('actualWorkHourlyRate');
    actualWorkHourlyRate.innerText = `${hourlyRate.toLocaleString()} 원`;

    const actualWorkDailyRate = document.getElementById('actualWorkDailyRate');
    actualWorkDailyRate.innerText = `${(hourlyRate * diffHours).toLocaleString()}`;

    // 수정, 삭제를 위한 id값
    const actualWorkId = info.event.extendedProps.actualId;

    const actualWorkUpdate = document.getElementById('actualWorkUpdate');
    // 수정 화살표 함수
    actualWorkUpdate.onclick = () => upDateActualWork(actualWorkId);

    const actualWorkDelete = document.getElementById('actualWorkDelete');
    actualWorkDelete.onclick = () => deleteActualWork(actualWorkId);

    const actualWorkModal = bootstrap.Modal.getOrCreateInstance('#actualWorkModal');
    actualWorkModal.show();
}

// 근무시간 수정창
function upDateActualWork(actualWorkId) {
    if (!confirm('근무시간을 수정하시겠습니까?')) {
        // 취소 버튼
        return;
    } else {
        // 확인 버튼
        const timeDiv = document.getElementById('timeDiv');
        timeDiv.classList.add('d-none');

        const timeUpdateDiv = document.getElementById('timeUpdateDiv');
        timeUpdateDiv.classList.remove('d-none');

        const actualWorkUpdateButton = document.getElementById('actualWorkUpdateButton');
        actualWorkUpdateButton.classList.remove('d-none');

        const actualWorkIdInput = document.getElementById('actualWorkIdInput');
        actualWorkIdInput.value = actualWorkId;

        // 초기화
        const startTimeUpdate = document.getElementById('startTimeUpdate');
        startTimeUpdate.value = '';

        const endTimeUpdate = document.getElementById('endTimeUpdate');
        endTimeUpdate.value = '';
    }
}

// 근무시간 삭제창
function deleteActualWork(actualWorkId) {
    if (!confirm('근무시간을 삭제하시겠습니까?')) {
        return;
    } else {
        const url = `/api/main/deleteActualWork?actualWorkId=${actualWorkId}`;
        fetch(url)
            .then(response => response.json())
            .then(response => {
                closeActualWorkInfoModal();
                showMessage('근무시간 삭제가 완료되었습니다.');
                calendar();
            });
    }
}

// 근무시간 수정 저장
function updateActualWorkTime() {
    const actualWorkIdInput = document.getElementById('actualWorkIdInput');
    const actualWorkId = actualWorkIdInput.value;

    const actualWorkInfoForm = document.getElementById('actualWorkInfoForm');

    // 유효성 검사
    const startTimeUpdate = document.getElementById('startTimeUpdate');
    if (startTimeUpdate.value === '') {
        alert('출근시간을 입력해 주세요.');
        return;
    }

    const endTimeUpdate = document.getElementById('endTimeUpdate');
    if (endTimeUpdate.value === '') {
        alert('퇴근시간을 입력해 주세요.');
        return;
    }

    const formData = new FormData(actualWorkInfoForm);

    const url = `/api/main/updateActualWork?id=${actualWorkId}`
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
            closeActualWorkInfoModal();
            showMessage('근무시간 수정이 완료되었습니다.');
            calendar();
        });
}

// 근무시간 상세 모달 닫기
function closeActualWorkInfoModal() {
    // 닫기 버튼을 눌러야지만 초기화가 됨!!
    // const timeDiv = document.getElementById('timeDiv');
    // timeDiv.classList.remove('d-none');
    //
    // const timeUpdateDiv = document.getElementById('timeUpdateDiv');
    // timeUpdateDiv.classList.add('d-none');
    //
    // const actualWorkUpdateButton = document.getElementById('actualWorkUpdateButton');
    // actualWorkUpdateButton.classList.add('d-none');

    const actualWorkModal = bootstrap.Modal.getOrCreateInstance('#actualWorkModal');
    actualWorkModal.hide();
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

// 근무시간 상세창 초기화 (백드롭까지 커버)
const actualWorkModalEl = document.getElementById('actualWorkModal');

actualWorkModalEl.addEventListener('hidden.bs.modal', () => {
    // d-none 초기화
    document.getElementById('timeDiv').classList.remove('d-none');
    document.getElementById('timeUpdateDiv').classList.add('d-none');
    document.getElementById('actualWorkUpdateButton').classList.add('d-none');
});

window.addEventListener("DOMContentLoaded", () => {
    setSessionUserId();
    // calendar();
});
