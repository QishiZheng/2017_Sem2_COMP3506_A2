package au.edu.uq.itee.comp3506.assn2.tests;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import au.edu.uq.itee.comp3506.assn2.api.TestAPI;
import au.edu.uq.itee.comp3506.assn2.entities.CallRecord;
import au.edu.uq.itee.comp3506.assn2.entities.MyBSTree;
import au.edu.uq.itee.comp3506.assn2.entities.MyList;
import au.edu.uq.itee.comp3506.assn2.entities.MyMultimap;

/**
 * Hook class used by automated testing tool.
 * The testing tool will instantiate an object of this class to test the functionality of your assignment.
 * You must implement the method and constructor stubs below so that they call the necessary code in your application.
 * 
 * @author 
 */
public final class AutoTester implements TestAPI {
	// TODO Provide any data members required for the methods below to work correctly with your application.
	
	//Initialize a binary search tree for storing all switchIDs
	static MyBSTree switchIDTree = new MyBSTree();
	
	//Initialize a multimap uses dialler as key and a list of CallRecord as value
	MyMultimap<Long, CallRecord> diallerMultimap = new MyMultimap<Long, CallRecord>();
	
	//Initialize a multimap uses receiver as key and a list of CallRecord as value
	MyMultimap<Long, CallRecord> receiverMultimap = new MyMultimap<Long, CallRecord>();
	//Initialize a list of call records
	MyList<CallRecord> callRecList  = new MyList<CallRecord>();

