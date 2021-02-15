import { html, render } from "lit-html";
import { ifDefined } from "lit-html/directives/if-defined";
class AstroImage {
    constructor(image) {
        this.id = image.id;
        this.title = image.title;
        this.begin = new Date(image.begin);
        this.count = image.count;
        this.countDark = image.countDark;
        this.countFlat = image.countFlat;
        this.countBias = image.countBias;
        this.city = image.city;
        this.description = image.description;
        this.releaseDate = image.releaseDate;
        this.updateDate = image.updateDate;
        this.pictures = new Map();
        if (image.pictures) {
            Object.keys(image.pictures).forEach((key) => {
                console.warn(key, image.pictures[key]);
                this.pictures.set(key, image.pictures[key]);
            });
        }
    }
    url(name, alt) {
        let picture = this.pictures.get(name);
        if (!picture && alt) {
            picture = this.pictures.get(alt);
        }
        if (!picture) {
            picture = this.pictures.values().next().value;
        }
        return picture ? `image/gallery/${this.id}/${picture.name}` : undefined; // todo: n/a image
    }
    get thumbUrl() {
        return this.url("thumb", "preview");
    }
    get previewUrl() {
        return this.url("preview");
    }
}
class Picture {
}
class Display {
    static date(date) {
        return date === null || date === void 0 ? void 0 : date.toLocaleDateString();
    }
}
class AstroImageService extends HTMLElement {
    constructor() {
        super();
        this.data = [];
        this.ready = false;
    }
    connectedCallback() {
        if (this.data.length > 0) {
            return; // nothing to do
        }
        window.fetch("json/astro-image.json")
            .then(response => response.json())
            .then((json) => json.map((image) => new AstroImage(image)))
            .then((images) => images.forEach((image) => this.data.push(image)))
            .then(() => {
            this.ready = true;
            document.dispatchEvent(new Event("AstroImageService.loaded"));
            console.error("--- [service] fetch then #4 length=", this.data.length);
        }); // XXX
    }
    findImageById(id) {
        return this.data.find((image) => image.id === id);
    }
    findImages() {
        return this.data;
    }
}
document.addEventListener("DOMContentLoaded", function (event) {
    window.customElements.define("astro-image-service", AstroImageService);
});
class AstroImageTable extends HTMLElement {
    constructor() {
        super();
    }
    connectedCallback() {
        // if ('serviceWorker' in navigator) {
        //   window.addEventListener('load', function() {
        //     navigator.serviceWorker.register('/sw.js').then(function(registration) {
        //       Registration was successful
        // console.log('ServiceWorker registration successful with scope: ', registration.scope);
        // }, function(err) {
        //   registration failed :(
        // console.log('ServiceWorker registration failed: ', err);
        // });
        // });
        // }
        console.error("--- [table cc] adding listener: AstroImageService.loaded to Table.init");
        document.addEventListener("AstroImageService.loaded", this.init.bind(this));
        this.service = document.querySelector("astro-image-service");
    }
    init() {
        let list = this.service.findImages();
        console.error("--- [table init] find images length", list.length);
        if (list) {
            let gallery = this.closest('astro-gallery');
            render(html `
        <div class="row">
          ${list.map((image) => html `
            <div class="col-sm-12 col-md-6 col-lg-4 col-xl-3">
              <div class="card preview">
                <a href="gallery-${image.id}.html" @click="${gallery.navigate.bind(gallery)}"
              ><img src="${image.thumbUrl}" class="float-left float-start" alt="Ansicht"/>
                  <b>${image.title}</b><br/>
                  <i>${image.description}</i><br/>
                  Datum: ${Display.date(image.begin)}<br/>
                  Anzahl: ${image.count}<br/>
                  Ort: ${image.city}<br/>
                </a>
              </div>
            </div>`)}
        </div>
      `, this);
        }
    }
}
document.addEventListener("DOMContentLoaded", function (event) {
    window.customElements.define("astro-image-table", AstroImageTable);
});
class AstroImageViewer extends HTMLElement {
    constructor() {
        super();
    }
    connectedCallback() {
        console.warn("................. CC");
        document.addEventListener("AstroImageService.loaded", this.init.bind(this));
        this.service = document.querySelector("astro-image-service");
    }
    attributeChangedCallback(name, oldValue, newValue) {
        console.warn("................. ACC", name);
        console.info("attributeChangedCallback", name, oldValue, newValue);
        switch (name) {
            case "image-id":
                if (oldValue !== newValue) {
                    // todo load image and check existence
                    if (newValue) {
                        this.render();
                    }
                }
                break;
        }
    }
    static get observedAttributes() {
        return ["image-id"];
    }
    get imageId() {
        return this.getAttribute("image-id");
    }
    set imageId(imageId) {
        this.setAttribute("image-id", imageId);
    }
    init() {
        this.render();
    }
    render() {
        if (this.service && this.service.ready) {
            console.warn("++++++++++++++++++ " + this.service);
            const image = this.service.findImageById(this.imageId);
            console.warn("++++++++++++++++++ " + image);
            if (image) {
                let gallery = this.closest('astro-gallery');
                render(html `
          <a href="gallery.html" @click="${gallery.navigate.bind(gallery)}" title="Schließen"
          >[Ansicht schließen]</a>
          <figure>
            <img src="${image.previewUrl}" alt="${image.title}"/>
            <figcaption>${image.description} vom ${Display.date(image.begin)}</figcaption>
          </figure>
        `, this);
                this.classList.remove("d-none");
            }
            else {
                this.classList.add("d-none");
            }
        }
        else {
            console.warn("service is not ready... still waiting...");
            this.classList.add("d-none");
        }
    }
}
document.addEventListener("DOMContentLoaded", function (event) {
    window.customElements.define("astro-image-viewer", AstroImageViewer);
});
class AstroGallery extends HTMLElement {
    constructor() {
        super();
    }
    connectedCallback() {
        this.parseUrl();
        this.render();
    }
    parseUrl() {
        const result = window.location.pathname.match(AstroGallery.PERMA_LINK);
        console.info("result = ", result);
        if (result && result.length == 3 && result[2]) {
            this.currentImage = result[2];
        }
        else {
            this.currentImage = null;
        }
    }
    render() {
        render(html `
      <astro-image-service>
      </astro-image-service>
      <layout-title>Here will be the gallery soon!</layout-title>
      <astro-image-viewer image-id="${ifDefined(this.currentImage)}">
      </astro-image-viewer>
      <astro-image-table>
      </astro-image-table>
    `, this);
    }
    navigate(event) {
        // idea from https://developers.google.com/search/docs/guides/javascript-seo-basics#use-history-api
        console.info("***************************** navigate");
        event.preventDefault();
        const target = event.currentTarget;
        const href = target.href;
        window.history.pushState({}, document.title, href); // Update URL as well as browser history.
        this.parseUrl();
        const viewer = this.querySelector("astro-image-viewer");
        viewer.imageId = this.currentImage;
    }
}
AstroGallery.PERMA_LINK = /\/gallery(-([A-Za-z0-9\-]+))?\.html/;
document.addEventListener("DOMContentLoaded", function (event) {
    window.customElements.define("astro-gallery", AstroGallery);
});
