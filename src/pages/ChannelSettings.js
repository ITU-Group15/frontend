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
} from 'react-native';
import { CheckBox } from 'react-native-elements';
import DatePicker from 'react-native-datepicker';

export default class ChannelSettings extends Component<{}> {
  
  state = {
    nickname: '',
    username: '',
    password: '',
    name: '',
    surname: '',
    phoneNum: '',
    isSignUpSuccessful: false,
    channelPassword: '',
    checked: false,
    mo: false,
    tu: false,
    we: false,
    th: false, 
    fr: false,
    sa: false,
    su: false,
    al: false,
    alD: false,
    startTime: '',
    endTime: '',
    req_none: false,
    req_name: false,
    req_surname: false,
    req_number: false,
    req_email: false
  }

  _checkbox_change = (day) => {
    if(day=='pr'){
      if (this.state.checked == true ){
        this.setState({ checked: false });
      }
      else
          this.setState({ checked: true });
    }

    else if(day == 'al'){
      if (this.state.al == true ){
        this.setState({ al: false });
      }
      else
          this.setState({ al: true });
    }

    else if(day == 'mo'){
      if (this.state.mo == true ){
        this.setState({ mo: false });
      }
      else
          this.setState({ mo: true });
    }

    else if(day == 'tu'){
      if (this.state.tu == true ){
        this.setState({ tu: false });
      }
      else
          this.setState({ tu: true });
    }

    else if(day == 'we'){
      if (this.state.we == true ){
        this.setState({ we: false });
      }
      else
          this.setState({ we: true });
    }

    else if(day == 'th'){
      if (this.state.th == true ){
        this.setState({ th: false });
      }
      else
          this.setState({ th: true });
    }

    else if(day == 'fr'){
      if (this.state.fr == true ){
        this.setState({ fr: false });
      }
      else
          this.setState({ fr: true });
    }

    else if(day == 'sa'){
      if (this.state.sa == true ){
        this.setState({ sa: false });
      }
      else
          this.setState({ sa: true });
    }

    else if(day == 'su'){
      if (this.state.su == true ){
        this.setState({ su: false });
      }
      else
          this.setState({ su: true });
    }

    else if(day == 'alD'){
      if (this.state.alD == true ){
        this.setState({ alD: false });
      }
      else
          this.setState({ alD: true });
    }

    else if(day == 'req_none'){
      if (this.state.req_none == true ){
        this.setState({ req_none: false });
      }
      else
          this.setState({ req_none: true });
    }

    else if(day == 'req_name'){
      if (this.state.req_name == true ){
        this.setState({ req_name: false });
      }
      else
          this.setState({ req_name: true });
    }

    else if(day == 'req_surname'){
      if (this.state.req_surname == true ){
        this.setState({ req_surname: false });
      }
      else
          this.setState({ req_surname: true });
    }

    else if(day == 'req_number'){
      if (this.state.req_number == true ){
        this.setState({ req_number: false });
      }
      else
          this.setState({ req_number: true });
    }

    else if(day == 'req_email'){
      if (this.state.req_email == true ){
        this.setState({ req_email: false });
      }
      else
          this.setState({ req_email: true });
    }

  }

