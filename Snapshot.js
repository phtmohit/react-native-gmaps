'use strict';

let React = require('react-native');

let {
  NativeModules
} = React;

var API = NativeModules.RNGMapsSnapshotModule;

class Snapshot {
  static snapShot(callback) {
    callback = callback || (()=>{});
    API.snapshot(callback);
  }
}

module.exports = Snapshot;
