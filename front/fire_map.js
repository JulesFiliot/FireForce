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
       for(var i = 0; i < reponse.length; i++) {
           fireList[i] = reponse[i];
        }
        create_fire();
   }
   
   function create_fire() {
       for(const fire of fireList){
           console.log(fire);
           print_fire(fire);
        }
    }
   
   function print_fire(fire) {
       var circle = L.circle([fire.lat, fire.lon],
        {
            color: 'red',
            fillColor: '#f03',
            fillOpacity: fire.intesity/10,
            radius: fire.range
        }
        ).addTo(mymap);
        firePrinted.push(circle);
    }
   
   // --- CODE ---

   let fireList = [];
   let firePrinted = [];

   var intervalId = window.setInterval(function(){
       for (i of firePrinted) {
           i.remove();
        }
        firePrinted = [];
        // call your function here
        fetch_fire();
    }, 20000);
    fetch_fire();