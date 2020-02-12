class Metering {
  location;
  date;
  iso;
  aperture;
  exposureTime;

  constructor(location, date, iso, aperture, exposureTime) {
    this.location = location;
    this.date = date;
    this.iso = iso;
    this.aperture = aperture;
    this.exposureTime = exposureTime;
  }

  quality() {
    return 7.43 + 2.5 * Math.log(this.iso * this.exposureTime / this.aperture / this.aperture) / Math.log(10);
  }
}

class SkyQualityMeter extends HTMLElement {

  data = [
    new Metering("Las Tricias, La Palma, Spanien", moment("2019-04-07T01:00"), 3600, 1.8, 30),
    new Metering("Las Tricias, La Palma, Spanien", moment("2019-04-03T01:30"), 9000, 1.8, 30),
    new Metering("Las Tricias, La Palma, Spanien", moment("2018-10-07T04:04"), 4000, 1.4, 30),
    new Metering("Llano del Jable, La Palma, Spanien", moment("2018-10-03T00:03"), 12800, 2.4, 30),
    new Metering("Llano del Jable, La Palma, Spanien", moment("2018-10-03T00:03"), 12800, 2.4, 30),
    new Metering("Oldenburg, Deutschland", moment("2018-04-20T23:18"), 3200, 2.4, 13),
    new Metering("Oldenburg, Deutschland", moment("2018-04-22T02:12"), 3200, 2.4, 25),
    new Metering("Oldenburg, Deutschland", moment("2018-05-08T00:17"), 3200, 2.4, 25),
    new Metering("Wapeldorf, Deutschland", moment("2018-05-20T23:37"), 8000, 2.4, 30),
    new Metering("Oldenburg, Deutschland", moment("2018-05-21T00:08"), 3200, 2.4, 30)
  ];

  constructor() {
    super();
  }

  connectedCallback() {
    console.debug("SkyQualityMeter connected!");

    this.innerHTML = `<table class="table"><thead>
  <tr>
    <th>Ort</th>
    <th>Datum</th>
    <th class="text-center">ISO</th>
    <th class="text-center">Blende</th>
    <th class="text-center">Zeit [s]</th>
    <th class="text-center">Mag/arcsec</th>
  </tr></thead><tbody></tbody></table>`;
    let tbody = this.querySelector("table>tbody");
    for (const metering of this.data) {
      tbody.insertAdjacentHTML("beforeend", "<tr></tr>");
      let tr = tbody.querySelector("tr");
      tr.insertAdjacentHTML("beforeend","<td>" + metering.location + "</td>");
      tr.insertAdjacentHTML("beforeend", "<td>" + metering.date + "</td>");
      tr.insertAdjacentHTML("beforeend", "<td>" + metering.iso + "</td>");
      tr.insertAdjacentHTML("beforeend", "<td>" + metering.aperture + "</td>");
      tr.insertAdjacentHTML("beforeend", "<td>" + metering.exposureTime + "</td>");
      tr.insertAdjacentHTML("beforeend", "<td>" + metering.quality() + "</td>");
    }
  }
}

document.addEventListener("DOMContentLoaded", function (event) {
  window.customElements.define("sky-quality-meter", SkyQualityMeter);
});
