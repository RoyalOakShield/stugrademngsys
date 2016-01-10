window.onload = function () {
		document.getElementsByName("submit")[0].onclick = function(){
			//send AJAX submit request;
			var request = new XMLHttpRequest();
			request.open("POST","reqrly");

			//create a JSON(loginInfo) to save the information of usrname,password,confirm and identity;
			var registInfo = {
				"Request":"ADD",
				"Detail":{
							"Identity":"",
							"Infotype":"ACCOUNT",
							"Detail":{
								"Username":"",
								"Password":""
							}
				}
			};
			registInfo.Detail.Detail.Username = document.getElementsByName("Regist_usrname")[0].value;
			registInfo.Detail.Detail.Password = document.getElementsByName("Regist_password")[0].value;
			var passwordConfirm = document.getElementsByName("Regist_confirm")[0].value;
			var identity = document.getElementsByName("userType")[0];	
				switch(identity.value){
					case "student" : 
						registInfo.Identity = "STUDENT";			
						break;
					case "teacher" : 
						registInfo.Identity = "TEACHER";		
						break;
					case "facultyman" : 
						registInfo.Identity = "FACULTYMAN";			
						break;
					}

			var isPasswordConfrimSuccess=false;
			if(registInfo.Detail.Detail.Password === passwordConfirm){
				isPasswordConfrimSuccess=true;
			}

			request.setRequestHeader("Content-Type","application/json");
			if(isPasswordConfrimSuccess){
				request.send(JSON.stringify(registInfo));
			}
			else{
				alert("Password Confirm Failed!");
			}

			request.onreadystatechange = function(){
				if(request.readyState === XMLHttpRequest.DONE){
					if(request.status === 200){
						alert("success to connect with server");
						switch(identity.value){
							case "student" :
								window.open('Stu_StuInfo.html','_selft');
								break;
							case "teacher" :
								window.open('Teach_TeachInfo.html','_selft');
								break;
							case "fucultyman" :
								window.open('Fac_FacInfo.html','_selft');
								break;
						}
					}
					else{
						alert("false:" + request.status);
					}
				}
			};
		};
	};
