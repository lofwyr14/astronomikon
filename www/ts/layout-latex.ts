import katex from "katex";

class LayoutLatex extends HTMLElement {

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
    let mainElement;
    if (this.displayMode) {
      const figure = document.createElement("figure");
      figure.insertAdjacentHTML("afterbegin", this.innerHTML);
      this.innerHTML = "";
      this.insertAdjacentElement("afterbegin", figure);
      mainElement = figure;
    } else {
      mainElement = this;
    }
    katex.render(this.textContent, mainElement, {
      throwOnError: true,
      displayMode: this.displayMode
    });
  }
}

document.addEventListener("DOMContentLoaded", function (event) {
  window.customElements.define("layout-latex", LayoutLatex);
});
