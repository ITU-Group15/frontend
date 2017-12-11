import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View,
  Image,
  ScrollView,
  TouchableHighlight,
  TextInput
} from 'react-native';
import { CheckBox } from 'react-native-elements';

export default class JoinToAChannel extends Component<{}> {

  state = {
        nickname: '',
        username: '',
        password: '',
        name: '',
        surname: '',
        phoneNum: '',
        channelPassword: '',
        checked: false
  }

  _checkbox_change = () => {

    if (this.state.checked == true ){
      this.setState({ checked: false });
    }
    else
        this.setState({ checked: true });
  }

  render(){
    return(
      <View>

        <View style={styles.header}>
          <Text style= {{fontSize: 20, padding: 15, color: '#fff'}}>Join to A Channel</Text>
        </View>

        <ScrollView behavior='padding'>
          <View style={styles.container}>
            <Text style={styles.info}>Channel Name:</Text>
            <Text style={styles.info}>Available Days:</Text>
            <Text style={styles.info}>Available Hours:</Text>

            <View style={styles.rowButton}>
              <CheckBox
                checked={this.state.checked}
                title= 'Public'
                containerStyle= {{borderColor: '#fff', backgroundColor: '#fff'}}
                checkedIcon= 'check-square-o'
                uncheckedIcon= 'square-o'
                checkedTitle= 'Private'
                onPress= {this._checkbox_change}
                checkedColor= '#64b9d1'
                uncheckedColor= '#000'
                />

              <TextInput
                style={styles.input}
                placeholder="Password"
                returnKeyType="next"
                secureTextEntry={true}
                blurOnSubmit= {true}
                multiline= {false}
                onChangeText={(channelPassword) => this.setState({channelPassword})}
              />
            </View>

            <Text style={styles.info}>Required Informations:</Text>

            <View style={styles.rowButton}>
              <TouchableHighlight
                //onPress={this._userSignUp}
                style={styles.cancelButton}
                multiline= {false}
              >
                <Text style={styles.cancelButtonText} >CANCEL</Text>
              </TouchableHighlight>

              <TouchableHighlight
                //onPress={this._userSignUp}
                style={styles.joinButton}
              >
                <Text style={styles.buttonText} >JOIN</Text>
              </TouchableHighlight>
            </View>

          </View>
        </ScrollView>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff',
  },
  info: {
    alignSelf: 'stretch',
    padding: 10,
    margin: 5,
    color: '#000',
    backgroundColor: '#ffff',
    fontSize: 18,
  },
  input: {
    alignSelf: 'stretch',
    padding: 10,
    margin: 5,
    color: '#ff8982',
    backgroundColor: '#ffff',
    fontSize: 15,
    width: 150
  },
  rowButton: {
    flexDirection: 'row',
    alignSelf: 'stretch',
    justifyContent: 'space-between',
  },
  cancelButton: {
      backgroundColor: 'lightgrey',
      borderRadius: 5,
      borderWidth: 1,
      borderColor: '#fff',
      padding: 20,
      marginRight: 5,
      marginLeft: 10,
      marginBottom: 5,
      marginTop: 5,
      width: 110
  },
  cancelButtonText: {
    color: '#000',
    textAlign:'center',
    fontSize: 16,
  },
  joinButton: {
      backgroundColor: '#ff5555',
      borderRadius: 5,
      borderWidth: 1,
      borderColor: '#fff',
      marginRight: 10,
      marginLeft: 5,
      marginBottom: 5,
      marginTop: 5,
      padding: 20,
      width: 110
  },
  buttonText: {
      color: '#fff',
      textAlign:'center',
      fontSize: 16,
  },
  routeStyle: {
      justifyContent: 'center',
      marginBottom: 10,
      paddingBottom: 50,
      fontSize: 16,
      textAlign:'center',
      color: '#005662',
  },
  header: {
    backgroundColor: '#2c89a0',
    height: 54,
    alignSelf: 'stretch'
  }
});
