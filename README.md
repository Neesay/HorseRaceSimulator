# HorseRaceSimulator
A horse racing simulator with a GUI that includes betting system and customisation of the simulator. This porject is made for ECS414U that includes a deep exploration of using Java as an OOP language and expanding its roots to GUI development using Java Swing standard library. This approach is a first-hand experience to creating a fully fledged GUI system that allows for dynamic functions in various areas to deepen my knowledge in this area.

This program has been developed within a Windows-based system, so the methods below are only guaranteed to work on Windows 8+ devices.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)

## Installation
This project can be dwonloaded from here: https://github.com/Neesay/HorseRaceSimulator

Download the latest Java Development Kit from here to run these files: https://www.oracle.com/uk/java/technologies/downloads/

### Steps
Extract the .zip folder into a suitable location. 

Open up the folder within the terminal, like this:
```bash
PS C:\Users\<your_username>> cd <path>
PS C:\Users\user\Downloads\Part 2>
```

Then compressing Horse.java, Race.java and Session.java files to .class files in the same directory, using "javac" like this:
```bash
PS C:\Users\user\Downloads\Part 2> javac Horse.java
PS C:\Users\user\Downloads\Part 2> javac Race.java
PS C:\Users\user\Downloads\Part 2> javac Session.java
PS C:\Users\user\Downloads\Part 2>
```
![image](https://github.com/Neesay/HorseRaceSimulator/assets/58012269/d2b43803-6b80-4e88-9e56-d3437bc91dd4)

## Usage
The program can then be run by doing "java Session" in the same folder as above where the files were originally compiled within:
```bash
PS C:\Users\user\Downloads\Part 2> java Session 
```

You will be greeted with a welcome message:

![image](https://github.com/Neesay/HorseRaceSimulator/assets/58012269/c72769b9-b684-4b19-a6dc-4369f067faf4)

Then you will be asked to enter your username:

![image](https://github.com/Neesay/HorseRaceSimulator/assets/58012269/fb1a520f-4562-474c-97ae-57732ebcd826)

You will then be able to see the Main page of the program "Racing" section:

![image](https://github.com/Neesay/HorseRaceSimulator/assets/58012269/502c05ab-90d4-4db7-a88b-1eb8234eaac6)

From here you can navigate to every other section of the program:

![image](https://github.com/Neesay/HorseRaceSimulator/assets/58012269/0dd7454f-4867-45db-9d9f-d03e0df1d296)

### Racing

The table shows all horses currently on being used for a race, and your username is laid out on the top left and your wallet amount containing your betting money on the top right:

![image](https://github.com/Neesay/HorseRaceSimulator/assets/58012269/70f7cc6c-b887-4548-838c-ad0e088e7900)

At the bottom we can see the current bets placed by you on horses of your preference and bet amounts.

The Clear button allows you to get rid of the bets if you wanted to decide not to bet anymore.

![image](https://github.com/Neesay/HorseRaceSimulator/assets/58012269/c57e7af7-8790-4f60-aa56-29c88671a5ac)

When the Start button is pressed the simulation is started:

![image](https://github.com/Neesay/HorseRaceSimulator/assets/58012269/d449190e-7ce7-4386-9f04-1035b706b5d4)

Simulation running:

![image](https://github.com/Neesay/HorseRaceSimulator/assets/58012269/e5d8c562-9ecc-4d60-b4ea-0f68eda2a82f)

### Horses
Here you can view the customisation of each horse to your preference, which includes their eyes, body and name.

![image](https://github.com/Neesay/HorseRaceSimulator/assets/58012269/30fd4f88-f6ca-4e2d-b5dc-92c12e7ee5d2)

The Eyes and Body buttons are used to select different customisation choices accordingly to your preference:

![image](https://github.com/Neesay/HorseRaceSimulator/assets/58012269/8d9e0e98-f4df-4c2b-9558-741468351c5a)

When the Name button is pressed you are met with a new window box asking for the new name to be entered:

![image](https://github.com/Neesay/HorseRaceSimulator/assets/58012269/d9cd9cfa-8ad8-4ea5-a1d6-183185552645)

The Remove Horse and Add Horse buttons are used to remove and add horses from the current race:

![image](https://github.com/Neesay/HorseRaceSimulator/assets/58012269/20ab17bf-391d-4805-ac35-556a62105f39)

The Change to Default button is used for to select off of other selected horses so you are able to craft a new horse from scratch:

![image](https://github.com/Neesay/HorseRaceSimulator/assets/58012269/4b9fdfd9-372a-41d8-bec8-ca4b0a43c15a)

At the top right you are able to select each horse in the race currently and customise them to your liking:

![image](https://github.com/Neesay/HorseRaceSimulator/assets/58012269/ab123b2d-7259-4004-9d14-4f1ac1fd9f71)

### Environment
Here you are able to choose different Track Locations with different effects to the race:

![image](https://github.com/Neesay/HorseRaceSimulator/assets/58012269/7d4e4510-b015-4bc4-8645-7755365ae4cd)

You are then able to also change the track lengths of the races to your liking:

![image](https://github.com/Neesay/HorseRaceSimulator/assets/58012269/7f09c5fc-9707-42a4-b414-4b698fa328bc)

The Set Track Design button is used to save these selections and apply them to the race:

![image](https://github.com/Neesay/HorseRaceSimulator/assets/58012269/c447b97f-2bd7-4955-9456-e159befd3680)

## Contributing

If you are willing to work on this project, then please feel free to follow the steps below to ensure your efforts are recognised.

1) Fork the repository

2) Create a new branch (git checkout -b feature)

3) Make your changes

4) Commit your changes (git commit -am 'Add new feature')

5) Push to the branch (git push origin feature)

6) Create a new Pull Request















