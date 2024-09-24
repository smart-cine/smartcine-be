# Smart Cine

## Description
The **Booking Cinema Ticket System** allows cinema providers to register branches and manage ticket bookings, schedules, movies, and customers. Each branch can handle ticket sales, view statistics, and manage screenings.

## Table of Contents
1. [Description](#description)
2. [Core Features](#core-features)
  [2.1 Authentication](#authentication)
  [2.2 Authorization - RBAC + hierarchy of ownership](#authorization--rbac-+hierarachy-of-ownership)
  [2.2 Realtime Booking](#realtime-booking)
  [2.3 Payment](#payment)
3. [Installation](#installation)
4. [Contributing](#contributing)
5. [License](#license)


## Core Features
### Authentication
- Secure user registration and login for providers, branch managers, and customers using JWT (JSON Web Tokens).
- Each time a request is made, database will check existing the credentials of its token.
- Redis is used to enhance authentication performance by caching credentials with a time-to-live (TTL). This reduces database load and speeds up the authentication process, ensuring quick access to user sessions.
### Authorization - RBAC + hierarchy of ownership
![image](https://github.com/user-attachments/assets/75a3b2e7-55c5-40ad-9556-43246b419809)
- RBAC (Role-Based Access Control) is used to determine permissions for Business Accounts and Customer Accounts.
- Business Account just have permissions on own their Cinema provider or cinema branches:
  - Business Accounts: Can access resources associated with their own cinema provider or cinema branches.
  - Customer Accounts: Have access to booking and ticket management features.
- Ownership Validation Using DFS concept:
  - The system checks whether the provider account or branch account is directly linked to the cinema branch.
  - If not directly linked, the DFS algorithm traverses up the ownership hierarchy to check if the provider account is associated with the parent provider of the cinema.
  - If a link is found, the request continues to role validation. If no link is found, access is denied.
  - Code:  ![image](https://github.com/user-attachments/assets/f323275b-73c8-4479-9297-e4068e47138c)
  - Table Database: To optimize searching, used NoSQL concept, create just only one table for link child entity to parent entity via ID ![image](https://github.com/user-attachments/assets/d260e283-be2e-448a-a00a-be2d937f338d)
 
  

- Role Permission Check:
  - Once ownership is verified, the system checks the role associated with the account to determine if it has the necessary permissions for the requested API.
  - If the role has permission, access is granted. Otherwise, access is denied.
  - Code: ![image](https://github.com/user-attachments/assets/5e631f12-2a22-4b99-a9a3-b0dfcb3107fc)
  - Table Database: ![image](https://github.com/user-attachments/assets/2fa02d9c-ed91-48a4-bab5-5f1948f42149)

  
### Realtime Booking 
### Payment 



![image](https://github.com/user-attachments/assets/75a3b2e7-55c5-40ad-9556-43246b419809)

### Integration
VNPay e-wallet
### Ordering seat realtime feature
Most Applications's ordering seat is just a normal API, if two or more users at the same time request to order just same seat, if the person whose condition good than, they will have that seat, otherwise that person will be in the queue constantly.
And myteam's solution is: if a person who pick before, that this seat will lock preventing to other request that want to pick it. if fail, just rollback, and the seat will be available.
### Flow of Payment API's activities
- If account want to navigate to payment, Backend will create and return the URL payment for Client(by the way, this url is the limited time url, and if this's expire, these seats was picked will be unlocked)
- If everything is sucessful, Backend will return the callback url (this url including useful params for showing state of this account's payment). By the way in backend will access to "IPN" API for getting information of this payment and then handling that.

## The database desgin 
LINK: https://dbdiagram.io/d/Trat-DJien-ITMC-Solution-65bf2fc6ac844320ae6458be

![image](https://github.com/user-attachments/assets/c3207c23-4d83-434e-91fb-88bad935f754)
## Structure of Application
![image](https://github.com/realtime-cinema/CinemaManagement/assets/90248665/e361de87-dad8-4714-8981-593d135c5969)

## Progress
In progress 

## License 
This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for details.
