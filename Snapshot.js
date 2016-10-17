'use strict';

let React = require('react-native');

let {
  NativeModules
} = React;

var API = NativeModules.RNGMapsSnapshotModule;

class Snapshot {
  static snapShot(points, callback) {
    callback = callback || (()=>{});
    API.snapshot(points, callback);
  }
}

module.exports = Snapshot;
