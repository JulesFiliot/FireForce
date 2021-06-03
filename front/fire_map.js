// FUNCTIONS CONFIGURATION ----------------------------------------------------------------------------------------------------

//PUT request to change the main fire configs
function put_creation_config(creationProba, creationSleep) {
    console.log(creationProba);
    const PUT_CREATION_URL = "http://127.0.0.1:8081/config/creation";
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
            "replicationProbability":0.0,
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

//deletes all fires and unprint them from map
function reset_fire() {
    const RESET_URL = "http://127.0.0.1:8081/fire/reset";
    let context = {
        method: 'GET',
    };
    fetch(RESET_URL, context)
        .catch(error => err_callback(error));
}

// Reset all vehicle and unprint them from map
function reset_vehicle() {
    const GET_VEHICLE_URL = "http://127.0.0.1:8081/vehicle";
    let context = {
        method: 'GET',
    };
    fetch(GET_VEHICLE_URL, context)
        .then(response => response.json().then(body => vehiclesList_callback_reset(body)))
        .catch(error => err_callback(error));
}

function vehiclesList_callback_reset(response) {    
    clear_vehicles();
    vehicleList = [];
    for(var i = 0; i < response.length; i++) {
        vehicleList[i] = response[i];
    }
    for(const vehicle of vehicleList) {
        delete_vehicle(vehicle.id);
    }
}



// FUNCTIONS FIRE ----------------------------------------------------------------------------------------------------

//Fetch all existing fires
function fetch_fire() {
    const GET_FIRE_URL="http://127.0.0.1:8081/fire"; 
    let context = {
        method: 'GET'
    };
    fetch(GET_FIRE_URL, context)
        .then(reponse => reponse.json().then(body => fireList_callback(body)))
        .catch(error => err_callback(error));
}

//Called when fetching fires => fill the fireList with all existing fires 
//and call fire_filter function for each fire to print them
function fireList_callback(reponse) {
    clear_fire();
    fireList = [];
    for(var i = 0; i < reponse.length; i++) {
        fireList[i] = reponse[i];
    }
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
            fillOpacity: fire.intensity/50, // MAX_INTENSITY
            radius: fire.range
        }
    ).addTo(mymap);
    create_fire_popup(circle, fire);
    firePrinted.push(circle);
}

//Display a popup when we click on a fire on the map. The popup display pertinents fire attributes 
function create_fire_popup(circle, fire) {
    let popup_text = "Type : " + fire.type + "<br>Intensity : " + fire.intensity + "<br>Range : " + fire.range;
    circle.bindPopup(popup_text);
}

//clear all printed fires
function clear_fire() {
    for (i of firePrinted) {
        i.remove();
    }
    firePrinted = [];
}



// FUNCTIONS VEHICLES ----------------------------------------------------------------------------------------------------

//Create vehicle from interface. Vehicle is created using create_vehicle
function vehicle_creator() {
    var vehicle_type = document.getElementById("vehicle_type").value;
    var liquid_type = document.getElementById("liquid_type").value;
    var lat = Math.random()*(45.7941125 - 45.7145454) + 45.7145454;
    var lon = Math.random()*(4.9266428 - 4.7736324) + 4.7736324;
    create_vehicle(vehicle_type, liquid_type, lon, lat);
}

/*
		this.id=id;
		this.lon = lon;
		this.lat = lat;
		this.type = type;
		this.efficiency = efficiency;
		this.liquidType = liquidType;
		this.liquidQuantity = liquidQuantity;
		this.liquidConsumption = liquidConsumption;
		this.fuel = fuel;
		this.fuelConsumption = fuelConsumption;
		this.crewMember = crewMember;
		this.crewMemberCapacity = crewMemberCapacity;
		this.facilityRefID = facilityRefID;

*/

//Uses a POST request to create a vehicle given some basic parameters of the vehicle
function create_vehicle(vehicle_type, liquid_type, lon, lat) {
    const POST_VEHICLE_URL = "http://127.0.0.1:8081/vehicle";
    let context = {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify({
            "lon":lon,
            "lat":lat,
            "type":vehicle_type,
            "liquidType":liquid_type
        })
    };
    fetch(POST_VEHICLE_URL, context)
        .catch(error => err_callback(error));
}

//GET request to fetch all existing vehicles. Calls the vehiclesList_callback when vehicles are fetched
function fetch_vehicles() {
    const GET_VEHICLE_URL = "http://127.0.0.1:8081/vehicle";
    let context = {
        method: 'GET',
    };
    fetch(GET_VEHICLE_URL, context)
        .then(response => response.json().then(body => vehiclesList_callback(body)))
        .catch(error => err_callback(error));
}

//Takes the list of all vehicles as parameter. Calls vehicle_filter functioin for each vehicle to print them 
function vehiclesList_callback(response) {    
    clear_vehicles();
    vehicleList = [];
    for(var i = 0; i < response.length; i++) {
        vehicleList[i] = response[i];
    }
    for(const vehicle of vehicleList) {
        vehicle_filter(vehicle);
    }
}

//Calls the print_vehicle function to print each vehicle which fits the filter parameters provided by the user
function vehicle_filter(vehicle) {
    print_vehicle(vehicle);
}

//Displays on the map the vehicle given in parameter
function print_vehicle(vehicle) {
    var circle = L.circle([vehicle.lat, vehicle.lon],
        {
            color: 'blue',
            fillColor: 'blue',
            fillOpacity: 100,
            radius: 5
        }
    ).addTo(mymap);
    vehiclePrinted.push(circle);
}

//clear printed vehicles
function clear_vehicles() {
    for (i of vehiclePrinted) {
        i.remove();
    }
    vehiclePrinted = [];
}

function delete_vehicle(id_vehicle) {
    const DELETE_VEHICLE_URL = "http://127.0.0.1:8081/vehicle/"+id_vehicle;
    let context = {
        method: 'DELETE',
    };
    fetch(DELETE_VEHICLE_URL, context)
        .catch(error => err_callback(error));
}



// FUNCTIONS OTHERS ----------------------------------------------------------------------------------------------------

//Hides the interface given in parameters (if class name is interface). Also hides all its childs
function hide_interface(obj) {
    var el = document.getElementById(obj);
    if (el.style.display == 'none') {
        var interface = document.getElementsByClassName("interface");
        for (i of interface) {
            i.style.display = 'none';
        }
        el.style.display = 'block';
    } else {
        el.style.display = 'none';
    }
}



// CODE ----------------------------------------------------------------------------------------------------

let fireList = [];
let firePrinted = [];
let vehicleList = [];
let vehiclePrinted = [];

//Instructions called every 5000 ms
var intervalId = window.setInterval(function(){
    fetch_fire();
    fetch_vehicles();
}, 1000);

//Functions called every time the page is refreshed
//create_vehicle(0, 1, 4.5, 45.5);
fetch_fire();
fetch_vehicles();
