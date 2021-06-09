//FUNCTIONS

function select_diff() {
    if (document.getElementById("one").checked) {
        difficulty = 0
    } else if(document.getElementById("two").checked) {
        difficulty = 1;
    }
}


//CODE

//Difficulty
var difficulty = 0;

// Get the modal
var modal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

//Final launch button
var launch_button = document.getElementById("launch_user_simu");

// When the user clicks on the button, open the modal
btn.onclick = function() {
  modal.style.display = "block";
  console.log("je log");
}

// When the user clicks on <span> (x), close the modal
/*span.onclick = function() {
  modal.style.display = "none";
}*/

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}

launch_button.onclick = function() {
    window.location.href = "fire_map.html?mode=0&diff=" + difficulty;
}