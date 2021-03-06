  $(document).ready(function(){
	  $('[rel="moreActions"]').popover({
          container: 'body',
          html: true,
          content: function () {
              var clone = $($(this).data('popover-content')).clone(true).removeClass('d-none');
              return clone;
          }
      }).click(function(e) {
          e.preventDefault();
      });
	  
      $("#all").change(function() {
        $(".checkbox").prop("checked", $(this).prop("checked"));
      });
      
      $(".checkbox").change(function() {
        if(false==$(this).prop("checked")){
          $("#all").prop("checked", false);
        }
        if($(".checkbox:checked").length == $(".checkbox").length) {
          $("#all").prop("checked", true);
        }
      });
	  
      $(".deleteBtn").click(function (e) {
		  $('.pover').popover('hide');
		  var flag = $(this).data('flag');
		  var url = $(this).data('url');
		  $('#deleteForm').attr('action', url);
		  
		  if(flag == 'MULTIPLE') {
			  $('#deleteForm').find("input[name=id]").remove();
			 
			 var selectedIds = getSelectedIds();
			 
			 if(selectedIds.length <= 0) {
				 $('#alertMessages').find(".modal-body").html("<p>please select atleast 1 item!</p>");
				 $('#alertMessages').modal({show:true});
			 } else {
				 $('#deleteConfirmation').modal({show:true});
			 }
		  } else {
			  $('#deleteConfirmation').modal({show:true});
		  }
	  });
      
      $('#deleteForm').submit(function(e) {
		e.preventDefault();
		var flag = $(".deleteBtn").data('flag');
		var idsDto = {};
		
		if(flag == 'MULTIPLE') {
			var selectedIds = getSelectedIds();
			idsDto = {"ids": selectedIds };
		}
		 
		$.post({
	        url: $(this).attr('action'),
	        data: JSON.stringify(idsDto),
	        contentType: "application/json",
	        headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
	        success: function(response) {
	        	if(response.code == 200) {
	        		window.parent.location.reload();
	        	} else {
	        		$('#deleteConfirmation').modal('hide');
	        		$('#alertMessages').find(".modal-body").html(response.description);
	        		$('#alertMessages').modal({show:true});
	        	}
	        },
	        error: function(response) {
	        	$('#deleteConfirmation').modal('hide');
	        	$('#alertMessages').find(".modal-body").html("<p>Something went wrong!</p>");
				$('#alertMessages').modal({show:true});
	        }
	    });
      });
      
	  $('.pover').popover();
	  $('.pover').on('click', function (e) {
	      $('.pover').not(this).popover('hide');
	  });
	  
	  function getSelectedIds() {
		  var selectedIds = [];
		  $.each($("input[name='ids']:checked"), function() {
			selectedIds.push($(this).val());
		    var index = selectedIds.indexOf(-1);
		    	
		    if (index > -1) {
		      array.splice(index, 1);
		    }
		  });
		  return selectedIds;
	  }
  });