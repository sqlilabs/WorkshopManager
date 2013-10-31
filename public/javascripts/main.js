$(document).ready(function(){
     
	$("#add-workshop-form #image-button").click(function(){
		$('#add-workshop-form #image-workshop').click();
	});
	
	$("#add-workshop-form #image-workshop").change(function(){
		$('#add-workshop-form #appendedInputButton').val($('#add-workshop-form #image-workshop').val());
	});
	
	$('.workshop-bloc a.btn-danger.delete').click(function(e){
		e.preventDefault();
		$('#valid-suppression').attr('data-link',$(this).attr('data-link')); 
		$('#modal-suppression-confirm').modal();
	});
	
	$('#modal-suppression-confirm').on('shown', function () {
		$('#modal-suppression-confirm #valid-suppression').click(function(){
			window.location.href = $(this).attr('data-link');
		});
	});
	
	// ------------------------------------------
	// handle the workshop support upload
	// ------------------------------------------
	$("#add-ressources-form #ressources-button").click(function(){
		$('#add-ressources-form #workshop-support').click();
	});
	
	$("#add-ressources-form #workshop-support").change(function(){
		$('#add-ressources-form #appendedInputButton').val($('#add-ressources-form #workshop-support').val());
	});
	
	// ------------------------------------------
	// handle the modal with the workshop details
	// ------------------------------------------
	$('.workshop-bloc h4').click( function(e) {
		e.preventDefault();
		var id = "#workshop-details-" + e.target.getAttribute("workshop-id") ;
		$(id).modal();
	});

    $('#sidebar h4').click( function(e) {
		e.preventDefault();
		var id = "#workshop-details-" + e.target.getAttribute("workshop-id") ;
		$(id).modal();
	});
	
	// ------------------------------------------
	// handle the modal with the workshop comments
	// ------------------------------------------
	$('.commentPicture img').click( function(e) {
		e.preventDefault();
		var id = "#workshop-comments-" + $(this).attr("workshop-id") ;
		$(id).modal();
	});
	
	// ------------------------------------------
	// handle the modal with the user picture
	// ------------------------------------------
	$('#user-picture-link').click( function(e) {
		e.preventDefault();
		$("#user-picture").modal();
	});
	
	/**
     * Ajax request to update the connected user image
	 */
	$('#user-picture-submit').click( function(e) {
		e.preventDefault();

        var oldValue = $("#user-picture-img").attr('src');

    	$.ajax({
          type: "PUT",
          url: '/ws/modifyUserPicture',
          data: {image: $("#imageURL").val()},
          dataType: 'json'
        })
        $("#user-picture-img").attr('src', $("#imageURL").val());
        $('#user-picture').modal('hide');

        var arrayPicture = $(".participantPicture > img");
        var userName = $("#user-data").text();
        $.each( arrayPicture, function() {
            if ( this.src === oldValue && this.title === userName ) {
                this.src = $("#imageURL").val();
            }
        })
        
	});

});