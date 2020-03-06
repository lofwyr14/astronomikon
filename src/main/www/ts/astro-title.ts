class AstroTitle extends HTMLElement {

  constructor() {
    super();
  }

  connectedCallback() {
    const title = this.innerText;
    const rootNode = this.getRootNode() as ShadowRoot | Document;
    this.innerHTML = `<h1>${title}</h1>`;
    rootNode.querySelector("title").innerText = title;
  }
}

document.addEventListener("DOMContentLoaded", function (event: Event) {
  window.customElements.define("astro-title", AstroTitle);
});
