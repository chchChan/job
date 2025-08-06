function initMapWithAddress(address) {
    const geocoder = new kakao.maps.services.Geocoder();

    geocoder.addressSearch(address, function (result, status) {
        if (status === kakao.maps.services.Status.OK) {
            const coords = new kakao.maps.LatLng(result[0].y, result[0].x);

            // 지도 생성
            const mapContainer = document.getElementById('map');
            const mapOption = {
                center: coords,
                level: 3
            };
            const map = new kakao.maps.Map(mapContainer, mapOption);

            // 마커 생성
            const marker = new kakao.maps.Marker({
                position: coords
            });
            marker.setMap(map);
        } else {
            console.error("주소 변환 실패:", status);
        }
    });
}

document.addEventListener("DOMContentLoaded", () => {
    const addressDiv = document.getElementById('addressDiv');
    const address = addressDiv.innerText.trim();

    if (address) {
        initMapWithAddress(address);
    }
});