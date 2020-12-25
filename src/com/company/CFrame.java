package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Frame of the program.
 */
public class CFrame extends JFrame implements ActionListener {

    private CMainPanel mainPanel;
    private JMenuItem newItem;
    private JMenuItem saveItem;
    private JMenuItem exitItem;

    /**
     * Constructor of the class.
     * @param title Title of the frame
     */
    public CFrame(String title) {
        super(title);

        initMenuBar(); //create menuBar

        initMainPanel(); //create main panel
    }

    /**
     * Sets the items on the frame.
     */
    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu jmenu = new JMenu("File");

        newItem = new JMenuItem("New");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        newItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        jmenu.add(newItem);
        jmenu.add(saveItem);
        jmenu.add(exitItem);

        menuBar.add(jmenu);
        setJMenuBar(menuBar);
    }

    /**
     * Sets the main panel of the JFrame.
     */
    private void initMainPanel() {
        mainPanel = new CMainPanel();
        add(mainPanel);
    }

    /**
     * Handler method
     * @param e Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newItem) {
            mainPanel.addNewTab();
        } else if (e.getSource() == saveItem) {
            mainPanel.saveNote();
        } else if (e.getSource() == exitItem) {
            //TODO: Phase1: check all tabs saved ...
            mainPanel.saveAllTabs();
            System.exit(0);
        } else {
            System.out.println("Nothing detected...");
        }
    }
}