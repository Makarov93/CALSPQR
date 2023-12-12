package com.company;

import java.io.IOException;
import java.util.*;

import static com.company.Main.Tools.*;

public class Main {
    public static void main(String[] args) throws EnterNumException {
        // Ввод информации от пользователя
        enterInformation();
        // Разбор на составляющие
        parsingExpression(example);
        // Обнаружение системы вычисления (Арабский вариант)
        if (Correction.IsThatArab(firstStr) && Correction.IsThatArab(secondStr)) {
            System.out.println(calculation(Integer.parseInt(firstStr), operation, Integer.parseInt(secondStr)));
        }
        // Обнаружение системы вычисления (Римский вариант)
        else if (Correction.isThatRome(firstStr) && Correction.isThatRome(secondStr)) {
            initArray();
            int one = translateRomeToArab(firstStr, romeNumerals);
            int two = translateRomeToArab(secondStr, romeNumerals);
            int res = (calculation(one, operation, two));
            if (res <= 0 || one < 1 || one >10 || two < 1 || two > 10){
                throw new EnterNumException();
            }
            else {
                System.out.println(translateArabToRomeAll(res));
            }
        }
        else{
            // Обработка исключений
            throw new EnterNumException();
        }
    }

    // Класс, который проводит операции вычисления
    public static class Tools {

        // строка от пользователя
        static String example;

        // оператор примера
        static String operation = "";
        // 2 переменные из строки example
        static String firstStr;
        static String secondStr;

        // массив римских значений
        static Map<String, String> romeNumerals = new HashMap<String, String>();

        // ввод
        static void enterInformation() {
            System.out.println("Enter your ex");
            Scanner in = new Scanner(System.in);
            example = in.nextLine();
        }

        // перевод
        static void parsingExpression(String example) throws EnterNumException {
            String str1 = "";
            String str2 = "";
            // local var
            boolean triggerToChangeTwo = false;
            char[] word = example.toCharArray();
            ArrayList<String> number = new ArrayList<>();
            ArrayList<String> number2 = new ArrayList<>();
            for (int i = 0; i < word.length; i++) {
                if (word[i] == '*' || word[i] == '/' || word[i] == '-' || word[i] == '+') {
                    operation = String.valueOf(word[i]);
                    triggerToChangeTwo = true;
                    continue;
                }

                if (word[i] == ' ' && number.isEmpty()) {
                    triggerToChangeTwo = false;
                    continue;
                } else if (!triggerToChangeTwo && word[i] != ' ') {
                    number.add(String.valueOf(word[i]));
                    continue;
                }
                if (word[i] == ' ' && number.size() != 0) {
                    triggerToChangeTwo = true;
                    continue;
                } else if (triggerToChangeTwo) {
                    number2.add(String.valueOf(word[i]));
                    continue;
                }
            }
            for (String s : number) {
                str1 += s;
            }
            for (String s : number2) {
                str2 += s;
            }
            firstStr = str1;
            secondStr = str2;
            if (firstStr.isEmpty() || secondStr.isEmpty() || operation.equals("")) {
                throw new EnterNumException();
            }
        }

        // вычисления
        static int calculation(int first, String operation, int second) {
            int answer = 0;
            switch (operation) {
                case ("*"):
                    answer = first * second;
                    break;
                case ("/"):
                    answer = first / second;
                    break;
                case ("+"):
                    answer = first + second;
                    break;
                case ("-"):
                    answer = first - second;
                    break;
            }
            return answer;
        }

