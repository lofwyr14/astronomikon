$(document).ready(function () {

  var planetarium = jQuery.virtualsky({
    id: 'starmap',
    longitude: 53,
    latitude: 8
  });

  // todo: fn

  jQuery("#page\\:compass").click(function () {
    // if ("geolocation" in navigator) {
    //   navigator.geolocation.getCurrentPosition(function(position) {
    //     planetarium.setLatitude(position.coords.latitude);
    //     planetarium.setLongitude(position.coords.longitude);

        planetarium.setLatitude(40);
        planetarium.setLongitude(40);
    // planetarium.

      // });
    // } else {
      /* geolocation IS NOT available */
    // }
  });




});
