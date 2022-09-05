import {Component, OnInit} from '@angular/core';
import { Network } from "vis-network/peer/esm/vis-network";
import { DataSet } from "vis-data/peer/esm/vis-data"
import {EdgeVis, NodeVis} from "../../domains/NetworkingDomains";
import {OrganisationService} from "../../service/organisation.service";
import {Organisation} from "../../domains/Organisation";

@Component({
  selector: 'app-diagram',
  templateUrl: './diagram.component.html',
  styleUrls: ['./diagram.component.scss']
})
export class DiagramComponent implements OnInit {

  org: Organisation = new Organisation();
  network:any;

  constructor(private organisationService: OrganisationService) {}

  ngOnInit(): void {
    this.organisationService.loadOrganisation().subscribe(o => {
      this.org = o;
      this.initVisNetwork();
    });

  }

  /**
   * See https://visjs.github.io/vis-network/examples/
   */
  private initVisNetwork() {
    let nodeArray:NodeVis[] = [];
    let edgeArray:EdgeVis[] = [];
    let idCount = 1;

    nodeArray.push({ id: idCount, label: this.org.name, shape: "circle", color: "#42f09f" });

    this.org.groups.forEach( g => {
      idCount++;
      nodeArray.push({ id: idCount, label: g.name, shape: "circle", color: "#c0b4ff" });
      edgeArray.push({ id: idCount, from: 1, to: idCount });

      let groupId = idCount;

      g.persons.forEach( p => {
        idCount++;
        nodeArray.push({ id: idCount, label: p.lastName + " " + p.firstName, shape: "box", color: "#2cff48" });
        edgeArray.push({ id: idCount, from: groupId, to: idCount });
      })
    });

    let nodes = new DataSet<NodeVis>(nodeArray);
    let edges: DataSet<EdgeVis> = new DataSet(edgeArray);
    let container = document.getElementById("mynetwork");
    let data = {
      nodes: nodes,
      edges: edges
    };
    let options = {};
    if (container) {
      this.network = new Network(container, data, options);
      this.network.on("click", (e: any) => {
        let nodeId:any = this.network.getNodeAt(e.pointer.DOM);
        if (nodeId) {
          console.log(nodes.get(nodeId))
        }
      })
    }
  }
}
