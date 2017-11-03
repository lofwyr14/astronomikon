function compute($cell) {
  var focalLength = $cell.siblings("[data-focal-length]").data("focal-length");
  console.log("focalLength=" + focalLength);
  var sensorSize = $("#" + $cell.data("sensor-ref")).data("sensor-size");
  console.log("sensorSize" + sensorSize);

  var arch = 2.0 * Math.atan(sensorSize / 2.0 / focalLength) / Math.PI * 180;

  $cell.data("arch", arch);
  $cell.html($cell.data("arch").formatLength(2, "°"));
}

$(document).ready(function () {

  $("[data-sensor-size]").each(function () {
    var cell = $(this);
    cell.html(cell.data("sensor-size").formatLength(1));
  });

  $("[data-focal-length]").each(function () {
    var cell = $(this);
    cell.html(cell.data("focal-length").formatLength());
  });

  $("[data-focal-length-slider]").each(function () {
    var cell = $(this);
    var $input = cell.children("input");
    $input.each(function() {
      var $input = $(this);
      var cell = $input.parent().siblings("[data-focal-length]");
      $input.val(cell.data("focal-length"));
    });
    $input.change(function() {
      var $input = $(this);
      var cell = $input.parent().siblings("[data-focal-length]");
      cell.data("focal-length", parseFloat($input.val()));
      cell.html(cell.data("focal-length").formatLength());
      cell.siblings("[data-sensor-ref]").each(function () {
        compute($(this));
      });
    });
  });

  $("[data-sensor-ref]").each(function () {
    compute($(this));
  });


});

// number utils

Number.prototype.round = function (prec) {
  var temp = this * Math.pow(10, prec);
  temp = Math.round(temp);
  return temp / Math.pow(10, prec)
};

/**
 * Number.prototype.format(n, x, s, c)
 *
 * @param n integer : length of decimal
 * @param x integer : length of whole part
 * @param s mixed   : sections delimiter
 * @param c mixed   : decimal delimiter
 */
Number.prototype.formatX = function (n, x, s, c) {
  var re = '\\d(?=(\\d{' + (x || 3) + '})+' + (n > 0 ? '\\D' : '$') + ')',
      num = this.toFixed(Math.max(0, ~~n));

  return (c ? num.replace('.', c) : num).replace(new RegExp(re, 'g'), '$&' + (s || ','));
};

Number.prototype.formatLength = function (n, unit) {
  unit = unit ? unit : 'mm';
  n = n ? n : 0;
  return this.formatX(n, 3, '.', ',') + ' ' + unit;
};

Number.prototype.formatCount = function () {
  return this.toString().replace('.', ',');
};
