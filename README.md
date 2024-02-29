
# Shopping List App

This is a Shopping List App that allows users to manage their shopping lists by listing, adding and editing items.

## Table of Contents

1. Room Database
2. UI Components
3. Installation
4. Running the project

## Database

The database setup is divided into two main modules: Item and Note, each with its own set of components: Entity, Dao, Repo, and OfflineRepo.
Additionally, there are three more files that are part of the database setup: AppContainer, Converters, and ShopListDatabase.

### Item and Note Modules

Both Item and Note modules follow a similar structure. They each have an Entity class (Item and Note) that represents a row in the respective
table in the database. 

The Dao interfaces (ItemDao and NoteDao) define the methods for interacting with the respective tables in the database.

The Repo interfaces (ItemsRepo and NotesRepo) define the contract for a repository that provides insert, update, delete, and retrieve
operations from a given data source. 

The OfflineRepo classes (OfflineItemsRepo and OfflineNotesRepo) are implementations of the Repo interfaces that use Dao to perform 
database operations.

### AppContainer

The AppContainer interface is an app container for dependency injection. It provides instances of ItemsRepo and NotesRepo. 
The AppDataContainer class is an implementation of AppContainer that provides instances of OfflineItemsRepo and OfflineNotesRepo.

### Converters

The Converters class contains type converters that allow Room to reference complex data types and transform them into a form 
that can be stored in the database. It includes methods to convert a list of integers to a string and vice versa.

### ShopListDatabase

The ShopListDatabase abstract class extends RoomDatabase and represents the database for the app. It includes abstract methods
to get ItemDao and NoteDao. It also includes a method to get the database instance.

Setup allows the app to use Room database efficiently and perform CRUD operations on the items and notes tables. It also ensures 
that the app can work in offline mode by interacting with the local database. 

The app uses a local database to store the items on emulator device. The database is managed using Room, which is an 
abstraction layer over SQLite. It provides compile-time checks of SQL queries and ensures that they are correctly formatted.


## UI Components

The app uses Jetpack Compose to build the UI. The UI components are organized into different folders
based on their functionality.


The `item` folder contains the UI components and ViewModel classes for the three item-related screens in the app: ItemList, ItemAdd and ItemEdit.
Similarly, the `note` folder contains the UI components and ViewModel classes for the three note-related screens: NoteList, NoteAdd and NoteEdit.
The ViewModel classes manage the UI-related data. They include a itemUiState and noteUiState property that holds the current  state of the UI and 
methods to update the UI state, validate the input and save the item keeping these operations separate from the UI components. The ViewModel 
also interacts with ItemsRepo and NotesRepo, abstracting away the details of data storage and retrieval from the UI layer.

Having a separate ViewModel for each screen has several advantages:

  Separation of Concerns: Each ViewModel handles the data and business logic for a specific screen. This makes the code more readable and maintainable.

  Testability: Since each ViewModel is responsible for a specific screen, it’s easier to write unit tests for each screen’s functionality. 

  State Management: ViewModels survive configuration changes such as screen rotations. This means the UI state is preserved and doesn’t need 
  to be manually saved and restored. 

  Data Sharing: ViewModels provide a way to share data between different composables in a safe and efficient manner.


## Installation

Before running the Shopping List App, ensure that the following software is installed on your machine:

1. **Java Development Kit (JDK)**

2. **Android Studio** 

The project is self-contained, and Android Studio should automatically handle the installation of any additional software or libraries needed (like Gradle and Android SDK).

## Running the Project


1) Open AVD Manager: On the main Android Studio toolbar, click on the AVD Manager
   (Android Virtual Device Manager) icon. It looks like a small phone in front of a purple circle.

2) Create Virtual Device: In the AVD Manager, click on the + Create Virtual Device button at the bottom
   left of the window.

3) Choose a Device: A dialog will appear where you can choose the type of hardware your virtual device should emulate.
   There are many predefined devices available, such as Pixel phones and Nexus devices.
   Select the one you want and click Next. (I used Pixel_3a_API_34_extension_level_7_x86_64)

4) Choose a System Image: Next, you’ll need to choose a system image (the version of Android) for your virtual device.
   It’s recommended to use the latest available image. If the image you want isn’t downloaded yet,
   click Download next to the image name to download it. Once the image is downloaded, select it and click Next.

5) Configure the AVD: click Finish.

6) Launch the Emulator: Back in the AVD Manager, you’ll see your new virtual device in the list. Click the green Play button to start the emulator.

