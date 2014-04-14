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
	public String mostTopRatings(){
		//spot [0] hold question number, spot [1] holds amount of ones for that question
		int [] questionAndAmountOfOnes = new int[] {0,0};
		ArrayList<Integer> ties = new ArrayList<Integer>(0);
		String result="";

		for(int i = 0; i < rawData.size(); i++){
			int count =0;
			for(int p = 0; p < rawData.get(0).size();p++){
				if(rawData.get(i).get(p).equals(1)){
					count++;
				}
			}
			if(count == questionAndAmountOfOnes[1]){
				//make sure no duplicate numbers
				if(!ties.contains(questionAndAmountOfOnes[0])){
					ties.add(questionAndAmountOfOnes[0]);
				}
				if(!ties.contains((i+1))){
					ties.add(i+1);
				}
			}
			if(count > questionAndAmountOfOnes[1]){
				questionAndAmountOfOnes[0] = (i+1);
				questionAndAmountOfOnes[1] = count;
				//previous ties are broken
				ties.clear();
			}
		}
		//no ties
		if(ties.isEmpty()){
			result = String.valueOf(questionAndAmountOfOnes[0]);
		}
		else{
			result = "";
			result+="TIE between ";
			for(int i =0; i<ties.size(); i++){
				result+= ties.get(i);
				result+=", ";
			}
		}
		return result ;
	}

	//assumes numberOfQuestions is the worst rating.
	public String mostLowRatings(){
		//spot [0] hold question number, spot [1] holds amount of ones for that question
		int [] questionAndAmountOfWorsts = new int[] {0,0};
		int numberOfQuestions = rawData.size();
		ArrayList<Integer> ties = new ArrayList<Integer>(0);
		String result="";

		for(int i = 0; i < rawData.size(); i++){
			int count =0;
			for(int p = 0; p < rawData.get(0).size();p++){
				if(rawData.get(i).get(p).equals(numberOfQuestions)){
					count++;
				}
			}
			if(count == questionAndAmountOfWorsts[1]){
				//make sure no duplicate numbers
				if(!ties.contains(questionAndAmountOfWorsts[0])){
					ties.add(questionAndAmountOfWorsts[0]);
				}
				if(!ties.contains((i+1))){
					ties.add(i+1);
				}
			}
			if(count > questionAndAmountOfWorsts[1]){
				questionAndAmountOfWorsts[0] = (i+1);
				questionAndAmountOfWorsts[1] = count;
				//previous ties are broken
				ties.clear();
			}
		}
		//no ties
		if(ties.isEmpty()){
			result = String.valueOf(questionAndAmountOfWorsts[0]);
		}
		else{
			result = "";
			result+="TIE between ";
			for(int i =0; i<ties.size(); i++){
				result+= ties.get(i);
				result+=", ";
			}
		}
		return result ;
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
		ArrayList<Integer> q3 = new ArrayList<Integer>();
		ArrayList<Integer> q4 = new ArrayList<Integer>();
		ArrayList<Integer> q5 = new ArrayList<Integer>();
		ArrayList<Integer> q6 = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> test = new ArrayList<ArrayList<Integer>>();
		q1.add(1);q1.add(2);q1.add(4);q1.add(1);
		q2.add(1);q2.add(1);q2.add(1);q2.add(3);
		q3.add(1);q3.add(1);q3.add(1);q3.add(5);
		q4.add(2);q4.add(3);q4.add(6);q4.add(1);
		q5.add(6);q5.add(6);q5.add(6);q5.add(5);
		q6.add(1);q6.add(1);q6.add(1);q6.add(1);
		test.add(q1);test.add(q2);test.add(q3);
		test.add(q4);test.add(q5);test.add(q6);

		createStats a = new createStats(test);
		System.out.print("Question with Most 1's: " + a.mostTopRatings());
	}
}
