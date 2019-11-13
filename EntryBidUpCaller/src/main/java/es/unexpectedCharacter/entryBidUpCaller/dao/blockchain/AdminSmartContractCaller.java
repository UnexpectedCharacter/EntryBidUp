package es.unexpectedCharacter.entryBidUpCaller.dao.blockchain;

import es.unexpectedCharacter.entryBidUpCaller.contractWrapper.AdminDemo;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.Quorum;
import org.web3j.quorum.tx.ClientTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class AdminSmartContractCaller {

    /**
     * The Quorum (admin) client
     */
    private Admin admin;

    /**
     * The Quorum (user) client
     */
    private Quorum quorum;

    /**
     * The SCAddress
     */
    private String scAddress = "0x80708739931e867e644642a009dad62f6a8c257f";

    /**
     * The SC
     */
    private AdminDemo adminDemoSC;

    private static final AdminSmartContractCaller INSTANCE = new AdminSmartContractCaller();

    public static AdminSmartContractCaller getInstance() {
        return INSTANCE;
    }

    public AdminSmartContractCaller() {
        final String hostUrl = "http://127.0.0.1";
        final String hostPort = "7545";
        final String host = hostUrl + ":" + hostPort;
        final HttpService httpService = new HttpService(host);
        this.admin = Admin.build(httpService);
        this.quorum = Quorum.build(httpService);
    }

    public void addEntry(String uuidName, String hashValue, BigInteger price, String trxAddress) throws Exception {
        loadContract(trxAddress);
        adminDemoSC.addEntry2AdminEntries(uuidName, hashValue, price).send();
    }

    public void addBidUp(String uuidName, BigInteger paymentValue, String trxAddress) throws Exception {
        loadContract(trxAddress);
        adminDemoSC.addBidUp(uuidName, paymentValue).send();
    }

    /**
     * ADMIN GETTERS
     */
    public String getAdminEntryTicketValue(String uuidName, String trxAddress) throws Exception {
        loadContract(trxAddress);
        return adminDemoSC.getAdminEntryTicketValue(uuidName).send();
    }

    public BigInteger getAdminEntryPrice(String uuidName, String trxAddress) throws Exception {
        loadContract(trxAddress);
        return adminDemoSC.getAdminEntryPrice(uuidName).send();
    }

    public String getAdminEntryOwner(String uuidName, String trxAddress) throws Exception {
        loadContract(trxAddress);
        return adminDemoSC.getAdminEntryOwner(uuidName).send();
    }

    public boolean getAdminEntryActive(String uuidName, String trxAddress) throws Exception {
        loadContract(trxAddress);
        return adminDemoSC.getAdminEntryActive(uuidName).send();
    }

    public BigInteger getAdminEntryNameArrayPosition(String uuidName, String trxAddress) throws Exception {
        loadContract(trxAddress);
        return adminDemoSC.getAdminEntryNameArrayPosition(uuidName).send();
    }

    /**
     * USER GETTERS
     */
    public String getUserEntryTicketValue(String uuidName, String trxAddress) throws Exception {
        loadContract(trxAddress);
        return adminDemoSC.getUserEntryTicketValue(uuidName, trxAddress).send();
    }

    public BigInteger getUsernEntryPrice(String uuidName, String trxAddress) throws Exception {
        loadContract(trxAddress);
        return adminDemoSC.getUsernEntryPrice(uuidName, trxAddress).send();
    }

    public String getUserEntryOwner(String uuidName, String trxAddress) throws Exception {
        loadContract(trxAddress);
        return adminDemoSC.getUserEntryOwner(uuidName, trxAddress).send();
    }

    public boolean getUserEntryActive(String uuidName, String trxAddress) throws Exception {
        loadContract(trxAddress);
        return adminDemoSC.getUserEntryActive(uuidName, trxAddress).send();
    }

    public BigInteger getUserEntryNameArrayPosition(String uuidName, String trxAddress) throws Exception {
        loadContract(trxAddress);
        return adminDemoSC.getUserEntryNameArrayPosition(uuidName, trxAddress).send();
    }

    public BigInteger getBidsPayedValByUUintNameEntryAndUserAddr(String uuidName, String trxAddress) throws Exception {
        loadContract(trxAddress);
        return adminDemoSC.getBidsPayedValByUUintNameEntryAndUserAddr(uuidName, trxAddress).send();
    }

    public String getAccount(int n) throws IOException {
        final EthAccounts ethAccounts = this.quorum.ethAccounts().send();
        final String ethFirstAccount = ethAccounts.getAccounts().get(ethAccounts.getAccounts().size() - n);
        final PersonalUnlockAccount personalUnlockAccount = this.admin.personalUnlockAccount(ethFirstAccount, "").send();
        return ethFirstAccount;
    }

    public void loadContract(String trxAddress) {
        final List<String> privateFor = null;
        final int sleepDuration = 1 * 1000;
        final int attempts = 10;
        final ContractGasProvider contractGasProvider = new DeployGasProvider();
        final ClientTransactionManager clientTransactionManager = new ClientTransactionManager(this.quorum, trxAddress, trxAddress, privateFor, sleepDuration, attempts);
        adminDemoSC = AdminDemo.load(scAddress, this.quorum, clientTransactionManager, contractGasProvider);
    }
}
