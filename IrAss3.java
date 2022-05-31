/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package irass3;

/**
 *
 * @author Omar Mostafa
 */

import java.io.*;
import java.util.*;


class DictEntry2 {
    public int doc_freq = 0; // number of documents that contain the term
    public int term_freq = 0; //number of times the term is mentioned in the collection
    public int index = 0; //number of times the term is mentioned in the collection

    DictEntry2() {
    }
}

public class IrAss3 {
    
    IrAss3(){
        sources = new HashMap<Integer, String>();
        inverted_index = new HashMap<String, DictEntry2>();
    }
    
    
    Map<Integer, String> sources;  // store the doc_id and the file name
    HashMap<String, DictEntry2> inverted_index; // THe inverted inverted_inverted_index
    
    
    public void buildIndex(String[] files) {
        int i = 0;
        for (String fileName : files) {
            try (BufferedReader file = new BufferedReader(new FileReader(fileName))) {
                sources.put(i, fileName);
                String ln;
                while ((ln = file.readLine()) != null) {
                    String[] words = ln.split("\\W+");
                    for (String word : words) {
                        word = word.toLowerCase();
                        // check to see if the word is not in the dictionary
                        if (!inverted_index.containsKey(word)) {
                            inverted_index.put(word, new DictEntry2());
                            
                        }
                        //set the term_fteq in the collection
                        inverted_index.get(word).term_freq += 1;
                    }
                }
            } catch (IOException e) {
                System.out.println("File " + fileName + " not found. Skip it");
            }
            i++;
        }
        int j = 0;
        for (Object key : inverted_index.keySet()) {
            inverted_index.get(key).index = j;   
            j++;
        }   
    }
    
    
    public ArrayList<String> assignToArray (String [] str){
        ArrayList<String> arrString = new ArrayList<>();
        for (String st : str){
            arrString.add(st.toLowerCase());
        }
        return arrString;
    }
    
    
    public ArrayList<Double> buildVector(String fileName) {
        
        ArrayList<Double> vec = new ArrayList<>(inverted_index.size());
        ArrayList <String> wholeWords = new ArrayList();
        for (Object key : inverted_index.keySet()) {
            vec.add(0.0);
        }     
        try (BufferedReader file = new BufferedReader(new FileReader(fileName))) {
            String ln;
                while ((ln = file.readLine()) != null) {
                    String[] words = ln.split("\\W+");
                    ArrayList<String> lineWords = assignToArray(words);
                    lineWords.forEach((word)-> wholeWords.add(word));
                }
                int i = 0;
                    for (Object key : inverted_index.keySet()) {
                            // check to see if the word is not in the dictionary
                        if (wholeWords.contains(key.toString())) {
                            vec.set(inverted_index.get(key).index,1.0);
                            }
                        else {
                            vec.set(inverted_index.get(key).index,0.0);
                        }
                    i++;
                    }
            }
            catch (IOException e) {
                System.out.println("File " + fileName + " not found. Skip it");
            }
    
            System.out.println("All words: " + inverted_index.keySet());
            System.out.println("The resultant Vector: " + vec);
            return vec;
    
    }
    
    public double cosineSimilarity(ArrayList<Double> vectorA, ArrayList<Double> vectorB) {
    double dotProduct = 0.0;
    double norA = 0.0;
    double norB = 0.0;
    ArrayList<Double> smallerVic = (vectorA.size() < vectorB.size()) ? (vectorA) : (vectorB);
    for (int i = 0; i < smallerVic.size(); i++) {
        dotProduct += vectorA.get(i) * vectorB.get(i);
        norA += Math.pow(vectorA.get(i), 2);
        norB += Math.pow(vectorB.get(i), 2);
    }   
    return dotProduct / (Math.sqrt(norA) * Math.sqrt(norB));
}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IrAss3 index = new IrAss3();
        
        // Those are the files provided by TA: Alaa Adel via email.
        String file1 = "C:\\Users\\EGYPT\\Documents\\NetBeansProjects\\IrAss3\\src\\irass3\\file1.txt";
        String file2 = "C:\\Users\\EGYPT\\Documents\\NetBeansProjects\\IrAss3\\src\\irass3\\file2.txt";
        String file3 = "C:\\Users\\EGYPT\\Documents\\NetBeansProjects\\IrAss3\\src\\irass3\\file3.txt";
        
        index.buildIndex(new String[]{ file1, file2, file3});
        ArrayList<Double> vectorA = index.buildVector(file1);
        ArrayList<Double> vectorB = index.buildVector(file2);
        ArrayList<Double> vectorC = index.buildVector(file3);
        
        double cos1 = index.cosineSimilarity(vectorA, vectorB);
        double cos2 = index.cosineSimilarity(vectorB, vectorC);
        double cos3 = index.cosineSimilarity(vectorA, vectorC);
        System.out.println("Vec1 and Vec2 cosine similarity = " + cos1);
        System.out.println("Vec2 and Vec3 cosine similarity = " + cos2);
        System.out.println("Vec1 and Vec3 cosine similarity = " + cos3);
    }
    
}
