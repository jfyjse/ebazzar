package com.sayone.ebazzar.common;

public interface Notes {

   /*
    * API notes for user related operations
    * */

   String USER_REGISTRATION = "Users can register with email id and other details including addresses \n" + "If the user already exists it will throw an exception \n" + "User status will become true on registration\n" + "Password will get encrypted and will be saved \n" + "http://localhost:8080/users/registration \n";

   String USER_LOGIN = "Users can login with the registered email and password.\n" + "If an incorrect password or email is used an exception will be throwed.\n" + "Will check for the user status and an exception will be thrown if the status is false(deactivated account).\n" + "Authorization header will be provided with the jwt token.\n" + "http://localhost:8080/users/login \n";
   ;

   String USER_PROFILE = "Authorization header will be passed and user will be identified and user details will be displayed along with address.\n" + "http://localhost:8080/users/profile \n";
   ;
   String USER_UPDATE = "User can pass Authorization header and update user’s information such as first name, last name, phone number" + "http://localhost:8080/users/update \n";
   ;
   String FORGOT_PASSWORD = "Users can use this API in case they forgot the password.\n" + "User will pass an email and a mail will be sent to the user with a link for resetting the password.\n" + "The link will contain a token which can be used for resetting the password.\n" + "Forgot password API will be unauthorised API.\n" + "User status will also be checked. Mail will be sent only if the user status is true(active account).\n" + "http://localhost:8080/users/teena@gmail.com/forgot-password \n";
   ;
   String RESET_PASSWORD = "This will be a link sent by mail in the forgot password API. \n" + "Token should be given along with the mail id and the user can provide a new password.\n" + "New password will get encrypted and will replace the old password.\n" + "This API is also unauthorised API.\n" + "http://localhost:8080/users/teena@gmail.com/resetpassword \n";
   ;
   String ADD_ADDRESS = "Users can add an address on placing an order by passing the authorization header." + "http://localhost:8080/users/add-address \n";
   ;
   String DELETE_USER = "User status will become inactive and users won’t be able to login from the registered mail id and password. \n" + "All user details will remain in the database even after the deletion process\n" + "http://localhost:8080/users/delete \n";
   ;


   /*
    * API notes for order related operations
    * */

   String CREATE_ORDER = "Users can place order for products by providing  \n" + "shipping and billing addresses \n";
   String GET_ALL_ORDERS = "Users can view all his orders. ";
   String GET_ORDER = "Users can view the details of a particular order using orderid. ";
   String UPDATE_STATUS = " Seller or Admin can update the status of any order. ";
   String GET_ORDER_STATUS = "Users can view al lthe orders having a particular order status .";
   String CANCEL_ORDER = "Users can cancel an order if it is not in-transit or delivered. ";

   /*
    * API notes for review related operations
    * */

   String GIVE_REVIEW = "Users can give review and rating for a product he has purchased";
   String GET_ALL = "Users can view all the reviews he have given ";
   String GET_REVIEW = "Users can view the details of review given for a particular product";
   String UPDATE_REVIEW = " Users can update or edit an already given review. ";
   String DELETE_REVIEW = "Users can delete a review for a product. ";

}
