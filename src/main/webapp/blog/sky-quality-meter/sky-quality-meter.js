$(document).ready(function () {

  /*
    const x = new Vue({
          el: '#page\\:box',
          data: {
            optic: {
              diameter: 100,
              focalLength: 1000
            }
          },
          computed: {
            f() {
              return this.optic.focalLength / this.optic.diameter;
            }
          }
        }
    );
  */

  const vue =  new Vue({
    el: '#page\\:box',
    data: {
      meterings: [
        // todo: Zeitzone
        {
          location: "Las Tricias, La Palma, Spanien",
          date: moment("2019-04-07T01:00"),
          iso: 3600,
          aperture: 1.8,
          exposureTime: 30
        },
        {
          location: "Las Tricias, La Palma, Spanien",
          date: moment("2019-04-03T01:30"),
          iso: 9000,
          aperture: 1.8,
          exposureTime: 30
        },
        {
          location: "Las Tricias, La Palma, Spanien",
          date: moment("2018-10-07T04:04"),
          iso: 4000,
          aperture: 1.4,
          exposureTime: 30
        },
        {
          location: "Llano del Jable, La Palma, Spanien",
          date: moment("2018-10-03T00:03"),
          iso: 12800,
          aperture: 2.4,
          exposureTime: 30
        },
        {
          location: "Oldenburg, Deutschland",
          date: moment("2018-04-20T23:18"),
          iso: 3200,
          aperture: 2.4,
          exposureTime: 13
        },
        {
          location: "Oldenburg, Deutschland",
          date: moment("2018-04-22T02:12"),
          iso: 3200,
          aperture: 2.4,
          exposureTime: 25
        },
        {
          location: "Oldenburg, Deutschland",
          date: moment("2018-05-08T00:17"),
          iso: 3200,
          aperture: 2.4,
          exposureTime: 25
        },
        {
          location: "Wapeldorf, Deutschland",
          date: moment("2018-05-20T23:37"),
          iso: 8000,
          aperture: 2.4,
          exposureTime: 30
        },
        {
          location: "Oldenburg, Deutschland",
          date: moment("2018-05-21T00:08"),
          iso: 3200,
          aperture: 2.4,
          exposureTime: 30
        }
      ]
    },
    methods: {

      quality: function (metering) {
        return 7.43 + 2.5 * Math.log(metering.iso * metering.exposureTime / metering.aperture / metering.aperture) / Math.log(10);
      }
    }
  });
});
