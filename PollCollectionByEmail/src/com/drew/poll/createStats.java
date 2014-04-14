package com.drew.poll;
import java.util.ArrayList;


public class createStats {
	ArrayList<ArrayList<Integer>> rawData = new ArrayList<ArrayList<Integer>>();
	
	public createStats(){
		this.rawData = null;
	}
	
	public createStats(ArrayList<ArrayList<Integer>> listOfQuestions){
		this.rawData = listOfQuestions;
	}
	
	//assumes 1 is the best rating.
	public int mostTopRatings(){
		//spot [0] hold question number, spot [1] holds amount of ones for that question
		int [] QuestionAndAmountOfOnes = new int[] {0,0};
		
		for(int i = 0; i < rawData.size(); i++){
			int count =0;
			for(int p = 0; p < rawData.get(0).size();p++){
				if(rawData.get(i).get(p).equals(1)){
					count++;
				}
			}
			if(count > QuestionAndAmountOfOnes[1]){
				QuestionAndAmountOfOnes[0] = (i+1);
				QuestionAndAmountOfOnes[1] = count;
			}
		}
		return QuestionAndAmountOfOnes[0];
	}
	
	
	
	
	
	@Override
	public String toString(){
		String s = "";
		for(int i=0; i<rawData.size();i++){
			s+="Question:" +(i+1)+". ";
			for(int p = 0; p<rawData.get(0).size();p++){
				s+= (rawData.get(i).get(p));
				s+= ", ";
			}
			s+="\n";
		}
		return s;
	}
	
	public static void main(String[] args) {
		ArrayList<Integer> q1 = new ArrayList<Integer>();
		ArrayList<Integer> q2 = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> test = new ArrayList<ArrayList<Integer>>();
		q1.add(1);
		q1.add(2);
		q1.add(4);
		q1.add(1);
		q1.add(2);
		q1.add(3);
		q2.add(1);
		q2.add(2);
		q2.add(6);
		q2.add(6);
		q2.add(1);
		q2.add(1);
		test.add(q1);
		test.add(q2);
		createStats a = new createStats(test);
		System.out.print("Question with Most 1's: " + a.mostTopRatings());
	}
}
