class Latex extends HTMLElement {

  constructor() {
    super();
  }

  get displayMode() {
    return this.hasAttribute("display-mode");
  }

  set displayMode(update) {
    if (update) {
      this.setAttribute("display-mode", "");
    } else {
      this.removeAttribute("display-mode");
    }
  }

  connectedCallback() {
    katex.render(this.textContent, this, {
      throwOnError: true,
      displayMode: this.displayMode
    });
  }
}

document.addEventListener("DOMContentLoaded", function (event) {
  window.customElements.define("popecke-latex", Latex);
});
