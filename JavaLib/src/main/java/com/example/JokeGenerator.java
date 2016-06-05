package com.example;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class JokeGenerator {

    String [] jokes = new String[] {
                     "It is so cold outside I saw a politician with his hands in his own pockets",
                     "Police officer: Can you identify yourself, sir? Driver pulls out his mirror and says: Yes, it's me.",
                     " Me : I was born in California A : Which part Me : All of me."};



    public String generateJoke(int count){

        List<String> jokeList = Arrays.asList(jokes);
        if (count >= jokeList.size()) {
            return "";
        }else {
            return jokeList.get(count);
        }
    }
}
