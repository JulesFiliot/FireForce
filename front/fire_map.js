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

//DEPRECIATED
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

//Fetch fetch object when clicking on a fire marker 
//and calls the fill_popup_fire function to display the corresponding fire info
function fetch_fire_fromMarker(event) {
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
    document.getElementById("over_map_left_bottom").style.display = 'none';}



// FUNCTIONS VEHICLES ----------------------------------------------------------------------------------------------------

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

//GET request to fetch a vehicle infos using its ID in URL parameter. Call the function to update the vehicle.
function fetch_vehicle_byId(id_vehicle, vehicle_update_callback) {
    const GET_VEHICLE_URL = "http://127.0.0.1:8081/vehicle/"+id_vehicle;
    let context = {
        method: 'GET',
    };
    fetch(GET_VEHICLE_URL, context)
        .then(response => response.json().then(body => vehicle_update_callback(body)))
        .catch(error => err_callback(error));
}

//GET request to fetch a vehicle infos using its ID in URL parameter. 
//Call the function to only update the left visual panel displaying vehicle infos.
function fetch_vehicle_byId_visu(id_vehicle, fill_popup_vehicle) {
    const GET_VEHICLE_URL = "http://127.0.0.1:8081/vehicle/"+id_vehicle;
    let context = {
        method: 'GET',
    };
    fetch(GET_VEHICLE_URL, context)
        .then(response => response.json().then(body => {console.log(body); fill_popup_vehicle(body);}))
        .catch(error => err_callback(error));
}

//Delete the vehicule corresponding to the given id in parameters 
function delete_vehicle(id_vehicle) {
    const DELETE_VEHICLE_URL = "http://127.0.0.1:8081/vehicle/"+id_vehicle;
    let context = {
        method: 'DELETE',
    };
    fetch(DELETE_VEHICLE_URL, context)
        .catch(error => err_callback(error));
}

//PUT request to update the vehicle infos given in parameters. 
//Then calls the fetch_vehicle_byId_visu to update vehicle info panel 
function modify_vehicle(id, vehicle_type, fuel, fuelConsumption, liquidQuantity, liquid_type, liquidConsumption,lon, lat, 
    crewMember, crewMemberCapacity, efficiency, facilityRefID) {

    const PUT_VEHICLE_URL = "http://127.0.0.1:8081/vehicle/" + id;
    let context = {
        method: 'PUT',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify({
            "id":id,
            "efficiency":efficiency,
            "liquidConsumption":liquidConsumption,
            "fuelConsumption":fuelConsumption,
            "crewMember":crewMember,
            "crewMemberCapacity":crewMemberCapacity,
            "facilityRefID":facilityRefID,
            "lon":lon,
            "lat":lat,
            "type":vehicle_type,
            "liquidType":liquid_type,
            "liquidQuantity":liquidQuantity,
            "fuel":fuel
        })
    };
    //return if needed
    fetch(PUT_VEHICLE_URL, context)
        .then(response => {fetch_vehicle_byId_visu(id, fill_popup_vehicle);})
        .catch(error => err_callback(error));
}

//Create vehicle from interface. Vehicle is created using create_vehicle
function vehicle_creator() {
    var vehicle_type = document.getElementById("vehicle_type").value;
    var liquid_type = document.getElementById("liquid_type").value;
    var lat = Math.random()*(45.7941125 - 45.7145454) + 45.7145454;
    var lon = Math.random()*(4.9266428 - 4.7736324) + 4.7736324;
    create_vehicle(vehicle_type, liquid_type, lon, lat);
}

//Takes the list of all vehicles as parameter. Calls vehicle_filter function for each vehicle to print them
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
    if (document.getElementById(vehicle.type).checked == true) {
        if (document.getElementById(vehicle.liquidType).checked == true) {
            print_vehicle(vehicle);
        }
    }
}

//Displays on the map the vehicle given in parameter
function print_vehicle(vehicle) {
    /*var circle = L.circle([vehicle.lat, vehicle.lon],
        {
            color: 'blue',
            fillColor: 'blue',
            fillOpacity: 1,
            radius: 50
        }
    ).addTo(vehiclesGroup);
    vehiclePrinted.push(circle);*/

    var fireIcon = L.icon({
        iconUrl: 'icons/car_map.png',    
        iconSize: [51, 51], // size of the icon
        iconAnchor: [25.5, 40], // point of the icon which will correspond to marker's location
        popupAnchor: [-3, -76] // point from which the popup should open relative to the iconAnchor
    });
    var marker = L.marker([vehicle.lat, vehicle.lon], {icon: fireIcon}).addTo(vehiclesGroup);
    vehiclePrinted.push(marker);
}

//clear printed vehicles
function clear_vehicles() {
    for (i of vehiclePrinted) {
        i.remove();
    }
    vehiclePrinted = [];
}

//Fetch vehicle object when clicking on a vehicle marker 
//and calls the fill_popup_vehicle function to display the corresponding vehicle info
function fetch_vehicle_fromMarker(event) {
    var lat_marker = event.latlng.lat;
    var lng_marker = event.latlng.lng;
    for (vehicle of vehicleList) {
        if (vehicle.lon == lng_marker && vehicle.lat == lat_marker) {
            fill_popup_vehicle(vehicle);
            return;
        }
    }
}

