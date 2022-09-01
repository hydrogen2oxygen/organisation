import {Component, OnInit} from '@angular/core';
import { Network } from "vis-network/peer/esm/vis-network";
import { DataSet } from "vis-data/peer/esm/vis-data"
import {EdgeVis, NodeVis} from "../../domains/NetworkingDomains";

@Component({
  selector: 'app-diagram',
  templateUrl: './diagram.component.html',
  styleUrls: ['./diagram.component.scss']
})
export class DiagramComponent implements OnInit {

  constructor() {}

  ngOnInit(): void {
    let nodes = new DataSet<NodeVis>([
      { id: 1, label: "Node 1" },
      { id: 2, label: "Node 2" },
      { id: 3, label: "Node 3" },
      { id: 4, label: "Node 4" },
      { id: 5, label: "Node 5" },
    ]);

    let edges: DataSet<EdgeVis> = new DataSet([
      { id: 1, from: 1, to: 3 },
      { id: 2, from: 1, to: 2 },
      { id: 3, from: 2, to: 4 },
      { id: 4, from: 2, to: 5 }
    ]);

    // create a network
    let container = document.getElementById("mynetwork");
    let data = {
      nodes: nodes,
      edges: edges
    };
    let options = {};
    if (container) {
      let network = new Network(container, data, options);
    }
  }

}
