window.onload = function(){
	//use i to count the grades' line;
	var i = 0,judge = 1;
	while(judge == 1){
		//send AJAX get the boolean to judge whether to continue sending AJAX to get the grades
		var request_continue = new XMLHttpRequest();
		request_continue.open("GET","backend_url");
		request_continue.send();
		request_continue.onreadystatechange = function(){
			if (request_continue.readyState === 4 && request_continue.status === 200) {
				alert("success to connect with server");
				var judge = JSON.parse(request_continue.responseText);
				if (judge == 1) {
					//send AJAX get the grades
					var request_grades = new XMLHttpRequest();

					request_grades.open("GET","backend_url?");
					request_grades.send();
					request_grades.onreadystatechange = function(){
						if(request_grades.readyState === 4 && request_grades.status === 200){
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
		//add data including subject, teacher, grade, ranking;
		function addGrades(gradesArray){
				var grades = document.getElementByTagName("tbody");
				var data = document.createElement("tr");
				var subject = document.createElement("td");
				var teacher = document.createElement("td");
				var grade = document.createElement("td");
				var ranking = document.createElement("td");

				subject.innerHTML = gradesArray[0];
				teacher.innerHTML = gradesArray[1];
				grade.innerHTML = gradesArray[2];
				ranking.innerHTML = gradesArray[3];

				document.grades.appendChild(data);
				document.data.appendChild(subject);
				document.data.appendChild(teacher);
				document.data.appendChild(grade);
				document.data.appendChild(ranking);
		}
	}