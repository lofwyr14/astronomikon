class LayoutTemplate extends HTMLElement {

  static get observedAttributes() {
    return ['content'];
  }

  constructor() {
    super();
    window.addEventListener("hashchange", (event: HashChangeEvent) => {
      console.log(event);
      this.sync();
    });
  }

  connectedCallback() {
    this.sync();
  }

  private sync() {
    const hash = window.location.hash;
    if (hash && hash.startsWith("#!")) {
      this.content = hash.substring(2);
    } else {
      this.content = "html";
    }
  }

  attributeChangedCallback(attrName, oldVal, newVal) {
    if (attrName === "content") {
      this.fetch();
    }
  }

  private fetch() {
    const content = this.content;
    if (content) {
      window.fetch(content)
          .then(response => response.text())
          .then((text: string) => this.innerHTML = text);
    }
  }

  get content(): string {
    return this.getAttribute("content");
  }

  set content(content: string) {
    this.setAttribute("content", content);
  }
}

document.addEventListener("DOMContentLoaded", function (event) {
  window.customElements.define("layout-template", LayoutTemplate);
});
