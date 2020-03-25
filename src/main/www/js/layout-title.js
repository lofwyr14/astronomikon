class LayoutTitle extends HTMLElement {
    constructor() {
        super();
    }
    connectedCallback() {
        const title = this.innerText;
        const rootNode = this.getRootNode();
        this.innerHTML = `<h1>${title}</h1>`;
        rootNode.querySelector("title").innerText = title;
    }
}
document.addEventListener("DOMContentLoaded", function (event) {
    window.customElements.define("layout-title", LayoutTitle);
});
