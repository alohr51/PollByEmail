package com.drew.poll;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

/*
 * users send an email to the address with their answers (integers in this case) with
 * whitespace separating each answer. When all answers are in, the moderator will send
 * a final email with the subject End and the program will tally everything up and provide
 * statistics.
 */
public class PollTally {

	//configure
	String emailUserName = "username";
	String password = "password";
	//end configure
	ArrayList<Integer> q1 = new ArrayList<Integer>();
	ArrayList<Integer> q2 = new ArrayList<Integer>();
	ArrayList<Integer> q3 = new ArrayList<Integer>();
	ArrayList<Integer> q4 = new ArrayList<Integer>();
	ArrayList<Integer> q5 = new ArrayList<Integer>();
	ArrayList<Integer> q6 = new ArrayList<Integer>();
	boolean isDone = false;
	int badInput = 0;

	public void testEnd(Folder inbox){        
		try {
			FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
			Message msg[] = inbox.search(ft);
			for(Message message:msg) {
				try {
					String subject = message.getSubject();		
					
					if("End".equals(subject)){
						analyzeStats(inbox);
						isDone = true;
					}
					else{
						System.out.println("Waiting on responses");
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Stats Error");
				}
			}

		} catch (MessagingException e) {
			System.out.println(e.toString());
		}
	}
	
	public ArrayList<ArrayList<Integer>> addTallys(Folder inbox){
		ArrayList<ArrayList<Integer>> listOfQuestions = new ArrayList<ArrayList<Integer>>();
		try {
			FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
			Message msg[] = inbox.search(ft);
			//length-1 because we do not count the End email sent.
			for(int i =0; i< msg.length-1;i++) {
				try {
					String content = msg[i].getContent().toString();
					String subject = msg[i].getSubject();
					
					//email responses are split by whitespace
					if(!"End".equals(subject)){
						String[] ranks = content.split("\\s+");
						q1.add(Integer.parseInt(ranks[0]));
						q2.add(Integer.parseInt(ranks[1]));
						q3.add(Integer.parseInt(ranks[2]));
						q4.add(Integer.parseInt(ranks[3]));
						q5.add(Integer.parseInt(ranks[4]));
						q6.add(Integer.parseInt(ranks[5]));
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					badInput++;
					System.out.println("error on input parsing");
				}
			}
			
		} catch (MessagingException e) {
			System.out.println(e.toString());
		}
		
		//add all questions and their ratings to an ArrayList of ArrayLists
		listOfQuestions.add(q1);
		listOfQuestions.add(q2);
		listOfQuestions.add(q3);
		listOfQuestions.add(q4);
		listOfQuestions.add(q5);
		listOfQuestions.add(q6);
		
		return listOfQuestions;
	}
	
	public void analyzeStats(Folder inbox){
		ArrayList<ArrayList<Integer>> rawData = new ArrayList<ArrayList<Integer>>();
		rawData = addTallys(inbox);
		createStats c = new createStats(rawData);
		System.out.println(c.mostTopRatings());
		
	}


	public static void printQuestions(List<Integer> a){
		for(int i = 0; i<a.size();i++){
			System.out.print(a.get(i)+", ");
		}
		System.out.println();
	}

	public static void main(String args[]) {
		final PollTally reader = new PollTally();
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getDefaultInstance(props, null);
			Store store = session.getStore("imaps");
			// IMAP host for yahoo.
			store.connect("imap.mail.yahoo.com", "trainingmethodspoll", "Lost4815");
			
			final Folder inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_ONLY);

			final Timer t = new Timer();
			TimerTask tt = new TimerTask() {

				@Override
				public void run() {
					if(!reader.isDone){
						reader.testEnd(inbox);
					}
					else{
						t.purge();
						t.cancel();
					}
				};
			};
			t.schedule(tt,1000,2000);


		} catch (NoSuchProviderException e) {
			System.out.println(e.toString());
			System.exit(1);
		} catch (MessagingException e) {
			System.out.println(e.toString());
			System.exit(2);
		}

	}

}