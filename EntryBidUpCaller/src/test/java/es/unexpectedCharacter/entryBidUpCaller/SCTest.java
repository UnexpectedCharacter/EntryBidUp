package es.unexpectedCharacter.entryBidUpCaller;

import es.unexpectedCharacter.entryBidUpCaller.dao.blockchain.AdminSmartContractCaller;
import es.unexpectedCharacter.entryBidUpCaller.encryptUtils.Hash;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigInteger;
import java.util.UUID;

@SpringBootTest
public class SCTest {

    private String ADMIN_ADDR;
    private String USER_ADDR0;
    private String USER_ADDR1;
    private String EVENT_ENTRY_NAME0;
    private String EVENT_ENTRY_NAME1;
    private String EVENT_ENTRY_NAME2;
    private String EVENT_ENTRY_NAME3;
    private String EVENT_ENTRY_NAME4;
    private String HASHED_JSON3;
    private String HASHED_JSON4;
    private String NO_HASHED_JSON0;
    private String NO_HASHED_JSON1;
    private String NO_HASHED_JSON2;
    private String NO_HASHED_JSON3;
    private String NO_HASHED_JSON4;

    @Test
    public void run() throws Exception {
        final Hash hash = new Hash();
        variableInitializer(hash);

        //Generate new entries
        AdminSmartContractCaller.getInstance().addEntry(EVENT_ENTRY_NAME0, hash.stringHash(NO_HASHED_JSON0), new BigInteger("100"), ADMIN_ADDR);
        AdminSmartContractCaller.getInstance().addEntry(EVENT_ENTRY_NAME1, hash.stringHash(NO_HASHED_JSON1), new BigInteger("100"), ADMIN_ADDR);
        AdminSmartContractCaller.getInstance().addEntry(EVENT_ENTRY_NAME2, hash.stringHash(NO_HASHED_JSON2), new BigInteger("100"), ADMIN_ADDR);
        AdminSmartContractCaller.getInstance().addEntry(EVENT_ENTRY_NAME3, hash.stringHash(NO_HASHED_JSON3), new BigInteger("100"), ADMIN_ADDR);
        AdminSmartContractCaller.getInstance().addEntry(EVENT_ENTRY_NAME4, hash.stringHash(NO_HASHED_JSON4), new BigInteger("100"), ADMIN_ADDR);
        Assert.assertEquals(AdminSmartContractCaller.getInstance().getAdminEntryOwner(EVENT_ENTRY_NAME0, ADMIN_ADDR).equals(ADMIN_ADDR), true);
        Assert.assertEquals(AdminSmartContractCaller.getInstance().getAdminEntryActive(EVENT_ENTRY_NAME4, ADMIN_ADDR), true);
        Assert.assertEquals(AdminSmartContractCaller.getInstance().getAdminEntryTicketValue(EVENT_ENTRY_NAME3, ADMIN_ADDR).equals(HASHED_JSON3), true);


        //Start Bids
        AdminSmartContractCaller.getInstance().addBidUp(EVENT_ENTRY_NAME4, new BigInteger("78"), USER_ADDR0);
        Assert.assertEquals(AdminSmartContractCaller.getInstance().getBidsPayedValByUUintNameEntryAndUserAddr(EVENT_ENTRY_NAME4, USER_ADDR0).compareTo(new BigInteger("78")) == 0, true);
        AdminSmartContractCaller.getInstance().addBidUp(EVENT_ENTRY_NAME4, new BigInteger("33"), USER_ADDR1);
        Assert.assertEquals(AdminSmartContractCaller.getInstance().getBidsPayedValByUUintNameEntryAndUserAddr(EVENT_ENTRY_NAME4, USER_ADDR1).compareTo(new BigInteger("33")) == 0, true);
        // Total price reached
        AdminSmartContractCaller.getInstance().addBidUp(EVENT_ENTRY_NAME4, new BigInteger("22"), USER_ADDR0);
        Assert.assertEquals(AdminSmartContractCaller.getInstance().getBidsPayedValByUUintNameEntryAndUserAddr(EVENT_ENTRY_NAME4, USER_ADDR0).compareTo(new BigInteger("100")) == 0, true);


        // Assert the struct is fine
        Assert.assertEquals(AdminSmartContractCaller.getInstance().getAdminEntryOwner(EVENT_ENTRY_NAME4, ADMIN_ADDR).equals(ADMIN_ADDR), false);
        Assert.assertEquals(AdminSmartContractCaller.getInstance().getUsernEntryPrice(EVENT_ENTRY_NAME4, USER_ADDR0).compareTo(new BigInteger("100")) == 0, true);
        Assert.assertEquals(AdminSmartContractCaller.getInstance().getUserEntryOwner(EVENT_ENTRY_NAME4, USER_ADDR0).equals(USER_ADDR0), true);
        Assert.assertEquals(AdminSmartContractCaller.getInstance().getUserEntryTicketValue(EVENT_ENTRY_NAME4, USER_ADDR0).equals(HASHED_JSON4), true);
    }

    private void variableInitializer(Hash hash) throws IOException {
        ADMIN_ADDR = AdminSmartContractCaller.getInstance().getAccount(1);
        USER_ADDR0 = AdminSmartContractCaller.getInstance().getAccount(2);
        USER_ADDR1 = AdminSmartContractCaller.getInstance().getAccount(3);

        EVENT_ENTRY_NAME0 = "Pink Floyd_" + UUID.randomUUID().toString();
        EVENT_ENTRY_NAME1 = "The Who_" + UUID.randomUUID().toString();
        EVENT_ENTRY_NAME2 = "U2_" + UUID.randomUUID().toString();
        EVENT_ENTRY_NAME3 = "The Rolling Stones_" + UUID.randomUUID().toString();
        EVENT_ENTRY_NAME4 = "Deep Purple_" + UUID.randomUUID().toString();

        NO_HASHED_JSON0 = "{\"ticketUID\":" + UUID.randomUUID().toString() + ", \"date\":1411192248}";
        NO_HASHED_JSON1 = "{\"ticketUID\":" + UUID.randomUUID().toString() + ", \"date\":1411192248}";
        NO_HASHED_JSON2 = "{\"ticketUID\":" + UUID.randomUUID().toString() + ", \"date\":1411192248}";
        NO_HASHED_JSON3 = "{\"ticketUID\":" + UUID.randomUUID().toString() + ", \"date\":1411192248}";
        NO_HASHED_JSON4 = "{\"ticketUID\":" + UUID.randomUUID().toString() + ", \"date\":1411192248}";
        HASHED_JSON3 = hash.stringHash(NO_HASHED_JSON3);
        HASHED_JSON4 = hash.stringHash(NO_HASHED_JSON4);
    }
}
