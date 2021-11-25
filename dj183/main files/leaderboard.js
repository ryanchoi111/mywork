
var config = {
    apiKey: "AIzaSyBg4Zzfmpvje0xEoO_5Ertp635vcxmX64s",
    authDomain: "dj183.firebaseapp.com",
    databaseURL: "https://dj183.firebaseio.com"
};
firebase.initializeApp(config);

// Get a reference to the database service
var database = firebase.database();

function writeUserData(userName, score) {
    firebase.database().ref('scores/' + name).set({
    userName: userName,
    score: score
    });
}
