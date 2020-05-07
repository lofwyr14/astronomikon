class LayoutLink extends HTMLElement {
    constructor() {
        super();
    }
    connectedCallback() {
        const rootNode = this.getRootNode();
        this.insertAdjacentHTML("afterbegin", "<a href='" + this.link + "'>" + this.label + "</a>");
        if (this.fa) {
            const a = this.querySelector("a");
            a.insertAdjacentHTML("afterbegin", "<i class='fa fa-" + this.fa + "'/>");
        }
    }
    get fa() {
        return this.getAttribute("fa");
    }
    set fa(fa) {
        if (fa) {
            this.setAttribute("fa", fa);
        }
        else {
            this.removeAttribute("fa");
        }
    }
    get link() {
        return this.getAttribute("link");
    }
    set link(link) {
        if (link) {
            this.setAttribute("link", link);
        }
        else {
            this.removeAttribute("link");
        }
    }
    get label() {
        return this.getAttribute("label");
    }
    set label(label) {
        if (label) {
            this.setAttribute("label", label);
        }
        else {
            this.removeAttribute("label");
        }
    }
}
document.addEventListener("DOMContentLoaded", function (event) {
    window.customElements.define("layout-link", LayoutLink);
});
