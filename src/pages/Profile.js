import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View,
  Image,
  KeyboardAvoidingView,
  TouchableHighlight,
  TextInput
} from 'react-native';

export default class Profile extends Component<{}> {

  state = {
        nickname: '',
        username: '',
        password: '',
        name: '',
        surname: '',
        phoneNum: '',
        isSignUpSuccessful: false
    }

  render(){
    return(
      <KeyboardAvoidingView behavior='padding' style={styles.container}>
        <Image
          style={{width: 50, height: 50}}
          source={require("../img/user_icon.png")} />

          <TextInput
            style={styles.input}
            placeholder="Nickname"
            returnKeyType="next"
            onSubmitEditing={() => this.emailInput.focus()}
            placeholderTextColor= "rgba(255, 137, 130, 0.7)"
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
            placeholderTextColor= "rgba(255, 137, 130, 0.7)"
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
            placeholderTextColor= "rgba(255, 137 ,130, 0.7)"
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
            placeholderTextColor= "rgba(255, 137, 130, 0.7)"
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
            placeholderTextColor= "rgba(255, 137, 130, 0.7)"
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
            placeholderTextColor= "rgba(255, 137, 130, 0.7)"
            blurOnSubmit= {true}
            multiline= {false}
            onChangeText={(phoneNum) => this.setState({phoneNum})}
          />

          <View style={styles.rowButton}>
            <TouchableHighlight
              //onPress={this._userSignUp}
              style={styles.cancelButton}
            >
              <Text style={styles.buttonText} >CANCEL</Text>
            </TouchableHighlight>

            <TouchableHighlight
              //onPress={this._userSignUp}
              style={styles.saveButton}
            >
              <Text style={styles.buttonText} >SAVE</Text>
            </TouchableHighlight>
          </View>

          <TouchableHighlight
            //onPress={this._userSignUp}
            style={styles.logoutButton}
          >
            <Text style={styles.buttonText} >LOGOUT</Text>
          </TouchableHighlight>

      </KeyboardAvoidingView>
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
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
  navBarStyle: {
      backgroundColor: '#006973',
  },
  navBarTitle: {
    color:'#ffff'
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
      backgroundColor: '#64b9d1',
      borderRadius: 8,
      borderWidth: 1,
      borderColor: '#fff',
      marginLeft: 10,
      marginRight: 5,
      marginBottom: 5,
      marginTop: 5,
      padding: 20,
  },
  saveButton: {
      backgroundColor: '#ff8982',
      borderRadius: 8,
      borderWidth: 1,
      borderColor: '#fff',
      marginRight: 10,
      marginLeft: 5,
      marginBottom: 5,
      marginTop: 5,
      padding: 20,
  },
  logoutButton: {
      backgroundColor: '#c5162c',
      borderRadius: 8,
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
});
