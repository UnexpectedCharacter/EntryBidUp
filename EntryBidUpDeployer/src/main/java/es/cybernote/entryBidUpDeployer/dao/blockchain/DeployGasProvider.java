package es.cybernote.entryBidUpDeployer.dao.blockchain;


import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

public class DeployGasProvider extends StaticGasProvider {

    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(4300000L);
    public static final BigInteger GAS_PRICE = BigInteger.valueOf(22000000000L);

    public DeployGasProvider() {
        super(GAS_PRICE, GAS_LIMIT);
    }
}
