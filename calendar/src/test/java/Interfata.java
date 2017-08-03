/**
 * Created by mircea on 7/22/2017.
 */

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import javax.swing.plaf.BorderUIResource;


public class Interfata extends JFrame {
    private JTextField answer;
    private JTextField address;
    private JTextArea descriere;
    private JTextField data;
    private JButton button;
    private JButton button2;
    private JPanel contentPanel;
    private JPanel panel1;
    private JPanel panel2;
    private JLabel titluEt;
    private JLabel desriereEt;
    private JLabel dataEt;
    private JLabel adresaEt;
    private JList <String> l1;

    private DefaultListModel <String> modelEvent = new DefaultListModel <String>();
    Quickstart q;
    String cale = "C:/array.txt";
    String expresie = "";
    String desc = "";
    String dat = "";
    com.google.api.services.calendar.Calendar service = null;
    Events events = null;
    String line = "";
    List <Event> items = null;


    void creeareLegatura() {
        try {
            service = q.getCalendarService();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        try {
            events = service.events().list("primary")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        items = events.getItems();
    }

    void afisareEventInitiale() {
        if (items.size() == 0) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
                modelEvent.addElement(event.getSummary());


            }
        }

    }

    private String citireFisier(String s) {
        try {
            FileReader in = new FileReader(s);
            BufferedReader br = new BufferedReader(in);
            this.line = br.readLine();
            System.out.println(line);

        } catch (IOException e) {
            System.out.println("io exc citire ser");
            e.printStackTrace();
        }
        return this.line;
    }


    public Interfata() {
        super("Licenta");
        setSize(500, 500);
        setResizable(true);
        q = new Quickstart();
        answer = new JTextField(expresie, 25);
        answer.setVisible(true);
        answer.setEditable(true);


        descriere = new JTextArea(expresie, 10, 25);
        descriere.setVisible(true);
        descriere.setEditable(true);


        data = new JTextField(expresie, 25);
        data.setVisible(true);
        data.setEditable(true);

        address = new JTextField(expresie, 25);
        address.setVisible(true);
        address.setEditable(true);

        button = new JButton("Imagine");
        button2 = new JButton("Refresh");
        l1 = new JList <String>();
        l1.setModel(modelEvent);
        l1.setBounds(0, 0, 100, 300);

        titluEt = new JLabel("Titlu:");
        adresaEt = new JLabel("Adresa:");
        dataEt = new JLabel("Data:");
        desriereEt = new JLabel("Descriere:");

        contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout());
        contentPanel.setBackground(Color.gray);

        this.setContentPane(contentPanel);
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());

        panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        contentPanel.add(panel2);

        GridBagConstraints cc = new GridBagConstraints();
        cc.gridx = 0;
        cc.gridy = 0;
        panel2.add(l1, cc);
        cc.gridy++;
        panel2.add(button, cc);
        cc.gridy++;
        panel2.add(button2, cc);

        contentPanel.add(panel1);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;
        panel1.add(titluEt, c);
        c.gridy++;
        panel1.add(adresaEt, c);
        c.gridy++;
        panel1.add(dataEt, c);
        c.gridy++;
        panel1.add(desriereEt, c);

        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 1;
        c.gridy = 0;

        panel1.add(answer, c);
        c.gridy++;
        panel1.add(address, c);
        c.gridy++;
        panel1.add(data, c);
        c.gridy++;
        panel1.add(descriere, c);
        c.gridy++;


        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                expresie = answer.getText();
                dat = data.getText();
                line = address.getText();
                desc = descriere.getText();

                MyImage imagine = new MyImage();
                imagine.creeareImagine(expresie, dat, line, desc);
            }
        });
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                modelEvent.clear();
                creeareLegatura();
                afisareEventInitiale();
            }
        });

        l1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int i = l1.getSelectedIndex();
                    expresie = items.get(i).getSummary();
                    dat = items.get(i).getStart().getDateTime().toString();
                    desc = items.get(i).getDescription();
                    line = items.get(i).getLocation();
                    answer.setText(expresie);
                    descriere.setText(desc);
                    data.setText(dat);
                    address.setText(line);
                }
            }
        });

    }
}