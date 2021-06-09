//Uses a POST request to create a station given some basic parameters of the station
function create_station(name, capacity, lon, lat) {
    const POST_STATION_URL = "http://127.0.0.1:8098/createStation";
    let context = {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify({
            "name":name,
            "capacity":capacity,
            "lon":lon,
            "lat":lat
        })
    };
    fetch(POST_STATION_URL, context)
        .catch(error => err_callback(error));
}

function fetch_stations() {
    const GET_STATION_URL = "http://127.0.0.1:8098/getAllStation";
    let context = {
        method: 'GET',
    };
    stationList = [];
    fetch(GET_STATION_URL, context)
        .then(response => response.json().then(body => stationList_callback(body)))
        .catch(error => err_callback_stations(error));
}

function err_callback_stations() {
    clear_stations();
}

//Takes the list of all stations as parameter. Calls print_station function for each vehicle to print them
function stationList_callback(response) {    
    for(var i = 0; i < response.length; i++) {
        stationList[i] = response[i];
    }
    station_vehicle_creator();
    station_vehicle_interface();
    clear_stations();
    for(const station of stationList) {
        print_station(station);
    }
}

//Displays on the map the station given in parameter
function print_station(station) {
    var stationIcon = L.icon({
        iconUrl: 'icons/fire_station.png',    
        iconSize: [34, 34], // size of the icon
        iconAnchor: [17, 30], // point of the icon which will correspond to marker's location
        popupAnchor: [-3, -76] // point from which the popup should open relative to the iconAnchor
    });
    var marker = L.marker([station.lat, station.lon], {icon: stationIcon}).addTo(stationGroup);
    stationPrinted.push(marker);
}

//clear printed stations
function clear_stations() {
    for (i of stationPrinted) {
        i.remove();
    }
    stationPrinted = [];
}

//Fills the popup concerning the Fire Station
function fill_popup_station(station) {
    document.getElementById("info_station_name").innerHTML = "Nom : "  + station.name;
    document.getElementById("info_station_capacity").innerHTML = "Capacity : " + station.capacity;


    document.getElementById("over_map_left").style.display = 'block';
    document.getElementById("over_map_left_bottom").style.display = 'none';
    document.getElementById("info_vehicle_update").style.display = 'none';
    document.getElementById("info_vehicle").style.display = 'none';
    document.getElementById("info_fire").style.display = 'none';
    document.getElementById("info_station").style.display = 'block';
}

function fetch_station_fromMarker(event) {
    var lat_marker = event.latlng.lat;
    var lng_marker = event.latlng.lng;
    for (station of stationList) {
        if (station.lon == lng_marker && station.lat == lat_marker) {
            fill_popup_station(station);
            return;
        }
    }
}

//Create fire station from interface. Station is created using create_station
function station_creator() {
    var name = document.getElementById("station_name").value;
    var capacity = document.getElementById("station_capacity").value;
    var lat = document.getElementById("station_lat").value;
    var lon = document.getElementById("station_long").value;
    create_station(name, capacity, lon, lat);
}


function station_vehicle_creator() {
    if (document.getElementById("vehicle_creator").style.display == "none") {
        var text_html = "";
        for (station of stationList) {
            text_html += "<option value="+station.id+">"+station.name+"</option>";
        }
        if (text_html != "") {
            document.getElementById("vehicle_station").innerHTML = text_html;
        }
    }
}

function station_vehicle_interface() {
    if (document.getElementById("vehicle_interface").style.display == "none") {
        var text_html = "";
        for (station of stationList) {
            text_html += "<div><input type='checkbox' id=STATION"+station.id+" name="+station.name+" checked><label for="+station.name+">"+station.name+"</label></div>"
        }
        if (text_html != "") {
            document.getElementById("vehicle_interface_station").innerHTML = text_html;
            vehic_station_filter = true;
        }
    }
}

function reset_station() {
    const RESET_URL = "http://127.0.0.1:8098/resetFStation";
    let context = {
        method: 'GET',
    };
    fetch(RESET_URL, context)
        .catch(error => err_callback(error));
}