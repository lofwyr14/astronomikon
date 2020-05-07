class Measuring {
    constructor(metering) {
        this.location = metering.location;
        this.date = new Date(metering.date);
        this.iso = metering.iso;
        this.aperture = metering.aperture;
        this.exposureTime = metering.exposureTime;
    }
    quality() {
        return (7.43 + 2.5 * Math.log(this.iso * this.exposureTime / this.aperture / this.aperture) / Math.log(10))
            .toFixed(1);
    }
}
class SkyQualityMeter extends HTMLElement {
    constructor() {
        super();
        this.format = new Intl.DateTimeFormat('de-DE', {
            weekday: "long",
            year: "numeric",
            month: "long",
            day: "numeric",
            hour: "numeric",
            minute: "numeric"
        });
        let quantity = document.getElementById("quantity");
        let iso = document.getElementById("iso");
        iso.addEventListener("keyup", (event) => { quantity.innerText = String(parseInt(aperture.value) + parseInt(iso.value)); });
        let aperture = document.getElementById("aperture");
        aperture.addEventListener("keyup", (event) => { quantity.innerText = String(parseInt(aperture.value) + parseInt(iso.value)); });
    }
    connectedCallback() {
        this.insertAdjacentHTML("afterbegin", `<table class="table"><thead>
  <tr>
    <th>Ort</th>
    <th>Datum</th>
    <th class="text-center">ISO</th>
    <th class="text-center">Blende <layout-latex>[f]</layout-latex></th>
    <th class="text-center">Zeit <layout-latex>[s]</layout-latex></th>
    <th class="text-center">Helligkeit <layout-latex>[Mag/arcsec]</layout-latex></th>
  </tr></thead><tbody></tbody></table>`);
        this.getMeasurings().then(measurings => {
            const tbody = this.querySelector("table>tbody");
            measurings.forEach(measuring => {
                const tr = document.createElement("tr");
                tbody.insertAdjacentElement("beforeend", tr);
                tr.insertAdjacentHTML("beforeend", "<td>" + measuring.location + "</td>");
                tr.insertAdjacentHTML("beforeend", "<td>" + this.format.format(measuring.date) + "</td>");
                tr.insertAdjacentHTML("beforeend", "<td>" + measuring.iso + "</td>");
                tr.insertAdjacentHTML("beforeend", "<td>" + measuring.aperture + "</td>");
                tr.insertAdjacentHTML("beforeend", "<td>" + measuring.exposureTime + "</td>");
                tr.insertAdjacentHTML("beforeend", "<td>" + measuring.quality() + "</td>");
            });
        });
    }
    getMeasurings() {
        return window.fetch("json/sky-quality-meter.json")
            .then(response => response.json())
            .then((json) => json.map((metering) => new Measuring(metering)));
    }
}
document.addEventListener("DOMContentLoaded", function (event) {
    window.customElements.define("sky-quality-meter", SkyQualityMeter);
});
