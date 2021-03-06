# 2017_Sem2_COMP3506_A2
This is a copy of my solution for the COMP3506 Algorithms Data and Structure Assignment 2 in 2017 Semster 2 at The University of Queensland.

Nothing from Java Framework Collection in the JDK was used for the implementation of the Abstract Data Types (ADTs) or Concrete Data Types (CDTs) in the assignment. The implementation of the data structures used in the assignment was based on basic Java language constructs and not libraries.

## Assignment Contents
### Introduction
Telephone networks are complex physical infrastructure that have wide geographic distribution. Australia has over 13,000 mobile phone towers that provide connection points to the physical phone network. The phone network itself has hundreds of switches routing phone calls through the physical network. The network is a hierarchical in structure with small switches handling direct connections in a small geographic area and then a connection to a larger switch that handles connections over a larger area. The network design caters for five levels of switches but modern technology has reduced the number of levels required.

Given the complexity of the phone network, and the hundreds of thousands of kilometres of cabling in the network, faults are difficult to find. Phone companies use a variety of tools to identify and trace faults in the network. For this assignment, you will implement a tool that identifies faults in the phone network based on call connection data. The data has been simplified to ignore the levels of switches and whether they are a switch or base station. For simple fault tracing in the real application these distinctions are also not relevant.

### Details
You are provided with files representing two sets of data. One lists the identifiers of the switches in the network. The other is the record of phone calls.

The first line of the file containing the current list of all switch identifiers is a single integer that is the number of switches in the phone network. Each remaining line in the file is an identifier of a single switch. Each switch has a unique five-digit identifier. You may assume that the contents of this file are correct.

The record of phone calls contains data about which phone number attempted to call which other number over a time period. This data includes the path taken to connect the call through the network. The path is represented by a sequence of switch identifiers, which are in the order in which the call was routed.

The file containing the phone call records will have an unspecified number of lines, each line containing the record of a single connection attempt. The first number in a line is the dialling phone number (a ten-digit integer). The second number is the switch identifier to which the phone was connected when it attempted to make a call. This will be followed a sequence of zero or more switch identifiers (the path made for the connection). The switch to which the dialling phone was connected will be repeated in this sequence, if it exists. The switch identifier to which the receiving phone number was connected at the time of the call will follow the connection path. The switch to which the receiving phone number was connected will also be in the connection path, if it exists. This will be followed by the receiving phone number (a ten-digit integer). The final part of the line will be the timestamp of when the connection was attempted in the format of:

    yyyy-mm-ddThh:mm:ss[.fractional_seconds]

Where the date is: yyyy being four digits representing the year, mm being two digits representing the month and dd being two digits representing the day. Time is: hh being two digits representing
the hour, mm being two digits representing the minute, ss being two digits representing the second, and [.fractional_seconds] being an optional sequence of up to three digits representing a fraction of a second. There is a capital “T” between the date and time component of the timestamp. The phone call records are from the first to twenty-first of September 2017.
The data files are generated from a Unix environment so may not display nicely in a simple MS Windows text editor, such as NotePad.

As the phone call records data comes from the switch logs and is transmitted over the phone network, it can be corrupted. When reading the data you will need to make sure that you only store valid data. Any invalid call records can be discarded. For the purposes of this assignment you can assume that the phone numbers and timestamp are not corrupted. You only need to check the switch identifiers to ensure that they are valid.

Your application needs to store the data in appropriate data structures allowing efficient searching. Part of your mark will be based on the data structures you use and your justification of your choices.
#### Tasks
Your application needs to implement the following searches:
1. Find all receiving phone numbers called from a single dialling phone number.
2. Find all phone numbers that dialled a single receiving phone number.
3. For a particular phone number determine if there is a fault in the network for any of its connection attempts. The phone number may be either a dialling or a receiving phone number. (i.e. A caller may report that they could not make a connection to someone they called. Or, a phone owner may report that they are not receiving calls.) There would be two versions of this search; one for dialling phone numbers and one for receiving phone numbers.

A fault is detected when the final switch in the connection path is not the same as the switch to which the receiving phone number was connected at the time of the call. Note that if the dialling and receiving phone numbers are connected to the same switch the connection path will consist of a single switch identifier. It is possible that the switch to which the dialling phone number is attached is the source of the fault. In this situation, there will not be any switch identifiers in the connection path. This means that the switch identifier is added to the connection path after connecting the call to the next switch or the receiving phone number.

A switch has many circuits and a fault in the switch does not mean that it will never make a connection to another switch. The fault may be intermittent or may only be related to connections to other specific switches. The phone call record data will contain both successful and unsuccessful connections through a faulty switch. For the purposes of the assignment you only need to be able to determine that a fault exists based on the criteria in the paragraph above. That is there is at least one instance in the data being searched where the connection was not completed. Another issue to consider is that it is possible that more than one switch has a fault causing phone calls being sent or received by one number to not be connected.

4. Determine which switch has the most connections.
5. Determine which switch has the fewest connections.
6. Determine all calls made over a specified time period.
Searches 1 to 5 above may be for all of the data in the call record file or for a specified period of time.
You are provided with an interface called TestAPI and a stub class AutoTester that implements it. These will be used to automate the testing of your application’s functionality. You will need to implement the constructor and method stubs in AutoTester to call the related functionality inyour assignment and return the necessary results. You are also provided with a simple entity class called CallRecord that is used by TestAPI.
You may implement a user interface for the assignment if you would like to do so. This will not be executed by markers and will not be considered in assessing your mark for the assignment.