function fill_popup_vehicle(vehicle) {
    

    console.log(vehicle);
    document.getElementById("info_vehicle_id").value = vehicle.id;
    document.getElementById("info_vehicle_type").innerHTML = "Type : " + vehicle.type;
    document.getElementById("info_vehicle_fuel").innerHTML = "Fuel quantity : " + vehicle.fuel;
    document.getElementById("info_vehicle_liquid_type").innerHTML = "Liquid type : " + vehicle.liquidType;
    document.getElementById("info_vehicle_liquid_quantity").innerHTML = "Liquid quantity : " + vehicle.liquidQuantity;

    document.getElementById("over_map_left").style.display = 'block';
    document.getElementById("over_map_left_bottom").style.display = 'block';
    document.getElementById("info_vehicle_update").style.display = 'none';
    document.getElementById("info_vehicle").style.display = 'block';
    document.getElementById("info_fire").style.display = 'none';
}

function delete_vehicle(id_vehicle) {
    const DELETE_VEHICLE_URL = "http://127.0.0.1:8081/vehicle/"+id_vehicle;
    let context = {
        method: 'DELETE',
    };
    fetch(DELETE_VEHICLE_URL, context)
        .catch(error => err_callback(error));
}

function button_delete_vehicle() {
    id_vehicle = document.getElementById("info_vehicle_id").value;
    delete_vehicle(id_vehicle);
    document.getElementById("over_map_left").style.display = 'none';
}

//Triggers the fecth vehicle by id then calls the vehicle_update_callback to update vehicle attributes
function button_update_vehicle() {
    //get vehicle id
    let vId = document.getElementById("info_vehicle_id").value;
    fetch_vehicle_byId(vId, vehicle_update_callback);
}

function vehicle_update_callback(vJSON) {
    console.log(vJSON);
    modify_vehicle(vJSON.id, document.getElementById("vehicle_type_update").value, document.getElementById("fuel_value_update").value, 
    vJSON.fuelConsumption, document.getElementById("liquid_quantity_update").value, document.getElementById("liquid_type_update").value, 
    vJSON.liquidConsumption, vJSON.lon, vJSON.lat, vJSON.crewMember, vJSON.crewMemberCapacity, vJSON.efficiency, vJSON.facilityRefID);
}


// FUNCTIONS OTHERS ----------------------------------------------------------------------------------------------------

//Hides the right interface given in parameters. Also hides all its childs
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

//Hides left interface when clicking at a random spot on the map
function hide_interface_left(event) {
    var lat_marker = event.latlng.lat;
    var lng_marker = event.latlng.lng;

    for (vehicle of vehicleList) {
        if (vehicle.lon == lng_marker && vehicle.lat == lat_marker) {
            return;
        }
    }
    for (fire of fireList) {
        if (fire.lon == lng_marker && fire.lat == lat_marker) {
            return;
        }
    }
   document.getElementById("over_map_left").style.display = 'None';
}

//Manages the display of the left panel when it's clicked
function switch_left_interface_display(obj) {
    var el = document.getElementById(obj);
    if (el.id == "info_vehicle") {
        if (el.style.display == 'none') {
            el.style.display = 'block';
        } else {
            el.style.display = 'none';
        }
    } else if (el.id == "info_vehicle_update") {
        if (el.style.display != 'none') {
            el.style.display = 'none';
        } else {
            el.style.display = 'block';
        }
    }
}

//LOGS errors on console
function err_callback(error) {
    console.log(error);
}

// CODE ----------------------------------------------------------------------------------------------------

//MAP INITIALISATION
var mymap = L.map('mapid').setView([45.76392211069434, 4.832544118002555], 12);  // [51.505, -0.09], 13

L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
	maxZoom: 18,
	attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, ' + 'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
	id: 'mapbox/streets-v11',
	tileSize: 512,
	zoomOffset: -1
}).addTo(mymap);
mymap.on('click', hide_interface_left);

//GLOBAL variables
let fireList = [];
let firePrinted = [];
let vehicleList = [];
let vehiclePrinted = [];
var fireGroup = L.featureGroup().addTo(mymap).on("click", fetch_fire_fromMarker);
var vehiclesGroup = L.featureGroup().addTo(mymap).on("click", fetch_vehicle_fromMarker);

//Instructions called every 1000 ms
var intervalId = window.setInterval(function(){
    fetch_fire();
    fetch_vehicles();
}, 1000);

//Functions called every time the page is refreshed
//create_vehicle(0, 1, 4.5, 45.5);
//modify_vehicle(id, vehicle_type, fuel, fuelConsumption, liquidQuantity, liquid_type, liquidConsumption,lon, lat, 
//crewMember, crewMemberCapacity, efficiency, facilityRefID)
//modify_vehicle(10453, 3, 0, 0, 10, 3, 20, 1, 1, 23, 25, 12, 45);   //TODO USE POSTEMAN PUT REQUEST TO UPDATE VEHICLE
fetch_fire();
fetch_vehicles();
