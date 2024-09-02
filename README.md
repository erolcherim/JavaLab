# JavaLab

Notice: The app runs on port 8081 to not interfere with mysql adminer docker container.
The application simulates an MDM (Mobile Device Management). In this system users can create organisations (representing a company, a university, etc), create multiple accounts in those organisation (one for each user) and link mobile devices to the organisation (this is done conceptually, we are not actually installing any agent on anything). The users can then send certain tasks to the endpoints, such as virus scanning, updating, restarting, etc. The tasks always finish with generating an event that states the completion status of the task.
For example, a user sends a "scan" task to an endpoint. An event is generated as a result of the task stating that "A scan task has been completed. There have been X issues found." 
The users can then visualise the generated events filtering by:
- events generated by a certain user
- events generated on a single endpoint
- all events in the organisation
Business Requirements:
1. Users must be able to register
2. Users must be able to login
3. Users must be able to be a part of an organisation
4. Multiple organisations can be created
5. Endpoints can and must be installed in organisations
6. Users can send tasks to endpoints
7. Tasks must finish with generating an event
8. An endpoint can receive multiple tasks
9. One event will generate per tasks
10. There must be a set number of tasks
Main features:
- Authentication and authorisation (JWT)
- Organisation creation
- Task sending and event generation
- Event viewing (per user, per endpoint, per organisation)
- Endpoint grouping in organisations
App functionalities:
- Org creation
	- Constraints:
		- Length constraints for all attributes (min 1 max 50)		
	- Api call:
			`curl --location 'http://localhost:8081/api/v1/organisation/' \`
			`--header 'Content-Type: application/json' \`
			`--data '{`
			`"name":"org1",`
			`"country":"RO",`
			`"fieldOfActivity":"Technology"`
			`}'`
	- Returns:
		- The Organisation object: 
			`{`
				`"id": 1,`
				`"name": "org1",`
				`"country": "RO",`
				`"field": "Technology"`
			`}`	
- Registration
	- Constraints:
		- Email must have email format
		- Password must follow security requirements:
			- Min 10 characters, alpha-numeric, one UpperCase letter (min), one symbol (min)
		- Passwords must match
		- Length constraints for FirstName, LastName (min 1 max 50)
		- Must create user in an existing Org
	- Api call:
			`curl --location 'localhost:8081/api/v1/auth/register' \`
			`--header 'Content-Type: application/json' \`
			`--data-raw '{`
			`"email":"test234@mail.com",`
			`"password":"Password123!@",`
			`"confirmPassword":"Password123!@",`
			`"firstName":"Test",`
			`"lastName":"User",`
			`"orgId":"1"`
			`}'`
	- Returns:
		- A Bearer token used for Authorisation
			`{`
				`"token": "token"`
			`}`
- Authentication
	- Constraints:
		- Must login with a valid username-password combination
	- Api call:
			`curl --location 'localhost:8081/api/v1/auth/authenticate' \`
			`--header 'Content-Type: application/json' \`
			`--data-raw '{`
			`"email":"test234@mail.com",`
			`"password":"Password123!@"`
			`}'`
- Create endpoint:
	- Constraints:
		- Length constraints: name, operatingSystem (min 1 max 50)
	- Api call:
			`curl --location 'http://localhost:8081/api/v1/endpoint/' \`
			`--header 'Content-Type: application/json' \`
			`--data '{`
			`"name":"telefon 1",`
			`"operatingSystem":"Android 13.1",`
			`"orgId":"1"`
			`}'`
	 - Returns: 
		 - The endpoint object
		 `{`
				`"id": 2,`
				`"name": "telefon 1",`
				`"operatingSystem": "Android 13.1",`
				`"ip": "10.17.25.145",`
				`"organisation": {`
					`"id": 1,`
					`"name": "org1",`
					`"country": "RO",`
					`"field": "Technology"`
					`},`
				`"tasks": null`
			
- Get user:
	- Constraints:
		- userId must point to an existing user
	- Api call:
		 `curl --location 'http://localhost:8081/api/v1/user/2'` 
	- Returns:
		- A UserResponse object
		 `{`
			`"email": "test23@mail.com",`
			`"firstName": "Test",`
			`"lastName": "User",`
			`"orgId": "1"`
		 `}`
 - Assign task to endpoint:
	 - Constraints:
		 - taskId must point to an existing task
		 - endpointId must point to an existing endpoint
		 - this request must be authorised with a Bearer JWT 
	 - Api call:
		 `curl --location 'http://localhost:8081/api/v1/endpoint/task' \`
			`--header 'Content-Type: application/json' \`
			`--header 'Authorization: ••••••' \`
			`--data '{`
			`"taskId":"1",`
			`"endpointId":"1"`
			`}'`
	 - Returns:
		 - A String response
			 `Assigned OK`

- Get events generated by user
	- Retrieves all the events that were created as a result of a specific user assigning a task to the endpoint. The user is taken from the application context on the assignRequestToEndpoint call
	- Constraints:
		- the userId must point to an existing user
	- Api call:
		- `curl --location 'http://localhost:8081/api/v1/event/user/1'`
	- Returns:
		- A list of eventResponses:
			 `[`
					`{`
						`"generatedByUser": "1",`
						`"name": "Endpoint restarted successfully",`
						`"result": true`
					`},`
					`{`
						`"generatedByUser": "1",`
						`"name": "Endpoint restarted successfully",`
						`"result": true`
					`},`
			 ]`
- Get events filtered by endpoint
	- Retrieves all the events generated on an endpoint
	- Constraints:
		- the endpointId must point to an existing endpoint
	- Api call:
		- `curl --location 'http://localhost:8081/api/v1/event/endpoint/1'`
	- Returns:
		- A list of eventResponses:
			 `[`
					`{`
						`"generatedByUser": "1",`
						`"name": "Endpoint restarted successfully",`
						`"result": true`
					`},`
					`{`
						`"generatedByUser": "1",`
						`"name": "Endpoint restarted successfully",`
						`"result": true`
					`},`
			 ]`
- Get events filtered by organisation
	- Retrieves all endpoints in the org then all events generated on the endpoints
	- Constraints:
		- the orgId must point to an existing organisation
	- Api call:
		- `curl --location 'http://localhost:8081/api/v1/event/organisation/1'`
	- Returns:
		- A list of eventResponses:
			 `[`
					`{`
						`"generatedByUser": "1",`
						`"name": "Endpoint restarted successfully",`
						`"result": true`
					`},`
					`{`
						`"generatedByUser": "1",`
						`"name": "Endpoint restarted successfully",`
						`"result": true`
					`},`
			 ]`

## Unit Tests

Unit tests have been implemented for all controllers/services except the Authentication/JWT ones because those are pretty generic methods. 
![[Pasted image 20240822142623.png]]
