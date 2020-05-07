class AstroFrameAngle extends HTMLElement {

  constructor() {
    super();
  }

  connectedCallback() {
    this.innerHTML = `<h2>test</h2>`;
  }
}

document.addEventListener("DOMContentLoaded", function (event: Event) {
  window.customElements.define("astro-frame-angle", AstroFrameAngle);
});
