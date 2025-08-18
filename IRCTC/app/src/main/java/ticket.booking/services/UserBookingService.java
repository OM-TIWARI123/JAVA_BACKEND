package ticket.booking.services;

import java.io.File;

import ticket.booking.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserBookingService {

    private User user;

    private static final OBJECT_MAPPER = new ObjectMapper(); 
    private static final String USERS_PATH = "../localDb/users.json";

    
    public UserBookingService(User user1){
        this.user = user1;
        File users = new File(USERS_PATH);
    }
}
