import {html, render} from "lit";
import {ifDefined} from "lit/directives/if-defined.js";

class AstroImage {

  id: string;
  title: string;
  begin: Date;
  count: number;
  countDark: number;
  countFlat: number;
  countBias: number;
  city: string;
  description: string;
  releaseDate: Date;
  updateDate: Date;
  dir: string;
  pictures: Map<string, Picture>

  constructor(image: any) {
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
    this.dir = image.dir;
    this.pictures = new Map<string, Picture>();
    if (image.pictures) {
      Object.keys(image.pictures).forEach((key: string) => {
        console.warn(key, image.pictures[key]);
        this.pictures.set(key, image.pictures[key]);
      });
    }
  }

  url(name: string, alt?: string): string {
    let picture = this.pictures.get(name);
    if (!picture && alt) {
      picture = this.pictures.get(alt);
    }
    if (!picture) {
      picture = this.pictures.values().next().value;
    }
    return picture ? `${this.dir}/${picture.name}` : undefined; // todo: n/a image
  }

  get thumbUrl(): string {
    return this.url("thumb", "preview");
  }

  get previewUrl(): string {
    return this.url("preview", "full");
  }

  get fullUrl(): string {
    return this.url("full", "preview");
  }
}

class Picture {
  name: string;
  width: number;
  height: number;
}

class Display {
  static date(date: Date): string {
    return date?.toLocaleDateString();
  }
}

class AstroImageService extends HTMLElement {

  data: AstroImage[] = [];

  ready: boolean = false;

  constructor() {
    super();
  }

  connectedCallback() {
    if (this.data.length > 0) {
      return; // nothing to do
    }
    window.fetch("json/astro-image.json")
        .then(response => response.json())
        .then((json: any[]) => json.map((image: any) => new AstroImage(image)))
        .then((images: AstroImage[]) =>
            images.forEach((image: AstroImage) => this.data.push(image)))
        .then(() => {
          this.ready = true;
          document.dispatchEvent(new Event("AstroImageService.loaded"));
          console.error("--- [service] fetch then #4 length=", this.data.length);
        }) // XXX
  }

  findImageById(id: string): AstroImage {
    return this.data.find((image) => image.id === id);
  }

  findImages(): AstroImage[] {
    return this.data;
  }
}

document.addEventListener("DOMContentLoaded", function (event) {
  window.customElements.define("astro-image-service", AstroImageService);
});

class AstroImageTable extends HTMLElement {

  service: AstroImageService;

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

