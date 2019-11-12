pragma solidity ^0.4.24;

contract AdminDemo {
    
    address owner;
    
    constructor() public{
        owner = msg.sender;
    }
    
    struct Entry {
        string uuidName;
        string ticket;
        uint64 price;
        address owner;
        bool active;
    }
    
    struct BidUp {
        address bidder;
        uint64 payed;
    }
    
    mapping (address => Entry[]) adminEntries;
    mapping (address => Entry[]) usersTickets;
    mapping (string => mapping(address => BidUp)) bids;
    
    
    /**
     * Add a new entry to adminEntries mapping
     */
    function addEntry2AdminEntries(string _uuidName, string  _ticket, uint64  _price) public {
        //Entry memory newEntry = entryStructBuilder(_uuidName, _ticket, _price, owner, true);
        //adminEntries[owner].push(newEntry);
        adminEntries[owner].push(Entry(_uuidName, _ticket, _price, owner, true));
    }
    
    /**
     * Add a new bidup to bids mapping and check if the given payed amount is equals or higher to the entry cost
     */
    function addBidUp(string _entryUuidName, uint64 _paymentValue) public {
        require(_paymentValue > 0, "Can't bid up with negatives values");
        require(getAdminEntryActive(_entryUuidName) == true, "Can't bid up a not active entry");
        
        uint64 totalPayed = getBidsPayedValByUUintNameEntryAndUserAddr(_entryUuidName, msg.sender);
        bids[_entryUuidName][msg.sender] = BidUp(msg.sender,  totalPayed + _paymentValue);

       if(checkIfPaymentSatisfied(_entryUuidName, totalPayed + _paymentValue) == true) {
            //Add entry to usersTickets
            usersTickets[msg.sender].push(Entry(_entryUuidName,
                                                            getAdminEntryTicketValue(_entryUuidName),
                                                            getAdminEntryPrice(_entryUuidName),
                                                            msg.sender,
                                                            true));
            //Delete the entry from adminEntries
            delete adminEntries[owner][getAdminEntryNameArrayPosition(_entryUuidName)];
        }
    }
    
    // *******************************************
    // CHECKERS
    // *******************************************
    function checkIfPaymentSatisfied(string _uuidName, uint64 _amount) public view returns (bool _result){
        for (uint i = 0; i < adminEntries[owner].length; i++) {
            if(keccak256(adminEntries[owner][i].uuidName) == keccak256(_uuidName))
                return _amount >= adminEntries[owner][i].price;
        }
    }
    
    
    // *******************************************
    // ADMIN MAPPING GETTERS
    // *******************************************
    function getAdminEntryTicketValue(string _uuidName) public view returns (string _entryTicket) {
        for (uint i = 0; i < adminEntries[owner].length; i++) {
            if(keccak256(adminEntries[owner][i].uuidName) == keccak256(_uuidName))
                return adminEntries[owner][i].ticket;
        }
    }
    
    function getAdminEntryPrice(string _uuidName) public view returns (uint64 _entryPrice) {
        for (uint i = 0; i < adminEntries[owner].length; i++) {
            if(keccak256(adminEntries[owner][i].uuidName) == keccak256(_uuidName))
                return adminEntries[owner][i].price;
        }
    }
    
    function getAdminEntryOwner(string _uuidName) public view returns (address _ticketOwner) { //Just for develop
        for (uint i = 0; i < adminEntries[owner].length; i++) {
            if(keccak256(adminEntries[owner][i].uuidName) == keccak256(_uuidName))
                return adminEntries[owner][i].owner;
        }
    }
    
    function getAdminEntryActive(string _uuidName) public view returns (bool _ticketState) {
        for (uint i = 0; i < adminEntries[owner].length; i++) {
            if(keccak256(adminEntries[owner][i].uuidName) == keccak256(_uuidName))
                return adminEntries[owner][i].active;
        }
    }
    
    function getAdminEntryNameArrayPosition(string _uuidName) public view returns (uint256 _entry) {
        for (uint i = 0; i < adminEntries[owner].length; i++) {
            if(keccak256(adminEntries[owner][i].uuidName) == keccak256(_uuidName))
                return i;
        }
    }
    
    // *******************************************
    // USERS MAPPING GETTERS
    // *******************************************
    function getUserEntryTicketValue(string _uuidName, address _userAddres) public view returns (string _entryTicket) {
        for (uint i = 0; i < usersTickets[_userAddres].length; i++) {
            if(keccak256(usersTickets[_userAddres][i].uuidName) == keccak256(_uuidName))
                return usersTickets[_userAddres][i].ticket;
        }
    }
    
    function getUsernEntryPrice(string _uuidName, address _userAddres) public view returns (uint64 _entryPrice) {
        for (uint i = 0; i < usersTickets[_userAddres].length; i++) {
            if(keccak256(usersTickets[_userAddres][i].uuidName) == keccak256(_uuidName))
                return usersTickets[_userAddres][i].price;
        }
    }
    
    function getUserEntryOwner(string _uuidName, address _userAddres) public view returns (address _ticketOwner) { //Just for develop
        for (uint i = 0; i < usersTickets[_userAddres].length; i++) {
            if(keccak256(usersTickets[_userAddres][i].uuidName) == keccak256(_uuidName))
                return usersTickets[_userAddres][i].owner;
        }
    }
    
    function getUserEntryActive(string _uuidName, address _userAddres) public view returns (bool _ticketState) {
        for (uint i = 0; i < usersTickets[_userAddres].length; i++) {
            if(keccak256(usersTickets[_userAddres][i].uuidName) == keccak256(_uuidName))
                return usersTickets[_userAddres][i].active;
        }
    }
    
    function getUserEntryNameArrayPosition(string _uuidName, address _userAddres) public view returns (uint256 _entry) {
        for (uint i = 0; i < usersTickets[_userAddres].length; i++) {
            if(keccak256(usersTickets[_userAddres][i].uuidName) == keccak256(_uuidName))
                return i;
        }
    }
    
    // *******************************************
    // BIDS MAPPING GETTERS
    // *******************************************
    function getBidsPayedValByUUintNameEntryAndUserAddr(string _uuidName, address _userAddr) public view returns (uint64 _payedValue) {
        return bids[_uuidName][_userAddr].payed;
    }
}