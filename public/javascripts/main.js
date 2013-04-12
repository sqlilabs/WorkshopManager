$(document).ready(function(){
     
	$("#add-workshop-form #image-button").click(function(){
		$('#add-workshop-form #image-workshop').click();
	});
	
	$("#add-workshop-form #image-workshop").change(function(){
		$('#add-workshop-form #appendedInputButton').val($('#add-workshop-form #image-workshop').val());
	});
	
	$('.workshop-bloc a.btn-danger').click(function(e){
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
		var id = "#workshop-details-" + $(this.innerHTML).attr("workshop-id") ;
		$(id).modal();
	});
	
	$('#sidebar h4').click( function(e) {
		e.preventDefault();
		var id = "#workshop-details-" + $(this.innerHTML).attr("workshop-id") ;
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
	 * Requête ajax permettant de mettre à jour l'image de l'utilisateur
	 * connecté
	 */
	$('#user-picture-submit').click( function(e) {
		e.preventDefault();
		// dans les autres cas on appelle le WS d'update
    	$.ajax({
          type: "PUT",
          url: '/ws/modifyUserPicture',
          data: {image: $("#imageURL").val()},
          dataType: 'json'
        })
        $("#user-picture-img").attr('src', $("#imageURL").val());
        $('#user-picture').modal('hide');
        
	});
	

	
	// ------------------------------------------
	// handle something else
	// ------------------------------------------
	$('#nextPlayButton').click(function(){
		$("#nextPlay").datepicker({
			format:'dd-mm-yyyy',
			weekStart:1,
		}).on('changeDate', function(ev){
			var now = new Date();
			if(ev.date.valueOf() < now.valueOf()){
				var $alert = $('<div class="alert alert-error fade in" id="changed-date-alert-error">La date choisie est inférieure à la date du jour<a class="close" data-dismiss="alert" href="#">&times;</a></div>')
				$alert.css({
					'position':'fixed',
					'bottom':'15px',
					'right' :'15px',
					'border' : '1px solid',
					'font-weight':'bolder'
					
					
				}).appendTo('body');
				$alert.alert();
				/*
				On peut vouloir supprimer l'alerte au bout d'un certain temp, ou alors seulement lorsque le choix de la date est ok
				*/
				setTimeout(function(){
					$('#changed-date-alert-error').alert('close');
				},2000);
			} else {
				$("#nextPlay").datepicker('hide');
				if( $('#changed-date-alert-error').length > 0){
					$('#changed-date-alert-error').alert('close');
				}
			}
		});
		$("#nextPlay").datepicker('show');
	});
});