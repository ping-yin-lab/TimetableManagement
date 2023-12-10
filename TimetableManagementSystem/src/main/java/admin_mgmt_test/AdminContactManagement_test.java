package admin_mgmt_test;

import admin_mgmt.tt_admin.contact;
import admin_mgmt.tt_admin.contactDatabase;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import com.mongodb.client.model.Filters;
import java.util.Random;

class AdminContactManagement_test {
	
	////////////Specification-Based Test cases/////////////////////////////
	//Add new contact
	@Test
    public void Specification_testAddNewContact_ValidInput() {
        // Arrange
		contactDatabase contactDB = new contactDatabase();
		contact contact = new contact("Johnathan", "Admin", "john@example.com", "1231231222");
        // Act
        String add_result = contactDB.addContact(contact);

        Document queryresult = contactDB.displayContactspecific("Johnathan");
        assertEquals(queryresult.get("name"),"Johnathan");
    }

	//Add new contact with duplicate name
	@Test
    public void Specification_testAddNewContact_DuplicationInput() {
        // Arrange
		contactDatabase contactDB = new contactDatabase();
		contact contact = new contact("Johnathan", "Admin", "john@example.com", "1231231222");
        // Act
        String adddup_result = contactDB.addContact(contact);
        // Assert
        if(adddup_result.equals("Passed")) {
        	fail("Duplication cased added");
        } else {
        	assertEquals(adddup_result,"Rejected");
        }
    }
	
	//Update contact with new email and phone
	@Test
    public void Specification_testUpdateContact_ValidInput() {
        // Arrange
		contactDatabase contactDB = new contactDatabase();

        // Add a contact to the database
		contact originalContact = new contact("Coral", "Admin", "lewis@example.com", "1234567890");
        contactDB.addContact(originalContact);

        // Act
        // Update the contact
        contact updatedContact = new contact("Coral", "Admin", "newemail@example.com", "9876543210");
        contactDB.updateContact(updatedContact);

        // Assert
        // Retrieve the updated contact from the database
        Document retrievedContact = contactDB.displayContactspecific("Coral");
        
        // Ensure that the contact details are updated correctly
        assertEquals(updatedContact.getEmail(), retrievedContact.get("email"));
        assertEquals(updatedContact.getTelephone(), retrievedContact.get("phone"));
    }
	
	@Test
    public void Specification_testUpdateContact_NonExistingContact() {
        // Arrange
		contactDatabase contactDB = new contactDatabase();

        // Act
        // Attempt to update a contact that doesn't exist
        contact nonExistingContact = new contact("NonExisting", "Admin", "nonexisting@example.com", "1234567890");
        
        // Assert
        // Ensure that the update operation does not throw an exception or cause any unexpected behavior
        assertDoesNotThrow(() -> contactDB.updateContact(nonExistingContact));
    }
	
	
	@Test
    public void Specification_testDeleteContact_ExistingContact() {
        // Arrange
        contactDatabase contactDB = new contactDatabase();

        // Add a contact to the database
        contact contactToDelete = new contact("Dampling", "Admin", "john@example.com", "1234567890");
        contactDB.addContact(contactToDelete);

        // Act
        // Delete the contact
        Bson filter = Filters.eq("name", "Dampling");
        contactDB.deletecontact(filter);

        // Assert
        // Ensure that the contact is deleted
        Document tester = contactDB.displayContactspecific("Dampling");
        assertEquals(tester.size(),0);
    }

	
    @Test
    public void Specification_testDeleteContact_NonExistingContact() {
        // Arrange
        contactDatabase contactDB = new contactDatabase();

        // Act
        Bson filter = Filters.eq("name", "NonExisting");
        // Attempt to delete a contact that doesn't exist
        assertDoesNotThrow(() -> {
            // Ensure that the delete operation does not throw an exception or cause any unexpected behavior
            contactDB.deletecontact(filter);
        });
    }
    
    //Read check whether the display is null or not
    @Test
    public void Specification_testDisplayContact() {
        // Arrange
        contactDatabase contactDB = new contactDatabase();
        contactDB.displayContact();

        // Assert
        assertNotNull(contactDB.displayContactspecific("Diglett"));
    }
    
