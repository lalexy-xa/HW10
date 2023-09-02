import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class HW {

    public void validNumbersTask1(String path){
        ArrayList<String> numbers = new ArrayList<String>();
        String number = "";

        try (FileReader reader = new FileReader(path)) {
            int c;
            while ((c = reader.read()) != -1) {
                if(c != 13 && c != 10){
                    number = number + (char)c;
                }else if(c == 13){
                    numbers.add(number);
                    number = "";
                }
            }
            numbers.add(number);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        for (String num: numbers){
            if(num.matches("\\(\\d{3}\\) \\d{3}-\\d{4}") || num.matches("\\d{3}-\\d{3}-\\d{4}")){
                System.out.println(num);
            }
        }
    }

    public void jsonTask2(String path){
        List<String[]> users = new ArrayList<>();
        File file = new File("src//user.json");
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            while (line != null) {
                users.add(line.split(" "));
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        User[] user = new User[users.size() - 1];
        for(int i = 1; i < users.size(); i++){
            user[i - 1] = new User(users.get(i)[0], Integer.parseInt(users.get(i)[1]));
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(user);
        try (FileWriter writer = new FileWriter(file))
        {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private class User{
        public String name;
        public int age;

        User(String name, int age){
            this.name = name;
            this.age = age;
        }
    }

    public void numberOgWordsTask3(){
        HashMap<String, Integer> wordsNumber = new HashMap<>();
        try (FileReader reader = new FileReader("src\\word.txt")) {
            int c;
            String word = "";
            while ((c = reader.read()) != -1) {
                if(c != 10 && c != 13 && c != 32){
                    word = word + (char)c;
                }else if(c == 10 || c == 32){
                    if(wordsNumber.containsKey(word)){
                        wordsNumber.put(word, wordsNumber.get(word) + 1);
                    }else{
                        wordsNumber.put(word, 1);
                    }
                    word = "";
                }
            }
            if(wordsNumber.containsKey(word)){
                wordsNumber.put(word, wordsNumber.get(word) + 1);
            }else{
                wordsNumber.put(word, 1);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        ArrayList<String> sortedResult = new ArrayList<>();
        for(String wrd: wordsNumber.keySet()){
            sortedResult.add(wordsNumber.get(wrd) + " " + wrd);
        }

        Collections.sort(sortedResult, Collections.reverseOrder());
        for(String word: sortedResult){
            System.out.println(word.split(" ")[1] + " " + word.split("")[0]);
        }

    }
}
