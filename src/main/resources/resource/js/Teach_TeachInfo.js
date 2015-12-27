window.onload = function () {
		document.getElementsByName("submit")[0].onclick = function(){
			//send AJAX submit request;
			var request = new XMLHttpRequest();
			request.open("POST","backend_url");

			//create a JSON(loginInfo) to save the information of usrname,password,confirm and identity;
			var teaInfo = {
				"Name":"",
				"TeaID":"",
				"Institute":"",
				"Specialty":"",
				//"Identity":"teacher",
			};
			teaInfo["Name"] = document.getElementsByName("Info_usrname")[0].value;
			teaInfo["TeaID"] = document.getElementsByName("Info_TeaID")[0].value;
			teaInfo["Institute"] = document.getElementsByName("Info_Institude")[0].value;
			teaInfo["Specialty"] = document.getElementsByName("Info_Specialty")[0].value;

			request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			request.send(JSON.stringify(teaInfo));
			request.onreadystatechange = function(){
				if(request.readyState === 4 && request.status ===200 ){
					alert("success to connect with server");
					window.open("../../htmls/TeateaGrade.html","_selft");
				}
				else{
					alert("false:" + request.status);
				}
			}
		}
	}