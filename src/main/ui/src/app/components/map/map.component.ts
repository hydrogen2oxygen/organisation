import {Component, OnInit} from '@angular/core';
import olMap from 'ol/Map';
import View from 'ol/View';
import VectorSource from "ol/source/Vector";
import TileLayer from "ol/layer/Tile";
import {OSM} from "ol/source";
import VectorLayer from "ol/layer/Vector";
import {OrganisationService} from "../../service/organisation.service";
import {Organisation, Person} from "../../domains/Organisation";
import {Feature} from "ol";
import {Point} from "ol/geom";
import {Fill, Icon, Style, Text} from "ol/style";

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
  org: Organisation = new Organisation();

  constructor(private organisationService: OrganisationService) { }

  ngOnInit(): void {

    const osmLayer = new TileLayer({
      source: new OSM(),
    });
    osmLayer?.getSource()?.setAttributions([]);

    const vectorLayer = new VectorLayer({
      source: this.source
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

    this.organisationService.loadOrganisation().subscribe(o => {

      this.org = o;
      let firstPoint:any|undefined;
      let personGroups:Map<string,Person[]> = new Map<string, Person[]>();

      // group by position ... TODO group by lastName too (write an wrapper class)
      this.org.groups.forEach(group => {
        group.persons.forEach( person => {
          if (person.address.position) {
            if (personGroups.get(person.address.position.x+' '+person.address.position.y) != null) {
              // @ts-ignore
              personGroups.get(person.address.position.x+' '+person.address.position.y).push(person);
            } else {
              let personGroup:any[] = [];
              personGroup.push(person);
              personGroups.set(person.address.position.x+' '+person.address.position.y, personGroup);
            }
          }
        })
      });

      personGroups.forEach(personGroup => {
        let position = new Array<number>;
        let person = personGroup[0];
        let names:string[] = [];
        personGroup.forEach( p => {
          names.push(p.lastName + ' ' + p.firstName);
          names.push('bold 13px Calibri,sans-serif');
          names.push('');
          names.push('');
          names.push('\n');
          names.push('');
        });

        position.push(Number(person.address.position?.y));
        position.push(Number(person.address.position?.x));
        let point = new Point(position).transform('EPSG:4326','EPSG:900913');
        let marker = new Feature(point);
        let text = new Text();
        text.setText(names);
        text.setFill(new Fill({color: '#990f0f'}))
        marker.set('name', person.lastName + ' ' + person.firstName);

        if (!firstPoint) firstPoint = point;

        let zIndex = 1;
        marker.setStyle(new Style({
          image: new Icon(({
            anchorXUnits: "pixels",
            anchorYUnits: "pixels",
            anchor: [0, 0],
            offset: [0,0],
            offsetOrigin: 'bottom-left',
            rotation: 0,
            opacity: 1,
            src: "assets/redPoint.png"
          })),
          text: text,
          zIndex: zIndex
        }));

        this.source.addFeature(marker);
      });

      if (firstPoint) {
        this.map?.getView().fit(this.source.getExtent());
        // @ts-ignore
        this.map?.getView().setZoom(this.map?.getView().getZoom() - 0.5);
      }
    });
  }
}
