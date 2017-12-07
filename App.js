import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View
} from 'react-native';
import bridgeBetweenAndroidAndReactNative from './ConnectToAndroid';
import Profile from './src/pages/Profile';

export default class App extends Component<{}> {
  render() {
    return (
      //bridgeBetweenAndroidAndReactNative();
      <Profile/>
    );
  }
}
