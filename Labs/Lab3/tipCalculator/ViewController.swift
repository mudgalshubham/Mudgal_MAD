//
//  ViewController.swift
//  tipCalculator
//
//  Created by Shubham Mudgal on 9/22/16.
//  Copyright © 2016 Shubham. All rights reserved.
//

import UIKit

class ViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet weak var tipLabel: UILabel!
    @IBOutlet weak var totalPerPersonLabel: UILabel!
    @IBOutlet weak var totalLabel: UILabel!
    @IBOutlet weak var tipText: UITextField!
    @IBOutlet weak var numberPeopleText: UITextField!
    @IBOutlet weak var amountText: UITextField!
 /*
    @IBAction func textFieldDoneEditing(sender: UITextField) {
        sender.resignFirstResponder()
    }
 */
    @IBAction func onTapGestureRecognized(sender: AnyObject) {
        amountText.resignFirstResponder()
        numberPeopleText.resignFirstResponder()
        tipText.resignFirstResponder()
    }
  
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
 
    func updateTipTotals() {
        var amount:Float //check amount
        var pct:Float //tip percentage
        
        if amountText.text!.isEmpty {
            amount = 0.0
        } else {
            amount = Float(amountText.text!)!
        }
        if tipText.text!.isEmpty {
            pct = 0.0
        }
        else {
            pct = Float(tipText.text!)!/100
        }
        
        let numberOfPeople=Int(numberPeopleText.text!) //returns an optional
        let tip=amount*pct
        let total=amount+tip
        var personTotal : Float = 0.0 //specify Float so it's not a Double
        if numberOfPeople != nil {
            if numberOfPeople! > 0 {
                personTotal = total / Float(numberOfPeople!)
            }
            else {
                let alert = UIAlertController(title: "Warning", message:"Number of people should be greater than 1", preferredStyle: UIAlertControllerStyle.Alert)
                
                let cancelAction = UIAlertAction(title: "Cancel", style: UIAlertActionStyle.Cancel, handler: nil)
                
                alert.addAction(cancelAction)
                let okAction = UIAlertAction(title: "Ok", style: UIAlertActionStyle.Default, handler:
                    {
                        action in self.numberPeopleText.text = "1"
                        self.updateTipTotals()})
            
                alert.addAction(okAction)
            presentViewController(alert, animated:true, completion:nil)
            }
        }
        
        //format results as currency
        let currencyFormatter = NSNumberFormatter()
        currencyFormatter.numberStyle=NSNumberFormatterStyle.CurrencyStyle //set the number style
        tipLabel.text=currencyFormatter.stringFromNumber(tip) //returns a formatted string
        totalLabel.text=currencyFormatter.stringFromNumber(total)
        totalPerPersonLabel.text=currencyFormatter.stringFromNumber(personTotal)
    }
    
    func textFieldDidEndEditing(textField: UITextField) {
        updateTipTotals()
    }
    
    
    
    override func viewDidLoad() {
        amountText.delegate=self
        tipText.delegate=self
        numberPeopleText.delegate=self
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

 /*
    @IBAction func textFieldDoneEditing(sender: UITextField) {
        sender.resignFirstResponder()
    }

    @IBAction func onTapGestureRecognized(sender: AnyObject) {
        tipText.resignFirstResponder()
        numberPeopleText.resignFirstResponder()
        amountText.resignFirstResponder()
    }
 */
}

