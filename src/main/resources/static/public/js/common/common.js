
// yyyy/mm/dd
export function formatDate(createdAt) {
    const date = new Date(createdAt);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');

    return `${year}/${month}/${day}`;
}


// ~분 전, ~시간 전, ~일 전, mm/dd
function formatDate2(params) {
    const date = new Date(params);
    const now = new Date();
    const diffMs = now - date; // 두 날짜의 차이 (밀리초 단위)

    const diffMinutes = Math.floor(diffMs / (1000 * 60)); // 1분 = 60,000ms
    const diffHours = Math.floor(diffMs / (1000 * 60 * 60)); // 1분 * 60 = 1시간
    const diffDays = Math.floor(diffMs / (1000 * 60 * 60 * 24)); // 1시간 * 24 = 1일

    const month = date.getMonth() + 1;
    const day = date.getDate();

    if (diffMinutes < 60) {
        return `${diffMinutes}분 전`;
    } else if (diffHours < 24) {
        return `${diffHours}시간 전`;
    } else if (diffDays <10) {
        return `${diffDays}일 전`;
    } else {
        return `${month}/${day}`;
    }
}

// 오전/오후 + 시:분 포맷
export function formatTime(createdAt) {
    const date = new Date(createdAt);
    let hours = date.getHours();
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const ampm = hours >= 12 ? '오후' : '오전';

    hours = hours % 12;
    if (hours === 0) hours = 12; // 0시는 12시로 표시

    return `${ampm} ${String(hours).padStart(2, '0')}:${minutes}`;
}