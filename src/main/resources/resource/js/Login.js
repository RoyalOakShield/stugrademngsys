window.onload = function () {
		document.getElementsByName("submit")[0].onclick = function(){
			//send AJAX submit request;
			var request = new XMLHttpRequest();
			request.open("POST","backend_url");

			//create a JSON(loginInfo) to save the information of usrname,password and identity;
			var loginInfo = {
				"Name":"",
				"Password":"",
				"Identity":"",
			};
			loginInfo["Name"] = document.getElementsByName("Login_usrname").value;
			loginInfo["Password"] = document.getElementsByName("Login_password").value;
			var identity = document.getElementsByName("userType")[0];	
				switch(identity.value){
					case "student" : 
						loginInfo["Identity"] = "student";			
						break;
					case "teacher" : 
						loginInfo["Identity"] = "teacher";		
						break;
					case "fucultyman" : 
						loginInfo["Identity"] = "fucultyman";			
						break;
					default: 
						alert("false");
						break;
					}

			request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			request.send(JSON.stringify(loginInfo));
			request.onreadystatechange = function(){
				if(request.readyState === 4 && request.status ===200 ){
					alert("success to connect with server");
					switch(identity.value){
						case "student" :
							window.open('./Stu_Grade.html','_blank');
							break;
						case "teacher" :
							window.open('./Teach_Grade.html','_blank');
							break;
						case "fucultyman" :
							window.open('./Facultyman.html','_blank');
							break;
						default:
							alert("false");
					}
				}
				else{
					alert("false：" + request.status);
			}
		}
	}
}