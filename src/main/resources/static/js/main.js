/**
 * 
 */

$('document').ready(function(){

   
//    let template = agrTemplate;

    $('.table .updateBtn').on('click', function(event){
        event.preventDefault();

        let href = $(this).attr('href');

        $.get(href, function(cohortToUpdate, status){
            $('#editName').val(cohortToUpdate.name);
            $('#editDate').val(cohortToUpdate.startDate);
        });

        $('#editModal').modal();
    });
});
