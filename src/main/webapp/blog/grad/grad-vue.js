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


  const y =  new Vue({
      el: '#page\\:box',
      data: {
        cameras: [
          {width: 32, height: 24}
        ],
        optics: [
          {
            name: "Nikon 50",
            diameter: "2",
            focalLength: 50,
            focalLengthMin: 50,
            focalLengthMax: 50,
            zoom: false
          }
        ]
      },
      computed: {

        angleWidth: function () {
          return this.angle(this.cameras[0].width, this.optics[0].focalLength);
        },
        angleHeight: function () {
          return this.angle(this.cameras[0].height, this.optics[0].focalLength);
        }
      },
      methods: {
        angle: function(size, focalLength){
          return Math.atan(size / 2.0 / focalLength) / Math.PI * 180.0 * 2.0;
        }
      }
    });
});
