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
import { Header } from 'react-native-elements';
import { List, ListItem } from 'react-native-elements';

export default class ChannelInformation extends Component<{}> {

  list = [
    {
      name: 'Amy Farha'
    },
    {
      name: 'Chris Jackson'
    },
  ]

  state = {
        channelName: '',
        username: '',
        password: '',
        name: '',
        surname: '',
        phoneNum: '',
        isSignUpSuccessful: false,
    }

_getChannelInfo = () => {

    fetch('https://channelbapd.herokuapp.com/create', {
            method: "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTI4MjkzNDksInVzZXJJRCI6MzV9.h0xMAKAJ_MI53KjvxQL7oVugccS-WPfJP5jT67G6aMCC94FEWa6XAfyiGcSmaGAQolWc5oRdwVUbf7oeD5GUtQ'
            },
            body: JSON.stringify({
                channelName: this.state.channelName,
                password: this.state.password,
                isPrivate: this.state.isPrivate
            })
        })
        .then((res) => res.json())
        .then((res) => {
            if (res.code == 0){
              Alert.alert("data", this.state.startTime);
            }
            else
              ExportNative.show(res.message, ExportNative.SHORT);
        })
        .catch((error) => {
            ExportNative.show("There was an error while connecting to the server: ", ExportNative.SHORT);
        });
  }

  render(){
    return(
      <View>
        
        <View style={styles.header}>
          <Text style= {{fontSize: 20, padding: 15, color: '#fff'}}>Channel Information</Text>
        </View>

        <ScrollView behavior='padding'>
          <View style={styles.container}>
            <Text style={styles.info}>User List:</Text>

            <List containerStyle={{marginBottom: 20, alignSelf: 'stretch'}}>
              {
                this.list.map((l, i) => (
                  <ListItem
                    key={i}
                    title={l.name}
                    hideChevron= {true}
                  />
                ))
              }
            </List>

            <Text style={styles.info}>Available Days:</Text>
            <Text style={styles.info}>Available Hours:</Text>

            <TouchableHighlight
                //onPress={this._userSignUp}
                style={styles.logoutButton}
              >
                <Text style={styles.buttonText} >LEAVE CHANNEL</Text>
              </TouchableHighlight>

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