	public AutoTester() {
		//fill in switchIDTree with all data from switches.txt
		switchIDTree = switchReader("data/switches.txt");
		//store all call records from file in a temp list
		List<CallRecord> allRecords = callRecordReader("data/call-records.txt");
		//fill in my data structures with data
		for (CallRecord callRec:allRecords) {
			//fill in diallerMultimap
			diallerMultimap.put(callRec.getDialler(), callRec);
			//fill in receiverMultimap
			receiverMultimap.put(callRec.getReceiver(), callRec);
			//fill in my callRecList
			callRecList.add(callRec);
		}
		
		//adds connection counts to every switches in switchIDTree
		for(int i = 0; i < callRecList.size(); i++) {	//iterate over all call records
			if (!callRecList.get(i).getConnectionPath().isEmpty()) {	//if path is not empty
				for (int s = 0; s < callRecList.get(i).getConnectionPath().size(); s++) {	//iterate over all switches in connection path
					if (switchIDTree.isExist(callRecList.get(i).getConnectionPath().get(s))) {	//check if switchIDTree has this ID
						switchIDTree.increaseCount(callRecList.get(i).getConnectionPath().get(s));  //if so, increase count of this switch 
					}
				}
			}
		}
	}
	
	
	/**
	 * Tests search 1 from the assignment specification.
	 * 
	 * @param dialler The phone number that initiated the calls.
	 * @return List of all the phone numbers called by dialler.
	 *         The list will contain duplicates of the receiver if the dialler called the receiver multiple times.
	 */
	@Override
	public List<Long> called(long dialler) {
		List<Long> receiverList = new ArrayList<Long>();
		
		//find all receivers called by dialler and add them to receiverList
		for (int i = 0; i< diallerMultimap.get(dialler).size();i++) {
			receiverList.add(diallerMultimap.get(dialler).get(i).getReceiver());
		}
		
		return receiverList;
	}

	
	/**
	 * Tests search 1 from the assignment specification.
	 * 
	 * @param dialler The phone number that initiated the calls.
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return List of all the phone numbers called by dialler between start and end time.
	 *         The list will contain duplicates of the receiver if the dialler called the receiver multiple times.
	 */
	@Override
	public List<Long> called(long dialler, LocalDateTime startTime, LocalDateTime endTime) {
		List<Long> receiverList = new ArrayList<Long>();
		
		//find all receivers called by dialler within the time range (inclusive) and add them to receiverList
		for (int i = 0; i< diallerMultimap.get(dialler).size();i++) {
			LocalDateTime callRecTimeStamp = diallerMultimap.get(dialler).get(i).getTimeStamp();
			if ((callRecTimeStamp.isAfter(startTime) || callRecTimeStamp.equals(startTime)) &&
				(callRecTimeStamp.isBefore(endTime) || callRecTimeStamp.equals(endTime)) ) {
				receiverList.add(diallerMultimap.get(dialler).get(i).getReceiver());
			}	
		}	
		return receiverList;
	}

	
	/**
	 * Tests search 2 from the assignment specification.
	 * 
	 * @param receiver The phone number that received the calls.
	 * @return List of all the phone numbers that called the receiver.
	 *         The list will contain duplicates of the caller if they called the receiver multiple times.
	 */
	@Override
	public List<Long> callers(long receiver) {
		List<Long> diallerList = new ArrayList<Long>();
		
		//find all diallers called this receiver and add them to diallerList
		for (int i = 0; i< receiverMultimap.get(receiver).size();i++) {
			diallerList.add(receiverMultimap.get(receiver).get(i).getDialler());
		}
		
		return diallerList;
	}

	
	/**
	 * Tests search 2 from the assignment specification.
	 * 
	 * @param receiver The phone number that received the calls.
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return List of all the phone numbers that called the receiver between start and end time.
	 *         The list will contain duplicates of the caller if they called the receiver multiple times.
	 */
	@Override
	public List<Long> callers(long receiver, LocalDateTime startTime, LocalDateTime endTime) {
		List<Long> diallerList = new ArrayList<Long>();
		
		//find all daillers that called this receiver within the time range (inclusive) and add them to receiverList
		for (int i = 0; i< receiverMultimap.get(receiver).size();i++) {
			LocalDateTime callRecTimeStamp = receiverMultimap.get(receiver).get(i).getTimeStamp();
			if ((callRecTimeStamp.isAfter(startTime) || callRecTimeStamp.equals(startTime)) &&
				(callRecTimeStamp.isBefore(endTime) || callRecTimeStamp.equals(endTime)) ) {
				diallerList.add(receiverMultimap.get(receiver).get(i).getDialler());
			}	
		}	
		return diallerList;
	}

	
	/**
	 * Tests search 3 from the assignment specification.
	 * 
	 * @param dialler The phone number that initiated the calls.
	 * @return The list of identifiers of the faulty switches or an empty list if no fault was found.
	 */
	@Override
	public List<Integer> findConnectionFault(long dialler) {
		List<Integer> fault = new ArrayList<Integer>();
		au.edu.uq.itee.comp3506.assn2.entities.List<CallRecord> callRecs = diallerMultimap.get(dialler);
		//check all call records called by this dialler
		for (int i = 0; i< callRecs.size();i++) {
			//empty path means dialler connected to faulty switch
			if (callRecs.get(i).getConnectionPath().isEmpty()) {
				fault.add(callRecs.get(i).getDiallerSwitch());
			} else {
				//check if the final switch in the connection path is the same as the receiverSwitch
				if (callRecs.get(i).getConnectionPath().get(callRecs.get(i).getConnectionPath().size()-1) 
						!= callRecs.get(i).getReceiverSwitch()) {
					fault.add(callRecs.get(i).getConnectionPath().get(callRecs.get(i).getConnectionPath().size()-1));
				}
			}
		}
		return fault;
	}

	
	/**
	 * Tests search 3 from the assignment specification.
	 * 
	 * @param dialler The phone number that initiated the calls.
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return The list of identifiers of the faulty switches;
	 *         or an empty list if no fault was found between start and end time.
	 */
	@Override
	public List<Integer> findConnectionFault(long dialler, LocalDateTime startTime, LocalDateTime endTime) {
		List<Integer> fault = new ArrayList<Integer>();
		au.edu.uq.itee.comp3506.assn2.entities.List<CallRecord> callRecs = diallerMultimap.get(dialler);
		
		//check all call records called by this dialler within the time range
		for (int i = 0; i< callRecs.size();i++) {
			LocalDateTime callRecTimeStamp = callRecs.get(i).getTimeStamp();
			if ((callRecTimeStamp.isAfter(startTime) || callRecTimeStamp.equals(startTime)) &&
					(callRecTimeStamp.isBefore(endTime) || callRecTimeStamp.equals(endTime)) ) {
				//empty path means dialler connected to faulty switch
				if (callRecs.get(i).getConnectionPath().isEmpty()) {
					fault.add(callRecs.get(i).getDiallerSwitch());
				} else {
					//check if the final switch in the connection path is the same as the receiverSwitch
					if (callRecs.get(i).getConnectionPath().get(callRecs.get(i).getConnectionPath().size()-1) 
							!= callRecs.get(i).getReceiverSwitch()) {
						fault.add(callRecs.get(i).getConnectionPath().get(callRecs.get(i).getConnectionPath().size()-1));
					}
				}
			}
		}
		return fault;
	}

	
	/**
	 * Tests search 3 from the assignment specification.
	 * 
	 * @param reciever The phone number that should have received the calls.
	 * @return The list of identifiers of the faulty switches or an empty list if no fault was found.
	 */
	@Override
	public List<Integer> findReceivingFault(long reciever) {
		List<Integer> fault = new ArrayList<Integer>();
		au.edu.uq.itee.comp3506.assn2.entities.List<CallRecord> callRecs = receiverMultimap.get(reciever);
		//check all call records this receiver received
		for (int i = 0; i< callRecs.size();i++) {
			//empty path means dialler connected to faulty switch
			if (callRecs.get(i).getConnectionPath().isEmpty()) {
				fault.add(callRecs.get(i).getDiallerSwitch());
			} else {
				//check if the final switch in the connection path is the same as the receiverSwitch
				if (callRecs.get(i).getConnectionPath().get(callRecs.get(i).getConnectionPath().size()-1) 
						!= callRecs.get(i).getReceiverSwitch()) {
					fault.add(callRecs.get(i).getConnectionPath().get(callRecs.get(i).getConnectionPath().size()-1));
				}
			}
		}
		return fault;
	}

	
	/**
	 * Tests search 3 from the assignment specification.
	 * 
	 * @param reciever The phone number that should have received the calls.
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return The list of identifiers of the faulty switches;
	 *         or an empty list if no fault was found between start and end time.
	 */
	@Override
	public List<Integer> findReceivingFault(long reciever, LocalDateTime startTime, LocalDateTime endTime) {
		List<Integer> fault = new ArrayList<Integer>();
		au.edu.uq.itee.comp3506.assn2.entities.List<CallRecord> callRecs = receiverMultimap.get(reciever);
		
		//check all call records this receiver received within the time range
		for (int i = 0; i< callRecs.size();i++) {
			LocalDateTime callRecTimeStamp = callRecs.get(i).getTimeStamp();
			if ((callRecTimeStamp.isAfter(startTime) || callRecTimeStamp.equals(startTime)) &&
					(callRecTimeStamp.isBefore(endTime) || callRecTimeStamp.equals(endTime)) ) {
				//empty path means dialler connected to faulty switch
				if (callRecs.get(i).getConnectionPath().isEmpty()) {
					fault.add(callRecs.get(i).getDiallerSwitch());
				} else {
					//check if the final switch in the connection path is the same as the receiverSwitch
					if (callRecs.get(i).getConnectionPath().get(callRecs.get(i).getConnectionPath().size()-1)
							!= callRecs.get(i).getReceiverSwitch()) {
						fault.add(callRecs.get(i).getConnectionPath().get(callRecs.get(i).getConnectionPath().size()-1));
					}
				}
			}
		}
		return fault;
	}

