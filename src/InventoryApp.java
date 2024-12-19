import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;

public class InventoryApp {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField, quantityField, priceField;
    private JLabel totalLabel, dateTimeLabel;
    private double totalHarga = 0;
    private DecimalFormat currencyFormat = new DecimalFormat("###,###.##");

    public InventoryApp() {
        frame = new JFrame("Manajemen Inventaris Barang (Indomaret)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        // GUI Layout
        JPanel panel = new JPanel(new BorderLayout());

        // Table Setup
        tableModel = new DefaultTableModel(new String[]{"Nama Barang", "Jumlah", "Harga Satuan (Rp)", "Total Harga (Rp)"}, 0);
        table = new JTable(tableModel);
        table.setBackground(new Color(230, 255, 230)); // Warna tabel
        table.setGridColor(Color.GRAY);
        table.setSelectionBackground(new Color(100, 200, 100));
        JScrollPane tableScroll = new JScrollPane(table);
        panel.add(tableScroll, BorderLayout.CENTER);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.setBackground(new Color(220, 240, 255)); // Warna panel input
        inputPanel.add(new JLabel("Nama Barang:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Jumlah:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        inputPanel.add(new JLabel("Harga Satuan (Rp):"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        JButton addButton = new JButton("Tambah Barang");
        addButton.setBackground(new Color(100, 200, 100));
        inputPanel.add(addButton);

        JButton deleteButton = new JButton("Hapus Barang");
        deleteButton.setBackground(new Color(255, 100, 100));
        inputPanel.add(deleteButton);

        JButton resetButton = new JButton("Reset Total & Barang");
        resetButton.setBackground(new Color(255, 200, 0));
        inputPanel.add(resetButton);

        panel.add(inputPanel, BorderLayout.SOUTH);

        // Total Harga Panel
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBackground(new Color(255, 240, 200)); // Warna panel total

        totalLabel = new JLabel("Total Harga: Rp 0");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalPanel.add(totalLabel, BorderLayout.WEST);

        dateTimeLabel = new JLabel();
        dateTimeLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        totalPanel.add(dateTimeLabel, BorderLayout.EAST);
        updateDateTime();

        panel.add(totalPanel, BorderLayout.NORTH);

        frame.add(panel);
        frame.setVisible(true);

        // Timer untuk update waktu setiap detik
        Timer timer = new Timer(1000, e -> updateDateTime());
        timer.start();

        // Action Listeners
        addButton.addActionListener(e -> addItem());
        deleteButton.addActionListener(e -> deleteItem());
        resetButton.addActionListener(e -> resetAll());
    }

    private void addItem() {
        try {
            String name = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText().replaceAll("\\.", "")); // Menghilangkan titik dari input harga

            if (name.isEmpty() || quantity <= 0 || price < 0) {
                throw new IllegalArgumentException("Data tidak valid.");
            }

            double totalItemPrice = quantity * price;
            tableModel.addRow(new Object[]{name, quantity, currencyFormat.format(price), currencyFormat.format(totalItemPrice)});
            totalHarga += totalItemPrice;
            updateTotalLabel();
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Jumlah dan Harga harus angka valid.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String totalHargaString = tableModel.getValueAt(selectedRow, 3).toString().replaceAll(",", "");
            double totalItemPrice = Double.parseDouble(totalHargaString);
            totalHarga -= totalItemPrice;
            tableModel.removeRow(selectedRow);
            updateTotalLabel();
        } else {
            JOptionPane.showMessageDialog(frame, "Pilih baris yang ingin dihapus.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
// code review : meengubah fungsi resetall ke public agar dapat ditesting
    public void resetAll() {
        tableModel.setRowCount(0);
        totalHarga = 0;
        updateTotalLabel();
        JOptionPane.showMessageDialog(frame, "Semua barang dan total harga telah direset.", "Reset Berhasil", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateTotalLabel() {
        totalLabel.setText("Total Harga: Rp " + currencyFormat.format(totalHarga));
    }

    private void updateDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        dateTimeLabel.setText("Tanggal & Waktu: " + formatter.format(new Date()));
    }

    private void clearFields() {
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventoryApp::new);
    }
    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getQuantityField() {
        return quantityField;
    }

    public JTextField getPriceField() {
        return priceField;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getTable() {
        return table;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public void callAddItem() {
        addItem();
    }

    public void callDeleteItem() {
        deleteItem();
    }

}