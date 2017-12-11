import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View
} from 'react-native';

import ExportNative from './ExportNative';

export default class App extends Component<{}> {

    _on_create = () => {
        ExportNative.connect();
    }


  render() {
    return(
        <View onLayout = { () => this._on_create() }>
        </View>
    );
  }
}
