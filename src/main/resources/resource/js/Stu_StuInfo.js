window.onload = function () {
		document.getElementsByName("submit")[0].onclick = function(){
			//send AJAX submit request;
			var request = new XMLHttpRequest();
			request.open("POST","backend_url");

			//create a JSON(loginInfo) to save the information of usrname,password,confirm and identity;
			var stuInfo = {
				"Name":"",
				"StuID":"",
				"Sex":"", 
				"Birthday":"",
				"Institute":"",
				"Specialty":"",
				//"Identity":"student",
			};
			stuInfo["Name"] = document.getElementsByName("Info_usrname")[0].value;
			stuInfo["StuID"] = document.getElementsByName("Info_StuID")[0].value;
			stuInfo["Sex"] = document.getElementsByName("Info_sex")[0].value;
			stuInfo["Birthday"] = document.getElementsByName("Info_Birthday")[0].value;
			stuInfo["Institute"] = document.getElementsByName("Info_Institude")[0].value;
			stuInfo["Specialty"] = document.getElementsByName("Info_Specialty")[0].value;

			request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			request.send(JSON.stringify(stuInfo));
			request.onreadystatechange = function(){
				if(request.readyState === 4 && request.status ===200 ){
					alert("success to connect with server");
					window.open("../../htmls/Stu_Grade.html","_selft");
				}
				else{
					alert("false:" + request.status);
				}
			}
		}
	}