	/**
	 * Tests search 4 from the assignment specification.
	 * 
	 * @return The identifier of the switch that had the most connections.
	 *         If multiple switches have the most connections, the smallest switch identifier is returned.
	 */
	@Override
	public int maxConnections() {
		return switchIDTree.getMaxCountNode(switchIDTree.getRoot()).getID();
	}

	/**
	 * Tests search 4 from the assignment specification.
	 * 
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return The identifier of the switch that had the most connections between start and end time.
	 *         If multiple switches have the most connections, the smallest switch identifier is returned.
	 */
	@Override
	public int maxConnections(LocalDateTime startTime, LocalDateTime endTime) {
		//creates a new switchTree with zero counts stored in each switch node 
		MyBSTree newSwitchIDTree = new MyBSTree();
		newSwitchIDTree = switchReader("data/switches.txt");
		MyList<CallRecord> temp = new MyList<CallRecord>();
		
		//add call record within the time range to temp list
		for(int i = 0; i < callRecList.size(); i++) {	//iterate over all call records
			LocalDateTime callTime = callRecList.get(i).getTimeStamp();
			if ((callTime.isAfter(startTime) || callTime.isEqual(startTime))	//check if its in the time range
					&& (callTime.isBefore(endTime) || callTime.isEqual(endTime))) {
				temp.add(callRecList.get(i));
			}
		}

		//check switches in connection path of all records in temp list 
		if (temp.size() != 0) {
			for (int j = 0; j < temp.size(); j++) {
				if (!temp.get(j).getConnectionPath().isEmpty()) {	//check if path is not empty
					for (int s = 0; s < temp.get(j).getConnectionPath().size(); s++) {	//iterate over all switches in connection path
						if (newSwitchIDTree.isExist(temp.get(j).getConnectionPath().get(s))) {	//check if switchIDTree has this ID
							newSwitchIDTree.increaseCount(temp.get(j).getConnectionPath().get(s));  //if so, increase count of this switch 
						}
					}
				}
			}
			//System.out.println("\n\n"+"*********************************"+newSwitchIDTree.getMaxCountNode(newSwitchIDTree.getRoot()).getCount());
			return newSwitchIDTree.getMaxCountNode(newSwitchIDTree.getRoot()).getID();
		} else {
			return 0;
		}
		
		
	}