    //////////////////Random-based Test case///////////////////////
  //Add new contact
  	@Test
      public void Random_testAddNewContact() {
          // Arrange
  		contactDatabase contactDB = new contactDatabase();
  		Random random = new Random();
  		String randomName = "RandomUser" + random.nextInt(1000); // Add some randomness to the name
        String randomEmail = "random" + random.nextInt(1000) + "@example.com";
        String randomPhone = String.valueOf(1000000000 + random.nextInt(900000000));
        
  		contact contact = new contact(randomName, "Admin", randomEmail, randomPhone);
          // Act
          String add_result = contactDB.addContact(contact);

          Document queryresult = contactDB.displayContactspecific(randomName);
          assertEquals(queryresult.get("name"),randomName);
      }
  	
  	//Display random input
  	public void Random_testDisplayContact() {
        // Arrange
        contactDatabase contactDB = new contactDatabase();
        contactDB.displayContact();
        Random random = new Random();
  		String randomName = "RandomUser" + random.nextInt(1000); // Add some randomness to the name

        // Assert
        assertNull(contactDB.displayContactspecific(randomName));
    }
  	///////////Statement-Based//////////////////////////
  	@Test
    public void Statement_testAddContactStatementCoverage() {
        // Arrange
        contactDatabase contactDB = new contactDatabase();

        // Act
        assertDoesNotThrow(() -> {
            contact contact = new contact("AdminLu", "Admin", "toohandsometohandle@example.com", "88888888");
            contactDB.addContact(contact);
        });
    }

    @Test
    public void testUpdateContactStatementCoverage() {
        // Arrange
        contactDatabase contactDB = new contactDatabase();

        // Act
        assertDoesNotThrow(() -> {
            contact contact = new contact("King", "Admin", "kingy@example.com", "75213456");
            contactDB.addContact(contact);

            // Update the contact
            contact updatedContact = new contact("King", "Admin", "newkingy@example.com", "789232");
            contactDB.updateContact(updatedContact);
        });
    }

    @Test
    public void testDeleteContactStatementCoverage() {
        // Arrange
        contactDatabase contactDB = new contactDatabase();

        // Act
        assertDoesNotThrow(() -> {
            contact contact = new contact("JohnWick", "Admin", "john@example.com", "1234567890");
            contactDB.addContact(contact);
            Bson filter = Filters.eq("name","JohnWick");
            // Delete the contact
            contactDB.deletecontact(filter);
        });
    }

    @Test
    public void testDisplayContactStatementCoverage() {
        // Arrange
        contactDatabase contactDB = new contactDatabase();

        // Act
        assertDoesNotThrow(() -> {
            // Assuming that there are some existing contacts in the database
            // You might want to add some contacts to the database for this test
            contactDB.displayContact();
        });
    }
    //////////Branch-based////////////////////////////
    public class ContactAlreadyExistsException extends Exception { 
        public ContactAlreadyExistsException(String errorMessage) {
            super(errorMessage);
        }
    }
    @Test
    public void Branch_testAddContactCoverage() {
        // Arrange
        contactDatabase contactDB = new contactDatabase();

        // Act
        assertDoesNotThrow(() -> {
            // Test the true branch: add a new contact
            contact newContact = new contact("SamSmith", "Admin", "peter@example.com", "1231231");
            contactDB.addContact(newContact);
            Document queryresult = contactDB.displayContactspecific("Johnathan");
            //assertEquals(queryresult.get("name"),"Peterberg");
            // Test the false branch: add an existing contact (should throw an exception)
            contact existingContact = new contact("SamSmith", "Admin", "peterpeter@example.com", "323443434");
            String adddup_result = contactDB.addContact(existingContact);
        });
    }
    @Test
    public void Branch_testDeleteContactCoverage() {
        // Arrange
        contactDatabase contactDB = new contactDatabase();
        // Act
        assertDoesNotThrow(() -> {
            contact contact = new contact("Pikachu", "Admin", "pikapika@example.com", "5454545");
            contactDB.addContact(contact);
            Bson filter = Filters.eq("name", "Pikachu");
            // Test the true branch: delete an existing contact
            String result = contactDB.deletecontact(filter);
            assertEquals(result,"Passed");
            result = contactDB.deletecontact(filter);
            // Test the false branch: delete a non-existing contact
            assertEquals(contactDB.deletecontact(filter),"Rejected");
        });
    }
}
