import java.io.*;
import java.util.*;
public class Apriori_Algo {
//Function to calculate support for 1-itemset and store it in count[]
public static int one_itemset_support(int[] count) throws IOException
{
//Counter for number of transactions
int trans_num = 0;
//Parse the transactions file and store absolute support values in count[]
Scanner read = new Scanner (new File("sample.txt"));
read.useDelimiter(",");
String item_read;
while (read.hasNext())
{
item_read = read.next();
//Specific handling for \n
if (item_read.contains("\n")) {
trans_num++;
item_read = item_read.replace("\n", " ");
String[] splits = item_read.split(" ");
for(int i=0;i<2;i++) {
item_read = splits[i];
System.out .println("Item Read = " + item_read);
switch (item_read) {
case "A": count[0]++;
break;
case "B": count[1]++;
break;
case "C": count[2]++;
break;
case "D": count[3]++;
break;
case "E": count[4]++;
break;
case "F": count[5]++;
break;
case "G": count[6]++;
break;
case "H": count[7]++;
break;
case "I": count[8]++;
break;
case "J": count[9]++;
break;
default: break;
}
}
}
//Normal case- without \n
else {
System.out .println("Item Read = " + item_read);
switch (item_read) {
case "A": count[0]++;
break;
case "B": count[1]++;
break;
case "C": count[2]++;
break;
case "D": count[3]++;
break;
case "E": count[4]++;
break;
case "F": count[5]++;
break;
case "G": count[6]++;
break;
case "H": count[7]++;
break;
case "I": count[8]++;
break;
case "J": count[9]++;
break;
default: break;
}
}
}
read.close();
return trans_num+1;
}
//Function to write selected candidates to a text file
public static void addToFile(int pos, double support, int run) {
String fileName = "Apriori_Run_"+run+".txt";
BufferedWriter writer = null;
try {
//create a temporary file
File runFile = new File(fileName);
if(runFile.exists() && !runFile.isDirectory())
System.out .println("File Exists");
else
runFile.createNewFile();
// This will output the full path where the file will be written to...
System.out .println("Path for " + fileName + ":");
System.out .println(runFile.getCanonicalPath());
writer = new BufferedWriter(new FileWriter(runFile, true));
switch(pos) {
case 0: writer.write("A="+support+"\n");
break;
case 1: writer.write("B="+support+"\n");
break;
case 2: writer.write("C="+support+"\n");
break;
case 3: writer.write("D="+support+"\n");
break;
case 4: writer.write("E="+support+"\n");
break;
case 5: writer.write("F="+support+"\n");
break;
case 6: writer.write("G="+support+"\n");
break;
case 7: writer.write("H="+support+"\n");
break;
case 8: writer.write("I="+support+"\n");
break;
case 9: writer.write("J="+support+"\n");
break;
default: break;
}
} catch (Exception e) {
e.printStackTrace();
} finally {
try {
// Close the writer regardless of what happens...
writer.close();
} catch (Exception e) {
}
}
}
//Apriori Algorithm for Association Rules Mining
public static void runApriori(int[] count, int min_sup, int
min_conf, int total_trans) throws IOException {
//Calculated support for each item
double sup_calc;
//Run 1 for 1-itemsets
for(int i=0;i<count.length;i++) {
sup_calc = (count[i]*100f)/total_trans;
System.out .println("Support for item "+i+" = "+sup_calc);
if(sup_calc < min_sup)
continue;
else addToFile(i,sup_calc,1);
}
//Run 2 for 2-itemsets
//Storing single candidate itemsets in an Array List
List<String> two_items = new ArrayList<String>();
//Length of the 2-itemsets list
int two_len = 0;
//Parse the 1-itemsets file and store items in two_items list
Scanner read = new Scanner (new File("Apriori_Run_1.txt"));
read.useDelimiter("=");
String item_read;
while (read.hasNext())
{
item_read = read.next();
System.out .println("##Considering Candidate 1-itemsets:\n" +
item_read);
//Storage
if (item_read.contains("\n")) {
item_read = item_read.replace("\n", " ");
String[] splits = item_read.split(" ");
if(splits.length>1)
two_items.add(splits[1]);
}
else two_items.add(item_read);
}
read.close();
System.out .println("Selected 1-itemsets:\n" + two_items);
two_len = two_items.size();
System.out .println("Number of 1-itemsets: " + two_len);
Scanner read_orig = new Scanner (new File("sample.txt"));
read_orig.useDelimiter(",");
String item_read_orig;
int flag;
float[][] count_2 = new float[10][10];
while (read_orig.hasNext())
{
item_read_orig = read_orig.nextLine();
for(int i=0;i<two_len-1;i++) {
for(int j=i+1;j<two_len;j++) {
if(item_read_orig.contains(two_items.get(i)))
if(item_read_orig.contains(two_items.get(j)))
count_2[i][j]++;
}
}
}
//Run 2 for 1-itemsets
for(int i=0;i<two_len-1;i++) {
for(int j=i+1;j<two_len;j++) {
if(count_2[i][j]>0.0) {
float calc = (count_2[i][j]*100f)/total_trans;
if(calc < min_sup)
continue;
else count_2[i][j] = calc;
}
}
}
String fileName = "Apriori_Run_2.txt";
BufferedWriter writer = null;
try {
//create a temporary file
File runFile = new File(fileName);
if(runFile.exists() && !runFile.isDirectory())
System.out .println("File Exists");
else
runFile.createNewFile();
System.out .println("Path for " + fileName + ":");
System.out .println(runFile.getCanonicalPath());
for(int i=0;i<two_len-1;i++) {
for(int j=i+1;j<two_len;j++) {
writer = new BufferedWriter(new FileWriter(runFile,
true));
switch(i+""+j) {
case "01":
writer.write("A+B="+count_2[i][j]+"\n");
break;
case "02":
writer.write("A+C="+count_2[i][j]+"\n");
break;
case "03":
writer.write("A+D="+count_2[i][j]+"\n");
break;
case "04":
writer.write("A+E="+count_2[i][j]+"\n");
break;
case "05":
writer.write("A+F="+count_2[i][j]+"\n");
break;
case "06":
writer.write("A+G="+count_2[i][j]+"\n");
break;
case "07":
writer.write("A+H="+count_2[i][j]+"\n");
break;
case "08":
writer.write("A+I="+count_2[i][j]+"\n");
break;
case "09":
writer.write("A+J="+count_2[i][j]+"\n");
break;
case "12":
writer.write("B+C="+count_2[i][j]+"\n");
break;
case "13":
writer.write("B+D="+count_2[i][j]+"\n");
break;
case "14":
writer.write("B+E="+count_2[i][j]+"\n");
break;
case "15":
writer.write("B+F="+count_2[i][j]+"\n");
break;
case "16":
writer.write("B+G="+count_2[i][j]+"\n");
break;
case "17":
writer.write("B+H="+count_2[i][j]+"\n");
break;
case "18":
writer.write("B+I="+count_2[i][j]+"\n");
break;
case "19":
writer.write("B+J="+count_2[i][j]+"\n");
break;
case "23":
writer.write("C+D="+count_2[i][j]+"\n");
break;
case "24":
writer.write("C+E="+count_2[i][j]+"\n");
break;
case "25":
writer.write("C+F="+count_2[i][j]+"\n");
break;
case "26":
writer.write("C+G="+count_2[i][j]+"\n");
break;
case "27":
writer.write("C+H="+count_2[i][j]+"\n");
break;
case "28":
writer.write("C+I="+count_2[i][j]+"\n");
break;
case "29":
writer.write("C+J="+count_2[i][j]+"\n");
break;
case "34":
writer.write("D+E="+count_2[i][j]+"\n");
break;
case "35":
writer.write("D+F="+count_2[i][j]+"\n");
break;
case "36":
writer.write("D+G="+count_2[i][j]+"\n");
break;
case "37":
writer.write("D+H="+count_2[i][j]+"\n");
break;
case "38":
writer.write("D+I="+count_2[i][j]+"\n");
break;
case "39":
writer.write("D+J="+count_2[i][j]+"\n");
break;
case "45":
writer.write("E+F="+count_2[i][j]+"\n");
break;
case "46":
writer.write("E+G="+count_2[i][j]+"\n");
break;
case "47":
writer.write("E+H="+count_2[i][j]+"\n");
break;
case "48":
writer.write("E+I="+count_2[i][j]+"\n");
break;
case "49":
writer.write("E+J="+count_2[i][j]+"\n");
break;
case "56":
writer.write("F+G="+count_2[i][j]+"\n");
break;
case "57":
writer.write("F+H="+count_2[i][j]+"\n");
break;
case "58":
writer.write("F+I="+count_2[i][j]+"\n");
break;
case "59":
writer.write("F+J="+count_2[i][j]+"\n");
break;
case "67":
writer.write("G+H="+count_2[i][j]+"\n");
break;
case "68":
writer.write("G+I="+count_2[i][j]+"\n");
break;
case "69":
writer.write("G+J="+count_2[i][j]+"\n");
break;
case "78":
writer.write("H+I="+count_2[i][j]+"\n");
break;
case "79":
writer.write("H+J="+count_2[i][j]+"\n");
break;
case "89":
writer.write("I+J="+count_2[i][j]+"\n");
break;
default: break;
}
}
}
} catch (Exception e) {
e.printStackTrace();
} finally {
try {
writer.close();
} catch (Exception e) {
}
}
for(int i=0;i<two_len-1;i++) {
for(int j=i+1;j<two_len;j++) {
if(count_2[i][j] < min_sup)
continue;
else System.out .println("Support for items "+i+" and "+j+" = "+count_2[i][j]);
}
}
}
public static void main(String[] args) throws IOException {
cleanup();
//Get the min support and confidence from the user
int min_sup=0,min_conf=0,total_trans=0;
int[] count;
String temp;
BufferedReader br = new BufferedReader(new
InputStreamReader(System.in ));
System.out .print("Enter Minimum Support (%): ");
try{
temp = br.readLine(); //Read user input for minimum support
min_sup = Integer.parseInt(temp);
if ((min_sup < 1) || (min_sup > 100))
throw new Exception();
}
catch(Exception e){
System.out .println("Error: Please enter an integer number between 1-100 !\n"); //Handling invalid user input
System.out .println("Run Program Again...\n");
System.exit(0);
}
System.out .print("Enter Minimum Confidence (%): ");
try{
temp = br.readLine(); //Read user input for minimum confidence
min_conf = Integer.parseInt(temp);
if ((min_conf < 1) || (min_conf > 100))
throw new Exception();
}
catch(Exception e){
System.out .println("Error: Please enter an integer number between 1-100 !\n"); //Handling invalid user input
System.out .println("Run Program Again...\n");
System.exit(0);
}
//Initialize support for each item
count = new int[10];
total_trans = one_itemset_support(count);
//Display support values
for(int i=0;i<10;i++) {
System.out .println("Number of transactions with item " + i +
" = " + count[i]);
}
System.out .println("Number of transactions = " + total_trans);
runApriori(count,min_sup,min_conf,total_trans);
}
public static void cleanup() throws IOException {
File file_1,file_2,file_3,file_4;
file_1 = new File("Apriori_Run_1.txt");
file_2 = new File("Apriori_Run_2.txt");
file_3 = new File("Apriori_Run_3.txt");
file_4 = new File("Apriori_Run_4.txt");
try{
file_1.delete();
file_2.delete();
file_3.delete();
file_4.delete();
}catch(Exception e){
e.printStackTrace();
}
}
}
