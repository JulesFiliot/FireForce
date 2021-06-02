// FUNCTIONS

//PUT request to change the fire config
function put_fire_config() {
    const PUT_FIRE_URL = "http://127.0.0.1:8081/config/creation";
    let context = {
        method: 'PUT',
        headers: {
            'Content-type': 'application/json'
          },
        body: JSON.stringify({
            "fireCreationProbability":0.2,
            "fireCreationSleep":20000,
            "fireCreationZone":[
                {"type":"Point","coordinates":[520820,5719535]},
                {"type":"Point","coordinates":[566984,5754240]}],
            "max_INTENSITY":50.0,
            "max_RANGE":50.0
        })
    };
    fetch(PUT_FIRE_URL, context)
        .catch(error => err_callback(error));
}

function put_behavior_config() {
    const PUT_BEHAVIOR_URL = "http://127.0.0.1:8081/config/behavior";
    let context = {
        method: 'PUT',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify({
            "propagationThreshold":5.0,
            "attenuationFactor":0.8,
            "intensityReplicationThreshold":10.0,
            "replicationProbability":0.2,
            "maxFireRange":50.0,
            "maxFireIntensity":50.0,
            "intensity_inc":0.1,
            "sleepTime":5000
        })
    };
    fetch(PUT_BEHAVIOR_URL, context)
        .catch(error => err_callback(error));
}

function fetch_fire() {
    const GET_FIRE_URL="http://127.0.0.1:8081/fire"; 
    let context = {
        method: 'GET'
    };
    
    fetch(GET_FIRE_URL, context)
        .then(reponse => reponse.json().then(body => fireList_callback(body)))
        .catch(error => err_callback(error));
}
   
function err_callback(error){
    console.log(error);
}
   
function fireList_callback(reponse) {
    clear_fire();
    fireList = [];
    for(var i = 0; i < reponse.length; i++) {
        fireList[i] = reponse[i];
    }
    create_fire();
}
   
function create_fire() {
    for(const fire of fireList){
        console.log(fire);
        fire_filter(fire);
    }
}
   
function print_fire(fire) {
    var circle = L.circle([fire.lat, fire.lon],
        {
            color: 'red',
            fillColor: '#f03',
            fillOpacity: fire.intensity/50, // MAX_INTENSITY
            radius: fire.range
        }
    ).addTo(mymap);
    create_fire_popup(circle, fire);
    firePrinted.push(circle);
}

//clear printed fires
function clear_fire() {
    for (i of firePrinted) {
        i.remove();
    }
    firePrinted = [];
}

function create_fire_popup(circle, fire) {
    let popup_text = "Type : " + fire.type + "<br>Intensity : " + fire.intensity + "<br>Range : " + fire.range;
    circle.bindPopup(popup_text);
 }

function fire_filter(fire) {
    if (document.getElementById(fire.type).checked == true) {
        print_fire(fire);
    }
}

// --- CODE ---

let fireList = [];
let firePrinted = [];

var intervalId = window.setInterval(function(){
    fetch_fire();
}, 5000);

put_fire_config();
put_behavior_config();
fetch_fire();
