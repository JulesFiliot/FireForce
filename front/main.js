// OTHER FUNCTIONS ----------------------------------------------------------------------------------------------------

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
    for (station of stationList) {
        if (station.lon == lng_marker && station.lat == lat_marker) {
            return;
        }
    }
   document.getElementById("over_map_left").style.display = 'None';
}

function pretty_text(name) {
    var pretty_name = "";
    var lower_flag = 0;
    for (i of name) {
        if (i == "_") {
            lower_flag = 0;
            pretty_name += " ";
        } else {
            if (lower_flag == 1) {
                pretty_name += i.toLowerCase();
            } else {
                pretty_name += i;
                lower_flag = 1;
            }
        }
    }
    return pretty_name;
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

function switch_map_style() {
    var map_style;
    for (var i = 1; i < 4; i++) {
        map_style = document.getElementById("map_style"+i);
        if (map_style.checked) {
            mymap.removeLayer(map_layer);
            map_layer = L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
	            //minZoom: 10,
                maxZoom: 20,
                attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, ' + 'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
                id: 'mapbox/' + map_style.value,
                tileSize: 512,
                zoomOffset: -1,
            }).addTo(mymap);
        }
    }
}


function reset_all() {
    put_creation_config(0,20);
    reset_fire();
    reset_vehicle();
    reset_station();
    //document.location.reload();
}

//LOGS errors on console
function err_callback(error) {
    //console.log(error);
}

// FUNCTIONS SIMULATION ----------------------------------------------------------------------------------------------------

function simulation_preset_soft() {
    //Reset
    reset_all();

    //Creates one fire station and three vehicles
    setTimeout(function(){

        create_station("Lyon Pompier Crew", 100, 4.86904827217447, 45.78391737991209);

        setTimeout(function(){
            fetch_stations();
            
            setTimeout(function(){
                let station = stationList[0];

                create_vehicle(1, 1, station.lon, station.lat, station.id);
                create_vehicle(2, 2, station.lon, station.lat, station.id);
                create_vehicle(3, 3, station.lon, station.lat, station.id);

            }, 250);

        }, 250);

        //Set the spawn probability and frequency and the child spawn rate
        put_creation_config(0.4, 10000);
        put_behavior_config(0.0);
    }, 250);  
}

function simulation_preset_balanced() {
    //Reset
    reset_all();

    //Creates one fire station and three vehicles
    setTimeout(function(){

        create_station("CPE Lyon", 100, 4.86904827217447, 45.78391737991209);
        create_station("Gangsta FireMen", 200, 4.96904827217447, 45.68391737991209);

        setTimeout(function(){
            fetch_stations();
            
            setTimeout(function(){
                let station = stationList[0];
                let station2 = stationList[1];

                create_vehicle(1, 1, station.lon, station.lat, station.id);
                create_vehicle(2, 2, station2.lon, station2.lat, station2.id);
                create_vehicle(3, 3, station2.lon, station2.lat, station2.id);

            }, 250);

        }, 250);

        //Set the spawn probability and frequency and the child spawn rate
        put_creation_config(0.8, 8000);
        put_behavior_config(0.2);
    }, 250);  
}

function simulation_preset_hell() {
    //Reset
    reset_all();

    //Creates one fire station and three vehicles
    setTimeout(function(){

        create_station("Irithyll Dungeon", 50, 4.86904827217447, 45.78391737991209);
        create_station("Kiln of the First Flame", 100, 4.96904827217447, 45.68391737991209);
        create_station("Cemetery of Ash", 200, 4.82, 45.71);

        setTimeout(function(){
            fetch_stations();
            
            setTimeout(function(){
                let station = stationList[0];
                let station2 = stationList[1];
                let station3 = stationList[2];

                create_vehicle(1, 1, station.lon, station.lat, station.id);
                create_vehicle(2, 2, station2.lon, station2.lat, station2.id);
                create_vehicle(3, 3, station2.lon, station2.lat, station2.id);
                create_vehicle(4, 4, station3.lon, station3.lat, station3.id);
                create_vehicle(5, 1, station3.lon, station3.lat, station3.id);
                create_vehicle(0, 0, station3.lon, station3.lat, station3.id);

            }, 250);

        }, 250);

        //Set the spawn probability and frequency and the child spawn rate
        put_creation_config(1, 5000);
        put_behavior_config(0.6);
    }, 250);  
}