	/**
	 * Tests search 5 from the assignment specification.
	 * 
	 * @return The identifier of the switch that had the fewest connections.
	 *         If multiple switches have the fewest connections, the smallest switch identifier is returned.
	 */
	@Override
	public int minConnections() {
		return switchIDTree.getMinCountNode(switchIDTree.getRoot()).getID();
	}

	/**
	 * Tests search 5 from the assignment specification.
	 * 
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return The identifier of the switch that had the fewest connections between start and end time.
	 *         If multiple switches have the fewest connections, the smallest switch identifier is returned.
	 */
	@Override
	public int minConnections(LocalDateTime startTime, LocalDateTime endTime) {
		//creates a new switchTree with zero counts stored in each switch node 
		MyBSTree newMinSwitchIDTree = new MyBSTree();
		newMinSwitchIDTree = switchReader("data/switches.txt");
		MyList<CallRecord> temp = new MyList<CallRecord>();
		
		//add call record within the time range to temp list
		for(int i = 0; i < callRecList.size(); i++) {	//iterate over all call records
			LocalDateTime callTime = callRecList.get(i).getTimeStamp();
				if ((callTime.isAfter(startTime) || callTime.isEqual(startTime))	//check if its in the time range
					&& (callTime.isBefore(endTime) || callTime.isEqual(endTime))) {
					temp.add(callRecList.get(i));
				}
			}
		
		//check switches in connection path of all records in temp list 
		if (temp.size() != 0) {
			for (int j = 0; j < temp.size(); j++) {
				if (!temp.get(j).getConnectionPath().isEmpty()) {	//check if path is not empty
					for (int s = 0; s < temp.get(j).getConnectionPath().size(); s++) {	//iterate over all switches in connection path
						if (newMinSwitchIDTree.isExist(temp.get(j).getConnectionPath().get(s))) {	//check if switchIDTree has this ID
							newMinSwitchIDTree.increaseCount(temp.get(j).getConnectionPath().get(s));  //if so, increase count of this switch 
							}
						}
					}
				}
			//System.out.println("\n\n"+"*********************************"+newMinSwitchIDTree.getMinCountNode(newMinSwitchIDTree.getRoot()).getCount());
				return newMinSwitchIDTree.getMinCountNode(newMinSwitchIDTree.getRoot()).getID();
			} else {
					return 0;
			}
	}

