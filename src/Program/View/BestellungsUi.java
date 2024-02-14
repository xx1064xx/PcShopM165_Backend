package Program.View;

import Program.Repository.Bestellposition;
import Program.Repository.Computer;
import Program.Repository.Kunde;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

public class BestellungsUi extends JDialog {

    private MainUi mainUi;
    private BestellungsUi bestellungsUi;
    private BestellpositionUi bestellpositionUi;
    private boolean isEmpty;

    private int[] computerBestellPosition;

    // UI

    // Eingabefelder
    private JFormattedTextField dateField;
    private JComboBox<String> kundenComboBox;
    private JLabel totalInfoLabel;

    // Labels
    private JLabel dateLabel;
    private JLabel kundenLabel;
    private JLabel totalLabel;

    // Buttons
    private JButton speichernButton;
    private JButton abbrechenButton;
    private JButton deleteButton;
    private JButton bestellpositionAddButton;
    private JButton bestellpositionDeleteButton;

    // panels
    private JPanel buttonPanel;
    private JPanel computerPanel;
    private JPanel bestellpositionenPanel;
    private JPanel bestellpositionenButtonPanel;

    // lists
    private JList<String> bestellpositionenList;

    // listmodels
    private DefaultListModel<String> bestellpositionenListModel;

    // tabbedpanes
    private JTabbedPane tabbedPane;
    public BestellungsUi (MainUi mainUi, boolean isEmpty) {

        super(mainUi, "kundenView", true);

        this.mainUi = mainUi;
        this.bestellungsUi = this;
        this.isEmpty = isEmpty;
        this.computerBestellPosition = new int[0];

        init();

    }

    public void init() {

        setTitle("Bestellungen");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(400, 250));
        setLayout(new BorderLayout());

        try {

            MaskFormatter dateFormatter = new MaskFormatter("####-##-##");

            dateFormatter.setPlaceholderCharacter('_');

            dateField = new JFormattedTextField(dateFormatter);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        kundenComboBox = new JComboBox<>(getAllKundenAsArray());
        totalInfoLabel = new JLabel("looool");

        dateLabel = new JLabel("Bestelldatum: (yyyy-MM-dd)");
        kundenLabel = new JLabel("Kunde:");
        totalLabel = new JLabel("Total:");

        bestellpositionenListModel = new DefaultListModel<>();

        bestellpositionenList = new JList<>(bestellpositionenListModel);

        bestellpositionenButtonPanel = new JPanel();


        tabbedPane = new JTabbedPane();

        computerPanel = new JPanel(new GridLayout(6, 1));

        bestellpositionenPanel = new JPanel(new BorderLayout());

        tabbedPane.add("Bestellung", computerPanel);
        tabbedPane.add("Bestellposition", bestellpositionenPanel);



        buttonPanel = new JPanel();

        speichernButton = new JButton("Speichern");
        abbrechenButton = new JButton("Abbrechen");
        deleteButton = new JButton("löschen");
        bestellpositionAddButton = new JButton("neue Position");
        bestellpositionDeleteButton = new JButton("Position löschen");

        buttonPanel.add(speichernButton);
        if (!isEmpty){
            buttonPanel.add(deleteButton);
        }
        buttonPanel.add(abbrechenButton);

        bestellpositionenButtonPanel.add(bestellpositionAddButton);
        bestellpositionenButtonPanel.add(bestellpositionDeleteButton);

        bestellpositionenPanel.add(bestellpositionenList, BorderLayout.CENTER);
        bestellpositionenPanel.add(bestellpositionenButtonPanel, BorderLayout.SOUTH);

        add(tabbedPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        computerPanel.add(dateLabel);
        computerPanel.add(dateField);
        computerPanel.add(kundenLabel);
        computerPanel.add(kundenComboBox);
        computerPanel.add(totalLabel);
        computerPanel.add(totalInfoLabel);


        addActionListener();

        pack();
        setLocationRelativeTo(null);

        setVisible(true);

    }

    public void addActionListener() {
        bestellpositionAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                bestellpositionUi = new BestellpositionUi(bestellungsUi, true);

            }
        });
    }

    public ArrayList<Computer> getAllComputer() {
        ArrayList<Computer> computers = mainUi.getAllComputer();

        return computers;
    }

    private String[] getAllKundenAsArray() {

        ArrayList<Kunde> kunden = mainUi.getAllKunden();
        String[] kundenArray = new String[kunden.size()];

        for (int i = 0; i < kunden.size(); i++) {
            Kunde kunde = kunden.get(i);
            String vorname = kunde.getVorname();
            String nachname = kunde.getNachname();
            kundenArray[i] = vorname + " " + nachname;
        }

        return kundenArray;

    }

    public double getPrice(int selectedIndex) {

        ArrayList<Computer> computers = mainUi.getAllComputer();

        Computer selectedComputer = computers.get(selectedIndex);

        double price = selectedComputer.getEinzelpreis();
        return price;

    }

    public Computer getComputerByIndex(int index) {

        Computer computer = mainUi.getComputerByIndex(index);

        return computer;

    }

    public void addToBestellpositionenList(Bestellposition bestellposition, int selectedIndex) {

        Computer computer = bestellposition.getComputer();
        String computerName = (computer.getHersteller() + " " + computer.getModell() + " | Stückzahl: " + bestellposition.getStueckzahl());

        bestellpositionenListModel.addElement(computerName);

        bestellpositionenList.setModel(bestellpositionenListModel);

    }

}
