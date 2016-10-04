//
//  AddFavoriteViewController.swift
//  Favorites
//
//  Created by Shubham Mudgal on 9/27/16.
//  Copyright © 2016 Shubham. All rights reserved.
//

import UIKit

class AddFavoriteViewController: UIViewController, UITextFieldDelegate {

    
    @IBOutlet weak var favName: UITextField!
    @IBOutlet weak var favAuthor: UITextField!
    @IBOutlet weak var favPlace: UITextField!
    
    @IBOutlet weak var doneAdding: UIBarButtonItem!
   
    @IBAction func onTapGesture(sender: AnyObject) {
         favName.resignFirstResponder()
        favAuthor.resignFirstResponder()
        favPlace.resignFirstResponder()
    }
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?){
        if segue.identifier == "doneFavs"{
            let scene1VC = segue.destinationViewController as! ViewController
            if favName.text!.isEmpty == false{
                scene1VC.user.favBook = favName.text
            }
            if favAuthor.text!.isEmpty == false{
                scene1VC.user.favAuthor = favAuthor.text
            }
            if favPlace.text!.isEmpty == false{
                scene1VC.user.favPlace = favPlace.text
            }
            
    }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        favName.delegate = self
        favAuthor.delegate = self
        favPlace.delegate = self
        
        
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
