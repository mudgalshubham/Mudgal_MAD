//
//  ViewController.swift
//  Favorites
//
//  Created by Shubham Mudgal on 9/27/16.
//  Copyright © 2016 Shubham. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    var user = Favorite()
    
    @IBOutlet weak var bookLabel: UILabel!
    @IBOutlet weak var authorLabel: UILabel!
    @IBOutlet weak var placeLabel: UILabel!
    @IBAction func addFavoritesMethod(sender: UIBarButtonItem) {
        performSegueWithIdentifier("addFavorites", sender: toolbarItems)
    }
    
    @IBAction func unwindSegue(segue: UIStoryboardSegue){
       bookLabel.text = user.favBook
        authorLabel.text = user.favAuthor
        placeLabel.text = user.favPlace
        
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