    this.service = document.querySelector("astro-image-service") as AstroImageService;
  }

  init() {
    let list = this.service.findImages();
    console.error("--- [table init] find images length", list.length);
    if (list) {
      let gallery = this.closest('astro-gallery') as AstroGallery;
      render(html`
        <div class="row">
          ${list.map((image) => html`
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

  service: AstroImageService;

  constructor() {
    super();
  }

  connectedCallback() {
    console.warn("................. CC")
    document.addEventListener("AstroImageService.loaded", this.init.bind(this));
    this.service = document.querySelector("astro-image-service") as AstroImageService;
  }

  attributeChangedCallback(name: string, oldValue: any, newValue: any) {
    console.warn("................. ACC", name)
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

  get imageId(): string {
    return this.getAttribute("image-id");
  }

  set imageId(imageId: string) {
    this.setAttribute("image-id", imageId);
  }

  init() {
    this.render();
  }

  private render() {

    if (this.service && this.service.ready) {
      console.warn("++++++++++++++++++ " + this.service);
      const image = this.service.findImageById(this.imageId);
      console.warn("++++++++++++++++++ " + image);
      if (image) {
        let gallery = this.closest('astro-gallery') as AstroGallery;
        render(html`
          <figure>
            <img class="astro-fit-image" src="${image.previewUrl}" alt="${image.title}"/>
            <div class="btn-group">
              <button @click="${this.natural.bind(this)}" class="btn btn-dark active">1:1</button>
              <button @click="${this.contract.bind(this)}" class="btn btn-dark"
              ><i class="bi bi-arrows-angle-contract"></i></button>
              <button @click="${this.expand.bind(this)}" class="btn btn-dark"
              ><i class="bi bi-arrows-angle-expand"></i></button>
              <button @click="${this.zoomOff.bind(this)}" class="btn btn-dark"
              >100 %</button>
              <button @click="${this.zoomOut.bind(this)}" class="btn btn-dark"
              ><i class="bi bi-zoom-out"></i></button>
              <button @click="${this.zoomIn.bind(this)}" class="btn btn-dark"
              ><i class="bi bi-zoom-in"></i></button>
            </div>
            <figcaption>${image.description} vom ${Display.date(image.begin)}</figcaption>
          </figure>`, this);
        this.classList.remove("d-none");
      } else {
        this.classList.add("d-none");
      }
    } else {
      console.warn("service is not ready... still waiting...");
      this.classList.add("d-none");
    }
  }

  natural(event: MouseEvent) {
    let img: HTMLImageElement = this.querySelector("img");
    img.style.width = img.naturalWidth + "px";
    img.style.height = img.naturalHeight + "px";
  }

  contract(event: MouseEvent) {
    let img = this.querySelector("img");
    img.style.width = "auto";
    img.style.height = this.offsetHeight + "px";
  }

  expand(event: MouseEvent) {
    let img = this.querySelector("img");
    img.style.width = this.offsetWidth + "px";
    img.style.height = "auto";
  }

  zoomOff(event: MouseEvent) {
    let img = this.querySelector("img");
    // console.log("img.style.width  ", img.style.width);
    // console.log("this.scrollWidth ", this.scrollWidth);
    // img.style.width = 0.70710678118 * this.scrollWidth + "px";
    // console.log("img.style.width  ", img.style.width);
    // console.log("this.scrollWidth ", this.scrollWidth);
    // img.style.height = "auto";
    const scale = img.getBoundingClientRect().width / img.offsetWidth;
    img.style.transform = `scale(1)`;
  }

  zoomOut(event: MouseEvent) {
    let img = this.querySelector("img");
    // console.log("img.style.width  ", img.style.width);
    // console.log("this.scrollWidth ", this.scrollWidth);
    // img.style.width = 0.70710678118 * this.scrollWidth + "px";
    // console.log("img.style.width  ", img.style.width);
    // console.log("this.scrollWidth ", this.scrollWidth);
    // img.style.height = "auto";
    const scale = img.getBoundingClientRect().width / img.offsetWidth;
    img.style.transform = `scale(${scale * 0.70710678118})`;

    // let boundingClientRect = img.getBoundingClientRect();
    // img.parentElement.style.width = boundingClientRect.width + "px";
    // img.parentElement.style.height = boundingClientRect.height + "px";
  }

  zoomIn(event: MouseEvent) {
    let img = this.querySelector("img");
    // img.style.width = 1.41421356237 * this.scrollWidth + "px";
    // img.style.height = "auto";
    const scale = img.getBoundingClientRect().width / img.offsetWidth;
    img.style.transform = `scale(${scale * 1.41421356237})`;

    // let boundingClientRect = img.getBoundingClientRect();
    // img.parentElement.style.width = boundingClientRect.width + "px";
    // img.parentElement.style.height = boundingClientRect.height + "px";
  }

}

document.addEventListener("DOMContentLoaded", function (event) {
  window.customElements.define("astro-image-viewer", AstroImageViewer);
});

class AstroGallery extends HTMLElement {

  private static readonly PERMA_LINK = /\/gallery(-([A-Za-z0-9\-]+))?\.html/;

  private currentImage: string;

  constructor() {
    super();
  }

  connectedCallback() {
    this.parseUrl();
    this.render();
  }

  private parseUrl() {
    const result = window.location.pathname.match(AstroGallery.PERMA_LINK);
    console.info("result = ", result);
    if (result && result.length == 3 && result[2]) {
      this.currentImage = result[2];
    } else {
      this.currentImage = null;
    }
  }

  private render() {
    render(html`
      <astro-image-service>
      </astro-image-service>

      <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel" data-bs-interval="1000000">
        <div class="carousel-inner">
          <div class="carousel-item active">
            <astro-image-viewer image-id="${ifDefined(this.currentImage)}">
            </astro-image-viewer>
          </div>
          <div class="carousel-item">
            <astro-image-viewer image-id="2017-10-15-Oldenburg-M31">
            </astro-image-viewer>
          </div>
          <div class="carousel-item">
            <astro-image-viewer image-id="2020-04-07-Oldenburg-Mond">
            </astro-image-viewer>
          </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls"
                data-bs-slide="prev">
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls"
                data-bs-slide="next">
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Next</span>
        </button>
      </div>

      <!--      <astro-image-table>-->
      <!--      </astro-image-table>-->
    `, this);

    // let element = document.querySelector("astro-image-viewer");
    // document.querySelector("body").insertAdjacentElement("afterbegin", element);
  }

  navigate(event: MouseEvent): void {
    // idea from https://developers.google.com/search/docs/guides/javascript-seo-basics#use-history-api
    console.info("***************************** navigate");
    event.preventDefault();
    const target = event.currentTarget as HTMLAnchorElement;
    const href = target.href;
    window.history.pushState({}, document.title, href) // Update URL as well as browser history.
    this.parseUrl();
    const viewer = document.querySelector("astro-image-viewer") as AstroImageViewer;
    viewer.imageId = this.currentImage;
    // target.parentElement.parentElement.insertAdjacentElement("afterend", viewer);
  }

}

document.addEventListener("DOMContentLoaded", function (event) {
  window.customElements.define("astro-gallery", AstroGallery);
});