  _editChannel = () => {

    fetch('https://channelbapd.herokuapp.com/create', {
            method: "POST",
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
              ExportNative.show("Channel is edited successfully.", ExportNative.SHORT);
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
          <Text style= {{fontSize: 20, padding: 15, color: '#fff'}}>Channel Settings</Text>
        </View>

        <ScrollView behavior='padding'>
          <View style={styles.container}>

          <TextInput
                style={styles.input}
                placeholder="Channel Name"
                returnKeyType="next"
                blurOnSubmit= {true}
                multiline= {false}
          />

          <View style={styles.rowButton}>
              <CheckBox
                checked={this.state.checked}
                title= 'Public'
                containerStyle= {{borderColor: '#fff', backgroundColor: '#fff'}}
                checkedTitle= 'Private'
                onPress= {() => this._checkbox_change('pr')}
                checkedColor= '#64b9d1'
                uncheckedColor= '#000'
                />

              <TextInput
                style={styles.passwordInput}
                placeholder="Password"
                returnKeyType="next"
                secureTextEntry={true}
                blurOnSubmit= {true}
                multiline= {false}
                onChangeText={(channelPassword) => this.setState({channelPassword})}
              />
            </View>

          <Text style={styles.info}>Available Days:</Text>

          <View style={{alignSelf: 'flex-start'}}>

            <CheckBox
              checked={this.state.al}
              title= 'All Week'
              containerStyle= {{borderColor: '#fff', backgroundColor: '#fff'}}
              onPress= {() => this._checkbox_change('al')}
              checkedColor= '#ff5555'
              uncheckedColor= '#000'
              textStyle={{fontSize: 12}}
            />

            <CheckBox
              checked={this.state.mo}
              title= 'Monday'
              containerStyle= {{borderColor: '#fff', backgroundColor: '#fff'}}
              onPress= {() => this._checkbox_change('mo')}
              checkedColor= '#ff5555'
              uncheckedColor= '#000'
              textStyle={{fontSize: 12}}
            />

            <CheckBox
              checked={this.state.tu}
              title= 'Tuesday'
              containerStyle= {{borderColor: '#fff', backgroundColor: '#fff'}}
              onPress= {() => this._checkbox_change('tu')}
              checkedColor= '#ff5555'
              uncheckedColor= '#000'
              textStyle={{fontSize: 12}}
            />

            <CheckBox
              checked={this.state.we}
              title= 'Wednesday'
              containerStyle= {{borderColor: '#fff', backgroundColor: '#fff'}}
              onPress= {() => this._checkbox_change('we')}
              checkedColor= '#ff5555'
              uncheckedColor= '#000'
              textStyle={{fontSize: 12}}
            />

            <CheckBox
              checked={this.state.th}
              title= 'Thursday'
              containerStyle= {{borderColor: '#fff', backgroundColor: '#fff'}}
              onPress= {() => this._checkbox_change('th')}
              checkedColor= '#ff5555'
              uncheckedColor= '#000'
              textStyle={{fontSize: 12}}
            />

            <CheckBox
              checked={this.state.fr}
              title= 'Friday'
              containerStyle= {{borderColor: '#fff', backgroundColor: '#fff'}}
              onPress= {() => this._checkbox_change('fr')}
              checkedColor= '#ff5555'
              uncheckedColor= '#000'
              textStyle={{fontSize: 12}}
            />

            <CheckBox
              checked={this.state.sa}
              title= 'Saturday'
              containerStyle= {{borderColor: '#fff', backgroundColor: '#fff'}}
              onPress= {() => this._checkbox_change('sa')}
              checkedColor= '#ff5555'
              uncheckedColor= '#000'
              textStyle={{fontSize: 12}}
            />

            <CheckBox
              checked={this.state.su}
              title= 'Sunday'
              containerStyle= {{borderColor: '#fff', backgroundColor: '#fff'}}
              onPress= {() => this._checkbox_change('su')}
              checkedColor= '#ff5555'
              uncheckedColor= '#000'
              textStyle={{fontSize: 12}}
            />
          </View>

          <Text style={styles.info}>Available Hours:</Text>
          <View style={{alignSelf: 'flex-start', justifyContent: 'center'}}>
            <CheckBox
                checked={this.state.alD}
                title= 'All Day'
                containerStyle= {{borderColor: '#fff', backgroundColor: '#fff'}}
                onPress= {() => this._checkbox_change('alD')}
                checkedColor= '#ff5555'
                uncheckedColor= '#000'
                textStyle={{fontSize: 12}}
            />
            
            <View style={{flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between'}}>
              <DatePicker
                style={{width: 100}}
                date={this.state.startTime}
                mode="time"
                format="HH:mm"
                is24Hour= {true}
                confirmBtnText="Confirm"
                cancelBtnText="Cancel"
                minuteInterval={10}
                showIcon={false}
                onDateChange={(startTime) => {this.setState({startTime})}}
              />

              <DatePicker
                style={{width: 100}}
                date={this.state.endTime}
                mode="time"
                format="HH:mm"
                is24Hour= {true}
                confirmBtnText="Confirm"
                cancelBtnText="Cancel"
                minuteInterval={10}
                showIcon={false}
                onDateChange={(endTime) => {this.setState({endTime})}}
              />
            </View>
          </View>

          <Text style={styles.info}>Required Informations:</Text>
          <View style={{alignSelf: 'flex-start'}}>

            <CheckBox
              checked={this.state.req_none}
              title= 'None'
              containerStyle= {{borderColor: '#fff', backgroundColor: '#fff'}}
              onPress= {() => this._checkbox_change('req_none')}
              checkedColor= '#ff5555'
              uncheckedColor= '#000'
              textStyle={{fontSize: 12}}
            />

            <CheckBox
              checked={this.state.req_name}
              title= 'Name'
              containerStyle= {{borderColor: '#fff', backgroundColor: '#fff'}}
              onPress= {() => this._checkbox_change('req_name')}
              checkedColor= '#ff5555'
              uncheckedColor= '#000'
              textStyle={{fontSize: 12}}
            />

            <CheckBox
              checked={this.state.req_surname}
              title= 'Surname'
              containerStyle= {{borderColor: '#fff', backgroundColor: '#fff'}}
              onPress= {() => this._checkbox_change('req_surname')}
              checkedColor= '#ff5555'
              uncheckedColor= '#000'
              textStyle={{fontSize: 12}}
            />

            <CheckBox
              checked={this.state.req_number}
              title= 'Phone Number'
              containerStyle= {{borderColor: '#fff', backgroundColor: '#fff'}}
              onPress= {() => this._checkbox_change('req_number')}
              checkedColor= '#ff5555'
              uncheckedColor= '#000'
              textStyle={{fontSize: 12}}
            />

            <CheckBox
              checked={this.state.req_email}
              title= 'Email'
              containerStyle= {{borderColor: '#fff', backgroundColor: '#fff'}}
              onPress= {() => this._checkbox_change('req_email')}
              checkedColor= '#ff5555'
              uncheckedColor= '#000'
              textStyle={{fontSize: 12}}
            />
          </View>

        <View style= {{paddingBottom: 70, alignSelf: 'stretch'}}>
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
              style={styles.saveButton}
            >
              <Text style={styles.buttonText} >CREATE</Text>
            </TouchableHighlight>
          </View>

          <TouchableHighlight
            //onPress={this._userSignUp}
            style={styles.logoutButton}
          >
            <Text style={styles.buttonText} >DELETE CHANNEL</Text>
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
    fontSize: 15,
  },
  input: {
    alignSelf: 'stretch',
    padding: 10,
    margin: 5,
    color: '#ff8982',
    backgroundColor: '#ffff',
    fontSize: 15,
  },
  passwordInput: {
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
      width: 110
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
