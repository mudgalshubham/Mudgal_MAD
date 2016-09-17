//
//  ViewController.swift
//  Bollywood
//
//  Created by Shubham Mudgal on 9/8/16.
//  Copyright © 2016 Shubham. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var imageArt: UIImageView!
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var imageControl: UISegmentedControl!
    
    @IBOutlet weak var labelFontControl: UISegmentedControl!
    
    @IBOutlet weak var capitalSwitch: UISwitch!
    
    @IBOutlet weak var fontSizeLabel: UILabel!
    
    @IBAction func updateFont(sender: UISwitch) {
        updateCaps()
    }
    
    func updateImage(){
        if imageControl.selectedSegmentIndex == 0 {
            titleLabel.text = "Hunn Dabang!"
            imageArt.image = UIImage(named: "salman")
        }
        else if imageControl.selectedSegmentIndex == 1 {
            titleLabel.text = "kkk..kkiran!"
            imageArt.image = UIImage(named: "shahrukh")
        }
    }
    
    func updateFontStyle(){
        if labelFontControl.selectedSegmentIndex == 0 {
            titleLabel.font = UIFont(name: "Baskerville", size: titleLabel.font.pointSize)
        }
        else if labelFontControl.selectedSegmentIndex == 1 {
            titleLabel.font = UIFont(name: "Papyrus", size: titleLabel.font.pointSize)
        }
    }
    
    func updateCaps(){
        if capitalSwitch.on {
            titleLabel.text = titleLabel.text?.uppercaseString
        }
        else {
            titleLabel.text = titleLabel.text?.lowercaseString
        }
    }
    
    @IBAction func changeInfo(sender: UISegmentedControl) {
        updateImage()
        updateCaps()
    }
    
    @IBAction func changeFontStyle(sender: UISegmentedControl) {
        updateFontStyle()
        
    }
    
    @IBAction func changeFontSize(sender: UISlider) {
        let fontSize = sender.value //float
        fontSizeLabel.text = String(format: "%.0f", fontSize)
        let fontSizeCGFloat = CGFloat(fontSize)
        titleLabel.font = UIFont.systemFontOfSize(fontSizeCGFloat)
        
        updateFontStyle()        
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

