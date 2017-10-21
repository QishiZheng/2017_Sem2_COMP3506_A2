package au.edu.uq.itee.comp3506.assn2.tests;

import au.edu.uq.itee.comp3506.assn2.entities.CallRecord;
import au.edu.uq.itee.comp3506.assn2.tests.AutoTester;
import org.junit.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.time.LocalDateTime;

public class TestShort {
    private static AutoTester tester;

    @BeforeClass
    public static void importFiles() {
        tester = new AutoTester();
    }

    /* TEST 1 QUERIES */

    @Test
    public void callsMadeByNonExistentDialler() {
        Assert.assertEquals(new ArrayList<Long>(), tester.called(736));
    }

    @Test
    public void callsMadeByDiallerInAllRecords() {
        long dialler = 1959159656L;
        List<Long> calls = tester.called(dialler);
        Collections.sort(calls);
        Assert.assertEquals(new ArrayList<Long>(Arrays.asList(6924935161L, 8296097399L)), calls);
    }

    @Test
    public void callsMadeByDiallerInSubsetOfRecords() {
        long dialler = 2746173943L;
        LocalDateTime startTime = LocalDateTime.parse("2017-09-05T03:47:14.678");
        LocalDateTime endTime = LocalDateTime.parse("2017-09-20T16:08:14.813");
        List<Long> calls = tester.called(dialler, startTime, endTime);
        Collections.sort(calls);
        Assert.assertEquals(new ArrayList<Long>(Arrays.asList(3873876464L, 7722107246L)), calls);
    }

    @Test
    public void callsMadeByDiallerNotInSubsetOfRecords() {
        long dialler = 8758444241L;
        LocalDateTime startTime = LocalDateTime.parse("2017-09-14T17:25:13.952");
        LocalDateTime endTime = LocalDateTime.MAX;
        Assert.assertEquals(new ArrayList<Long>(), tester.called(dialler, startTime, endTime));
    }

    /* TEST 2 QUERIES */

    @Test
    public void callsMdeToNonExistentReceiver() {
        long receiver = 8758444241L;
        Assert.assertEquals(new ArrayList<Long>(), tester.callers(receiver));
    }

    @Test
    public void callsMadeToDiallerInAllRecords() {
        long receiver = 9100082231L;
        List<Long> calls = tester.callers(receiver);
        Collections.sort(calls);
        Assert.assertEquals(new ArrayList<Long>(Arrays.asList(2537611679L, 6656742506L, 9355850850L)), calls);
    }

    @Test
    public void callsMadeToReceierInSubsetOfRecords() {
        long receiver = 1867046471L;
        LocalDateTime startTime = LocalDateTime.parse("2017-09-07T05:02:43.725");
        LocalDateTime endTime = LocalDateTime.parse("2017-09-13T03:39:31.873");
        List<Long> calls = tester.callers(receiver, startTime, endTime);
        Collections.sort(calls);
        Assert.assertEquals(new ArrayList<Long>(Arrays.asList(3925101975L, 9085860714L)), calls);
    }

    @Test
    public void callsMadeToReceiverNotInSubsetOfRecords() {
        long receiver = 6483742990L;
        LocalDateTime startTime = LocalDateTime.parse("2017-09-13T13:48:58.142");
        LocalDateTime endTime = LocalDateTime.parse("2017-09-17T07:05:13.799");
        Assert.assertEquals(new ArrayList<Long>(), tester.callers(receiver, startTime, endTime));
    }

    /* TEST 3.1 (dialler faults) QUERIES */

    @Test
    public void findDiallerFaultInAllRecords() {
        long dialler = 4266295042L;
        List<Integer> faultySwitches = tester.findConnectionFault(dialler);
        Assert.assertEquals(new ArrayList<Integer>(Arrays.asList(10401)), faultySwitches);
    }

