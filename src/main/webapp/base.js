jQuery(document).ready(function () {

  jQuery("#page\\:fullscreen").click(function () {

    var element = jQuery("body").get(0);
    if (element.requestFullScreen) {

      if (!document.fullScreen) {
        element.requestFullscreen();
      } else {
        document.exitFullScreen();
      }

    } else if (element.mozRequestFullScreen) {

      if (!document.mozFullScreen) {
        element.mozRequestFullScreen();
      } else {
        document.mozCancelFullScreen();
      }

    } else if (element.webkitRequestFullScreen) {

      if (!document.webkitIsFullScreen) {
        element.webkitRequestFullScreen();
      } else {
        document.webkitCancelFullScreen();
      }

    }
  });
});

astro = {};

astro.init = function() {
  jQuery(".latex").each(function () {
    var $element = jQuery(this);
    if ($element.find(".katex").length > 0) {
      // was already processed
      return;
    }
    var latex = $element.text();
    katex.render(latex, $element.get(0), {
      throwOnError: true,
      displayMode: $element.css("display") === "block"
    });
  });
};

Tobago.registerListener(astro.init, Tobago.Phase.DOCUMENT_READY);
Tobago.registerListener(astro.init, Tobago.Phase.AFTER_UPDATE);
