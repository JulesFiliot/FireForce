// FUNCTIONS FIRE ----------------------------------------------------------------------------------------------------

//Fetch all existing fires
function fetch_fire() {
    const GET_ALL_FIRE_URL="http://127.0.0.1:8092/getAllFire"; // 8081/fire
    let context = {
        method: 'GET'
    };
    fireList = [];
    fetch(GET_ALL_FIRE_URL, context)
        .then(reponse => reponse.json().then(body => fireList_callback(body)))
        .catch(error => err_callback_fire(error));
}

function err_callback_fire() {
    clear_fire();
}

//Called when fetching fires => fill the fireList with all existing fires 
//and call fire_filter function for each fire to print them
function fireList_callback(reponse) {
    for(var i = 0; i < reponse.length; i++) {
        fireList[i] = reponse[i];
    }
    clear_fire();
    for(const fire of fireList){
        fire_filter(fire);
    }
}

//Filter fires and call the function to print only those in the range defined by the user
function fire_filter(fire) {
    if (document.getElementById(fire.type).checked == true) {
        if (fire.intensity >= document.getElementById("intensitymin").value && fire.intensity <= document.getElementById("intensitymax").value) {
            if (fire.range >= document.getElementById("rangemin").value && fire.range <= document.getElementById("rangemax").value) {
                print_fire(fire);
            }
        }
    }
}

//Print on the map the fire given in parameter
function print_fire(fire) {
    var circle = L.circle([fire.lat, fire.lon],
        {
            color: 'red',
            fillColor: '#f03',
            fillOpacity: fire.intensity/55, // MAX_INTENSITY + 10%
            radius: fire.range
        }
    ).addTo(fireGroup);
    firePrinted.push(circle);

    var fireIcon = L.icon({
        iconUrl: 'icons/fire_map.png',    
        iconSize: [34, 34], // size of the icon
        iconAnchor: [17, 30], // point of the icon which will correspond to marker's location
        popupAnchor: [-3, -76] // point from which the popup should open relative to the iconAnchor
    });
    var marker = L.marker([fire.lat, fire.lon], {icon: fireIcon}).addTo(fireGroup);
    firePrinted.push(marker);
}

//clear all printed fires
function clear_fire() {
    //console.log("printed :");
    //console.log(firePrinted);
    for (i of firePrinted) {
        i.remove();
    }
    firePrinted = [];
}

//Fetch fetch object when clicking on a fire marker 
//and calls the fill_popup_fire function to display the corresponding fire info
function fetch_fire_fromMarker(event) {
    clickedArea = event.latlng;

    var lat_marker = event.latlng.lat;
    var lng_marker = event.latlng.lng;
    for (fire of fireList) {
        if (fire.lon == lng_marker && fire.lat == lat_marker) {
            fill_popup_fire(fire);
            return;
        }
    }
}

function fill_popup_fire(fire) {
    document.getElementById("info_fire_type").innerHTML = "Type : " + fire.type;
    document.getElementById("info_fire_intensity").innerHTML = "Intensity : " + fire.intensity;
    document.getElementById("info_fire_range").innerHTML = "Range : " + fire.range;

    document.getElementById("over_map_left").style.display = 'block';
    document.getElementById("info_fire").style.display = 'block';
    document.getElementById("over_map_left_bottom").style.display = 'none';
    document.getElementById("info_station").style.display = 'none';
}

//Updates the fire popup when it's already open
function live_fill_popup_fire(clkA) {
    var lat = clkA.lat;
    var long = clkA.lng;
    var good_fire;
    for (fire of fireList) {
        if (fire.lon == long && fire.lat == lat) {
            good_fire = fire;
            document.getElementById("info_fire_type").innerHTML = "Type : " + good_fire.type;
            document.getElementById("info_fire_intensity").innerHTML = "Intensity : " + good_fire.intensity;
            document.getElementById("info_fire_range").innerHTML = "Range : " + good_fire.range;
        }
    }
}

//deletes all fires and unprint them from map
function reset_fire() {
    const RESET_URL = "http://127.0.0.1:8092/fire/reset";
    let context = {
        method: 'GET',
    };
    fetch(RESET_URL, context)
        .catch(error => err_callback(error));
}

// FUNCTIONS CONFIGURATION ----------------------------------------------------------------------------------------------------

//PUT request to change the main fire configs
function put_creation_config(creationProba, creationSleep) {
    const PUT_CREATION_URL = "http://127.0.0.1:8092/config/creation";
    let context = {
        method: 'PUT',
        headers: {
            'Content-type': 'application/json'
          },
        body: JSON.stringify({
            "fireCreationProbability":creationProba,
            "fireCreationSleep":creationSleep,
            "fireCreationZone":[
                {"type":"Point","coordinates":[520820,5719535]},
                {"type":"Point","coordinates":[566984,5754240]}],
            "max_INTENSITY":50.0,
            "max_RANGE":50.0
        })
    };
    fetch(PUT_CREATION_URL, context)
        .catch(error => err_callback(error));
}

//PUT request to change the configuration of fires and their child spawning rate
function put_behavior_config(childProba) {
    const PUT_BEHAVIOR_URL = "http://127.0.0.1:8092/config/behavior";
    let context = {
        method: 'PUT',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify({
            "propagationThreshold":5.0,
            "attenuationFactor":0.8,
            "intensityReplicationThreshold":10.0,
            "replicationProbability":childProba,
            "maxFireRange":50.0,
            "maxFireIntensity":50.0,
            "intensity_inc":0.1,
            "sleepTime":5000
        })
    };
    fetch(PUT_BEHAVIOR_URL, context)
        .catch(error => err_callback(error));
}

//Fetch parameters from client interface and call put_creation_config to tweak the fire creation parameters
function put_config() {
    creationProba = document.getElementById("creationProba").value;
    creationSleep = document.getElementById("creationSleep").value*1000;
    put_creation_config(creationProba,creationSleep);
}