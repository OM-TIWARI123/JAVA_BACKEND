package ticket.booking.services;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.util.UserServiceUtil;

public class UserBookingService {

    private User user;

    private List<User> userList;

    private  ObjectMapper objectMapper = new ObjectMapper();
    private static final String USERS_PATH = "app/src/main/java/ticket/booking/localDb/users.json";




    public UserBookingService() throws IOException{
        loadUsers();
    }

    public List<User> loadUsers() throws IOException{
        File users = new File(USERS_PATH);
        userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }
    
    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        loadUsers();
    }
    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 ->{
            return user1.getName().equalsIgnoreCase(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(),user1.getHashedPassword());;
        }).findFirst();

        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListTofile();
            return Boolean.TRUE;
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }
    private void saveUserListTofile() throws IOException {
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile,userList);
    }

    public void fetchBookings(){
        user.printTickets();
    }

    public void cancelTicket(String ticketId) throws IOException {
        List<Ticket>temp = user.getTicketsBooked();
        List<Ticket>temp2 = temp.stream().filter(ticket->!ticket.getTicketId().equals(ticketId) ).toList();
        user.setTicketsBooked(temp2);
        saveUserListTofile();

    }
    public List<Train> getTrains(String source,String destination) throws IOException{
        try{
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source,destination);
        } catch (IOException e) {
            System.out.println("Something went Wrong");
            return new ArrayList<>();
        }

    }
}
