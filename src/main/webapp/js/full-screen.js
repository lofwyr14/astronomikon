class FullScreen extends HTMLElement {

  constructor() {
    super();
  }

  connectedCallback() {
    this.addEventListener("click", function () {
      let body = document.body;
      if (body.requestFullScreen) {
        if (!document.fullScreen) {
          body.requestFullscreen();
        } else {
          document.exitFullScreen();
        }
      } else if (body.mozRequestFullScreen) {
        if (!document.mozFullScreen) {
          body.mozRequestFullScreen();
        } else {
          document.mozCancelFullScreen();
        }
      } else if (body.webkitRequestFullScreen) {
        if (!document.webkitIsFullScreen) {
          body.webkitRequestFullScreen();
        } else {
          document.webkitCancelFullScreen();
        }
      }
    });
  }
}

document.addEventListener("DOMContentLoaded", function (event) {
  window.customElements.define("popecke-full-screen", FullScreen);
});
