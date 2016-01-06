window.onload = function () {
		document.getElementsByName("submit")[0].onclick = function(){
			//send AJAX submit request;
			var request = new XMLHttpRequest();
			request.open("POST","reqrly");

			//create a JSON(loginInfo) to save the information of usrname,password and identity;
			var loginInfo = {
				"Request":"Login";
				"Detail":{
							"Name":"",
							"Password":"",
							"Identity":"",
				}
			};
			loginInfo.Detail.Name = document.getElementsByName("Login_usrname")[0].value;
			loginInfo.Detail.Password = document.getElementsByName("Login_password")[0].value;
			var identity = document.getElementsByName("userType")[0];	
				switch(identity.value){
					case "student" : 
						loginInfo.Detail.Identity = "student";			
						break;
					case "teacher" : 
						loginInfo.Detail.Identity = "teacher";		
						break;
					case "fucultyman" : 
						loginInfo.Detail.Identity = "fucultyman";			
						break;
					}

			request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			request.send(JSON.stringify(loginInfo));
			request.onreadystatechange = function(){
				if(request.readyState === 4 && request.status ===200 ){
					alert("success to connect with server");
					switch(identity.value){
						case "student" :
							window.open('../../htmls/Stu_Grade.html','_selft');
							break;
						case "teacher" :
							window.open('../../htmls/Teach_Grade.html','_selft');
							break;
						case "fucultyman" :
							window.open('../../htmls/Facultyman.html','_selft');
							break;
					}
				}
				else{
					alert("false：" + request.status);
			}
		}
	}
}
