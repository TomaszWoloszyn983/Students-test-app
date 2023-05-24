/**
 * 
 */

$('document').ready(function(){

   console.log("JavaScript function called")
//    let template = agrTemplate;

    $('.form .updateBtn').on('click', function(event){
        event.preventDefault();

        let href = $(this).attr('href');

        console.log("Jakiś href "+href)

        $.get(href, function(cohortToUpdate, status){
            $('#editName').val(cohortToUpdate.name);
            $('#editdate-box').val(cohortToUpdate.startDate);
            console.log("")
        });

        $('#editModal').modal();
    });
});

document.getElementById("editModalBtn").addEventListener("click", function() {
    // Make an AJAX request to the controller method
    console.log("Ajax called")
    $.ajax({
        type: "GET",
        url: "/student/update/{studentId}", // Replace {id} with the appropriate value
        success: function(response) {
            // Handle the response from the controller method
            // For example, populate the modal form with the data from the response
            $('#updateModal .modal-body').html(response);
            $('#updateModal').modal('show');
            console.log("What do we have here: "+response)
        },
        error: function() {
            // Handle any error that occurs during the AJAX request
            alert("Error occurred while loading data.");
        }
    });
});