    @Test
    public void findDiallerFaultInSubsetOfRecords() {
        long dialler = 3497915596L;
        LocalDateTime startTime = LocalDateTime.parse("2017-09-01T00:31:24.884");
        LocalDateTime endTime = LocalDateTime.parse("2017-09-05T06:51:49.669");
        List<Integer> faultySwitches = tester.findConnectionFault(dialler, startTime, endTime);
        Assert.assertEquals(new ArrayList<Integer>(Arrays.asList(69067)), faultySwitches);
    }

    @Test
    public void findNoDiallerFaultInAllRecords() {
        long dialler = 6004266553L;
        List<Integer> faultySwitches = tester.findConnectionFault(dialler);
        Assert.assertEquals(new ArrayList<>(), faultySwitches);
    }

    @Test
    public void findNoDiallerFaultInSubsetOfRecords() {
        long dialler = 8904615371L;

        LocalDateTime startTime = LocalDateTime.parse("2017-09-01T00:31:24.884");
        LocalDateTime endTime = LocalDateTime.parse("2017-09-05T06:51:49.669");
        List<Integer> faultySwitches = tester.findConnectionFault(dialler, startTime, endTime);
        Assert.assertEquals(new ArrayList<>(), faultySwitches);
    }

    /* TEST 3.2 (receiver faults) QUERIES */

    @Test
    public void findReceiverFaultInAllRecords() {
        long receiver = 7917226377L;
        List<Integer> faultySwitches = tester.findReceivingFault(receiver);
        Assert.assertEquals(new ArrayList<Integer>(Arrays.asList(10401)), faultySwitches);
    }

    @Test
    public void findReceiverFaultInSubsetOfRecords() {
        long receiver = 2989355522L;
        LocalDateTime startTime = LocalDateTime.parse("2017-09-10T16:43:36.481");
        LocalDateTime endTime = LocalDateTime.parse("2017-09-20T08:39:27.257");
        List<Integer> faultySwitches = tester.findReceivingFault(receiver, startTime, endTime);
        Assert.assertEquals(new ArrayList<Integer>(Arrays.asList(74949)), faultySwitches);
    }

    @Test
    public void findNoReceiverFaultInAllRecords() {
        long receiver = 6004266553L;
        List<Integer> faultySwitches = tester.findReceivingFault(receiver);
        Assert.assertEquals(new ArrayList<>(), faultySwitches);
    }

    @Test
    public void findNoReceiverFaultInSubsetOfRecords() {
        long receiver = 8904615371L;
        LocalDateTime startTime = LocalDateTime.parse("2017-09-01T00:31:24.884");
        LocalDateTime endTime = LocalDateTime.parse("2017-09-05T06:51:49.669");
        List<Integer> faultySwitches = tester.findReceivingFault(receiver, startTime, endTime);
        Assert.assertEquals(new ArrayList<>(), faultySwitches);
    }

    /* TEST 4 QUERIES */

    @Test
    public void mostConnectedInAllRecords() {
        Assert.assertEquals(39746, tester.maxConnections());
    }

    @Test
    public void mostConnectedInNoRecords() {
        LocalDateTime startTime = LocalDateTime.parse("2017-09-12T00:17:35.833");
        Assert.assertEquals(0, tester.maxConnections(startTime, startTime));
    }

    @Test
    public void mostConnectedInSingleRecord() {
        LocalDateTime startTime = LocalDateTime.parse("2017-09-07T06:29:20.305");
        Assert.assertEquals(11945, tester.maxConnections(startTime, startTime));
    }

    @Test
    public void mostConnectedInSubsetOfRecords() {
        LocalDateTime startTime = LocalDateTime.parse("2017-09-12T14:45:01.992");
        LocalDateTime endTime = LocalDateTime.parse("2017-09-17T03:38:21.914");
        Assert.assertEquals(39746, tester.maxConnections(startTime, endTime));
    }

    @Test
    public void mostConnectedInSubsetOfRecords2() {
        LocalDateTime startTime = LocalDateTime.parse("2017-09-01T06:48:52.260");
        LocalDateTime endTime = LocalDateTime.parse("2017-09-01T10:58:12.956");
        Assert.assertEquals(79180, tester.maxConnections(startTime, endTime));
    }

