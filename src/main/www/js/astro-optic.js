import { html, render } from "lit-html";
class AstroOptic {
    constructor(optic) {
        this.id = optic.id;
        this.name = optic.name;
        this.manufacturer = optic.manufacturer;
        this.focalLength = parseInt(optic.focalLength);
        this.diameter = parseInt(optic.diameter);
    }
    get aperture() {
        return this.focalLength / this.diameter;
    }
}
class AstroOpticService {
    static init() {
        if (this.data.length > 0) {
            return; // nothing to do
        }
        window.fetch("json/astro-optic.json")
            .then(response => response.json())
            .then((json) => json.map((optic) => new AstroOptic(optic)))
            .then((optics) => optics.forEach((optic) => AstroOpticService.data.push(optic)))
            .then(() => document.dispatchEvent(new Event("AstroOpticService.data.loaded"))); // XXX
    }
    static findOpticById(id) {
        AstroOpticService.data.forEach((optic) => {
            if (optic.id === id) {
                return optic;
            }
        });
        return null;
    }
    static findOptics() {
        return AstroOpticService.data;
    }
}
AstroOpticService.data = [];
class AstroOpticSelector extends HTMLElement {
    constructor() {
        super();
        this.name = "astro-optic";
        AstroOpticService.init();
    }
    connectedCallback() {
        if (AstroOpticService.findOptics().length > 0) {
            this.init();
        }
        else {
            document.addEventListener("AstroOpticService.data.loaded", this.init.bind(this));
        }
    }
    init() {
        this.insertAdjacentHTML("afterbegin", `<div class="form-check">
  <label>
<select class="form-control"></select>
</div>`);
        const select = this.querySelector("select");
        for (const optic of AstroOpticService.findOptics()) {
            select.insertAdjacentHTML("beforeend", `
  <option name="${this.name}" value="${optic.id}">
     ${optic.manufacturer} ${optic.name} (${optic.focalLength} / ${optic.diameter})
  </label>`);
            this.querySelectorAll("input[type='radio']").forEach((element) => element.addEventListener("change", (event) => {
                const target = event.target;
                const selected = AstroOpticService.findOpticById(target.value);
                const current = this.findForm().current;
                current.focalLength = selected.focalLength;
                current.diameter = selected.diameter;
                document.dispatchEvent(new Event("astro.optic.selector.current.change"));
            }));
        }
    }
    findForm() {
        return this.closest("astro-optic-selector");
    }
    get name() {
        return this.getAttribute("name");
    }
    set name(name) {
        this.setAttribute("name", name);
    }
}
document.addEventListener("DOMContentLoaded", function (event) {
    window.customElements.define("astro-optic-selector", AstroOpticSelector);
});
class AstroOpticForm extends HTMLElement {
    constructor() {
        super();
    }
    connectedCallback() {
        // const myTemplate = (name) => html`<div>Hello ${name}</div>`;
        const clickHandler = {
            // handleEvent method is required.
            handleEvent(e) {
                console.log('clicked!');
            },
            // event listener objects can also define zero or more of the event
            // listener options: capture, passive, and once.
            capture: true,
        };
        const myTemplate = (value) => html `<button @click=${clickHandler}>Click Me ${value}!</button>`;
        // Render the template with some data
        render(myTemplate('world'), document.body);
        // ... Later on ...
        // Render the template with different data
        render(myTemplate('lit-html'), document.body);
        render(html `<astro-optic-selector>
</astro-optic-selector>
<button @click=${clickHandler}>Click Me ${this.tagName}!</button>
<div class="form-group">
  <label>
    ${"Brennweite Y"}
    <input type="number" class="form-control" min="4" max="100000" @click="${event => this.log(event)}">
  </label>
<div class="form-group">
  <label>
    Öffnung
    <input type="number" class="form-control" min="4" max="10000">
  </label>
<div class="form-group">
  <label>
    Öffnungsverhältnis
    <input type="number" class="form-control" min="0.5" max="100" list="aperture-list">
  </label>
</div>`, document.body);
    }
    log(event) {
        console.log(event);
    }
}
document.addEventListener("DOMContentLoaded", function (event) {
    window.customElements.define("astro-optic-form", AstroOpticForm);
});
