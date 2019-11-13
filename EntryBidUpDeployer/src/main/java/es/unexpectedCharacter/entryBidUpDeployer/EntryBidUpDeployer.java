package es.unexpectedCharacter.entryBidUpDeployer;

import es.unexpectedCharacter.entryBidUpDeployer.dao.blockchain.Deployer;

public class EntryBidUpDeployer {

    /**
     * The main method for deployer
     *
     * @param args The command line arguments
     * @throws Exception Any Excepiton
     */
    public static void main(String... args) throws Exception {
        final Deployer deployer = new Deployer();
        deployer.deploy();
    }
}