    /* TEST 5 QUERIES */

    @Test
    public void leastConnectedInAllRecords() {
        Assert.assertEquals(26128, tester.minConnections());
    }

    @Test
    public void leastConnectedInNoRecords() {
        LocalDateTime startTime = LocalDateTime.parse("2017-09-12T00:17:35.833");
        Assert.assertEquals(0, tester.minConnections(startTime, startTime));
    }

    @Test
    public void leastConnectedInSingleRecord() {
        LocalDateTime startTime = LocalDateTime.parse("2017-09-07T06:29:20.305");
        Assert.assertEquals(11945, tester.minConnections(startTime, startTime));
    }

    @Test
    public void leastConnectedInSubsetOfRecords() {
        LocalDateTime startTime = LocalDateTime.parse("2017-09-10T08:15:58.801");
        LocalDateTime endTime = LocalDateTime.parse("2017-09-19T20:48:44.517");
        Assert.assertEquals(47793, tester.minConnections(startTime, endTime));
    }

    @Test
    public void leastConnectedInSubsetOfRecords2() {
        LocalDateTime startTime = LocalDateTime.parse("2017-09-21T16:14:17.973");
        LocalDateTime endTime = LocalDateTime.parse("2017-09-21T17:15:27.990");
        Assert.assertEquals(10864, tester.minConnections(startTime, endTime));
    }

    /* TEST 6 QUERIES */

    @Test
    public void TotalNumberOfRecords() {
        Assert.assertEquals(6498, tester.callsMade(LocalDateTime.MIN, LocalDateTime.MAX).size());
    }

    @Test
    public void NumberInNoRecords() {
        LocalDateTime startTime = LocalDateTime.parse("2017-09-12T00:17:35.833");
        Assert.assertEquals(0, tester.callsMade(startTime, startTime).size());
    }

    @Test
    public void SingleCallRecord() {
        LocalDateTime time = LocalDateTime.parse("2017-09-13T00:17:35.833");
        List<CallRecord> records = tester.callsMade(time, time);
        Assert.assertEquals(1, records.size());
        CallRecord record = records.get(0);
        Assert.assertEquals(3463463994L, record.getDialler());
        Assert.assertEquals(7933491782L, record.getReceiver());
        Assert.assertEquals(13386, record.getDiallerSwitch());
        Assert.assertEquals(47063, record.getReceiverSwitch());
        Assert.assertEquals(8, record.getConnectionPath().size());
        List<Integer> connectionPath = new ArrayList<>(Arrays.asList(13386, 88683, 93707,
                95338, 43512, 56037, 53741, 47063));
        Assert.assertEquals(connectionPath, record.getConnectionPath());
    }

    @Test
    public void NumberInSubsetOfRecords() {
        LocalDateTime startTime = LocalDateTime.parse("2017-09-08T07:19:23.317");
        LocalDateTime endTime = LocalDateTime.parse("2017-09-19T23:56:32.160");
        Assert.assertEquals(3586, tester.callsMade(startTime, endTime).size());
    }

    @Test
    public void NumberInSubsetOfRecords2() {
        LocalDateTime startTime = LocalDateTime.parse("2017-09-01T08:05:13.337");
        LocalDateTime endTime = LocalDateTime.parse("2017-09-01T08:39:28.538");
        Assert.assertEquals(11, tester.callsMade(startTime, endTime).size());
    }

    @Test
    public void NumberBeforeRecords() {
        LocalDateTime startTime = LocalDateTime.MIN;
        LocalDateTime endTime = LocalDateTime.parse("2017-09-01T00:05:39.471");
        Assert.assertEquals(0, tester.callsMade(startTime, endTime).size());
    }

    @Test
    public void NumberAfterRecords() {
        LocalDateTime startTime = LocalDateTime.parse("2017-09-21T23:55:09.972");
        LocalDateTime endTime = LocalDateTime.MAX;
        Assert.assertEquals(0, tester.callsMade(startTime, endTime).size());
    }
}
