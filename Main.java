package com.company;
import java.io.*;
import java.net.*;
import com.google.gson.*;
import org.jibble.pircbot.IrcException;

import java.util.Map;

/*
    Instructions:
    1 - Open webchat.freenode.net
    2 - Set channel to "#anishg"
    3 - Start the ChatBot
    4 - Run the program

 */


public class Main
{
    public static void main(String[] args) throws Exception
    {
        //create a new ChatBot
        ChatBot bot = new ChatBot();
        bot.setVerbose(true);
        //connect to freenode
        bot.connect("irc.freenode.net");
        //set/join channel for the ChatBot
        bot.joinChannel("#anishg");
        bot.sendMessage("#anishg", "Hello user! Enter zipcode to find the weather (case sensitive)");
    }
}
