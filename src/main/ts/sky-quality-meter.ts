class Measuring {

  location: string;
  date: Date;
  iso: number;
  aperture: number;
  exposureTime: number;

  constructor(metering: any) {
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

  format = new Intl.DateTimeFormat('de-DE', {
    weekday: "long",
    year: "numeric",
    month: "long",
    day: "numeric",
    hour: "numeric",
    minute: "numeric"
  });

  constructor() {
    super();
  }

  connectedCallback() {

    this.insertAdjacentHTML("afterbegin", `<table class="table"><thead>
  <tr>
    <th>Ort</th>
    <th>Datum</th>
    <th class="text-center">ISO</th>
    <th class="text-center">Blende <astro-latex>[f]</astro-latex></th>
    <th class="text-center">Zeit <astro-latex>[s]</astro-latex></th>
    <th class="text-center">Helligkeit <astro-latex>[Mag/arcsec]</astro-latex></th>
  </tr></thead><tbody></tbody></table>`);

    this.getMeasurings().then(
        measurings => {
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
        }
    );
  }

  getMeasurings(): Promise<Measuring[]> {
    return window.fetch("data.json")
        .then(response => response.json())
        .then((json: any[]) => json.map((metering: any) => new Measuring(metering)))
  }

}

document.addEventListener("DOMContentLoaded", function (event) {
  window.customElements.define("sky-quality-meter", SkyQualityMeter);
});
