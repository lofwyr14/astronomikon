class LayoutLink extends HTMLElement {

  constructor() {
    super();
  }

  connectedCallback() {
    const rootNode = this.getRootNode() as ShadowRoot | Document;
    this.insertAdjacentHTML("afterbegin",
        "<a href='" + this.link + "'>" + this.label + "</a>");
    if (this.fa) {
       const  a =  this.querySelector("a");
       a.insertAdjacentHTML("afterbegin", "<i class='fa fa-" + this.fa + "'/>")
    }
  }

  get fa(): string {
    return this.getAttribute("fa");
  }

  set fa(fa: string) {
    if (fa) {
      this.setAttribute("fa", fa);
    } else {
      this.removeAttribute("fa");
    }
  }

  get link(): string {
    return this.getAttribute("link");
  }

  set link(link: string) {
    if (link) {
      this.setAttribute("link", link);
    } else {
      this.removeAttribute("link");
    }
  }

  get label(): string {
    return this.getAttribute("label");
  }

  set label(label: string) {
    if (label) {
      this.setAttribute("label", label);
    } else {
      this.removeAttribute("label");
    }
  }


}

document.addEventListener("DOMContentLoaded", function (event: Event) {
  window.customElements.define("layout-link", LayoutLink);
});
