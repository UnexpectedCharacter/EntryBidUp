package es.cybernote.entryBidUpDeployer.dao.blockchain;


import es.cybernote.entryBidUpDeployer.contractWrapper.AdminDemo;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.Quorum;
import org.web3j.quorum.tx.ClientTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.util.List;

public class Deployer {

    /**
     * The Quorum (admin) client
     */
    private Admin admin;
    /**
     * The Quorum (user) client
     */
    private Quorum quorum;

    public Deployer() {
        final String hostUrl = "http://127.0.0.1";
        final String hostPort = "7545";
        final String host = hostUrl + ":" + hostPort;

        final HttpService httpService = new HttpService(host);

        this.admin = Admin.build(httpService);
        this.quorum = Quorum.build(httpService);
    }

    /**
     * This method is used to deploy the smart contract
     *
     * @throws Exception Any expcetion
     */
    public void deploy() throws Exception {
        final EthAccounts ethAccounts = this.quorum.ethAccounts().send();
        final String ethFirstAccount = ethAccounts.getAccounts().get(ethAccounts.getAccounts().size() - 1);
        final PersonalUnlockAccount personalUnlockAccount = this.admin.personalUnlockAccount(ethFirstAccount, "").send();

        if (!personalUnlockAccount.accountUnlocked()) {
            throw new IllegalStateException("Account " + ethFirstAccount + " can not be unlocked!");
        }

        final List<String> privateFor = null;
        final int sleepDuration = 1 * 1000;
        final int attempts = 10;
        final ClientTransactionManager clientTransactionManager = new ClientTransactionManager(this.quorum, ethFirstAccount, ethFirstAccount, privateFor, sleepDuration, attempts);

        final ContractGasProvider contractGasProvider = new DeployGasProvider();

        final AdminDemo contract = AdminDemo.deploy(this.quorum, clientTransactionManager, contractGasProvider).send();
        System.out.println(contract.getContractAddress());
    }
}
