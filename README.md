# README.md

```markdown
# User Session Tracker

## 📋 Project Description

**User Session Tracker** is a Java-based desktop application developed as an Object-Oriented Programming (OOP) Lab project. It simulates a user session management system where an administrator can manage users and track active sessions with simulated IP addresses and login timestamps.

The application demonstrates core OOP concepts including classes, objects, encapsulation, inheritance, polymorphism, method overriding, exception handling, and GUI programming using Java Swing.

---

## ✨ Features

- **🔐 Admin Login** – Secure login with hidden password input
- **👥 User Management** – Add and remove users with role selection (USER/ADMIN)
- **🟢 Session Tracking** – Automatically create sessions when users login
- **🌐 Simulated IP Addresses** – Generate realistic IP addresses for each session
- **⏰ Login Timestamps** – Record exact login time for each session
- **📊 Active Sessions** – View all active sessions in a tabular format
- **🚪 Logout Functionality** – End sessions and logout users
- **📈 Dashboard Statistics** – Display total users, active sessions, and regular users count
- **✅ Validation & Error Handling** – Proper input validation and user-friendly error messages

---

## 🛠️ Technologies Used

| Technology | Purpose |
|------------|---------|
| **Java** | Core programming language |
| **Java Swing** | GUI framework for desktop application |
| **Java Collections** | ArrayList for storing users and sessions |
| **OOP Concepts** | Classes, Inheritance, Polymorphism, Encapsulation |

---

## 📁 Project Structure

```
User-Session-Tracker/
├── src/
│   └── main/
│       └── java/
│           ├── Main.java              # Application entry point
│           ├── User.java              # Base user class
│           ├── Admin.java             # Admin class (extends User)
│           ├── Session.java           # Session with IP and timestamp
│           ├── SessionManager.java    # Core business logic
│           ├── Authentication.java    # Login validation
│           ├── LoginFrame.java        # Login GUI screen
│           ├── DashboardFrame.java    # Main dashboard GUI
│           ├── AddUserFrame.java      # Add user dialog
│           └── SessionTableFrame.java # Session table GUI
│
├── screenshots/                        # Application screenshots
├── README.md                           # Project documentation
└── .gitignore                          # Git ignore file
```

---

## 🎯 OOP Concepts Demonstrated

| Concept | Implementation |
|---------|---------------|
| **Classes & Objects** | All `.java` files define classes with instantiated objects |
| **Encapsulation** | Private fields with public getters/setters in User, Session |
| **Inheritance** | `Admin extends User` – Admin is a specialized User |
| **Polymorphism** | `Admin.displayInfo()` overrides `User.displayInfo()` |
| **Method Overriding** | Admin class overrides displayInfo() method |
| **Constructors** | Multiple constructors in User, Admin, Session |
| **Exception Handling** | Try-catch blocks for validation and error handling |
| **Collections** | ArrayList for storing users and sessions |
| **Event Handling** | ActionListener for GUI button interactions |

---

## 🚀 How to Run

### Prerequisites
- **Java JDK 8 or higher** installed
- **Command Line / Terminal**

### Steps to Run

1. **Clone or download the project**
   ```bash
   git clone https://github.com/yourusername/User-Session-Tracker.git
   cd User-Session-Tracker
   ```

2. **Compile the Java files**
   ```bash
   javac src/main/java/*.java
   ```

3. **Run the application**
   ```bash
   java -cp src/main/java main.java.Main
   ```

### Default Login Credentials
| Field | Value |
|-------|-------|
| **Username** | `admin` |
| **Password** | `admin123` |

---

## 📸 Screenshots

### Login Screen
![Login Screen](screenshots/login-screen.png)
*Secure login with hidden password input*

### Dashboard
![Dashboard](screenshots/dashboard.png)
*Main dashboard with statistics and action buttons*

### Add User
![Add User](screenshots/add-user.png)
*Add new users with role selection*

### Active Sessions
![Active Sessions](screenshots/session-table.png)
*View all active sessions in a tabular format*

---

## 💻 Sample Usage

### 1. Login
- Enter username `admin` and password `admin123`
- Click "Login" or press Enter

### 2. Add a User
- Click "Add User" on the dashboard
- Enter username, password, confirm password
- Select role (USER or ADMIN)
- Click "Add User"

### 3. View Active Sessions
- Click "View Sessions" on the dashboard
- See all active sessions with IP addresses and timestamps
- Select a session and click "End Session" to logout a user

### 4. Remove a User
- Click "Remove User" on the dashboard
- Select a user from the dropdown
- Confirm removal

### 5. Logout
- Click "Logout" on the dashboard
- Confirm logout

---

## 🧪 Testing Scenarios

| Scenario | Expected Result |
|----------|-----------------|
| Login with correct credentials | Success → Dashboard opens |
| Login with incorrect credentials | Error message displayed |
| Add user with empty username | Validation error |
| Add user with existing username | Error message |
| Add user with mismatched passwords | Validation error |
| Remove a user | User removed from system |
| View sessions while no users logged in | Empty table with 0 sessions |
| End a session | User logged out, session removed |

---

## 🔧 Future Enhancements

- [ ] Persistent data storage using file I/O
- [ ] Session duration tracking
- [ ] Login history for users
- [ ] Password encryption
- [ ] Export session reports
- [ ] Multi-admin support

---

## 🤝 Contributing

This project is developed for educational purposes as part of the **GUB CSE 202 Object-Oriented Programming Lab**. Contributions and improvements are welcome!

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

---

## 📝 License

This project is for educational purposes only. Feel free to use it for learning and reference.

---

## 👨‍💻 Author

**Developer:** [Your Name]  
**Course:** CSE 202 – Object-Oriented Programming Lab  
**Institution:** Green University of Bangladesh (GUB)

---

## 🙏 Acknowledgments

- GUB CSE Department
- Java Swing Documentation
- Object-Oriented Programming Concepts

---

**Happy Coding! 🚀**
```

---

## Additional Files

### `.gitignore`

```gitignore
# Compiled class files
*.class

# Package files
*.jar
*.war
*.ear

# IDE files
.idea/
*.iml
.vscode/
.settings/
.project
.classpath

# OS files
.DS_Store
Thumbs.db

# Log files
*.log

# Build directories
build/
bin/
target/
out/

# Screenshots folder (keep but ignore contents)
screenshots/*.png
!screenshots/.gitkeep

# Any other temporary files
*.tmp
*.swp
*~
```

### `screenshots/.gitkeep`

This is an empty file that ensures the `screenshots/` folder is included in the repository even when empty.

---

## Summary of Files Created

| File | Status |
|------|--------|
| `README.md` | ✅ Created |
| `.gitignore` | ✅ Created |
| `screenshots/.gitkeep` | ✅ Created |

---

## Complete Project Structure

```
User-Session-Tracker/
├── src/
│   └── main/
│       └── java/
│           ├── Main.java
│           ├── User.java
│           ├── Admin.java
│           ├── Session.java
│           ├── SessionManager.java
│           ├── Authentication.java
│           ├── LoginFrame.java
│           ├── DashboardFrame.java
│           ├── AddUserFrame.java
│           └── SessionTableFrame.java
├── screenshots/
│   └── .gitkeep
├── README.md
└── .gitignore
```
