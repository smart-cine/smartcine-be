# Smart Cine

## Description
The **Booking Cinema Ticket System** allows cinema providers to register branches and manage ticket bookings, schedules, movies, and customers. Each branch can handle ticket sales, view statistics, and manage screenings.

## Table of Contents
- [1. Description](#description)
- [2. Core Features](#core-features)
  - [2.1 Authentication](#authentication)
  - [2.2 Authorization - RBAC + hierarchy of ownership](#authorization---rbac--hierarchy-of-ownership)
  - [2.2 Booking Handle](#booking-handle)
  - [2.3 Payment](#payment)
- [3. Database Design](#database-design)
- [4. Installation](#installation)
- [5. Structure of Application](#structure-of-application)
- [6. Progress](#progress)
- [7. License](#license)

## Project Structure 
```
  |-exception
  |  |-ErrorMessage.java
  |  |-GlobalExceptionHandler.java
  |-model
  |  |-Ranking.java
  |  |-CinemaRoom.java
  |  |-Cinema.java
  |  |-BusinessBank.java
  |  |-Film.java
  |  |-CinemaLayoutSeat.java
  |  |-Perform.java
  |  |-PickSeat.java
  |  |-UserAccount.java
  |  |-Payment.java
  |  |-Account.java
  |  |-Comment.java
  |  |-BusinessAccount.java
  |  |-CinemaLayoutGroup.java
  |  |-BankCinema.java
  |  |-OwnerShip.java
  |  |-OwnerShipTree.java
  |  |-CinemaLayout.java
  |  |-VnPayModel.java
  |  |-BusinessProvider.java
  |  |-CinemaProvider.java
  |  |-BusinessOwnership.java
  |  |-Item.java
  |  |-Tag.java
  |-CinemaManagementApplication.java
  |-pagination
  |  |-PagingModel.java
  |  |-CursorBasedPageable.java
  |  |-PageSpecificationTag.java
  |  |-PageSpecificationPerform.java
  |  |-PageSpecificationCinemaLayout.java
  |  |-PageSpecification.java
  |-mapper
  |  |-JpaConverterJson.java
  |  |-PerformMapper.java
  |  |-CinemaProviderMapper.java
  |  |-MapperConfig.java
  |  |-AccountMapper.java
  |  |-FilmMapper.java
  |  |-TagMapper.java
  |  |-CinemaLayoutMapper.java
  |  |-PickSeatMapper.java
  |  |-CinemaMapper.java
  |  |-CinemaRoomMapper.java
  |  |-JsonToMapConverter.java
  |-service
  |  |-CommentService.java
  |  |-PickSeatService.java
  |  |-JwtService.java
  |  |-PaymentService.java
  |  |-PerformService.java
  |  |-CinemaRoomService.java
  |  |-TagService.java
  |  |-CinemaLayoutService.java
  |  |-CinemaProviderService.java
  |  |-BusinessBankService.java
  |  |-impl
  |  |  |-SocketIOServiceImpl.java
  |  |  |-CommentServiceImpl.java
  |  |  |-UserService.java
  |  |  |-PickSeatServiceImpl.java
  |  |  |-GetPaymentStatusService.java
  |  |  |-VNPayServiceImpl.java
  |  |  |-FilmServiceImpl.java
  |  |  |-BusinessBankServiceImpl.java
  |  |  |-CinemaProviderServiceImpl.java
  |  |  |-TagServiceImpl.java
  |  |  |-OrderPaymentService.java
  |  |  |-CinemaRoomServiceImpl.java
  |  |  |-CinemaServiceImpl.java
  |  |  |-RedisServiceImpl.java
  |  |  |-CinemaLayoutServiceImpl.java
  |  |  |-JwtServiceImpl.java
  |  |  |-PerformServiceImpl.java
  |  |-SocketIOService.java
  |  |-CachingService.java
  |  |-FilmService.java
  |  |-CinemaService.java
  |  |-FilmPriceService.java
  |-auth
  |  |-RegisterRequest.java
  |  |-AuthenticationResponse.java
  |  |-AuthenticationRequest.java
  |  |-AuthenticationController.java
  |  |-AuthorizationService.java
  |  |-AuthenticationService.java
  |-repository
  |  |-CinemaRepository.java
  |  |-PickSeatRepository.java
  |  |-BusinessOwnershipRepository.java
  |  |-CinemaLayoutRepository.java
  |  |-CinemaRoomRepository.java
  |  |-CinemaProviderRepository.java
  |  |-CinemaLayoutSeatRepository.java
  |  |-CommentRepository.java
  |  |-OwnerShipTreeRepository.java
  |  |-AccountRepository.java
  |  |-BusinessAccountRepository.java
  |  |-CinemaLayoutGroupRepository.java
  |  |-PaymentRepository.java
  |  |-PerformRepository.java
  |  |-FilmRepository.java
  |  |-BusinessBankRepository.java
  |  |-BusinessRepository.java
  |  |-TagRepository.java
  |-dto
  |  |-UserDTO.java
  |  |-payment
  |  |  |-BusinessBankDTO.java
  |  |  |-BusinessBankDTOItem.java
  |  |-RankingDTO.java
  |  |-film
  |  |  |-FilmDTO.java
  |  |-TranslateTypeDTO.java
  |  |-FilmPriceDTO.java
  |  |-CommentDTO.java
  |  |-PickSeatDTO.java
  |  |-cinema
  |  |  |-CinemaRoomDTO.java
  |  |  |-CinemaManagerDTO.java
  |  |  |-CinemaDTO.java
  |  |  |-CinemaProviderDTO.java
  |  |  |-item
  |  |  |  |-CinemaRoomDTOItem.java
  |  |  |  |-CinemaLayoutDTOItem.java
  |  |  |  |-CinemaLayoutSeatDTOItem.java
  |  |  |  |-CinemaLayoutGroupDTOItem.java
  |  |  |-CinemaLayoutDTO.java
  |  |-BusinessOwnerShipDTO.java
  |  |-SeatPriceDTO.java
  |  |-perform
  |  |  |-PerformDTOItem.java
  |  |  |-PerformDTO.java
  |  |-TagDTO.java
  |  |-PaymentDTO.java
  |-controller
  |  |-ManagerCinemaController.java
  |  |-OrderPaymentController.java
  |  |-PaymentController.java
  |  |-FilmController.java
  |  |-PickSeatController.java
  |  |-PerformController.java
  |  |-GetPaymentStatusController.java
  |  |-CallbackPaymentController.java
  |  |-cinema
  |  |  |-CinemaProviderController.java
  |  |  |-CinemaRoomController.java
  |  |  |-CinemaController.java
  |  |  |-CinemaLayoutController.java
  |  |-CommentController.java
  |  |-TagController.java
  |  |-BusinessBankController.java
  |-configuration
  |  |-RedisConfiguration.java
  |  |-SecurityConfiguration.java
  |  |-KeyExpiredListener.java
  |  |-JwtAuthenticationFilter.java
  |  |-Subcriber.java
  |  |-ApplicationConfig.java
  |-utils
  |  |-ConvertJsonNameToTypeName.java
  |  |-VnPayHelper.java
  |-common
  |  |-TranslateType.java
  |  |-ErrorKey.java
  |  |-ViewType.java
  |  |-RoomType.java
  |  |-Role.java
  |  |-Status.java
  |  |-LayoutType.java
  |  |-CommentType.java
  |  |-RoleCinema.java
  |  |-RoleProvider.java
  |  |-BusinessRole.java
  |  |-BankType.java
  |  |-VnPayConstant.java
  |  |-SeatStatus.java
  |-payload
  |  |-request
  |  |  |-AddCommentRequest.java
  |  |  |-AddCinemaRequest.java
  |  |  |-OrderRequestDTO.java
  |  |  |-AddOrUpdateCinemaRoom.java
  |  |  |-AddFilmPriceRequest.java
  |  |  |-AddSeatPaymentRequest.java
  |  |  |-CloneLayoutCinemaRequest.java
  |  |  |-AddPerformRequest.java
  |  |  |-AddBusinessBankRequest.java
  |  |  |-PickSeatRequest.java
  |  |  |-DeletePickSeatRequest.java
  |  |  |-AddFilmRequest.java
  |  |  |-AddOrDeleteTagRequest.java
  |  |  |-AddAndDeleteManagerRequest.java
  |  |  |-AddCinemaLayoutRequest.java
  |  |  |-AddOrDeletePickSeatRequest.java
  |  |  |-StatusRequestDTO.java
  |  |  |-AddPaymentRequest.java
  |  |-response
  |  |  |-DataResponse.java
  |  |  |-PagingResponse.java
  |  |  |-SocketResponse.java
  |  |  |-PickSeatResponse.java
  |  |  |-PageResponse.java
  |  |  |-CommentResponse.java
  |-output.txt

```



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

  
### Booking Handle
-  Implementing booking for screenings and managing seat status (e.g., pending, booked) with Nodejs (is pending).
-  Caching pending seat selections in Redis to optimize database writes.
-  Ensuring seats marked as pending can be resolved in case the server is terminated by CronJob (is pending).

### Payment 
- The application features a scalable design that allows providers to easily add multiple types of wallets (VNPAY, MOMO, ZALOPAY) to enhance payment flexibility. Currently supported wallet types include: VNPay ![image](https://github.com/user-attachments/assets/82c569bd-731d-4283-a760-14773b42189a)



## Database Design
LINK: https://dbdiagram.io/d/Trat-DJien-ITMC-Solution-65bf2fc6ac844320ae6458be
![image](https://github.com/user-attachments/assets/c3207c23-4d83-434e-91fb-88bad935f754)

## Installation 
  - **Prerequisite**: Docker with latest version
  - Clone this repository, move to its directory on your device and run
  ```zsh
  docker compose up
  ```


## Structure of Application
![image](https://github.com/realtime-cinema/CinemaManagement/assets/90248665/e361de87-dad8-4714-8981-593d135c5969)

## Progress
In progress 

## License 
This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for details.
