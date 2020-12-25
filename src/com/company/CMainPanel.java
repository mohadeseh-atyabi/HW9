package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Main panel of the JFrame.
 */
public class CMainPanel extends JPanel {

    private JTabbedPane tabbedPane;
    private JList<File> directoryList;

    /**
     * Constructor of the class
     */
    public CMainPanel() {

        setLayout(new BorderLayout());

        initDirectoryList(); // add JList to main Panel

        initTabbedPane(); // add TabbedPane to main panel

        addNewTab(); // open new empty tab when user open the application
    }

    /**
     * Initializes a tabbed pane & adds it to the panel.
     */
    private void initTabbedPane() {
        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Initializes a JList & adds it to the panel.
     */
    private void initDirectoryList() {
        File[] files = FileUtils.getFilesInDirectory();
        directoryList = new JList<>(files);

        directoryList.setBackground(new Color(211, 211, 211));
        directoryList.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        directoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        directoryList.setVisibleRowCount(-1);
        directoryList.setMinimumSize(new Dimension(130, 100));
        directoryList.setMaximumSize(new Dimension(130, 100));
        directoryList.setFixedCellWidth(130);
        directoryList.setCellRenderer(new MyCellRenderer());
        directoryList.addMouseListener(new MyMouseAdapter());

        add(new JScrollPane(directoryList), BorderLayout.WEST);
    }

    /**
     * Adds new tab to the tabbed pane.
     */
    public void addNewTab() {
        JTextArea textPanel = createTextPanel();
        textPanel.setText("Write Something here...");
        tabbedPane.addTab("Tab " + (tabbedPane.getTabCount() + 1), textPanel);
    }

    /**
     * Opens an existing file on a new tab.
     * @param content Text of the file to write on the text area.
     */
    public void openExistingNote(String content) {
        JTextArea existPanel = createTextPanel();
        existPanel.setText(content);

        int tabIndex = tabbedPane.getTabCount() + 1;
        tabbedPane.addTab("Tab " + tabIndex, existPanel);
        tabbedPane.setSelectedIndex(tabIndex - 1);
    }

    /**
     * Write the text on a existing file or creates new file.
     */
    public void saveNote() {
        JTextArea textPanel = (JTextArea) tabbedPane.getSelectedComponent();
        String note = textPanel.getText();
        if (!note.isEmpty()) {
            //BufferedWriter
            //FileUtils.fileWriter(note);

            //FileOutputStream
            //FileUtils.fileStreamWriter(note);

            //ObjectOutputStream
            FileUtils.fileSerializeWriter(note);
        }
        updateListGUI();
    }

    /**
     * Creates new JTextArea to add to the tabbed pane.
     * @return New JTextArea
     */
    private JTextArea createTextPanel() {
        JTextArea textPanel = new JTextArea();
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return textPanel;
    }

    /**
     * Updates list according to last changes.
     */
    private void updateListGUI() {
        File[] newFiles = FileUtils.getFilesInDirectory();
        directoryList.setListData(newFiles);
    }

    /**
     * Saves all opened tabs.
     */
    public void saveAllTabs() {
        for (int i=0 ; i<tabbedPane.getTabCount() ; i++){
            JTextArea txtArea = (JTextArea) tabbedPane.getComponentAt(i);
            String note = txtArea.getText();
            if (!note.isEmpty())
                //BufferedWriter
                //FileUtils.fileWriter(note);

                //FileOutputStream
                //FileUtils.fileStreamWriter(note);

                //ObjectOutputStream
                FileUtils.fileSerializeWriter(note);
        }
    }

    /**
     * Inner class to handle mouse actions.
     */
    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent eve) {
            // Double-click detected
            if (eve.getClickCount() == 2) {
                int index = directoryList.locationToIndex(eve.getPoint());
                System.out.println("Item " + index + " is clicked...");
                //TODO: Phase1: Click on file is handled... Just load content into JTextArea
                File curr[] = FileUtils.getFilesInDirectory();
                //BufferedReader
                //String content = FileUtils.fileReader(curr[index]);

                //FileInputStream
                //String content = FileUtils.fileStreamReader(curr[index]);

                //ObjectInputStream
                String content = FileUtils.fileSerializeReader(curr[index]);

                openExistingNote(content);
            }
        }
    }

    private class MyCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object object, int index, boolean isSelected, boolean cellHasFocus) {
            if (object instanceof File) {
                File file = (File) object;
                //Set text with date & title
                Note note = FileUtils.listText(file);
                String labelText = "<html> " + note.getTitle() + "<br/> " + note.getDate();
                setText(labelText);

//                setText(file.getName());
                setIcon(FileSystemView.getFileSystemView().getSystemIcon(file));
                if (isSelected) {
                    setBackground(list.getSelectionBackground());
                    setForeground(list.getSelectionForeground());
                } else {
                    setBackground(list.getBackground());
                    setForeground(list.getForeground());
                }
                setEnabled(list.isEnabled());
            }
            return this;
        }
    }

}
