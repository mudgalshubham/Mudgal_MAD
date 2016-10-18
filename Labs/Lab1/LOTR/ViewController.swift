//
//  ViewController.swift
//  LOTR
//
//  Created by Shubham Mudgal on 9/6/16.
//  Copyright © 2016 Shubham. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBAction func characterSelect(sender: UIButton) {
        Characters.image = UIImage(named: sender.currentTitle!)
        
        switch sender.currentTitle!{
            case "Frodo" : InfoText.text = "Frodo Baggins is a hobbit from The Shire."
            case "Gandalf" : InfoText.text = "Gandalf the Grey is a wizard."
            case "Aragorn" : InfoText.text = "Aragorn is a man and the true heir to the throne."
            case "Samwise" : InfoText.text = "Samwise Gangee is a hobbit and is Frodo's best friend from The Shire."
            case "Arwin" : InfoText.text = "Arwin is an Elf."
            case "Legolas" : InfoText.text = "Legolas is an Elf and the best archer in town."
            default : print("")
        }
    }
    @IBOutlet weak var Characters: UIImageView!
    @IBOutlet weak var InfoText: UILabel!
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

