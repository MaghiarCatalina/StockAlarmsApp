# Stock Alarm App

### Database
Tables: User, Stock, Alarm
* User - Stock (ManyToMany): A user can have many stocks monitored. 
  A stock can be monitored by many users.
* User - Alarm (OneToMany): A user can create many alarms. An alarm belongs to 
  a single user.
* Stock - Alarm (OneToMany): A stock can have many alarms on it. An alarm is for
a single stock.

### Implementation Details (TBA)

### Endpoints available (TBA)