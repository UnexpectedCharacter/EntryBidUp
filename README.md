# EntryBidUp
 Simple java program that generate calls to a SC that manage a entry bids

# Environment
[Ganache](https://www.trufflesuite.com/ganache)
[IntellIj](https://www.jetbrains.com/idea/download/#section=windows)

This repository contains a solidity smart contract(SC) that allow generate bids to entitys. The SC functions are called from a Java Project(EntryBidUpCaller) and a Test assert the great functionallity. The SC can be deployed via Java Project(EntryBidUpDeployer) or using Remix Web3Provider option and connect to Ganache. Remember stablish connection to Remix via HTTP protocol.

# JAVA
# EntryBidUp Usage
    Enable ganache
    
    Open EntryBidUpDeployer inside a IDE like IntellIJ and execute
        - Copy de console sc address
        
    Open EntryBidUpCaller inside a IDE like IntellIJ
        - Open es.unexpectedCharacter.entryBidUpCaller.dao.blockchain.AdminSmartContractCaller and paste the value to "scAddress"
        
    Execute the SCTest 
    
# SOLIDITY    
# Legend Explanation
    Are just the address that must be selected once the transaction goes
    - ExcMrk1 for example "0xCA35b7d915458EF540aDe6068dFe2F44E8fa733c"
    - ExcMrk2 for example "0x14723A09ACff6D2A60DcdF7aA4AFf308FDDC160C"
    - ExcMrk3 for example "0x4B0897b0513fdC7C541B6d9D7E929C4e5364D2dB"
    
# SmartContrac Usage

    - Call addEntry2AdminEntries("Fito Y Los Fitipaldis EL Concierto", "878e040fe84b631375f7f50ca0ab9146", "100") ExcMrk1
    
    - Call getAdminEntryOwner("Fito Y Los Fitipaldis EL Concierto");  ExcMrk1
        - Expected = ErcMk1 
        
    - Call addBidUp("Fito Y Los Fitipaldis EL Concierto","78") ExcMrk2
    
    - Call getBidsPayedValByUUintNameEntryAndUserAddr("Fito Y Los Fitipaldis EL Concierto","0x14723A09ACff6D2A60DcdF7aA4AFf308FDDC160C") ExcMrk2
        - Expected = 78 
        
    - Call addBidUp("Fito Y Los Fitipaldis EL Concierto","33") ExcMrk3
    
    - Call getBidsPayedValByUUintNameEntryAndUserAddr("Fito Y Los Fitipaldis EL Concierto","0x4B0897b0513fdC7C541B6d9D7E929C4e5364D2dB") ExcMrk3
        - Expected = 33
        
    - Call addBidUp("Fito Y Los Fitipaldis EL Concierto","22") ExcMrk2
    
    - Call getAdminEntryOwner("Fito Y Los Fitipaldis EL Concierto");  ExcMrk1
        - Expected = 0x0000000000000000000000000000000000000000
        
    - Call getUsernEntryPrice("Fito Y Los Fitipaldis EL Concierto","0x14723A09ACff6D2A60DcdF7aA4AFf308FDDC160C")
        - Expected = 100
        
    - Call getUserEntryOwner("Fito Y Los Fitipaldis EL Concierto","0x14723A09ACff6D2A60DcdF7aA4AFf308FDDC160C")
        - Expected = 0x14723A09ACff6D2A60DcdF7aA4AFf308FDDC160C
        
    - Call getUserEntryTicketValue("Fito Y Los Fitipaldis EL Concierto","0x14723A09ACff6D2A60DcdF7aA4AFf308FDDC160C")
        - Expected = 878e040fe84b631375f7f50ca0ab9146
        

