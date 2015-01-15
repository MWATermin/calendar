		var last200 = 0;
		
		function Item(id, name) 
		{
		    this.id = id;
		    this.name = name;
		}	
		
		var AllUsers = new Array();
		var AllInvUsers = new Array();
		
		function getJSONUser()
		{
			
			$.getJSON("/Calendar_Rest/rest/getuserlist", function(jd) {
				
				
				
				for(var i=0;i<jd.length;i++){
			        var obj = jd[i];
			        AllUsers[i] = new Item( obj.id, obj.username);
			        $("#members").append(obj.username + ", ");
			    }
			});
		}
		
		function getJSONInvUser()
		{
			$.getJSON("/Calendar_Rest/rest/getinvusers?usr=" + document.getElementById("cell8").value + "&id=" + document.getElementById("cell9").value, function(jd) {
				$("#invmembers").html("");
				
				if( jd != null)
				{	
					for(var i=0;i<jd.length;i++){
						for(var j=0; j<AllUsers.length; j++)
						{
							if(AllUsers[j].id == jd[i])
							{
						        $("#invmembers").append(AllUsers[j].name + ", ");
							}
						}
				    }
				}
			})
		}
		
		function getJSONData()
		{
			var div = document.getElementById('overview');
			
			$.getJSON("/Calendar_Rest/rest/get?usr=" + document.getElementById("cell8").value + "&id=" + document.getElementById("cell9").value, function(jd) { 
				document.getElementById("cell1").value = jd.authorID;
				var DateObj = new Date(jd.dateAndTime);
				var Month = ('0' + (DateObj.getMonth()+1)).slice(-2);
				var Day = ('0' + DateObj.getUTCDate()).slice(-2);
				document.getElementById("cell2").value = DateObj.getFullYear() + "-" + Month + "-" + Day;
				document.getElementById("cell3").value = jd.description;
				document.getElementById("cell4").value = jd.duration;
				document.getElementById("cell5").value = jd.id;
				document.getElementById("cell6").value = jd.label;
				last200 = parseInt($("#cell9").val());

				div.innerHTML = '<h1>' + Day + '.' + Month + '.' + DateObj.getFullYear()  +'</h1>' +
				'<h3>' + jd.label + '</h3>'  + "<b>Information:</b> " + jd.description + "</br>" + "<b>Duration:</b> " + jd.duration + "h";

				
				}).fail(function(jqXHR) {
				    if (jqXHR.status == 500) 
					{
						$("#cell9").val( last200);
						//getJSONData();
				    }
				});
		}

		function putJSONData()
		{
			var DateObj = new Date(document.getElementById("cell2").value);
		
			var a = {
			    "id": (parseInt($("#cell5").val()) + 1),
			    "dateAndTime": DateObj.getTime(),
			    "duration": document.getElementById("cell4").value,
			    "authorID": document.getElementById("cell1").value,
			    "place": document.getElementById("cell7").value,
			    "label": document.getElementById("cell6").value,
			    "description": document.getElementById("cell3").value
			};
			
			var myString = JSON.stringify(a);
			
			$.ajax({
	            type: "PUT",
	            url: "/Calendar_Rest/rest/put?user=" + $("#cell8").val(),
	            data: myString,
	            dataType: "json",
	            contentType: "application/json"
	        });
		}

		function postJSONData()
		{
			var DateObj = new Date(document.getElementById("cell2").value);
		
			var a = {
			    "id": $("#cell5").val(),
			    "dateAndTime": DateObj.getTime(),
			    "duration": document.getElementById("cell4").value,
			    "authorID": document.getElementById("cell1").value,
			    "place": document.getElementById("cell7").value,
			    "label": document.getElementById("cell6").value,
			    "description": document.getElementById("cell3").value
			};
			
			var myString = JSON.stringify(a);
			
			$.ajax({
	            type: "POST",
	            url: "/Calendar_Rest/rest/post",
	            data: myString,
	            dataType: "json",
	            contentType: "application/json"
	        });
		}

		function deleteJSONData()
		{
			var DateObj = new Date(document.getElementById("cell2").value);
		
			var a = {
			    "id": parseInt($("#cell5").val()),
			    "dateAndTime": DateObj.getTime(),
			    "duration": document.getElementById("cell4").value,
			    "authorID": document.getElementById("cell1").value,
			    "place": document.getElementById("cell7").value,
			    "label": document.getElementById("cell6").value,
			    "description": document.getElementById("cell3").value
			};
			
			var myString = JSON.stringify(a);
			
			$.ajax({
	            type: "DELETE",
	            url: "/Calendar_Rest/rest/delete?dateId=" + $("#cell5").val() + "&user=" + $("#cell8").val(),
	            data: myString,
	            dataType: "json",
	            contentType: "application/json"
	        });
		}
	
		$(document).ready(function() { 
			$("#bget").click(function(event){ 
				 getJSONData();
				}); 
			});
		
		$(document).ready(function() { 
			$("#bpost").click(function(event){
				postJSONData();
				alert("Posted");
				getJSONData();
			}); 
		});

		$(document).ready(function() { 
			$("#bput").click(function(event){
				putJSONData();
				alert("Putted");
				getJSONData();
			}); 
		});

		$(document).ready(function() { 
			$("#bdel").click(function(event){
				deleteJSONData();
				alert("Deleted");
			}); 
		});
		
		$(document).ready(function() { 
			$("#bfwd").click(function(event){
				$("#cell9").val(parseInt($("#cell9").val()) + 1); 
				 getJSONData();
				 getJSONInvUser();
			}); 
		});
		
		$(document).ready(function() { 
			$("#bbw").click(function(event){
				$("#cell9").val(parseInt($("#cell9").val()) - 1); 
				getJSONData();
				getJSONInvUser();
			}); 
		});

		$( document ).ready(function() {
			$("#cell9").val(parseInt("0")); 
			 getJSONData();
		});
		
		$( window ).load(function() {
			  getJSONUser();
			  getJSONInvUser();
			});
		
		