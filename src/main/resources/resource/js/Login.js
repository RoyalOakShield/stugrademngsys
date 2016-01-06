window.onload = function () {
		document.getElementsByName("submit")[0].onclick = function(){
			//send AJAX submit request;
			var request = new XMLHttpRequest();
			request.open("POST","reqrly");

			//create a JSON(loginInfo) to save the information of usrname,password and identity;
			var loginInfo = {
				"Request":"LOGIN",
				"Detail":{
							"Identity":"",
							"Infotype":"ACCOUNT",
							"Detail":{
								"Username":"",
								"Password":"",
							}
				}
			};
			loginInfo.Detail.Detail.Username = document.getElementsByName("Login_usrname")[0].value;
			loginInfo.Detail.Detail.Password = document.getElementsByName("Login_password")[0].value;
			var identity = document.getElementsByName("userType")[0];	
				switch(identity.value){
					case "student" : 
						loginInfo.Detail.Identity = "STUDENT";			
						break;
					case "teacher" : 
						loginInfo.Detail.Identity = "TEACHER";		
						break;
					case "fucultyman" : 
						loginInfo.Detail.Identity = "FACULTYMAN";			
						break;
					}

			request.setRequestHeader("Content-Type","application/json");
			request.send(JSON.stringify(loginInfo));
			request.onreadystatechange = function(){
			if(request.readyState === XMLHttpRequest.DONE){
				if( request.status ===200 ){
					alert("success to connect with server");
					var replyJSON=JSON.parse(request.responseText);
					if(replyJSON.Return === 1){
						switch(identity.value){
							case "student" :
								window.open('Stu_Grade.html','_selft');
								break;
							case "teacher" :
								window.open('Teach_Grade.html','_selft');
								break;
							case "fucultyman" :
								window.open('Facultyman.html','_selft');
								break;
						}
					}
					else{
						alert("Login Failed.Check your username and password please");
					}
				}
				else{
					alert("http error:" + request.status);
				}
			}
		};
	};
};
