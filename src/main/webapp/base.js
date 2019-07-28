import {Listener, Phase} from "./tobago/standard/tobago-bootstrap/5.0.0-SNAPSHOT/js/tobago-listener";
import {DomUtils} from "./tobago/standard/tobago-bootstrap/5.0.0-SNAPSHOT/js/tobago-utils";
import {katex} from "./webjars/katex/0.9.0/dist/katex.js";

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

// XXX currently broken!

var astro_init = function(element) {
  for(var latex of DomUtils.selfOrQuerySelectorAll(element, ".latex")) {
    if (latex.querySelector(".katex")) {
      // was already processed
      return;
    }
    var text = latex.textContent;
    katex.render(text, latex, {
      throwOnError: true,
      displayMode: latex.style.display === "block"
    });
  }
};

Listener.register(astro_init, Phase.DOCUMENT_READY);
Listener.register(astro_init, Phase.AFTER_UPDATE);

// Tobago.registerListener(astro_init, Tobago.Phase.DOCUMENT_READY);
// Tobago.registerListener(astro_init, Tobago.Phase.AFTER_UPDATE);
