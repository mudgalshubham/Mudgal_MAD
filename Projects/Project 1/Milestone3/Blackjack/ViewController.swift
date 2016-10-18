//
//  ViewController.swift
//  Blackjack
//
//  Created by Shubham Mudgal on 9/27/16.
//  Copyright © 2016 Shubham. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    let blackJack = 21
    
    class Users {
        var cardsSum: Set<Int> = []
        var userCards: [String:Int] = [:]
        //var cardsView: [UIImageView]!
        
        func isBlackjack() -> Bool{
            if((self.getSumOfCards() == 21)){
                return true
            }
            else{
                return false
            }
        }
        
        func isBust() -> Bool{
            if(self.userCards != [:] && self.getSumOfCards() == 0){
                return true
            }
            else{
                return false
            }
        }
        
        func addCard(key : String, value : Int){
            self.userCards[key] = value
            updateSum(value)
        }
        
        func updateSum(value: Int){
            var newSum: Set<Int> = []
            //print(self.cardsSum)
            //print(num)
            if(self.cardsSum == [])
            {
                self.cardsSum = [0]
            }
            for number in self.cardsSum{
                //let number = self.cardsSum.popFirst()
                if (number + value <= 21){
                    newSum.insert(number + value)
                }
                if (value == 1){
                    if(number + 11 <= 21){
                        newSum.insert(number + 11)
                    }
                }
                    
            }
            self.cardsSum = newSum
        }

        func getSumOfCards() -> Int{
            if(self.cardsSum.isEmpty){
                return 0
            }
            return self.cardsSum.maxElement()!
        }
        
        func getCards() -> [String:Int]!{
            return userCards
        }
        
        func resetUser(){
            self.cardsSum = []
            self.userCards = [:]
        }
        
    }
    
    @IBOutlet var dealerCards: [UIImageView]!
    @IBOutlet var playerCards: [UIImageView]!
    @IBOutlet weak var ScoreBoard: UILabel!
    @IBOutlet weak var dealButton: UIButton!
    @IBAction func dealButtonPressed(sender: UIButton) {
        startGame()
    }
    
    let deck:[String:Int] = ["ClubA":1, "Club2":2, "Club3":3, "Club4":4, "Club5":5,"Club6":6,"Club7":7, "Club8":8, "Club9":9, "Club10":10, "ClubJ":10, "ClubQ":10, "ClubK":10, "HeartA":1, "Heart2":2, "Heart3":3, "Heart4":4, "Heart5":5, "Heart6":6, "Heart7":7, "Heart8":8, "Heart9":9, "Heart10":10, "HeartJ":10, "HeartQ":10, "HeartK":10,
                             "SpadeA":1, "Spade2":2, "Spade3":3, "Spade4":4, "Spade5":5, "Spade6":6, "Spade7":7, "Spade8":8, "Spade9":9, "Spade10":10, "SpadeJ":10, "SpadeQ":10, "SpadeK":10,
                             "DiamondA":1, "Diamond2":2, "Diamond3":3, "Diamond4":4, "Diamond5":5, "Diamond6":6, "Diamond7":7, "Diamond8":8, "Diamond9":9, "Diamond10":10, "DiamondJ":10, "DiamondQ":10, "DiamondK":10]
    
    var deckLeft:[String:Int] = [:]
    
    var dealer = Users()
    var player = Users()
    
    var wins:Int = 0
    var gamesPlayed:Int = 0
    
    
    
    
    func dealCardToUser(user: Users){
        
        let randomIndex = Int(arc4random_uniform(UInt32(deckLeft.count)))
        let randomVal = Array(deckLeft.values)[randomIndex]
        let randomKey = Array(deckLeft.keys)[randomIndex]
        
        user.addCard(randomKey, value: randomVal)
        
        deckLeft.removeValueForKey(randomKey)
        
        if(user === dealer){
            for views in dealerCards{
                if(views.image == UIImage(named: "back.png") || views.image == nil){
                    views.image = UIImage(named: randomKey)
                    break
                }
            }
        }
        
        if(user === player){
            for views in playerCards{
                if(views.image == UIImage(named: "back.png") || views.image == nil){
                    views.image = UIImage(named: randomKey)
                    break
                }
            }
        }

    }
    
    func resetCardsImage(userCards: [UIImageView]){
        for i in 0..<2 {
            closedCard(userCards[i])
        }
        
        for i in 2..<userCards.count {
            userCards[i].image = nil
        }
    }
    
    func closedCard(card: UIImageView){
        card.image = UIImage(named: "back.png")
    }
    
    func startGame() {
        
        self.ScoreBoard.text = "\(self.wins)" + "/" + "\(self.gamesPlayed)"     //update scoreboard to show number of games the player has won
        
        enableDealButton()
        
        deckLeft = deck;                //reset the deck
        
        dealer.resetUser()              //reset dealer's cards
        resetCardsImage(dealerCards)    //remove the card images for dealer
        
        player.resetUser()              //reset player's cards
        resetCardsImage(playerCards)    //remove the card images for player

        
        dealCardToUser(dealer)          //deal cards to dealer
        dealCardToUser(dealer)
        
        dealCardToUser(player)          //deal cards to player
        dealCardToUser(player)
        
        checkBlackJack()                //check if anyone got a blackjack in their first hand
    
    }
    
    func enableDealButton(){
        // Change the button's title.
        //dealButton.setTitle("Clicked.", forState: UIControlState.Normal)
        
        // Change the color of the title.
        dealButton.setTitleColor(UIColor.blackColor(), forState: UIControlState.Normal)
        
        // Enable the button.
        dealButton.userInteractionEnabled = true
    }
    
    func disableDealButton(){
        // Change the button's title.
        //dealButton.setTitle("Clicked.", forState: UIControlState.Normal)
        
        // Change the color of the title.
        dealButton.setTitleColor(UIColor.grayColor(), forState: UIControlState.Normal)
        
        // Disable the button.
        dealButton.userInteractionEnabled = false
    }
    
    @IBAction func buttonHit(sender: UIButton) {
        disableDealButton()
        
        dealCardToUser(player)      //deal a card to player
       
        //check if player got bust, i.e. sum of cards greater than 21
        if(player.isBust()){
            gameOverMessage("Game Over - Dealer Wins", messageContent: "You got Bust!")
        }
        
        //check if player got a BlackJack, i.e. sum of cards equal to 21
        if(player.isBlackjack()){
            self.wins += 1
            gameOverMessage("Game Over - You Win", messageContent: "You got a BlackJack!!")
        }

    }
    
    func delay(delay: Double, closure: ()->()) {
        dispatch_after(
            dispatch_time(
                DISPATCH_TIME_NOW,
                Int64(delay * Double(NSEC_PER_SEC))
            ),
            dispatch_get_main_queue(),
            closure
        )
    }
    
    
    @IBAction func buttonStand(sender: UIButton) {
        disableDealButton()
        print("Dealer sum: \(dealer.getSumOfCards())")
        print("Player sum: \(player.getSumOfCards())")
        print("Is dealer bust? : \(dealer.isBust())")
        
        while(dealer.getSumOfCards() <= player.getSumOfCards() && !dealer.isBust()){
            dealCardToUser(dealer)
        }
        
        if(dealer.isBust()){
            self.wins += 1
            gameOverMessage("Game Over - You Win", messageContent: "Dealer got Bust!")
        }
        else{
            gameOverMessage("Game Over - Dealer Wins", messageContent: "Dealer is closer to BlackJack than you!")
        }
    }
    
    func gameOverMessage(messageTitle: String, messageContent: String){
        self.gamesPlayed += 1
        
        let alert = UIAlertController(title: messageTitle, message:messageContent, preferredStyle: UIAlertControllerStyle.Alert)
        
        let okAction = UIAlertAction(title: "Play Again", style: UIAlertActionStyle.Default, handler:
            {
                action in self.startGame()
            })
        
        alert.addAction(okAction)
        presentViewController(alert, animated:true, completion:nil)
    }
    
    func checkBlackJack(){
        print("Player cardsSum Set: \(player.cardsSum)")
        print("Player cardsSum: \(player.getSumOfCards())")
        
        if(dealer.getSumOfCards() == blackJack){
            gameOverMessage("Game Over - Dealer Wins", messageContent: "Dealer got a BlackJack!")
        }
        else if(player.getSumOfCards() == blackJack){
            self.wins += 1
            gameOverMessage("Game Over - You Win", messageContent: "You got a BlackJack!!")
        }
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        startGame()
        //   self.view.backgroundColor = UIColor(patternImage: UIImage(named: "background.png", contentMode = UIViewContentModeScaleAspectFill))
       // imageView.contentMode = UIViewContentModeScaleAspectFill;
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

