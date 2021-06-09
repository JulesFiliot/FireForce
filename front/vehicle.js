// FUNCTIONS VEHICLES ----------------------------------------------------------------------------------------------------

//Uses a POST request to create a vehicle given some basic parameters of the vehicle
function create_vehicle(vehicle_type, liquid_type, lon, lat, facility) {
    const POST_VEHICLE_URL = "http://127.0.0.1:8094/vehicle";  // 8081/vehicle
    let context = {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify({
            "lon":lon,
            "lat":lat,
            "type":vehicle_type,
            "efficiency":10,
            "liquidType":liquid_type,
            "liquidQuantity":1000,
            "liquidConsumption":1,
            "fuel":1,
            "fuelConsumption":1,
            "crewMember":4,
            "crewMemberCapacity":4,
            "facilityRefID":facility

        })
    };
    fetch(POST_VEHICLE_URL, context)
        .catch(error => err_callback(error));
}

//GET request to fetch all existing vehicles. Calls the vehiclesList_callback when vehicles are fetched
function fetch_vehicles() {
    const GET_VEHICLE_URL = "http://127.0.0.1:8094/getAllVehic"; // 8081/vehicle
    let context = {
        method: 'GET',
    };
    vehicleList = [];
    fetch(GET_VEHICLE_URL, context)
        .then(response => response.json().then(body => vehiclesList_callback(body)))
        .catch(error => err_callback_vehicles(error));
}

function err_callback_vehicles() {
    clear_vehicles();
}

//GET request to fetch a vehicle infos using its ID in URL parameter. Call the function to update the vehicle.
function fetch_vehicle_byId(id_vehicle, vehicle_update_callback) {
    const GET_VEHICLE_URL = "http://127.0.0.1:8094/getVehic/"+id_vehicle; // 8081/vehicle/
    let context = {
        method: 'GET',
    };
    fetch(GET_VEHICLE_URL, context)
        .then(response => response.json().then(body => vehicle_update_callback(body)))
        .catch(error => err_callback(error));
}

//Delete the vehicule corresponding to the given id in parameters 
function delete_vehicle(id_vehicle) {
    const DELETE_VEHICLE_URL = "http://127.0.0.1:8094/vehicle/"+id_vehicle; // 8081/vehicle/
    let context = {
        method: 'DELETE',
    };
    fetch(DELETE_VEHICLE_URL, context)
        .catch(error => err_callback(error));
}

