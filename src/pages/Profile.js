import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View,
  Image,
  ScrollView,
  TouchableHighlight,
  TextInput,
  DeviceEventEmitter
} from 'react-native';
import ExportNative from '../../ExportNative';

export default class Profile extends Component<{}> {

  state = {
        nickname: '',
        username: '',
        password: '',
        name: '',
        surname: '',
        phoneNum: '',
    }

    componentWillMount: function() {
      DeviceEventEmitter.addListener('keyboardWillShow', function(e: Event);
    }

    _changeUserData = () => {

      fetch('https://channelbapd.herokuapp.com/profile', {
              method: "POST",
              headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json',
                  'Authorization': 'eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTI4MjkzNDksInVzZXJJRCI6MzV9.h0xMAKAJ_MI53KjvxQL7oVugccS-WPfJP5jT67G6aMCC94FEWa6XAfyiGcSmaGAQolWc5oRdwVUbf7oeD5GUtQ'
              },
              body: JSON.stringify({
                  nickname: this.state.nickname,
                  password: this.state.password,
                  username: this.state.username,
                  name: this.state.name,
                  surname: this.state.surname,
                  phoneNum: this.state.phoneNum
              })
          })
          .then((res) => res.json())
          .then((res) => {
              if (res.code == 0){
                ExportNative.show("Data updated successfully.", ExportNative.SHORT);
              }
              else
                ExportNative.show(res.message, ExportNative.SHORT);
          })
          .catch((error) => {
              ExportNative.show("There was an error while connecting to the server: ", ExportNative.SHORT);
          });
  }

  _logout = () => {
    ExportNative.logout();
  }

  _goBack = () => {
    ExportNative.go_back_to_channel_list();
  }

  render(){
    return(
      <View>

        <View style={styles.header}>
          <Text style= {{fontSize: 20, padding: 15, color: '#fff'}}>Profile</Text>
        </View>

        <ScrollView behavior='padding'>
          <View style={styles.container}>
            <Image
              style={{width: 60, height: 60}}
              source={require("../img/user_icon.png")} />

              <TextInput
                style={styles.input}
                placeholder="Nickname"
                returnKeyType="next"
                onSubmitEditing={() => this.emailInput.focus()}
                blurOnSubmit= {true}
                multiline= {false}
                onChangeText={(nickname) => this.setState({nickname})}
              />

              <TextInput
                style={styles.input}
                placeholder="E-mail"
                returnKeyType="next"
                onSubmitEditing={() => this.passwordInput.focus()}
                ref={(input) => this.emailInput = input}
                keyboardType="email-address"
                blurOnSubmit= {true}
                multiline= {false}
                onChangeText={(username) => this.setState({username})}
              />

              <TextInput
                style={styles.input}
                placeholder="Password"
                returnKeyType="next"
                secureTextEntry={true}
                onSubmitEditing={() => this.nameInput.focus()}
                ref={(input) => this.passwordInput = input}
                blurOnSubmit= {true}
                multiline= {false}
                onChangeText={(password) => this.setState({password})}
              />

              <TextInput
                style={styles.input}
                placeholder="Name"
                returnKeyType="next"
                onSubmitEditing={() => this.surnameInput.focus()}
                ref={(input) => this.nameInput = input}
                blurOnSubmit= {true}
                multiline= {false}
                onChangeText={(name) => this.setState({name})}
              />

              <TextInput
                style={styles.input}
                placeholder="Surname"
                returnKeyType="next"
                onSubmitEditing={() => this.phoneNumInput.focus()}
                ref={(input) => this.surnameInput = input}
                blurOnSubmit= {true}
                multiline= {false}
                onChangeText={(surname) => this.setState({surname})}
              />

              <TextInput
                style={styles.input}
                placeholder="Phone Number"
                returnKeyType="go"
                ref={(input) => this.phoneNumInput = input}
                keyboardType="numeric"
                blurOnSubmit= {true}
                multiline= {false}
                onChangeText={(phoneNum) => this.setState({phoneNum})}
              />

            <View style= {{paddingBottom: 70, alignSelf: 'stretch'}}>
              <View style={styles.rowButton}>
                <TouchableHighlight
                  onPress={ () => this._goBack() }
                  style={styles.cancelButton}
                  multiline= {false}
                >
                  <Text style={styles.cancelButtonText} >CANCEL</Text>
                </TouchableHighlight>

                <TouchableHighlight
                  onPress={ () => this._changeUserData() }
                  style={styles.saveButton}
                >
                  <Text style={styles.buttonText} >SAVE</Text>
                </TouchableHighlight>
              </View>

              <TouchableHighlight
                onPress={ () => this._logout() }
                style={styles.logoutButton}
              >
                <Text style={styles.buttonText} >LOGOUT</Text>
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
    paddingTop: 20
  },
  input: {
    alignSelf: 'stretch',
    padding: 10,
    margin: 5,
    color: '#ff8982',
    backgroundColor: '#ffff',
    fontSize: 15,
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
  saveButton: {
      backgroundColor: '#64b9d1',
      borderRadius: 5,
      borderWidth: 1,
      borderColor: '#fff',
      marginRight: 10,
      marginLeft: 5,
      marginBottom: 5,
      marginTop: 5,
      padding: 20,
  },
  logoutButton: {
      backgroundColor: '#ff5555',
      borderRadius: 5,
      borderWidth: 1,
      borderColor: '#fff',
      padding: 16,
      margin: 10,
      alignSelf: 'stretch',
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
