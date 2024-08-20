import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class HackingSimulator {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(HackingSimulator::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Hacking Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.GREEN);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton inputButton = new JButton("Simulate User Input");
        inputButton.addActionListener(e -> simulateUserInput(textArea));
        frame.add(inputButton, BorderLayout.SOUTH);

        frame.setVisible(true);

        // Start the simulation
        startSimulation(textArea);
    }

    private static void startSimulation(JTextArea textArea) {
        new Thread(() -> {
            Random random = new Random();
            String[] commands = {"ping 192.168.1.1", "tracert example.com", "netstat -a", "whois example.com", "nmap -p 80 192.168.1.1"};

            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(random.nextInt(500) + 200); // Random delay
                    textArea.append(generateRandomText() + "\n");
                    if (random.nextBoolean()) {
                        textArea.append(commands[random.nextInt(commands.length)] + "\n");
                        textArea.append(generateRandomOutput() + "\n");
                    }
                    textArea.setCaretPosition(textArea.getDocument().getLength()); // Scroll to bottom
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void simulateUserInput(JTextArea textArea) {
        SwingUtilities.invokeLater(() -> {
            String userInput = JOptionPane.showInputDialog(null, "Enter command:");
            if (userInput != null && !userInput.trim().isEmpty()) {
                textArea.append("> " + userInput + "\n");
                textArea.append(generateRandomOutput() + "\n");
                textArea.setCaretPosition(textArea.getDocument().getLength()); // Scroll to bottom
            }
        });
    }

    private static String generateRandomText() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = random.nextInt(50) + 10; // Random length of the text
        for (int i = 0; i < length; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            sb.append(c);
        }
        return sb.toString();
    }

    private static String generateRandomOutput() {
        String[] outputs = {
            "Connecting to 192.168.1.1...",
            "Tracing route to example.com [93.184.216.34]...",
            "TCP Port 80: OPEN",
            "Domain Name: example.com",
            "Nmap scan report for 192.168.1.1",
            "Received response from 192.168.1.1",
            "Connection established.",
            "Analyzing packet data...",
            "Route complete. No issues detected.",
            "Command executed successfully."
        };
        Random random = new Random();
        return outputs[random.nextInt(outputs.length)];
    }
}
