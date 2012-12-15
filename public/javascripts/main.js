$(document).ready(function(){
     
	// We hide the authentication module
    //$('#authentication').hide(); 
    
    // We add a listener on the authenticationButton
    $('#authenticationButton').bind('click', function() {
    	
    	$('#authentication').show(); 
	});


	$("#add-workshop-form #image-button").click(function(){
		$('#add-workshop-form #image-workshop').click();
	});
	$("#add-workshop-form #image-workshop").change(function(){
		$('#add-workshop-form #appendedInputButton').val($('#add-workshop-form #image-workshop').val());
	});
});