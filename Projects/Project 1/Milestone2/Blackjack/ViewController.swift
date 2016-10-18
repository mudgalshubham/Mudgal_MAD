//
//  ViewController.swift
//  Blackjack
//
//  Created by Shubham Mudgal on 9/27/16.
//  Copyright © 2016 Shubham. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    let deck:[String:Int] = ["ClubA":1, "Club2":2, "Club3":3, "Club4":4, "Club5":5,"Club6":6,"Club7":7, "Club8":8, "Club9":9, "Club10":10, "ClubJ":10, "ClubQ":10, "ClubK":10, "HeartA":1, "Heart2":2, "Heart3":3, "Heart4":4, "Heart5":5, "Heart6":6, "Heart7":7, "Heart8":8, "Heart9":9, "Heart10":10, "HeartJ":10, "HeartQ":10, "HeartK":10,
                             "SpadeA":1, "Spade2":2, "Spade3":3, "Spade4":4, "Spade5":5, "Spade6":6, "Spade7":7, "Spade8":8, "Spade9":9, "Spade10":10, "SpadeJ":10, "SpadeQ":10, "SpadeK":10,
                             "DiamondA":1, "Diamond2":2, "Diamond3":3, "Diamond4":4, "Diamond5":5, "Diamond6":6, "Diamond7":7, "Diamond8":8, "Diamond9":9, "Diamond10":10, "DiamondJ":10, "DiamondQ":10, "DiamondK":10]
    
    var deckLeft:[String:Int] = [:]
    
    var dealerCards:[String:Int] = [:]
    var dealerCardsSum:Int = 0
    
    var playerCards:[String:Int] = [:]
    var playerCardsSum:Int = 0
    
    var wins:Int = 0
    var gamesPlayed:Int = 0
    
    @IBOutlet weak var DealerCard1: UIImageView!
    @IBOutlet weak var DealerCard2: UIImageView!
    @IBOutlet weak var DealerCard3: UIImageView!
    @IBOutlet weak var DealerCard4: UIImageView!
    
    @IBOutlet weak var PlayerCard1: UIImageView!
    @IBOutlet weak var PlayerCard2: UIImageView!
    @IBOutlet weak var PlayerCard3: UIImageView!
    @IBOutlet weak var PlayerCard4: UIImageView!
    
    @IBOutlet weak var ScoreBoard: UILabel!
    
    @IBOutlet var dealerCardsImage: [UIImageView]!
    
    @IBOutlet var playerCardsImage: [UIImageView]!
    
   // var dealerCardsImage:[UIImageView] = [DealerCard1, DealerCard2, DealerCard3, DealerCard4]
    
    @IBAction func StartGame(sender: UIButton) {
        deckLeft = deck;
        
        dealerCards = [:]
        dealerCardsSum = 0
        
        playerCards = [:]
        playerCardsSum = 0
        
        for count in 1...2 {
            let randomIndex = Int(arc4random_uniform(UInt32(deckLeft.count)))
            let randomVal = Array(deckLeft.values)[randomIndex]
            let randomKey = Array(deckLeft.keys)[randomIndex]
            
            dealerCards[randomKey] = randomVal
            deckLeft.removeValueForKey(randomKey)
            dealerCardsSum += randomVal
            
            /*
            if count == 1{
                self.DealerCard1.image = UIImage(named: randomKey)
                if randomVal == 1{
                    dealerCardsSum[0] = 1
                    dealerCardsSum.append(11)
                }
                else{
                    dealerCardsSum.append(randomVal)
                }
            }
            else{
                self.DealerCard2.image = UIImage(named: randomKey)
                if randomVal == 1{
                    dealerCardsSum[0] += 1
                    dealerCardsSum.append(dealerCardsSum[0]+10)
                }
                else{
                    dealerCardsSum[0] += randomVal
                }

            }
 */
            if count == 1{
                self.DealerCard1.image = UIImage(named: randomKey)
            }
            else{
                self.DealerCard2.image = UIImage(named: randomKey)
            }
        }
        
        for count in 1...2 {
            let randomIndex = Int(arc4random_uniform(UInt32(deckLeft.count)))
            let randomVal = Array(deckLeft.values)[randomIndex]
            let randomKey = Array(deckLeft.keys)[randomIndex]
            
            playerCards[randomKey] = randomVal
            deckLeft.removeValueForKey(randomKey)
            playerCardsSum += randomVal
     /*
            if count == 1{
                self.PlayerCard1.image = UIImage(named: randomKey)
                if randomVal == 1{
                    playerCardsSum[0] = 1
                    playerCardsSum.append(11)
                }
                else{
                    playerCardsSum.append(randomVal)
                }
            }
            else{
                self.PlayerCard2.image = UIImage(named: randomKey)
                if randomVal == 1{
                    playerCardsSum[0] += 1
                    playerCardsSum.append(playerCardsSum[0]+10)
                }
                else{
                    playerCardsSum[0] += randomVal
                }
                
            } */
            
            if count == 1{
                self.PlayerCard1.image = UIImage(named: randomKey)
            }
            else{
                self.PlayerCard2.image = UIImage(named: randomKey)
            }

        }
        
        checkBlackJack()

    }
    
    @IBAction func buttonStand(sender: UIButton) {
    }
    
    @IBAction func buttonHit(sender: UIButton) {
        let randomIndex = Int(arc4random_uniform(UInt32(deckLeft.count)))
        let randomVal = Array(deckLeft.values)[randomIndex]
        let randomKey = Array(deckLeft.keys)[randomIndex]
        
        playerCards[randomKey] = randomVal
        playerCardsSum += randomVal
        deckLeft.removeValueForKey(randomKey)
        
        for imageView in playerCardsImage{
            if(imageView.image==nil){
                imageView.image = UIImage(named: randomKey)
                break
            }
        }
        //self.PlayerCard1.image = UIImage(named: randomKey)
       
        checkBust()
        checkBlackJack()

    }
    
    func checkBust(){
        if(dealerCardsSum > 21){
            self.wins += 1
            self.gamesPlayed += 1
            let alert = UIAlertController(title: "Hand Over", message:"Player Wins!", preferredStyle: UIAlertControllerStyle.Alert)
            
            let okAction = UIAlertAction(title: "Deal Again", style: UIAlertActionStyle.Default, handler:
                {
                    
                    action in self.ScoreBoard.text = "\(self.wins)" + "/" + "\(self.gamesPlayed)"
            })
            
            alert.addAction(okAction)
            presentViewController(alert, animated:true, completion:nil)
        }
        
        if(playerCardsSum > 21){
            self.gamesPlayed += 1
            let alert = UIAlertController(title: "Hand Over", message:"Dealer Wins!", preferredStyle: UIAlertControllerStyle.Alert)
            
            let okAction = UIAlertAction(title: "Deal Again", style: UIAlertActionStyle.Default, handler:
                {
                    
                    action in self.ScoreBoard.text = "\(self.wins)" + "/" + "\(self.gamesPlayed)"
                    self.setup()
            })
            
            alert.addAction(okAction)
            presentViewController(alert, animated:true, completion:nil)
        }

    }
    
    func checkBlackJack(){
        if(dealerCardsSum == 21){
            self.gamesPlayed += 1
            let alert = UIAlertController(title: "Hand Over", message:"Dealer Wins!", preferredStyle: UIAlertControllerStyle.Alert)
            
            let okAction = UIAlertAction(title: "Deal Again", style: UIAlertActionStyle.Default, handler:
                {
                    
                    action in self.ScoreBoard.text = "\(self.wins)" + "/" + "\(self.gamesPlayed)"
            })
            
            alert.addAction(okAction)
            presentViewController(alert, animated:true, completion:nil)
        }
        
        if(playerCardsSum == 21){
            self.wins += 1
            self.gamesPlayed += 1
            let alert = UIAlertController(title: "Hand Over", message:"Player Wins!", preferredStyle: UIAlertControllerStyle.Alert)
            
            let okAction = UIAlertAction(title: "Deal Again", style: UIAlertActionStyle.Default, handler:
                {
                    
                    action in self.ScoreBoard.text = "\(self.wins)" + "/" + "\(self.gamesPlayed)"
            })
            
            alert.addAction(okAction)
            presentViewController(alert, animated:true, completion:nil)
        }
    }
    
    func setup(){
        
        deckLeft = deck;
        
        dealerCards = [:]
        dealerCardsSum = 0
        
        playerCards = [:]
        playerCardsSum = 0
        
        for count in 1...2 {
            let randomIndex = Int(arc4random_uniform(UInt32(deckLeft.count)))
            let randomVal = Array(deckLeft.values)[randomIndex]
            let randomKey = Array(deckLeft.keys)[randomIndex]
            
            dealerCards[randomKey] = randomVal
            deckLeft.removeValueForKey(randomKey)
            dealerCardsSum += randomVal
            
            /*
             if count == 1{
             self.DealerCard1.image = UIImage(named: randomKey)
             if randomVal == 1{
             dealerCardsSum[0] = 1
             dealerCardsSum.append(11)
             }
             else{
             dealerCardsSum.append(randomVal)
             }
             }
             else{
             self.DealerCard2.image = UIImage(named: randomKey)
             if randomVal == 1{
             dealerCardsSum[0] += 1
             dealerCardsSum.append(dealerCardsSum[0]+10)
             }
             else{
             dealerCardsSum[0] += randomVal
             }
             
             }
             */
            if count == 1{
                self.DealerCard1.image = UIImage(named: randomKey)
            }
            else{
                self.DealerCard2.image = UIImage(named: randomKey)
            }
        }
        
        for count in 1...2 {
            let randomIndex = Int(arc4random_uniform(UInt32(deckLeft.count)))
            let randomVal = Array(deckLeft.values)[randomIndex]
            let randomKey = Array(deckLeft.keys)[randomIndex]
            
            playerCards[randomKey] = randomVal
            deckLeft.removeValueForKey(randomKey)
            playerCardsSum += randomVal
            /*
             if count == 1{
             self.PlayerCard1.image = UIImage(named: randomKey)
             if randomVal == 1{
             playerCardsSum[0] = 1
             playerCardsSum.append(11)
             }
             else{
             playerCardsSum.append(randomVal)
             }
             }
             else{
             self.PlayerCard2.image = UIImage(named: randomKey)
             if randomVal == 1{
             playerCardsSum[0] += 1
             playerCardsSum.append(playerCardsSum[0]+10)
             }
             else{
             playerCardsSum[0] += randomVal
             }
             
             } */
            
            if count == 1{
                self.PlayerCard1.image = UIImage(named: randomKey)
            }
            else{
                self.PlayerCard2.image = UIImage(named: randomKey)
            }
            
        }
        
        checkBlackJack()

        
    
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