	@Override
	public List<CallRecord> callsMade(LocalDateTime startTime, LocalDateTime endTime) {
		List<CallRecord> result = new ArrayList<CallRecord>();
		for(int i = 0; i < callRecList.size(); i++) {
			LocalDateTime callTime = callRecList.get(i).getTimeStamp();
			if ((callTime.isAfter(startTime) || callTime.isEqual(startTime)) 
				&& (callTime.isBefore(endTime) || callTime.isEqual(endTime))) {
				result.add(callRecList.get(i));
			}
		}
		return result;
	}
	
	
	/**
	 * Reads all call records from text file 
	 * @param fileName, the file path for reading
	 * @return a list of CallRecord
	 */
	private static List<CallRecord> callRecordReader(String filePath) {
		List<CallRecord> result = new ArrayList<CallRecord>();
		String line;
		try {
			FileReader fr = new FileReader(filePath);
			BufferedReader input = new BufferedReader(fr);
			line = input.readLine();
			while(line != null) {
				String[] tokens = line.trim().split(" +");
				List<Integer> path = new ArrayList<>();
				
				//check if there are more than 5 elements in tokens array, less than 5 elements means invalid record
				if (tokens.length >= 5) {
					//check if DiallerSwitch and ReceiverSwitch is valid SwitchID
					if (switchIDValidator(tokens[1]) && switchIDValidator(tokens[tokens.length-3])) {
						
						//path is empty if tokenCount is less or equal to 5
						if (tokens.length > 5) {
							//adds all switches to path 
							for (int i = 2; i < tokens.length-3; i++) {
								//check if the string at index i is a valid switch ID
								if (switchIDValidator(tokens[i])) {
									path.add(Integer.parseInt(tokens[i]));
								}
							}
							//System.out.println(path.get(path.size()-1));
						} 
						
						//parse a line of record to a CallRecord
						CallRecord callRec = new CallRecord(
								Long.parseLong(tokens[0]),	//dialler
								Long.parseLong(tokens[tokens.length-2]),	//receiver
								Integer.parseInt(tokens[1]),	//dialler switch
								Integer.parseInt(tokens[tokens.length-3]),	//receiver switch
								path,	//connection path
								LocalDateTime.parse(tokens[tokens.length-1]));	//timeStamp
						
						//add the callRecord to result if it is a valid record
						if (callRecValidator(callRec)) {
							result.add(callRec);
						}
					}
				}
				
				line = input.readLine();
			}
			input.close();
			fr.close();
			
		} catch (IOException e ) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Read the switch file and add all switchIDs to a binary search tree
	 * @param filePath, the file path for reading
	 * @return a MyBSTree fulfilled with all switchIDs from the file
	 */
	private static MyBSTree switchReader(String filePath) {
		MyBSTree bsTree = new MyBSTree();
		String line;
		try {
			FileReader fr = new FileReader(filePath);
			BufferedReader input = new BufferedReader(fr);
			line = input.readLine();
			while (line != null) {
				if (isInteger(line) && line.length() == 5) {
					int switchID = Integer.parseInt(line);
					bsTree.add(switchID);
				}
				line = input.readLine();
			}
			input.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bsTree;
	}
	
	/**
	 * Check if the given switch id is a valid SwitchID
	 * @param id, String representation of a SwitchID
	 * @return true if the id is a 5-digit integer and in the given switches.txt file
	 */
	private static boolean switchIDValidator(String id) {
		if (id.length() == 5 
				&& isInteger(id) 
				&& switchIDTree.isExist(Integer.parseInt(id))) {
			return true;
		}
		return false;
	}
	
	/**
	 * check if diallerSwitch is the same as first switch in path
	 * @param callRec, given call record for checking 
	 * @return true if it meets all conditions
	 */
	private static boolean callRecValidator(CallRecord callRec) {
		if (switchIDValidator(callRec.getDiallerSwitch()+"")
				&& switchIDValidator(callRec.getReceiverSwitch()+"") ) {
			if (!callRec.getConnectionPath().isEmpty()) {
				for (int i = 0; i < callRec.getConnectionPath().size(); i++) {
					if (!switchIDTree.isExist(callRec.getConnectionPath().get(i))) {	//return false if switch is not in switch.txt
						return false;
					} else if (i>0) {
						if (callRec.getConnectionPath().get(i) 	//check if there is switch directly connects to itself in path
								== callRec.getConnectionPath().get(i-1)) {
							return false;
						}
					}
				}
				if (callRec.getDiallerSwitch() != callRec.getConnectionPath().get(0)) { //check if the dialler switch is the first in path
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}
	
	/**
	 * Check if the input string is an integer
	 * @param str input string
	 * @return true is the given string is an integer
	 */
	private static boolean isInteger(String str) {
	    try {
	        Integer.parseInt(str);
	        return true;
	    } catch ( NumberFormatException e ) {
	        return false;
	    }
	}
	
	
	

	
	public static void main(String[] args) {
//		Long testStartTime = System.currentTimeMillis();
//		LocalDateTime startTime = LocalDateTime.parse("2017-09-21T15:02:21.142");
//		LocalDateTime endTime = LocalDateTime.parse("2017-09-21T20:02:21.142");
//		
//		AutoTester test = new AutoTester();
//		Long initTime = System.currentTimeMillis();
//		System.out.println("\nInitialization Time: " + (initTime - testStartTime));
//				
//		Long dialler = 2697621983L;
//		System.out.println("\nDialler: "+dialler);
//		List<Long>  receivers = test.called(dialler);
//		System.out.println("Receivers: "+receivers);
//		System.out.println("size: "+receivers.size());
//		Long search1aEndTime = System.currentTimeMillis();
//		System.out.println("Search 1a execution Time: " + (search1aEndTime - initTime));
//	
//		List<Long> withTime = test.called(dialler,startTime,endTime);
//		System.out.println("\nStart at: " + startTime + "\nendTime: " + endTime + "\n" +withTime);
//		System.out.println("size: "+withTime.size());
//		Long search1bEndTime = System.currentTimeMillis();
//		System.out.println("Search 1b execution Time: " + (search1bEndTime - search1aEndTime));
//		
//		Long receiver = 5585599930L;
//		System.out.println("\nReceiver: " + receiver);
//		List<Long> diallers = test.callers(receiver);
//		System.out.println("Diallers: " + diallers);
//		System.out.println("size: " + diallers.size());
//		Long search2aEndTime = System.currentTimeMillis();
//		System.out.println("Search 2a execution Time: " + (search2aEndTime - search1bEndTime));
//		
//		List<Long> receiversWithTime = test.callers(receiver,startTime,endTime);
//		System.out.println("\nStart at: " + startTime + "\nendTime: " + endTime + "\n" +receiversWithTime);
//		System.out.println("Size: " + receiversWithTime.size());
//		Long search2bEndTime = System.currentTimeMillis();
//		System.out.println("Search 2b execution Time: " + (search2bEndTime - search2aEndTime));
//		
////		for (int i=0 ;i < test.diallerMultimap.get(dialler).size();i++) {
////		System.out.println(test.diallerMultimap.get(dialler).get(i).getConnectionPath());
////	}
//		
//		List<Integer> dFault = test.findConnectionFault(dialler);
//		System.out.println("\nDailler " + dialler + " has faults: \n" + dFault);
//		Long search3a1EndTime = System.currentTimeMillis();
//		System.out.println("Search 3a1 execution Time: " + (search3a1EndTime - search2bEndTime));
//		
//		List<Integer> dFaultInTime = test.findConnectionFault(dialler, startTime, endTime);
//		System.out.println("\nDailler " + dialler + "\nStart at: " + startTime + "\nendTime: " + endTime + " has faults: \n" + dFaultInTime);
//		Long search3a2EndTime = System.currentTimeMillis();
//		System.out.println("Search 3a2 execution Time: " + (search3a2EndTime - search3a1EndTime));
//		
//		List<Integer> rFault = test.findReceivingFault(receiver);
//		System.out.println("\nReceiver " + receiver + " has faults: \n" + rFault);
//		Long search3b1EndTime = System.currentTimeMillis();
//		System.out.println("Search 3b1 execution Time: " + (search3b1EndTime - search3a2EndTime));
//		
//		List<Integer> rFaultInTime = test.findConnectionFault(receiver, startTime, endTime);
//		System.out.println("\nReceiver " + receiver + "\nStart at: " + startTime + "\nendTime: " + endTime + " has faults: \n" + rFaultInTime);
//		Long search3b2EndTime = System.currentTimeMillis();
//		System.out.println("Search 3a2 execution Time: " + (search3b2EndTime - search3b1EndTime));
//		
//		int switchWithMax = test.maxConnections();
//		Long search4aEndTime = System.currentTimeMillis();
//		System.out.println("\nSwitch with max connections is: " + switchWithMax + 
//				"\n has connection amount: " + switchIDTree.getMaxCountNode(switchIDTree.getRoot()).getCount());
//		System.out.println("Search 4a Execution time: " + (search4aEndTime - search3b2EndTime));
//		
//		int switchWithMaxInTime = test.maxConnections(startTime, endTime);
//		Long search4bEndTime = System.currentTimeMillis();
//		System.out.println("\nBetween " + startTime + " and "+ endTime  +"\nSwitch with max connections is: " + switchWithMaxInTime + 
//				"\n has connection amount: ");
//		System.out.println("Search 4b Execution time: " + (search4bEndTime - search4aEndTime));
//		
//		int switchWithMin = test.minConnections();
//		Long search5aEndTime = System.currentTimeMillis();
//		System.out.println("\nSwitch with min connections is: " + switchWithMin + 
//				"\n has connection amount: " + switchIDTree.getMinCountNode(switchIDTree.getRoot()).getCount());
//		System.out.println("Search 5a Execution time: " + (search5aEndTime - search4bEndTime));
//		
//		int switchWithMinInTime = test.minConnections(startTime, endTime);
//		Long search5bEndTime = System.currentTimeMillis();
//		System.out.println("\nBetween " + startTime + " and "+ endTime  +"\nSwitch with min connections is: " + switchWithMinInTime + 
//				"\n has connection amount: ");
//		System.out.println("Search 5b Execution time: " + (search5bEndTime - search5aEndTime));
//		
//		List<CallRecord> allRecInTime = test.callsMade(startTime, endTime);
//		Long search6EndTime = System.currentTimeMillis();
//		System.out.println("\nAll Calls made in time range: " + allRecInTime.size());
//		System.out.println("Search 6a execution Time: " + (search6EndTime - search5bEndTime));
//		
//		Long testEndTime = System.currentTimeMillis();
//		System.out.println("\n\nTotal Execytion Time: " + (testEndTime - testStartTime));
//		

//		//test fro number of invalid records in file
//		AutoTester test = new AutoTester();
//		List<CallRecord> allRecs = test.callRecordReader("data/call-records-short.txt");
//		System.out.println("\nTotal Number of invalid record: " + (6500 - allRecs.size()));
		

		
//		List<CallRecord> test = new ArrayList<CallRecord>();
//		MyMultimap<Long, CallRecord> mapTest = new MyMultimap<Long, CallRecord>();
//		test = callRecordReader("data/call-records-short.txt");
//		for (CallRecord callRec:test) {
//			mapTest.put(callRec.getDialler(), callRec);
//		}
//		
//		Long dialler = 5332712146L;
//		System.out.println("Dialler: "+dialler);
//		System.out.println("CallRecords Number: " + mapTest.get(dialler).size());
//		System.out.println("CallRecords: ");
//		for (int i = 0; i< mapTest.get(dialler).size();i++) {
//			
//			System.out.println(mapTest.get(dialler).get(i));
//		}
//		//System.out.println(mapTest.get(dialler));
//		System.out.println("All Record: " + mapTest.size());

		
//		MyBSTree testTree = switchReader("data/switches.txt");
//		System.out.println("Total Switch Number: " + testTree.size());
//		System.out.println("All Switch IDs: ");
//		//testTree.dis­play(testTree.getRoot());
		
		
	}
}