//
//  ViewController.swift
//  Mudgal_Midterm
//
//  Created by Shubham Mudgal on 10/13/16.
//  Copyright © 2016 Shubham. All rights reserved.
//

import UIKit

class ViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet weak var workoutTimeLabel: UITextField!
    @IBOutlet weak var caloriesBurned: UILabel!
    @IBOutlet weak var milesRun: UILabel!
    @IBOutlet weak var workoutPerWeekLabel: UILabel!
    @IBOutlet weak var showWeeklySwitch: UISwitch!
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var runSwimBikeSegmentOutlet: UISegmentedControl!
    @IBAction func workoutPerWeekSlider(sender: UISlider) {
    }
    @IBAction func showWeekly(sender: UISwitch) {
        updateMilesAndCalories()
    }
    @IBAction func workoutTime(sender: UITextField) {
    }
    @IBAction func RunSwimBikeSegment(sender: UISegmentedControl) {
        updateMilesAndCalories()
    }
    @IBAction func workoutButton(sender: UIButton) {
        updateMilesAndCalories()
    }
    
    
    func updateMilesAndCalories(){
        var miles: Float = 0.00
        var speed: Float = 0.00
        var caloriesSpeed:Float = 0.00
        var calories:Float = 0.00

        if(!workoutTimeLabel.text!.isEmpty){
            let time = Float(workoutTimeLabel.text!)!
            if(time < 30){
                let alert = UIAlertController(title: "Alert", message:"Workout time is less than 10 minutes", preferredStyle: UIAlertControllerStyle.Alert)
                    

                    let okAction = UIAlertAction(title: "Ok", style: UIAlertActionStyle.Default, handler:
                        {
                            action in
                            })
                    
                    alert.addAction(okAction)
                    presentViewController(alert, animated:true, completion:nil)
            }
            if(runSwimBikeSegmentOutlet.selectedSegmentIndex == 1){
                speed = 30
                caloriesSpeed = 7
                imageView.image = UIImage(named: "swim")
            }
            else if(runSwimBikeSegmentOutlet.selectedSegmentIndex == 2){
                speed = 4
                caloriesSpeed = 8.5
                imageView.image = UIImage(named: "bike")
            }
            else{
                speed = 10
                caloriesSpeed = 10
                imageView.image = UIImage(named: "run")
            }
            
            miles = time * speed
            calories = time * caloriesSpeed
            
            if(showWeeklySwitch.on){
                miles = miles * 5
                calories = calories * 5
            }
            
            let milesStr = String(format: "%.2f", miles)
            let calStr = String(format: "%.2f", calories)
            
            milesRun.text = milesStr + " miles"
            caloriesBurned.text = calStr + " calories"
        }
    }

    
    override func viewDidLoad() {
        workoutTimeLabel.delegate=self
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

