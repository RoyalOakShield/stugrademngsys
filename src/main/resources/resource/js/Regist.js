window.onload = function () {
		document.getElementsByName("submit")[0].onclick = function(){
			//send AJAX submit request;
			var request = new XMLHttpRequest();
			request.open("POST","backend_url");

			//create a JSON(loginInfo) to save the information of usrname,password,confirm and identity;
			var registInfo = {
				"Name":"",
				"Password":"",
				"Confirm":"", 
				"Identity":"",
			};
			registInfo["Name"] = document.getElementsByName("Regist_usrname")[0].value;
			registInfo["Password"] = document.getElementsByName("Regist_password")[0].value;
			registInfo["Confirm"] = document.getElementsByName("Regist_confirm")[0].value;
			var identity = document.getElementsByName("userType")[0];	
				switch(identity.value){
					case "student" : 
						registInfo["Identity"] = "student";			
						break;
					case "teacher" : 
						registInfo["Identity"] = "teacher";		
						break;
					case "fucultyman" : 
						registInfo["Identity"] = "fucultyman";			
						break;
					default: 
						alert("false");
						break;
					}

			request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			request.send(JSON.stringify(registInfo));
			request.onreadystatechange = function(){
				if(request.readyState === 4 && request.status ===200 ){
					alert("success to connect with server");
					switch(identity.value){
						case "student" :
							window.open('./Stu_StuInfo.html','_blank');
							break;
						case "teacher" :
							window.open('./Teach_TeachInfo.html','_blank');
							break;
						case "fucultyman" :
							window.open('./Fac_FacInfo.html','_blank');
							break;
						default:
							alert("false");
					}
				}
				else{
					alert("false:" + request.status);
				}
			}

		}
	}