//Start the simulation in user mode : deletes the panels allowing to create vehicles, stations, ...
function start_user_mode() {
    document.getElementById("station_creator").style.display = 'none';
    document.getElementById("station_creator_title").style.display = 'none';
    document.getElementById("vehicle_creator").style.display = 'none';
    document.getElementById("vehicle_creator_title").style.display = 'none';
    document.getElementById("config_interface_title").style.display = 'none';
    document.getElementById("config_interface").style.display = 'none';
}

function test_mode_param() {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const simu_mode = urlParams.get('mode');
    console.log(simu_mode);
    if (simu_mode == '0') {
        start_user_mode();
        console.log('User mode launched !');
        const difficulty = urlParams.get('diff');

        if (difficulty == '0') {
            simulation_preset_hell()
            console.log("Difficulty balanced");

        } else if (difficulty == '1') {
            simulation_preset_balanced()
            console.log("Difficulty hell");
        }

    } else {
        console.log('Dev mode launched !');
    }
}

// CODE ----------------------------------------------------------------------------------------------------

//MAP INITIALISATION
var mymap = L.map('mapid').setView([45.76392211069434, 4.832544118002555], 12);  // [51.505, -0.09], 13

var map_layer = L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
	//minZoom: 10,
    maxZoom: 20,
	attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, ' + 'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
	id: 'mapbox/streets-v11',
	tileSize: 512,
	zoomOffset: -1,
    //id: 'mapbox/dark-v10'
    //id: 'mapbox/satellite-v9',
}).addTo(mymap);


switch_map_style();
mymap.on('click', hide_interface_left);
/*
mymap.on('click', function(e) {
    alert("Lat, Lon : " + e.latlng.lat + ", " + e.latlng.lng)
});
*/

//GLOBAL variables
let fireList = [];
let firePrinted = [];
var fireGroup = L.featureGroup().addTo(mymap).on("click", fetch_fire_fromMarker);

let vehicleList = [];
let vehiclePrinted = [];
var vehiclesGroup = L.featureGroup().addTo(mymap).on("click", fetch_vehicle_fromMarker);

let stationList = [];
let stationPrinted = [];
let stationGroup = L.featureGroup().addTo(mymap).on("click", fetch_station_fromMarker);

let clickedArea;

let vehic_station_filter = false;

//Functions called every time the page is refreshed
/*
put_config();
fetch_fire();
fetch_vehicles();
fetch_stations();
*/
/*
//create_station("CPE Lyon", 100, 4.86904827217447, 45.78391737991209);
//create_vehicle(1, 1, Math.random()*(4.9266428 - 4.7736324) + 4.7736324, Math.random()*(45.7941125 - 45.7145454) + 45.7145454);
setTimeout(function(){
    if (stationList.length == 0) {
        create_station("CPE Lyon", 100, 4.86904827217447, 45.78391737991209);
        setTimeout(function(){
            fetch_stations();
            setTimeout(function(){
                station = stationList[0];
                create_vehicle(1, 1, 4.86904827217447, 45.78391737991209, station.id);
            }, 100);
        }, 100);
    }
}, 100);
*/

//simulation_preset_hell();
//Once page is loaded launch the mode check function
window.onload = function() {
    test_mode_param();
};

//Instructions called every 1000 ms
var intervalId = window.setInterval(function(){
    if (document.getElementById("info_fire").style.display == 'block') {
        live_fill_popup_fire(clickedArea);
    }
    if (document.getElementById("info_vehicle").style.display == 'block') {
        live_fill_popup_vehicle(clickedArea);
    }
    fetch_fire();
    fetch_vehicles();
    fetch_stations();
    setTimeout(function(){
        station_vehicle_creator()
        station_vehicle_interface()
    }, 250);
}, 2000);
