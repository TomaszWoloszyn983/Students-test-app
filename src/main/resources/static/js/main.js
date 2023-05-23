/**
 * 
 */

$('document').ready(function(){

   console.log("JavaScript function runs")
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
