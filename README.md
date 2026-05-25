# Rubik's Cube Simulation in Java

A fully interactive console-based 3x3 Rubik’s Cube simulator built with Java.  
This project demonstrates cube mechanics, move tracking, undo functionality, random shuffling, and solving detection using object-oriented programming principles.

---

## Features

- Full 3x3 Rubik’s Cube simulation
- Clockwise and counter-clockwise rotations
- Interactive terminal gameplay
- Move counter system
- Undo last move support
- Random shuffle generator
- Built-in timer
- Solved-state detection
- Custom corner move algorithm
- Clean object-oriented design

---

## Technologies Used

- Java
- OOP (Object-Oriented Programming)
- Console-based interaction
- Data structures (`Stack`, arrays)

---

## Project Structure

CubeSimulationProject/
│
├── CubeSimulation.java
└── README.md

---

## How to Compile

```bash
javac CubeSimulation.java
```

## How to Run

```bash
java CubeSimulation
```

---

## Gameplay Instructions

When the program starts:

1. The cube is automatically shuffled
2. The current cube state is displayed
3. Enter a face name to rotate

### Available Faces

- FRONT
- BACK
- UP
- DOWN
- LEFT
- RIGHT

### Rotation Directions

- `CW` → Clockwise
- `CCW` → Counter-clockwise

---

## Special Commands

### Undo Last Move

```text
UNDO
```

Reverts the most recent user move.

### Apply Corner Algorithm

```text
CORNER
```

Executes a predefined corner movement algorithm.

---

## Example Usage

```text
Enter face:
FRONT

Enter rotation direction:
CW
```

---

## Features Demonstrated

- Matrix rotation logic
- State management
- Move history tracking
- Timer handling
- Command-line interaction
- Rubik’s Cube mechanics simulation

---

## Future Improvements

- JavaFX or Swing GUI
- 3D cube visualization
- AI solving algorithm
- Save/load cube states
- Speedcubing statistics
- Multiplayer mode
- Scramble notation support

---

## Author

Created by Mani Najmeddin

GitHub: https://github.com/mani-najmeddin
