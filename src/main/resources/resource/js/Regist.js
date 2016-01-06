window.onload = function () {
		document.getElementsByName("submit")[0].onclick = function(){
			//send AJAX submit request;
			var request = new XMLHttpRequest();
			request.open("POST","reqrly");

			//create a JSON(loginInfo) to save the information of usrname,password,confirm and identity;
			var registInfo = {
				"Request":"REGIST",
				"Detail":{
							"Identity":"",
							"Infotype":"ACCOUNT",
							"Detail":{
								"Username":"",
								"Password":"",
								"Confirm":"",
							},
				},
			};
			registInfo.Detail.Detail.Username = document.getElementsByName("Regist_usrname")[0].value;
			registInfo.Detail.Detail.Password = document.getElementsByName("Regist_password")[0].value;
			registInfo.Detail.Detail.Confirm = document.getElementsByName("Regist_confirm")[0].value;
			var identity = document.getElementsByName("userType")[0];	
				switch(identity.value){
					case "student" : 
						registInfo.Identity = "STUDENT";			
						break;
					case "teacher" : 
						registInfo.Identity = "TEACHER";		
						break;
					case "fucultyman" : 
						registInfo.Identity = "FACULTYMAN";			
						break;
					}

			request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			request.send(JSON.stringify(registInfo));
			request.onreadystatechange = function(){
				if(request.readyState === 4 && request.status ===200 ){
					alert("success to connect with server");
					switch(identity.value){
						case "student" :
							window.open('../../htmls/Stu_StuInfo.html','_selft');
							break;
						case "teacher" :
							window.open('../../htmls/Teach_TeachInfo.html','_selft');
							break;
						case "fucultyman" :
							window.open('../../htmls/Fac_FacInfo.html','_selft');
							break;
					}
				}
				else{
					alert("false:" + request.status);
				}
			};
		}
	}
