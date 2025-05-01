PlanMate - Task Management CLI Application 📝🛠️
> A test-driven, layered architecture task management system built in Kotlin following SOLID principles. 🔥

✨ Overview
PlanMate is a command-line task management application designed to help teams stay organized! It supports Admins and Mates , dynamic task states, and a powerful audit system  to track changes.

🔥 Features
✅ User Management 👤🔐

Supports Admins & Mates 🤝

Passwords are securely hashed (No plain text!) 🔒

Admins can create new Mate users 🆕

✅ Project & Task Management 🛠️📝

Admins can create, edit, delete projects & states ✨

Mates can create, edit, delete tasks within a project ✅

Each project has its own set of tasks 🗂️

✅ Dynamic Task States 🚦

States (e.g., TODO, In Progress, Done) are editable 🛠️

Admins can modify states per project ✍️

✅ Task Display & Swimlanes UI 📊🖥️

Users can view tasks visually inside the console 👀

✅ Audit System 🔍📜

Tracks who modified tasks/projects, when, and what changed 🕵️‍♂️

Example log:

👤 User 'abc' changed task 'XYZ-001' from 'InProgress' to 'InDevReview' at 2025/05/24 8:00 PM.
⚡ Technical Architecture
🛠️ Layered Architecture
PlanMate follows a simple, uni-directional dependency rule 🔄:

💾 Data ➡ ⚡ Logic ➡ 🖥️ UI
💾 data → Handles CSV-based storage for users, projects, tasks 📂

⚡ logic → Contains business logic (repositories, authentication, task management) ⚙️

🖥️ ui → Manages command-line interactions 🖥️

🗂️ Data Storage
PlanMate uses multiple CSV files for data persistence 📑

Dependency Inversion ensures easy future storage replacements 🔄

🛠️ Repositories
AuthenticationRepository → Manages users & authentication 🔐

ProjectsRepository → Handles project creation & editing ✏️

TasksRepository → Manages task lifecycle ✅

🧩 Dependency Injection
PlanMate uses Koin 🏗️ for DI to simplify object creation and dependencies 🔧

🧪 Test-Driven Development (TDD)
The system follows TDD ⚡ ensuring 100% test coverage 🔬

Every feature is tested first before implementation 🚀

bash
./gradlew test
3️⃣ Start the CLI application 🖥️

bash
./gradlew run
