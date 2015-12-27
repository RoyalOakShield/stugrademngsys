window.onload = function(){
	//use i to count the grades;
	var i = 0,judge = 1;
	while(judge == 1){
		//send AJAX get the boolean to judge whether to continue sending AJAX to get the grades
		var request_continue = new XMLHttpRequest();
		request_continue.open("GET","backend_url");
		request_continue.send();
		request_continue.onreadystatechange = function(){
			if (request_subject.readyState === 4 && request_subject.status === 200) {
				alert("success to connect with server");
				var judge = JSON.parse(request_continue.responseText);
				if (judge == 1) {
					//send AJAX get the grades
					var request_grades = new XMLHttpRequest();

					request_grades.open("GET","backend_url");
					request_grades.send();
					request_subject.onreadystatechange = function(){
						if(request_subject.readyState === 4 && request_subject.status === 200){
							alert("success to connect with server");
							var gradesArray = JSON.parse(request_grades.responseText);
							addGrades(gradesArray);
							i++;
						}
						else{
							alert("false of loading the grades:" + request_grades.status);
						}
					}
				}
				else{
					alert("Finished!");
				}
			}
			else{
				alert("false:" + request_continue.status);
			}
		}
	}
		document.getElementById("submit").onclick = function(){
			var request_submit = new XMLHttpRequest();

			request_submit.open("POST","backend_url");
			//create a JSON(grades) to save the grades including ranking, name, stuID, changegrade;
			var grades ={	"ranking":"",
							"name":"",
							"stuID":"",
							"grade":"",
						};
			for (var j = 0; j < i; j++) {
				grades["ranking"] = document.getElementsByName("ranking")[j].value;
				grades["name"] = document.getElementsByName("name")[j].value;
				grades["stuID"] = document.getElementsByName("stuID")[j].value;
				grades["grade"] = document.getElementsByName("change")[j].value;
			};

			request_submit.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			request_submit.send(JSON.stringify(grades));
			request_submit.onreadystatechange = function(){
				if(request_submit.readyState === 4 && request_submit.status === 200){
					alert("success to connect with server");
					document.write("submit successful");
					window.open("./Teach_Grade.html","_selft");
				}
				else{
					alert("false:" + request_submit.status);
				}
			}

		}

		//add data including ranking, name, stuId, grade
		function addGrades(gradesArray){
				var grades = document.getElementByTagName("tbody");
				var data = document.createElement("tr");
				var ranking = document.createElement("td");
				var name = document.createElement("td");
				var stuID = document.createElement("td");
				var grade = document.createElement("td");
				var change = document.createElement("input");

				ranking.name = "ranking";
				name.name = "name";
				stuID.name = "stuID";
				grade.name = "grade";
				change.name = "change";

				ranking.innerHTML = gradesArray[0];
				name.innerHTML = gradesArray[1];
				stuID.innerHTML = gradesArray[2];
				grade.innerHTML = gradesArray[3];

				document.grades.appendChild(data);
				document.data.appendChild(ranking);
				document.data.appendChild(name);
				document.data.appendChild(stuID);
				document.data.appendChild(grade);
				document.grade.appendChild(change);
		}
}
