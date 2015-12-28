window.onload = function(){
		//send AJAX get the subjects
		var request_subject = new XMLHttpRequest();

		request_subject.open("GET","reqrly");
		request_subject.send();
		request_subject.onreadystatechange = function(){
			if(request_subject.readyState === 4 && request_subject.status === 200){
				alert("success to connect with server");
				var subjArray = JSON.parse(request_subject.responseText);
				addSubject(subjArray);
			}
		}

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

					request_grades.open("GET","backend_url?subject:" + document.getElementById("subject").value);
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
		//create<option> for <select> to add subject which can be chosen
		function addSubject(subjArray){
			for (var i = 0; i < subjArray.length; i++) {
				var subj = document.getElementById("subject");
				var option = document.createElement("option");
				option.innerHTML = subjArray[i];
				subj.appendChild(option);
			};
		}
		//add data including ranking, name, stuId, grade
		function addGrades(gradesArray){
				var grades = document.getElementByTagName("tbody");
				var data = document.createElement("tr");
				var ranking = document.createElement("td");
				var name = document.createElement("td");
				var stuId = document.createElement("td");
				var grade = document.createElement("td");

				ranking.innerHTML = gradesArray[0];
				name.innerHTML = gradesArray[1];
				stuId.innerHTML = gradesArray[2];
				grade.innerHTML = gradesArray[3];

				document.grades.appendChild(data);
				document.data.appendChild(ranking);
				document.data.appendChild(name);
				document.data.appendChild(stuId);
				document.data.appendChild(grade);
		}
	}
