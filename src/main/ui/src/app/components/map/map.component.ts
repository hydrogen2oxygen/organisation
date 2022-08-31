import {Component, OnInit} from '@angular/core';
import olMap from 'ol/Map';
import View from 'ol/View';
import XYZ from 'ol/source/XYZ';
import VectorSource from "ol/source/Vector";
import TileLayer from "ol/layer/Tile";
import {OSM} from "ol/source";
import VectorLayer from "ol/layer/Vector";

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {

  map: olMap | null = null;
  view: View = new View();
  source = new VectorSource();
  interaction: any = null;

  constructor() { }

  ngOnInit(): void {

    const osmLayer = new TileLayer({
      source: new OSM(),
    });
    osmLayer?.getSource()?.setAttributions([]);

    const vectorLayer = new VectorLayer({
      source: this.source
    });

    const xyzLayer = new TileLayer({
      source: new XYZ({
        url: 'http://tile.osm.org/{z}/{x}/{y}.png'
      })
    });

    this.view = new View({
      center: [-472202, 7530279],
      zoom: 12
    });
    this.map = new olMap({
      layers: [
        osmLayer,
        vectorLayer
      ],
      view: this.view,
      controls: []
    });
  }

  ngAfterViewInit(): void {
    this.map?.setTarget('map');
  }
}
