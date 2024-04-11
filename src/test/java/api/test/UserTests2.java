package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;


import api.endpoints.UserEndpoints;
import api.endpoints.UserEndpoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests2 {
	
	Faker faker;
	User userPayload;
	
	public Logger logger;//logs
	
	@BeforeClass 
	public void setup()
	{
		faker=new Faker();
		userPayload=new User();
		
		
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().subscriberNumber(10));
		userPayload.setUserStatus(0);
		
		//logs
		logger=LogManager.getLogger(this.getClass()); //this.getclass - same class name displayed in logs
		logger.debug("debugging.....");
	}
	
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("*********Creating user***************");
		
		Response response=UserEndpoints2.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("**********User is created  ***************");

	}
	@Test(priority=2)
	public void testGetUserbyName()
	{
		logger.info("********** Reading User Info ***************");

		Response response=UserEndpoints2.readUser(this.userPayload.getUsername());
		response.then().log().all();
		//response.statusCode();
		Assert.assertEquals(response.getStatusCode(), 200); //in assertion getStatusCode
		logger.info("**********User info  is displayed ***************");
	}
	@Test(priority=3)
	public void testUpdateUserbyName()
	{
		logger.info("********** Updating User ***************");

		//update data using payload
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response=UserEndpoints2.updateUser(userPayload,this.userPayload.getUsername());
		response.then().log().all();
		//response.statusCode();
		//response.then().log().body().statusCode(200); //validation
		
		Assert.assertEquals(response.getStatusCode(), 200); //in assertion getStatusCode
		logger.info("********** User updated ***************");

		//Checking data after update
				Response responseAfterupdate=UserEndpoints2.readUser(this.userPayload.getUsername());
				Assert.assertEquals(responseAfterupdate.getStatusCode(),200);

	}
	
	@Test(priority=4)
	public void testDeleteUserbyName()
	{
		logger.info("**********   Deleting User  ***************");

		Response response=UserEndpoints2.deleteUser(this.userPayload.getUsername());
		response.then().log().all();
		//response.statusCode();
		Assert.assertEquals(response.getStatusCode(), 200); //in assertion getStatusCode
		logger.info("********** User deleted ***************");
	}
	
}
