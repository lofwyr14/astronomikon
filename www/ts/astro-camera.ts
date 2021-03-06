export class AstroCamera {

  name: string;
  id: string;

  manufacturer: string;
  sensorFormat: string;
  width: number;
  height: number;
  pixelCountX: bigint;
  pixelCountY: bigint;

  constructor(camera: any) {
    this.id = camera.id;
    this.name = camera.name;
    this.manufacturer = camera.manufacturer;
    this.sensorFormat = camera.sensorFormat;
    this.width = camera.width;
    this.height = camera.height;
    this.pixelCountX = camera.pixelCountX;
    this.pixelCountY = camera.pixelCountY;
  }
}

export class AstroCameraService {

  static data: AstroCamera[] = [];

  static init() {
    if (this.data.length > 0) {
      return; // nothing to do
    }
    window.fetch("json/astro-camera.json")
        .then(response => response.json())
        .then((json: any[]) => json.map((camera: any) => new AstroCamera(camera)))
        .then((cameras: AstroCamera[]) =>
            cameras.forEach((camera: AstroCamera) => AstroCameraService.data.push(camera)))
        .then(() => document.dispatchEvent(new Event("AstroCameraService.data.loaded")))
  }

  static findCameraById(id: string): AstroCamera {
    AstroCameraService.data.forEach((camera) => {
      if (camera.id === id) {
        return camera;
      }
    });
    return null;
  }

  static findCameras(): AstroCamera[] {
    return AstroCameraService.data;
  }
}

class AstroCameraSelector extends HTMLElement {

  current: AstroCamera;

  constructor() {
    super();
    this.name = "astro-camera";
    AstroCameraService.init();
  }

  connectedCallback() {
    if (AstroCameraService.findCameras().length > 0) {
      this.init();
    } else {
      document.addEventListener("AstroCameraService.data.loaded", this.init.bind(this));
    }
  }

  init() {
    for (const camera of AstroCameraService.findCameras()) {
      this.insertAdjacentHTML("afterbegin", `<div class="form-check">
  <label class="form-check-label">
  <input class="form-check-input" type="radio" name="${this.name}" value="${camera.id}">
     ${camera.manufacturer} ${camera.name} (${camera.sensorFormat} - ${camera.width}mm x ${camera.height}mm) 
  </label>
</div>`);

      this.querySelectorAll("input[type='radio']").forEach(
          (element) => element.addEventListener("change",
              (event) => {
                const target = event.target as HTMLInputElement;
                this.current = AstroCameraService.findCameraById(target.value);
                document.dispatchEvent(new Event("astro.camera.selector.current.change"));
              }));
    }
  }

  get name(): string {
    return this.getAttribute("name");
  }

  set name(name: string) {
    this.setAttribute("name", name);
  }
}

document.addEventListener("DOMContentLoaded", function (event) {
  window.customElements.define("astro-camera-selector", AstroCameraSelector);
});
