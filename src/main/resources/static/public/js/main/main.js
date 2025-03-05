let serviceTeamMemberId = null;

// 아이디 세팅
// function setSessionMemberId() {
//     const url = '/api/service/getSessionServiceId';
//
//     fetch(url)
//         .then(response => response.json())
//         .then((response) => {
//             serviceTeamMemberId = response.data.id;
//             calendar();
//         });
// }

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
                initialView: 'dayGridMonth',  // 달력을 월 단위로 표시
                height: '83vh', // 캘린더가 부모 요소의 높이에 맞도록 설정
                contentHeight: '100vh', // 콘텐츠 높이 자동 조정
                expandRows: true, // 모든 행의 높이를 동일하게 맞춤
                events: events,
                displayEventTime: false,  // 시간 표시
                locale: "ko" // 달력 한글로
            });

            calendar.render(); // 달력 렌더링
        // });
}

window.addEventListener("DOMContentLoaded", () => {
    // setSessionMemberId();
    calendar();
});