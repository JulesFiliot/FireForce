// FUNCTIONS

function fetch_fire() {
    const GET_FIRE_URL="http://127.0.0.1:8081/fire"; 
    let context = {
        method: 'GET'
    };
    
    fetch(GET_FIRE_URL,context)
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
    firePrinted.push(circle);
}

function clear_fire() {
    for (i of firePrinted) {
        i.remove();
    }
    firePrinted = [];
}

function fire_filter(fire) {
    var typeselect = document.getElementById("type").value;
    if (typeselect == "") {
        print_fire(fire);
    } else {
        if (fire.type == typeselect) {
            print_fire(fire);
        }
    }
}

// --- CODE ---

let fireList = [];
let firePrinted = [];

var intervalId = window.setInterval(function(){
    fetch_fire();
}, 20000);

fetch_fire();
