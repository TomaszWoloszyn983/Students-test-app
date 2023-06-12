/**
 * 
 */

// Messages Timeout function Doesn't work from here because the JavaScript file doesn't load.
function messageTimeout(){
    let warMessages = document.getElementById("warningMessage");
    if (warMessages != null){
            console.log("Massage from JS file")
            setTimeout(function(){
            warMessages.style.display = "none";
        }, 3000);
    }
}

