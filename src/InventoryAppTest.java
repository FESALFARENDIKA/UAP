import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InventoryAppTest {
    private InventoryApp app;

    @Before
    public void setUp() {
        // Inisialisasi instance InventoryApp sebelum setiap pengujian
        app = new InventoryApp();
    }

    @Test
    public void testAddItemValid() {
        // Simulasi input data barang
        app.getNameField().setText("Barang A");
        app.getQuantityField().setText("2");
        app.getPriceField().setText("5000");

        // Panggil metode untuk menambahkan item
        app.callAddItem();

        // Periksa jumlah baris di tabel (harus ada 1 baris setelah penambahan)
        assertEquals(1, app.getTableModel().getRowCount());

        // Periksa data pada tabel
        assertEquals("Barang A", app.getTableModel().getValueAt(0, 0)); // Nama barang
        assertEquals("2", app.getTableModel().getValueAt(0, 1).toString()); // Jumlah
        assertEquals("5,000", app.getTableModel().getValueAt(0, 2).toString()); // Harga satuan
        assertEquals("10,000", app.getTableModel().getValueAt(0, 3).toString()); // Total harga

        // Periksa total harga keseluruhan
        assertEquals(10000.0, app.getTotalHarga(), 0.01);
    }

    @Test
    public void testDeleteItem() {
        // Tambahkan item terlebih dahulu
        app.getNameField().setText("Barang A");
        app.getQuantityField().setText("2");
        app.getPriceField().setText("5000");
        app.callAddItem();

        // Pastikan ada satu item di tabel
        assertEquals(1, app.getTableModel().getRowCount());

        // Pilih baris pertama dan hapus
        app.getTable().setRowSelectionInterval(0, 0); // Pilih baris pertama
        app.callDeleteItem();

        // Periksa jumlah baris di tabel setelah penghapusan
        assertEquals(0, app.getTableModel().getRowCount());

        // Periksa total harga keseluruhan (harus 0 setelah penghapusan)
        assertEquals(0.0, app.getTotalHarga(), 0.01);
    }

    @Test
    public void testResetAll() {
        // Tambahkan beberapa item terlebih dahulu
        app.getNameField().setText("Barang A");
        app.getQuantityField().setText("2");
        app.getPriceField().setText("5000");
        app.callAddItem();

        app.getNameField().setText("Barang B");
        app.getQuantityField().setText("3");
        app.getPriceField().setText("7000");
        app.callAddItem();

        // Periksa bahwa tabel memiliki 2 item
        assertEquals(2, app.getTableModel().getRowCount());

        // Reset semua item
        // code review ubahlah fungsi resetall ke public agar dapat digunakan
        app.resetAll();

        // Periksa jumlah baris di tabel setelah reset
        assertEquals(0, app.getTableModel().getRowCount());

        // Periksa total harga keseluruhan (harus 0 setelah reset)
        assertEquals(0.0, app.getTotalHarga(), 0.01);
    }
}
