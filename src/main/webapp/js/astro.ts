class Title extends HTMLElement {

  constructor() {
    super();
  }

  connectedCallback() {
    const rootNode = this.getRootNode() as ShadowRoot | Document;
    this.insertAdjacentHTML("afterbegin",
        "<h1>" + rootNode.querySelector("title").innerText + "</h1>");
  }
}

document.addEventListener("DOMContentLoaded", function (event: Event) {
  window.customElements.define("astro-title", Title);
});