//PUT request to update the vehicle infos given in parameters. 
//Then calls the fetch_vehicle_byId_visu to update vehicle info panel 
function modify_vehicle(id, remoteId, vehicle_type, fuel, fuelConsumption, liquidQuantity, liquid_type, liquidConsumption,lon, lat, 
    crewMember, crewMemberCapacity, efficiency, facilityRefID) {

    const PUT_VEHICLE_URL = "http://127.0.0.1:8094/vehicle"; // 8081/vehicle/
    let context = {
        method: 'PUT',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify({
            "id":id,
            "remoteId":remoteId,
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
        .catch(error => err_callback(error));
}

//Create vehicle from interface. Vehicle is created using create_vehicle
function vehicle_creator() {
    var vehicle_type = document.getElementById("vehicle_type").value;
    var liquid_type = document.getElementById("liquid_type").value;
    var lat = 0;
    var lon = 0;
    var facility = 0;
    for (station of stationList) {
        vehicle_station = document.getElementById("vehicle_station").value;
        if (vehicle_station == station.id) {
            lat = station.lat;
            lon = station.lon;
            facility = station.id;
        }
    }
    //var lat = Math.random()*(45.7941125 - 45.7145454) + 45.7145454;
    //var lon = Math.random()*(4.9266428 - 4.7736324) + 4.7736324;
    create_vehicle(vehicle_type, liquid_type, lon, lat, facility);
}

//Takes the list of all vehicles as parameter. Calls vehicle_filter function for each vehicle to print them
function vehiclesList_callback(response) {    
    for(var i = 0; i < response.length; i++) {
        vehicleList[i] = response[i];
    }
    clear_vehicles();
    for(const vehicle of vehicleList) {
        vehicle_filter(vehicle);
    }
}

//Calls the print_vehicle function to print each vehicle which fits the filter parameters provided by the user
function vehicle_filter(vehicle) {
    if (document.getElementById(vehicle.type).checked == true) {
        if (document.getElementById(vehicle.liquidType).checked == true) {
            if (vehic_station_filter) {
                if (document.getElementById("STATION"+vehicle.facilityRefID).checked == true) {
                    print_vehicle(vehicle);
                }
            }
            else (print_vehicle(vehicle));
        }
    }
}

//Displays on the map the vehicle given in parameter
function print_vehicle(vehicle) {
    var fireIcon = L.icon({
        iconUrl: 'icons/car_map_filled.png',    
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
    clickedArea = event.latlng;

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
    document.getElementById("info_vehicle_id").value = vehicle.id;
    document.getElementById("info_vehicle_img").src = "images/" + vehicle.type;
    document.getElementById("info_vehicle_type").innerHTML = "Type : " + pretty_text(vehicle.type);
    document.getElementById("info_vehicle_fuel").innerHTML = "Fuel quantity : " + vehicle.fuel;
    document.getElementById("info_vehicle_liquid_type").innerHTML = "Liquid type : " + pretty_text(vehicle.liquidType);
    document.getElementById("info_vehicle_liquid_quantity").innerHTML = "Liquid quantity : " + vehicle.liquidQuantity;
    for (station of stationList) {
        if (vehicle.facilityRefID == station.id) {
            document.getElementById("info_vehicle_station").innerHTML = "Fire station : " + station.name;
        }
    }

    document.getElementById("over_map_left").style.display = 'block';
    document.getElementById("over_map_left_bottom").style.display = 'block';
    document.getElementById("info_vehicle_update").style.display = 'none';
    document.getElementById("info_vehicle").style.display = 'block';
    document.getElementById("info_fire").style.display = 'none';
    document.getElementById("info_station").style.display = 'none';
}

//Updates the vehicle popup when it's already open
function live_fill_popup_vehicle(clkA) {
    var lat = clkA.lat;
    var long = clkA.lng;
    var good_vehicle;
    for (vehicle of vehicleList) {
        if (vehicle.lon == long && vehicle.lat == lat) {
            good_vehicle = vehicle;
            document.getElementById("info_vehicle_id").value = good_vehicle.id;
            document.getElementById("info_vehicle_img").src = "images/" + good_vehicle.type;
            document.getElementById("info_vehicle_type").innerHTML = "Type : " + pretty_text(good_vehicle.type);
            document.getElementById("info_vehicle_fuel").innerHTML = "Fuel quantity : " + good_vehicle.fuel;
            document.getElementById("info_vehicle_liquid_type").innerHTML = "Liquid type : " + pretty_text(good_vehicle.liquidType);
            document.getElementById("info_vehicle_liquid_quantity").innerHTML = "Liquid quantity : " + good_vehicle.liquidQuantity;
        }
    }
}

function delete_vehicle(id_vehicle) {
    const DELETE_VEHICLE_URL = "http://127.0.0.1:8094/vehicle/"+id_vehicle; // 8081/vehicle/ /////-------------------
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
    modify_vehicle(vJSON.id, vJSON.remoteId, document.getElementById("vehicle_type_update").value, document.getElementById("fuel_value_update").value, 
    vJSON.fuelConsumption, document.getElementById("liquid_quantity_update").value, document.getElementById("liquid_type_update").value, 
    vJSON.liquidConsumption, vJSON.lon, vJSON.lat, vJSON.crewMember, vJSON.crewMemberCapacity, vJSON.efficiency, vJSON.facilityRefID);
}


// Reset all vehicle and unprint them from map
function reset_vehicle() {
    const GET_VEHICLE_URL = "http://127.0.0.1:8094/vehicle/reset";
    let context = {
        method: 'GET',
    };
    fetch(GET_VEHICLE_URL, context)
        .catch(error => err_callback(error));
}