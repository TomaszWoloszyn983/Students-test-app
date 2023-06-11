/**
 * 
 */

// Messages Timeout function Doesn't work from here becauce the JavaScript file doesn't load.
let warMessages = document.getElementById("warningMessage");
if (warMessages != null){
        setTimeout(function(){
        warMessages.style.display = "none";
    }, 3000);
}