        // перевод римских чисел в арабские
        static int translateRomeToArab(String number, Map<String, String> romeNumerals) {
            char[] symbols = number.toCharArray();
            int resultRomeToArab;
            int[] arabNum = new int[symbols.length];
            for (int i = 0; i < symbols.length; i++) {
                String value = String.valueOf(symbols[i]);
                arabNum[i] = Integer.parseInt(romeNumerals.get(value));
            }
            if (symbols.length == 1) {
                resultRomeToArab = arabNum[0];
                return resultRomeToArab;
            } else {
                for (int i = 0; i < arabNum.length; i++) {
                    int twoIndexVal = i + 1;
                    if (arabNum[i] == 1 && arabNum[twoIndexVal] == 5
                            || arabNum[i] == 10 && arabNum[twoIndexVal] == 50
                            || arabNum[i] == 100 && arabNum[twoIndexVal] == 500) {
                        arabNum[i] *= -1;
                    } else if (arabNum[i] == 1 && arabNum[twoIndexVal] == 10
                            || arabNum[i] == 10 && arabNum[twoIndexVal] == 100
                            || arabNum[i] == 100 && arabNum[twoIndexVal] == 1000) {
                        arabNum[i] *= -1;
                    }
                    if (twoIndexVal == arabNum.length - 1) {
                        break;
                    }
                }
                int sum = 0;
                for (int j = 0; j < arabNum.length; j++) {
                    sum = sum + arabNum[j];
                }
                return sum;
            }

        }

        // перевод арабских чисел в римские
        static String translateArabToRomeAll(int val) {
            int firstInt = 1;
            String result = "";
            String valString = "" + val;
            char[] arr = valString.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                int depth = String.valueOf(Math.abs(val)).length() - 1 - i;
                String firstString = String.valueOf(arr[i]);
                firstInt = Integer.parseInt(firstString) * (int) Math.pow(10, depth);
                result += translateArabToRomeOne(firstInt);
            }
            return result;
        }

        static String translateArabToRomeOne(int val) {
            // инициализация массивов
            String[] one = {"I", "V", "X"};
            String[] two = {"X", "L", "C"};
            String[] three = {"C", "D", "M"};
            String[] four = {"M", "MD"};
            // получаем количество нулей
            int depth = String.valueOf(Math.abs(val)).length() - 1;

            // получаем первое значение
            String valString = "" + val;
            char[] arr = valString.toCharArray();
            String firstString = String.valueOf(arr[0]);
            int firstInt = Integer.parseInt(firstString);
            String[] actualArray = new String[3];
            //Смотрим разрядность
            if (depth == 0) {
                actualArray = one;
            }
            if (depth == 1) {
                actualArray = two;
            }
            if (depth == 2) {
                actualArray = three;
            }
            if (depth == 3) {
                actualArray = four;
            }
            if (firstInt == 1) {
                return actualArray[0];
            }
            if (firstInt == 2) {
                return actualArray[0] + actualArray[0];
            }
            if (firstInt == 3) {
                return actualArray[0] + actualArray[0] + actualArray[0];
            }
            if (firstInt == 4) {
                return actualArray[0] + actualArray[1];
            }
            if (firstInt == 5) {
                return actualArray[1];
            }
            if (firstInt == 6) {
                return actualArray[1] + actualArray[0];
            }
            if (firstInt == 7) {
                return actualArray[1] + actualArray[0] + actualArray[0];
            }
            if (firstInt == 8) {
                return actualArray[1] + actualArray[0] + actualArray[0] + actualArray[0];
            }
            if (firstInt == 9) {
                return actualArray[0] + actualArray[2];
            }
            if (firstInt == 10) {
                return actualArray[0];
            }
            return "";
        }

        static void initArray() {
            romeNumerals.put("I", "1");
            romeNumerals.put("V", "5");
            romeNumerals.put("X", "10");
            romeNumerals.put("L", "50");
            romeNumerals.put("C", "100");
            romeNumerals.put("D", "500");
            romeNumerals.put("M", "1000");
        }
    }

    // Класс, который уточняет систему вычислений
    public static class Correction {

        static boolean IsThatArab(String value) {
            try {
                int x = Integer.parseInt(value);
                if (x > 10 || x < 1){
                    return false;
                }
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        static boolean isThatRome(String value) {
            int count = 0;
            String[] romeSimphols = {"I", "V", "X", "L", "C", "D", "M", "MD"};
            String[] arr = value.split("");
            for (String s : arr) {
                for (int i = 0; i < romeSimphols.length; i++) {
                    if (s.equals(romeSimphols[i])) {
                        count++;
                    }
                    else{
                        continue;
                    }
                }

            }
            if (arr.length != count){
                return false;
            }
            return true;
        }
    }
}



