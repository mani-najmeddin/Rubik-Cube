import java.util.*;

public class CubeSimulation {

enum Color { WHITE, YELLOW, RED, ORANGE, BLUE, GREEN }

private final int UP = 0, DOWN = 1, FRONT = 2, BACK = 3, LEFT = 4, RIGHT = 5;
private Color[][][] cube = new Color[6][3][3];
private Color[][][] solvedCube = new Color[6][3][3];
private Random rand = new Random();

// Move history
private Stack<String> historyUser = new Stack<>();
public int moveCountUser = 0;

// Timer
private long startTime = 0;
private boolean timerStarted = false;

public CubeSimulation() {
init();
for (int i = 0; i < 6; i++)
for (int j = 0; j < 3; j++)
for (int k = 0; k < 3; k++)
solvedCube[i][j][k] = cube[i][j][k];
}

private void init() {
for (int i = 0; i < 6; i++)
for (int j = 0; j < 3; j++)
for (int k = 0; k < 3; k++)
cube[i][j][k] = Color.values()[i];
}

private void startTimerIfNeeded() {
if (!timerStarted) {
startTime = System.currentTimeMillis();
timerStarted = true;
}
}

private String getElapsedTime() {
if (!timerStarted) return "00:00";
long seconds = (System.currentTimeMillis() - startTime) / 1000;
long minutes = seconds / 60;
seconds %= 60;
return String.format("%02d:%02d", minutes, seconds);
}

private void rotateFaceCW(int face) {
Color[][] old = cube[face];
Color[][] neu = new Color[3][3];
for (int i = 0; i < 3; i++)
for (int j = 0; j < 3; j++)
neu[j][2 - i] = old[i][j];
cube[face] = neu;
}

// ================= Basic Moves =================
private void FRONT(boolean isUser) {
rotateFaceCW(FRONT);
Color[] temp = cube[UP][2].clone();
for(int i=0;i<3;i++){
cube[UP][2][i]=cube[LEFT][2-i][2];
cube[LEFT][2-i][2]=cube[DOWN][0][2-i];
cube[DOWN][0][2-i]=cube[RIGHT][i][0];
cube[RIGHT][i][0]=temp[i];
}
if(isUser){ historyUser.push("FRONT"); moveCountUser++; }
}

private void BACK(boolean isUser) {
rotateFaceCW(BACK);
Color[] temp = cube[UP][0].clone();
for(int i=0;i<3;i++){
cube[UP][0][i]=cube[RIGHT][i][2];
cube[RIGHT][i][2]=cube[DOWN][2][2-i];
cube[DOWN][2][2-i]=cube[LEFT][2-i][0];
cube[LEFT][2-i][0]=temp[i];
}
if(isUser){ historyUser.push("BACK"); moveCountUser++; }
}

private void UP(boolean isUser) {
rotateFaceCW(UP);
Color[] temp = cube[FRONT][0].clone();
cube[FRONT][0] = cube[RIGHT][0].clone();
cube[RIGHT][0] = cube[BACK][0].clone();
cube[BACK][0] = cube[LEFT][0].clone();
cube[LEFT][0] = temp;
if(isUser){ historyUser.push("UP"); moveCountUser++; }
}

private void DOWN(boolean isUser) {
rotateFaceCW(DOWN);
Color[] temp = cube[FRONT][2].clone();
cube[FRONT][2] = cube[LEFT][2].clone();
cube[LEFT][2] = cube[BACK][2].clone();
cube[BACK][2] = cube[RIGHT][2].clone();
cube[RIGHT][2] = temp;
if(isUser){ historyUser.push("DOWN"); moveCountUser++; }
}

private void LEFT(boolean isUser) {
rotateFaceCW(LEFT);
Color[] temp = new Color[3];
for(int i=0;i<3;i++) temp[i]=cube[UP][i][0];
for(int i=0;i<3;i++){
cube[UP][i][0]=cube[BACK][2-i][2];
cube[BACK][2-i][2]=cube[DOWN][i][0];
cube[DOWN][i][0]=cube[FRONT][i][0];
cube[FRONT][i][0]=temp[i];
}
if(isUser){ historyUser.push("LEFT"); moveCountUser++; }
}

private void RIGHT(boolean isUser) {
rotateFaceCW(RIGHT);
Color[] temp = new Color[3];
for(int i=0;i<3;i++) temp[i]=cube[UP][i][2];
for(int i=0;i<3;i++){
cube[UP][i][2]=cube[FRONT][i][2];
cube[FRONT][i][2]=cube[DOWN][i][2];
cube[DOWN][i][2]=cube[BACK][2-i][0];
cube[BACK][2-i][0]=temp[i];
}
if(isUser){ historyUser.push("RIGHT"); moveCountUser++; }
}

// ================= Inverse Moves =================
private void FRONT_INVERSE() { FRONT(false); FRONT(false); FRONT(false); }
private void BACK_INVERSE() { BACK(false); BACK(false); BACK(false); }
private void UP_INVERSE() { UP(false); UP(false); UP(false); }
private void DOWN_INVERSE() { DOWN(false); DOWN(false); DOWN(false); }
private void LEFT_INVERSE() { LEFT(false); LEFT(false); LEFT(false); }
private void RIGHT_INVERSE() { RIGHT(false); RIGHT(false); RIGHT(false); }

// ================= Undo =================
private void undoLastUserMove() {
if(!historyUser.isEmpty()){
String last = historyUser.pop();
switch(last){
case "FRONT": FRONT_INVERSE(); break;
case "FRONT_CCW": FRONT(false); break;
case "BACK": BACK_INVERSE(); break;
case "BACK_CCW": BACK(false); break;
case "UP": UP_INVERSE(); break;
case "UP_CCW": UP(false); break;
case "DOWN": DOWN_INVERSE(); break;
case "DOWN_CCW": DOWN(false); break;
case "LEFT": LEFT_INVERSE(); break;
case "LEFT_CCW": LEFT(false); break;
case "RIGHT": RIGHT_INVERSE(); break;
case "RIGHT_CCW": RIGHT(false); break;
case "CORNER_MOVE_1":
case "CORNER_MOVE_2":
case "CORNER_MOVE_3":
case "CORNER_MOVE_4":
// Undo moves individually
FRONT_INVERSE(); DOWN_INVERSE(); RIGHT(false); DOWN(false);
break;
}
moveCountUser--;
System.out.println("Undo applied: " + last);
} else System.out.println("No user moves to undo!");
}

// ================= Check Solved =================
private boolean isSolved(){
for(int i=0;i<6;i++)
for(int j=0;j<3;j++)
for(int k=0;k<3;k++)
if(cube[i][j][k]!=solvedCube[i][j][k]) return false;
return true;
}

// ================= Corner as User Move =================
private void applyCornerAsUser(){
RIGHT_INVERSE();
DOWN_INVERSE();
RIGHT(false);
DOWN(false);

// به جای الگوریتم، به moveCountUser اضافه کن
moveCountUser += 4;
historyUser.push("CORNER_MOVE_1");
historyUser.push("CORNER_MOVE_2");
historyUser.push("CORNER_MOVE_3");
historyUser.push("CORNER_MOVE_4");

System.out.println("Three-color corner applied as user moves!");
}

// ================= Shuffle =================
private void shuffle(int moves){
String[] allMoves = {"FRONT","BACK","UP","DOWN","LEFT","RIGHT"};
for(int i=0;i<moves;i++){
int m = rand.nextInt(allMoves.length);
moveWithoutCount(allMoves[m]);
}
}

private void moveWithoutCount(String move){
switch(move.toUpperCase()){
case "FRONT": FRONT(false); break;
case "BACK": BACK(false); break;
case "UP": UP(false); break;
case "DOWN": DOWN(false); break;
case "LEFT": LEFT(false); break;
case "RIGHT": RIGHT(false); break;
}
}

// ================= Print =================
private void printFace(int f){
for(int i=0;i<3;i++){
for(int j=0;j<3;j++){
System.out.print(cube[f][i][j].toString().charAt(0) + " ");
}
System.out.println();
}
System.out.println();
}

private void printCube(){
String[] names = { "UP", "DOWN", "FRONT", "BACK", "LEFT", "RIGHT" };
for(int i=0;i<6;i++){
System.out.println(names[i]+" face:");
printFace(i);
}
}

// ================= Interactive =================
public void playInteractive(){
Scanner sc = new Scanner(System.in);
System.out.println("Shuffling cube...");
shuffle(20);
printCube();

while(true){
System.out.println("⏱ Time: " + getElapsedTime() + " | User Moves: " + moveCountUser);
System.out.println("Enter face (FRONT, BACK, UP, DOWN, LEFT, RIGHT) or UNDO / CORNER:");
String faceInput = sc.nextLine().trim().toUpperCase();

switch(faceInput){
case "FRONT": case "BACK": case "UP": case "DOWN": case "LEFT": case "RIGHT":
System.out.println("Enter rotation direction: CW or CCW:");
String dir = sc.nextLine().trim().toUpperCase();
if(!dir.equals("CW") && !dir.equals("CCW")){
System.out.println("Invalid direction! Move not executed.");
continue;
}
startTimerIfNeeded();
boolean clockwise = dir.equals("CW");
switch(faceInput){
case "FRONT": if(clockwise) FRONT(true); else { FRONT_INVERSE(); historyUser.push("FRONT_CCW"); moveCountUser++; } break;
case "BACK": if(clockwise) BACK(true); else { BACK_INVERSE(); historyUser.push("BACK_CCW"); moveCountUser++; } break;
case "UP": if(clockwise) UP(true); else { UP_INVERSE(); historyUser.push("UP_CCW"); moveCountUser++; } break;
case "DOWN": if(clockwise) DOWN(true); else { DOWN_INVERSE(); historyUser.push("DOWN_CCW"); moveCountUser++; } break;
case "LEFT": if(clockwise) LEFT(true); else { LEFT_INVERSE(); historyUser.push("LEFT_CCW"); moveCountUser++; } break;
case "RIGHT": if(clockwise) RIGHT(true); else { RIGHT_INVERSE(); historyUser.push("RIGHT_CCW"); moveCountUser++; } break;
}
break;

case "UNDO": undoLastUserMove(); break;
case "CORNER": applyCornerAsUser(); break;

default: System.out.println("Invalid input! Try again."); continue;
}

printCube();

if(isSolved()){
System.out.println("🎉 Cube solved in "+moveCountUser+" moves!");
System.out.println("⏱ Final Time: " + getElapsedTime());
break;
}
}

sc.close();
}

// ================= Main =================
public static void main(String[] args){
CubeSimulation cube = new CubeSimulation();
cube.playInteractive();
}
}